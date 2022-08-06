package com.homedex.categories.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record Item(
        UUID id,
        String name,
        String measurement,
        String brand,
        String addInfo,
        LocalDateTime expiration,
        Long unit
) {
}
