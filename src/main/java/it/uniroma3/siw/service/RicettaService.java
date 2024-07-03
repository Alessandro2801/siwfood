package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.RicettaRepository;

@Service
public class RicettaService {
	@Autowired
	RicettaRepository ricettaRepository;
	
	@Transactional
	public Ricetta findById(Long id) {
		return this.ricettaRepository.findById(id).get();
	}
	
	public Iterable<Ricetta> findAll() {
		return this.ricettaRepository.findAll();
	}

	public Ricetta save(Ricetta ricetta) {
		return this.ricettaRepository.save(ricetta);		
	}
	
	@Transactional
	public Iterable<Ricetta> findByNome(String nome) {
		return this.ricettaRepository.findByNome(nome);
	}
	
	@Transactional
	public void deleteById(Long id) {
		 this.ricettaRepository.deleteById(id);
	}
	
	public Iterable<Ricetta> findTop3() {
		return ricettaRepository.findAllByOrderByIdAsc(PageRequest.of(0, 3));
    }
}
