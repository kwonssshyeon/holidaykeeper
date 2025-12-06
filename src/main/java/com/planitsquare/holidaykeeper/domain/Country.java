package com.planitsquare.holidaykeeper.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "contries")
public class Country {
    @Id
    @Column(nullable = false, length = 2)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;
}
