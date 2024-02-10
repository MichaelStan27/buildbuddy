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
}
