package com.School.Smart.Backend.Service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void registerBasicUser(String fullname,String email, String password, Role role){

        User user=new User();
        user.setFullname(fullname);
        user.setEmail(email);
        //user.setPassword(password);
        user.setRole(role);
        user.setVerified(false);
        user.setVerified(false);
        user.setApproved(false);

        user.setPassword(passwordEncoder.encode(password));
        user.setProfileCompleted(false);

        userRepository.save(user);

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
