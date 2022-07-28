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
@Entity(name = "categories")
public class CategoryEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_households_id")
    private HouseholdEntity householdEntity;

    @OneToMany
    @JoinColumn(name = "fk_categories_id")
    private Set<ItemEntity> itemEntities;
}
