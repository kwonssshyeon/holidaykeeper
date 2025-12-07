package com.planitsquare.holidaykeeper.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "holiday_types")
public class HolidayType {
    @EmbeddedId
    private HolidayTypeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("holidayId") // HolidayCountyId 안의 holidayId와 매핑
    @JoinColumn(name = "holiday_id", nullable = false)
    private Holiday holiday;

    @Getter
    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class HolidayTypeId implements Serializable {
        @Column(name = "type_name", nullable = false, length = 20)
        private String typeName;

        @JoinColumn(name = "holiday_id", nullable = false)
        private Long holidayId;
    }

    HolidayType(Holiday holiday, String typeName) {
        this.holiday = holiday;
        this.id = new HolidayTypeId();
        this.id.holidayId = holiday.getId();
        this.id.typeName = typeName;
    }
}
