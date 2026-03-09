package com.School.Smart.Backend.Controller;

import org.springframework.web.bind.annotation.*;
import com.School.Smart.Backend.DTO.MassGenerateInviteRequest;
import com.School.Smart.Backend.Service.InviteCodeService;
import com.School.Smart.Backend.entity.InviteCode;
import com.School.Smart.Backend.model.Role;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InviteCodeController {

        private final InviteCodeService inviteCodeService;

        // Principal generating ClassTeacher invite
        @PostMapping("/principal/generate")
        public ResponseEntity<InviteCode> generateForClassTeacher(
                        @RequestParam String className,
                        @RequestParam String section,
                        @RequestParam Role roleAllowed,
                        @RequestParam Long principalId,
                        @RequestParam(required = false) List<String> subjects,
                        @RequestParam String intendedPersonName) {

                InviteCode code = inviteCodeService.generateCode(
                                roleAllowed,
                                className,
                                section,
                                subjects,
                                principalId,
                                intendedPersonName);

                return ResponseEntity.ok(code);
        }

        // ClassTeacher generating Teacher or Student invite
        @PostMapping("/classTeacher/generate")
        public ResponseEntity<InviteCode> generateForStudentOrTeacher(
                        @RequestParam String className,
                        @RequestParam String section,
                        @RequestParam(required = false) List<String> subjects,
                        @RequestParam Role roleAllowed,
                        @RequestParam Long generatedById,
                        @RequestParam String intendedPersonName) {

                InviteCode code = inviteCodeService.generateCode(
                                roleAllowed,
                                className,
                                section,
                                subjects,
                                generatedById,
                                intendedPersonName);

                return ResponseEntity.ok(code);
        }

    @GetMapping("/list")
    public ResponseEntity<?> listInviteCodes(
                 @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam Long generatedById,
        @RequestParam(required = false) Boolean used,
        @RequestParam(required = false) Role roleAllowed,
        @RequestParam(required = false) String subject) {

        return ResponseEntity.ok(
                inviteCodeService.getCodeByGenerator(generatedById,used, roleAllowed, subject, page, size)
        );
    }

        @PostMapping("/mass-generate")
        public ResponseEntity<List<InviteCode>> massGenerate(
                        @Valid @RequestBody MassGenerateInviteRequest request) {

                return ResponseEntity.ok(
                                inviteCodeService.massGenerateCodes(
                                                request.getRoleAllowed(),
                                                request.getClassName(),
                                                request.getSection(),
                                                request.getSubjectPerTeacher(),
                                                request.getGeneratedById(),
                                                request.getNames()));
        }

        @GetMapping("/preview/{code}")
        public ResponseEntity<InviteCode> preview(@PathVariable String code) {

                return ResponseEntity.ok(
                                inviteCodeService.getValidInviteWithoutUsing(code));
        }
}