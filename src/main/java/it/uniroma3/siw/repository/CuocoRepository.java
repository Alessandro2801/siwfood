package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;

public interface CuocoRepository extends CrudRepository<Cuoco, Long>{
	
	public Iterable<Cuoco> findByCognome(String cognome);
	
	public boolean existsByNomeAndCognomeAndDataNascita(String nome, String cognome, LocalDate dataNascita);
	
	List<Cuoco> findAllByOrderByIdAsc(PageRequest pageRequest);
}
