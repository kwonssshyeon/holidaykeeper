package com.planitsquare.holidaykeeper.repository;

import com.planitsquare.holidaykeeper.domain.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

}