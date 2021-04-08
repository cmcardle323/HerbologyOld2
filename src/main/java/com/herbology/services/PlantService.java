package com.herbology.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herbology.models.Plant;
import com.herbology.repositories.PlantRepository;

@Service
@Transactional
public class PlantService {

	@Autowired
	private PlantRepository plantRepo;
	
	public List<Plant> listAll() {
		return plantRepo.findAll();
	}
	
	public void save(Plant plant) {
		plantRepo.save(plant);
	}
	
	public Plant get(long id) {
		return plantRepo.findById(id).get();
	}
	
	public void delete(long id) {
		plantRepo.deleteById(id);
	}
}
