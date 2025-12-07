package com.planitsquare.holidaykeeper.repository;

import com.planitsquare.holidaykeeper.domain.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    @Query("""
        SELECT h
        FROM Holiday h
        WHERE (:countryCode IS NULL OR h.country.code = :countryCode)
          AND (:start IS NULL OR h.date BETWEEN :start AND :end)
    """)
    Page<Holiday> searchHolidaysByCountryCodeAndYear(
            @Param("countryCode") String countryCode,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            Pageable pageable
    );
}