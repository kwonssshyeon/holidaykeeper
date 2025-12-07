package com.planitsquare.holidaykeeper.repository;

import com.planitsquare.holidaykeeper.domain.HolidayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayTypeRepository extends JpaRepository<HolidayType, HolidayType.HolidayTypeId> {

}