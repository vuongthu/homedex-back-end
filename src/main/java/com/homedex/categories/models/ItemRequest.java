package com.homedex.categories.models;

import java.time.LocalDateTime;

public record ItemRequest(String name, String measurement, String brand, String addInfo, LocalDateTime expiration) {
}
