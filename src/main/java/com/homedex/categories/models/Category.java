package com.homedex.categories.models;

import java.util.UUID;

public record Category(UUID id, String name, UUID householdId) {
}
