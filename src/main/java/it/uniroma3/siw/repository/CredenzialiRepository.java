package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Credenziali;

import java.util.Optional;

public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {

    Credenziali findByUsername(String username);

    Optional<Credenziali> findById(Long id);

	boolean existsByUsername(String username);
}
