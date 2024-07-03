package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

@Service
public class IngredienteService {

	@Autowired
	IngredienteRepository ingredienteRepository;
	
	public Ingrediente findById(Long id) {
		return this.ingredienteRepository.findById(id).get();
	}
	
	public void save(Ingrediente ingrediente) {
		this.ingredienteRepository.save(ingrediente);		
	}
	
	@Transactional
	public void deleteById(Long id) {
		 this.ingredienteRepository.deleteById(id);
	}
}
