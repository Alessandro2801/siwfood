package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CuocoRepository;

@Service
public class CuocoService {
	@Autowired
	private CuocoRepository cuocoRepository;
	
	@Transactional
	public Cuoco findById(Long id) {
		return cuocoRepository.findById(id).get();
	}
	
	public Iterable<Cuoco> findAll() {
		return cuocoRepository.findAll();
	}
	
	@Transactional
	public Iterable<Cuoco> findByCognome(String cognome) {
		return this.cuocoRepository.findByCognome(cognome);
	}

	public void save(Cuoco cuoco) {
		this.cuocoRepository.save(cuoco);		
	}
	
	@Transactional
	public void deleteById(Long id) {
		 this.cuocoRepository.deleteById(id);
	}
	
	public Iterable<Cuoco> findTop3() {
		return cuocoRepository.findAllByOrderByIdAsc(PageRequest.of(0, 3));
    }
}
