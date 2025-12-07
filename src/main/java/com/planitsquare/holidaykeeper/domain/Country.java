package com.planitsquare.holidaykeeper.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "countries")
public class Country {
    @Id
    @Column(nullable = false, length = 2)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    private Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Country of(String code, String name) {
        return new Country(code, name);
    }
}
