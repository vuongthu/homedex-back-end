package com.homedex.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
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
    private String measurement;
    private LocalDateTime expiration;

    @ManyToOne
    @JoinColumn(name = "fk_categories_id")
    private CategoryEntity categoryEntity;
}
