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

    @Getter
    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class HolidayTypeId implements Serializable {
        @Column(name = "type_name", nullable = false, length = 20)
        private String typeName;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "holiday_id", nullable = false)
        private Holiday holiday;
    }
}
