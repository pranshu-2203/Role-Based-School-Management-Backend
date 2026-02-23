package com.School.Smart.Backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.School.Smart.Backend.Service.InviteCodeService;
import com.School.Smart.Backend.entity.InviteCode;
import com.School.Smart.Backend.model.Role;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InviteCodeController {
    private final InviteCodeService inviteCodeService;

    @PostMapping("/principal/generate")
    public ResponseEntity<InviteCode>generateForClassTeacher(
        @RequestParam String className,
        @RequestParam String Section,
        @RequestParam Role roleAllowed,
        @RequestParam Long principalId,
        @RequestParam String Subject
    ){
        InviteCode Code=inviteCodeService.generateCode(roleAllowed, className, Section, Subject, principalId,Role.PRINCIPAL);
        return ResponseEntity.ok(Code);
    }
    @PostMapping("/classTeacher/generate")
    public ResponseEntity<InviteCode>generateFoeStudentOrTeachers(
        @RequestParam String className,
        @RequestParam String Section,
        @RequestParam(required = false) String Subject,//only required for teachers
        @RequestParam Role roleAllowed,
        @RequestParam Long generateById
    ) {
        InviteCode code =inviteCodeService.generateCode(roleAllowed, className, Section, Subject, generateById,Role.CLASS_TEACHER);
                
        return ResponseEntity.ok(code);
    }

    //List all the code gernerated by the user

    @GetMapping("/list/InviteCodes")
    public ResponseEntity<List<InviteCode>>listInviteCodes(@RequestParam Long generateById) {
        List<InviteCode>Codes=inviteCodeService.getCodeByGenerator(generateById);
        return ResponseEntity.ok(Codes);
    }
    
    
    
}
