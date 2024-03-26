package com.buildbuddy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    USER("user"),
    CONSULTANT("consultant"),
    ADMIN("admin");

    private String value;

    public static boolean isAValidRole(String role){
        for(UserRole validRole: values()){
            if(validRole.value.equals(role))
                return true;
        }
        return false;
    }
}
