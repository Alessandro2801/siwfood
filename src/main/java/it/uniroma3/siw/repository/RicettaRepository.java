package it.uniroma3.siw.repository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;

public interface RicettaRepository extends CrudRepository<Ricetta, Long>{

	public Iterable<Ricetta> findByNome(String nome);
	
	public boolean existsByNomeAndCuocoAndDescrizione(String nome, Cuoco cuoco, String descrizione);

	List<Ricetta> findAllByOrderByIdAsc(PageRequest pageRequest);
	
	public boolean existsByNome(String nome);
	
	boolean existsByDescrizione(String descrizione);

}
