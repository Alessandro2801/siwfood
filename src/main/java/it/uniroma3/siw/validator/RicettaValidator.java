package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.RicettaRepository;

@Component
public class RicettaValidator implements Validator {

	@Autowired
	RicettaRepository ricettaRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Ricetta.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Ricetta ricetta = (Ricetta) o;

		if (this.ricettaRepository.existsByNomeAndCuocoAndDescrizione(ricetta.getNome(), ricetta.getCuoco(),
				ricetta.getDescrizione()))
			errors.reject("ricetta.duplicate");

		if (ricetta.getIngredienti() == null || ricetta.getIngredienti().isEmpty())
			errors.reject("ricetta.empty.ingredienti");

		 // Verifica se esiste già una ricetta con lo stesso nome
        if (ricettaRepository.existsByNome(ricetta.getNome()) && ricettaRepository.existsByDescrizione(ricetta.getDescrizione())) {
            // Controlla se gli ingredienti corrispondono
            for (Ricetta existingRicetta : ricettaRepository.findByNome(ricetta.getNome())) {
                if (ingredientiCorrispondenti(ricetta, existingRicetta)) {
                    errors.reject("ricetta.duplicate.ingredienti");
                    return; // esci non appena trovi una corrispondenza
                }
            }
        }

	}

	// Metodo ausiliario per verificare se due ricette hanno gli stessi ingredienti
	private boolean ingredientiCorrispondenti(Ricetta ricetta1, Ricetta ricetta2) {
		if (ricetta1.getIngredienti().size() != ricetta2.getIngredienti().size()) {
			return false;
		}

		for (Ingrediente ingrediente1 : ricetta1.getIngredienti()) {
			boolean found = false;
			for (Ingrediente ingrediente2 : ricetta2.getIngredienti()) {
				if (ingrediente1.getNome().equals(ingrediente2.getNome())
						&& ingrediente1.getQuantita().equals(ingrediente2.getQuantita())) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}

		return true;
	}

}