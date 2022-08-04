package com.homedex.dao.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "items")
public class ItemEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String brand;
    @Column(name = "add_info")
    private String addInfo;
    @Enumerated(EnumType.ORDINAL)
    private Measurement measurement;
    private LocalDateTime expiration;

    @ManyToOne
    @JoinColumn(name = "fk_categories_id")
    private CategoryEntity categoryEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemEntity that = (ItemEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
