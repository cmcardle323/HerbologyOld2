package com.herbology.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.herbology.models.Plant;

public interface PlantRepository extends JpaRepository<Plant, Long> {
	//Extend SQL queries here
}
