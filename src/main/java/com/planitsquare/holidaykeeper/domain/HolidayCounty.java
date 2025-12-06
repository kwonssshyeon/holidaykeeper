package com.planitsquare.holidaykeeper.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "holiday_counties")
public class HolidayCounty {
    @EmbeddedId
    private HolidayCountyId id;

    @Getter
    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class HolidayCountyId implements Serializable {
        @Column(name = "county_code", nullable = false, length = 10)
        private String countyCode;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "holiday_id", nullable = false)
        private Holiday holiday;
    }
}
