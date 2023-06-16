package com.example.cinema.entity.hall;

import com.example.cinema.entity.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Hall extends AbstractEntity {
    private String name;

}
