package com.planitsquare.holidaykeeper.repository;

import com.planitsquare.holidaykeeper.domain.HolidayCounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayCountyRepository extends JpaRepository<HolidayCounty, HolidayCounty.HolidayCountyId> {

}