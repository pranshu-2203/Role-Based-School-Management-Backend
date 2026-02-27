package com.School.Smart.Backend.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.School.Smart.Backend.DTO.ProfileCompletionRequest;
import com.School.Smart.Backend.entity.InviteCode;
import com.School.Smart.Backend.model.OtpVerfication;
import com.School.Smart.Backend.model.Role;
import com.School.Smart.Backend.model.User;
import com.School.Smart.Backend.repository.OtpVerficationRepository;
import com.School.Smart.Backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final OtpVerficationRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final InviteCodeService inviteCodeService;

    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public void registerBasicUser(
            String fullname,
            String email,
            String password,
            Role role,
            String inviteCode) {

        InviteCode invite = inviteCodeService.validateCode(inviteCode, role);

        if (!invite.getIntendedPersonName().trim()
                .equalsIgnoreCase(fullname.trim())) {
            throw new RuntimeException("This invite code is assigned to another person");
        }

        if (userRepository.existsByEmail(email))
            throw new RuntimeException("Email already registered");

        User user = new User();
        user.setFullname(fullname.trim());
        user.setEmail(email.trim());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(password));
        user.setVerified(false);
        user.setApproved(false);
        user.setProfileCompleted(false);

        user.setClassName(invite.getClassName());
        user.setSection(invite.getSection());

        // Subject for disctinct Roles
        if (role == Role.CLASS_TEACHER || role == Role.TEACHER) {
            user.setSubject(new ArrayList<>(invite.getSubject()));
        } else {
            user.setSubject(new ArrayList<>());
        }

        userRepository.save(user);

        inviteCodeService.markAsUsed(invite, user.getId());

        generateOtp(email);
    }
    // Generate otp of 6 digit

    private void generateOtp(String email) {
        String otp = String.valueOf(100000 + secureRandom.nextInt(900000));

        OtpVerfication otpEntity = new OtpVerfication();
        otpEntity.setEmail(email);
        otpEntity.setOtpCode(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpEntity.setUsed(false);

        otpRepository.save(otpEntity);

        System.out.println("OTP generated: " + otp);
    }

    // Verify otp
    @Transactional
    public void verifyOtp(String email, String otpInput) {

        OtpVerfication otpEntity = otpRepository
                .findTopByEmailOrderByCreatedAtDesc(email)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otpEntity.isUsed())
            throw new RuntimeException("OTP already used");

        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP expired");

        if (!otpEntity.getOtpCode().equals(otpInput))
            throw new RuntimeException("Invalid OTP");

        otpEntity.setUsed(true);
        otpRepository.save(otpEntity);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setVerified(true);
        userRepository.save(user);
    }
    @Transactional
    public void completeProfile(Long userId, ProfileCompletionRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isProfileCompleted()) {
            throw new RuntimeException("Profile already completed");
        }

        switch (user.getRole()) {

            case STUDENT -> {
                if (request.getFatherName() == null ||
                        request.getGuardianNo() == null ||
                        request.getEmergencyNo() == null) {
                    throw new RuntimeException("Student profile incomplete");
                }

                user.setFatherName(request.getFatherName());
                user.setMotherName(request.getMotherName());
                user.setGuardianName(request.getGuardianName());
                user.setGuardianNo(request.getGuardianNo());
                user.setEmergencyNo(request.getEmergencyNo());
            }

            case TEACHER, CLASS_TEACHER -> {
                if (user.getSubject() == null || user.getSubject().isEmpty()) {
                    if (request.getSubjects() == null || request.getSubjects().isEmpty()) {
                        throw new RuntimeException("Subjects required for teacher");
                    }
                    user.setSubject(request.getSubjects());
                }
            }

            default -> throw new RuntimeException("Profile not required for this role");
        }

        user.setProfileCompleted(true);
        userRepository.save(user);
    }
}