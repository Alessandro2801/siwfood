package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;


@Component
public class UtenteValidator implements Validator {
	
	@Autowired
	UtenteRepository utenteRepository;
	
	@Override
    public boolean supports(Class<?> clazz) {
        return Utente.class.equals(clazz);
    }

	@Override
	public void validate(Object o, Errors errors) {
		 Utente utente = (Utente) o;
		 if(this.utenteRepository.existsByNomeAndCognomeAndDataNascita(utente.getNome(), utente.getCognome(), utente.getDataNascita()))
			 errors.reject("utente.duplicate");	       
	}

}