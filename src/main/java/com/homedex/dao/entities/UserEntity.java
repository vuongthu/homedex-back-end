package com.homedex.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private String email;
    @ManyToMany(mappedBy = "userEntities", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<HouseholdEntity> householdEntities;
}
