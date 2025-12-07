package com.planitsquare.holidaykeeper.repository;

import com.planitsquare.holidaykeeper.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findAll();
}