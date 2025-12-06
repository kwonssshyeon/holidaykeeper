package com.planitsquare.holidaykeeper.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "holidays",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_holidays_key", columnNames = {"country_code", "date", "name"})
    }
)
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Holiday(LocalDate date, String name, String localName, Country country,
                    boolean fixed, boolean global, Integer launchYear) {
        this.date = date;
        this.name = name;
        this.localName = localName;
        this.country = country;
        this.fixed = fixed;
        this.global = global;
        this.launchYear = launchYear;
    }

    public static Holiday of(LocalDate date, String name, String localName, Country country,
                             boolean fixed, boolean global, Integer launchYear) {
        return new Holiday(date, name, localName, country, fixed, global, launchYear);
    }
}
