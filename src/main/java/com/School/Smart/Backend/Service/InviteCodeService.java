package com.School.Smart.Backend.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.School.Smart.Backend.entity.InviteCode;
import com.School.Smart.Backend.model.Role;
import com.School.Smart.Backend.repository.InviteCodeRepository;
import com.School.Smart.Backend.util.CodeGenerator;

@Service
public class InviteCodeService {
    private final InviteCodeRepository inviteCodeRepository;

    public InviteCodeService(InviteCodeRepository inviteCodeRepository) {
        this.inviteCodeRepository = inviteCodeRepository;
    }

    public InviteCode generateCode(
            Role roleAllwoed,
            String className,
            String section,
            String subject,
            Long generateById,
            Role GenerateByRole

    ) {
        InviteCode inviteCode = new InviteCode();
        inviteCode.setCode(CodeGenerator.generateCode());
        inviteCode.setRoleAllowed(roleAllwoed);
        inviteCode.setClassName(className);
        inviteCode.setSection(section);
        inviteCode.setSubject(subject);
        inviteCode.setGeneratedById(generateById);
        inviteCode.setGeneratedByRole(GenerateByRole);
        inviteCode.setExpiryTime(LocalDateTime.now().plusHours(12));

        return inviteCodeRepository.save(inviteCode);

    }

    public InviteCode generateCode(
            Role roleAllowed,
            String className,
            String section,
            String subject,
            Long generatedById) {
        return generateCode(roleAllowed, className, section, subject, generatedById, null);
    }

    public InviteCode validateCode(String code, Role expectedRole) {
        InviteCode invite = inviteCodeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid Code"));

        if (invite.isUsed()) {
            throw new RuntimeException("Code already used");
        }

        if (invite.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Code expired");
        }

        if (!invite.getRoleAllowed().equals(expectedRole)) {
            throw new RuntimeException("Code not valid for this role");
        }

        return invite;

    }

    public void markAsUSed(InviteCode invite, Long userId) {
        invite.setUsed(true);
        invite.setUsedById(userId);
        inviteCodeRepository.save(invite);
    }
    public List<InviteCode> getCodeByGenerator(Long generatedById){
        return inviteCodeRepository.findByGeneratedById(generatedById);
    }
}
