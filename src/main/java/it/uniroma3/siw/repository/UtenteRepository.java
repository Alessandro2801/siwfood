package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Utente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface UtenteRepository extends CrudRepository<Utente,Long> {
    
	Optional<Utente> findById(Long id);

	boolean existsByNomeAndCognomeAndDataNascita(String nome, String cognome, LocalDate dataNascita);

}
