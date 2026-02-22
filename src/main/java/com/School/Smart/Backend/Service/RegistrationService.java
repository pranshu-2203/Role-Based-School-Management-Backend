package com.School.Smart.Backend.Service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final OtpVerficationRepository otpVerficationRepository;
    private final PasswordEncoder passwordEncoder;
    private final InviteCodeService inviteCodeService;

    public void registerBasicUser(String Fullname,String email, String password, Role role,String inviteCode){


        InviteCode invite=inviteCodeService.validateCode(inviteCode, role);
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("Email already registered");
        }
        User user=new User();
        user.setFullname(Fullname);
        user.setEmail(email);
        //user.setPassword(password);
        user.setRole(role);
        user.setVerified(false);
        user.setApproved(false);

        user.setPassword(passwordEncoder.encode(password));
        user.setProfileCompleted(false);
        user.setClassName(invite.getClassName());
        user.setSection(invite.getSection());
        user.setSubject(invite.getSubject());

        userRepository.save(user);

        //Mark invite as used

        inviteCodeService.markAsUSed(invite, user.getId());
        //Otp genration
        String otp=String.valueOf(100000+new Random().nextInt(900000));
        OtpVerfication otpEntity=new OtpVerfication();
        otpEntity.setEmail(email);
        otpEntity.setOtpCode(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusSeconds(300));

        otpVerficationRepository.save(otpEntity);

        System.out.println("OTP generatd: "+otp);



    }

    public void verifyOtp(String email,String otpIntput){
        OtpVerfication otpEntity=otpVerficationRepository.findTopByEmailOrderByCreatedAtDesc(email).orElseThrow(()->new RuntimeException("Otp not found"));
        if(otpEntity.isUsed()) throw new RuntimeException("Otp Alrady used");
        if(otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) throw new RuntimeException("Otp is Expired");
        if(!otpEntity.getOtpCode().equals(otpIntput)) throw new RuntimeException("Invalid OTP");

        otpEntity.setUsed(true);
        otpVerficationRepository.save(otpEntity);

        User user= userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        user.setVerified(true);
        userRepository.save(user);
    }


}
