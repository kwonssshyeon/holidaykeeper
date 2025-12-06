package com.planitsquare.holidaykeeper.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(
    name = "holidays",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_holidays_key", columnNames = {"country_code", "date", "name"})
    }
)
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String name;

    private String localName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code", nullable = false)
    private Country country;

    private boolean fixed;

    private boolean global;

    private Integer launchYear;
}
