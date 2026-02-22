package com.School.Smart.Backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.Smart.Backend.DTO.GenerateInviteRequest;
import com.School.Smart.Backend.Service.InviteCodeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InviteCodeController {
    private final InviteCodeService inviteCodeService;

    @PostMapping("/generate")
    public ResponseEntity<String>generateInvite(@RequestBody GenerateInviteRequest generateInviteRequest) {
        try{
            inviteCodeService.generateCode( generateInviteRequest.getRoleAllowed(),
                generateInviteRequest.getClassName(),
                generateInviteRequest.getSection(),
                generateInviteRequest.getSubject(),
                generateInviteRequest.getGeneratedByid() );
                return ResponseEntity.ok("Invite code generated successfully");
        }catch(RuntimeException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        
    }
    
}
