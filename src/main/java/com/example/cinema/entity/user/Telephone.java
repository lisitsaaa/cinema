package com.example.cinema.entity.user;

import com.example.cinema.entity.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Telephone extends AbstractEntity {
    private String code;
    private String number;
}
