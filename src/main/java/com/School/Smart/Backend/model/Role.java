package com.School.Smart.Backend.model;

public enum Role {
    ADMIN, //System Administrator (principle, or higher authority)
    TEACHER, //here Teacher means Subject teacher
    CLASS_TEACHER, //head teacher of specific class
    PRINCIPAL, //head of institute/school
    STUDENT,
    //these are the roles in the project where the admin of a class would be Class_teacher,and admin
}
