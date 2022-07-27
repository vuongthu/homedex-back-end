package com.homedex.users.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    private final UUID id;
    private final String username;
    private final String email;
}
