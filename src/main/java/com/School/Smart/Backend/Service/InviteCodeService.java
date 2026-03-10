package com.School.Smart.Backend.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.School.Smart.Backend.entity.InviteCode;
import com.School.Smart.Backend.exception.AuthorizationException;
import com.School.Smart.Backend.exception.InviteException;
import com.School.Smart.Backend.model.Role;
import com.School.Smart.Backend.repository.InviteCodeRepository;
import com.School.Smart.Backend.repository.UserRepository;
import com.School.Smart.Backend.util.CodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteCodeService {

    private final InviteCodeRepository inviteCodeRepository;
    private final UserRepository userRepository;

    @Transactional
    public InviteCode generateCode(
            Role roleAllowed,
            String className,
            String section,
            List<String> subjects,
            Long generatedById,
            String intendedPersonName) {

        var generator = userRepository.findById(generatedById)
                .orElseThrow(() -> new AuthorizationException("Generator not found"));

        enforceHierarchy(generator.getRole(), roleAllowed);

        InviteCode invite = new InviteCode();
        invite.setCode(CodeGenerator.generateCode());
        invite.setRoleAllowed(roleAllowed);
        invite.setClassName(className);
        invite.setSection(section);
        if (roleAllowed == Role.CLASS_TEACHER || roleAllowed == Role.TEACHER) {

            if (subjects == null || subjects.isEmpty()) {
                throw new RuntimeException("Subjects are required for this role");
            }

            invite.setSubject(new ArrayList<>(subjects));

        } else {
            invite.setSubject(new ArrayList<>());
        }
        invite.setGeneratedById(generatedById);
        invite.setGeneratedByRole(generator.getRole());
        invite.setIntendedPersonName(intendedPersonName.trim());
        invite.setExpiryTime(LocalDateTime.now().plusHours(12));
        invite.setUsed(false);

        return inviteCodeRepository.save(invite);
    }

    private void enforceHierarchy(Role generatorRole, Role roleAllowed) {

        if (generatorRole == Role.PRINCIPAL && roleAllowed != Role.CLASS_TEACHER) {
            throw new AuthorizationException("Principal can only generate ClassTeacher invites");
        }

        if (generatorRole == Role.CLASS_TEACHER &&
                !(roleAllowed == Role.TEACHER || roleAllowed == Role.STUDENT)) {
            throw new AuthorizationException("ClassTeacher can only generate Teacher or Student invites");
        }

        if (generatorRole == Role.TEACHER || generatorRole == Role.STUDENT) {
            throw new AuthorizationException("You are not authorized to generate invites");
        }
    }

    public InviteCode validateCode(String code, Role expectedRole) {

        InviteCode invite = inviteCodeRepository.findByCode(code)
                .orElseThrow(() -> new InviteException("Invalid code"));

        if (invite.isUsed())
            throw new InviteException("Code already used");

        if (invite.getExpiryTime().isBefore(LocalDateTime.now()))
            throw new InviteException("Code expired");

        if (!invite.getRoleAllowed().equals(expectedRole))
            throw new InviteException("Code not valid for this role");

        return invite;
    }

    @Transactional
    public void markAsUsed(InviteCode invite, Long userId) {
        invite.setUsed(true);
        invite.setUsedById(userId);
        inviteCodeRepository.save(invite);
    }

    @Transactional
    public List<InviteCode> massGenerateCodes(
            Role roleAllowed,
            String className,
            String section,
            List<String> subjectsPerTeacher,
            Long generatedById,
            List<String> names) {

        var generator = userRepository.findById(generatedById)
                .orElseThrow(() -> new AuthorizationException("Generator not found"));

        enforceHierarchy(generator.getRole(), roleAllowed);
        List<InviteCode> invites = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {

            InviteCode invite = new InviteCode();
            invite.setCode(CodeGenerator.generateCode());
            invite.setRoleAllowed(roleAllowed);
            invite.setClassName(className);
            invite.setSection(section);
            if (roleAllowed == Role.TEACHER) {
                if (subjectsPerTeacher.size() != names.size()) {
                    throw new IllegalArgumentException("Names and subjects size must match for teachers");
                }
                invite.setSubject(Collections.singletonList(subjectsPerTeacher.get(i)));
            } else {
                invite.setSubject(Collections.emptyList());
            }
            invite.setGeneratedById(generatedById);
            invite.setGeneratedByRole(generator.getRole());
            invite.setIntendedPersonName(names.get(i).trim());
            invite.setExpiryTime(LocalDateTime.now().plusHours(12));
            invite.setUsed(false);

            invites.add(invite);
        }

        return inviteCodeRepository.saveAll(invites);
    }

    public InviteCode getValidInviteWithoutUsing(String code) {
        InviteCode invite = inviteCodeRepository.findByCode(code)
                .orElseThrow(() -> new InviteException("Invalid code"));

        if (invite.isUsed())
            throw new InviteException("Code already used");

        if (invite.getExpiryTime().isBefore(LocalDateTime.now()))
            throw new InviteException("Code expired");

        return invite;
    }

    public Page<InviteCode> getCodeByGenerator(
            Long generatedById,
            Boolean used,
            Role roleAllowed,
            String subject,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        if (used != null && roleAllowed != null && subject != null) {
            return inviteCodeRepository.findByGeneratedByIdAndIsUsedAndRoleAllowedAndSubjectContaining(
                    generatedById, used, roleAllowed, subject, pageable);
        }

        if (used != null && roleAllowed != null) {
            return inviteCodeRepository.findByGeneratedByIdAndIsUsedAndRoleAllowed(
                    generatedById, used, roleAllowed, pageable);
        }

        if (used != null && subject != null) {
            return inviteCodeRepository.findByGeneratedByIdAndIsUsedAndSubjectContaining(
                    generatedById, used, subject, pageable);
        }

        if (roleAllowed != null && subject != null) {
            return inviteCodeRepository.findByGeneratedByIdAndRoleAllowedAndSubjectContaining(
                    generatedById, roleAllowed, subject, pageable);
        }

        if (used != null) {
            return inviteCodeRepository.findByGeneratedByIdAndIsUsed(generatedById, used, pageable);
        }

        if (roleAllowed != null) {
            return inviteCodeRepository.findByGeneratedByIdAndRoleAllowed(generatedById, roleAllowed, pageable);
        }

        if (subject != null) {
            return inviteCodeRepository.findByGeneratedByIdAndSubjectContaining(generatedById, subject, pageable);
        }

        return inviteCodeRepository.findByGeneratedById(generatedById, pageable);
    }
}