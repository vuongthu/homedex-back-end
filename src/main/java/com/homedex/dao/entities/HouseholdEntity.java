package com.homedex.dao.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "households")
public class HouseholdEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
}
