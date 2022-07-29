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
@Table(name = "households")
public class HouseholdEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @OneToMany
    @JoinColumn(name = "fk_households_id")
    private Set<CategoryEntity> categoryEntities;
    @ManyToMany
    @JoinTable(
            name = "user_household_mappings",
            joinColumns = {@JoinColumn(name = "fk_households_id")},
            inverseJoinColumns = {@JoinColumn(name = "fk_users_id")}
    )
    private Set<UserEntity> userEntities;
}
