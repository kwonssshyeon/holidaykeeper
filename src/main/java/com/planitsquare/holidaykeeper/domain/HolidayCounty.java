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

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("holidayId") // HolidayCountyId 안의 holidayId와 매핑
    @JoinColumn(name = "holiday_id", nullable = false)
    private Holiday holiday;

    @Getter
    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class HolidayCountyId implements Serializable {
        @Column(name = "county_code", nullable = false, length = 10)
        private String countyCode;

        @JoinColumn(name = "holiday_id", nullable = false)
        private Long holidayId;
    }

    HolidayCounty(Holiday holiday, String countyCode) {
        this.holiday = holiday;
        this.id = new HolidayCountyId();
        this.id.holidayId = holiday.getId();
        this.id.countyCode = countyCode;
    }
}
