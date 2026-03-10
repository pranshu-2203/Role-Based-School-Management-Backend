package com.School.Smart.Backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.School.Smart.Backend.model.Role;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "Invite_Codes")
@Getter
@Setter
public class InviteCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // invite code

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role roleAllowed; // which role are allwoed to use the code

    private String className;
    private String section;
    @ElementCollection
    @CollectionTable(name = "invite_subjects", joinColumns = @JoinColumn(name = "invite_id") // must link to
                                                                                             // invite_codes.id
    )
    @Column(name = "subject")
    private List<String> subject;
    private Long generatedById;
    @Enumerated(EnumType.STRING)
    private Role generatedByRole;

    private Long usedById;
    private boolean isUsed = false;
    private LocalDateTime expiryTime;
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private String intendedPersonName;
}
