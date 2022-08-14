package com.homedex.dao.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
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
    private String image;
    @OneToMany
    @JoinColumn(name = "fk_households_id")
    @ToString.Exclude
    private Set<CategoryEntity> categoryEntities;
    @ManyToMany
    @JoinTable(
            name = "user_household_mappings",
            joinColumns = {@JoinColumn(name = "fk_households_id")},
            inverseJoinColumns = {@JoinColumn(name = "fk_users_id")}
    )
    @ToString.Exclude
    private Set<UserEntity> userEntities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HouseholdEntity that = (HouseholdEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
