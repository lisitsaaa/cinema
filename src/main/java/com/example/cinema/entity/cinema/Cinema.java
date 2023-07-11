package com.example.cinema.entity.cinema;

import com.example.cinema.entity.AbstractEntity;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Cinema extends AbstractEntity {
    @NotNull @NotEmpty @NotBlank
    private String name;
    private String city;

    @OneToMany(mappedBy = "cinema",cascade = CascadeType.ALL)
    private List<Hall> halls;
}
