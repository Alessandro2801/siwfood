package it.uniroma3.siw.service;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.repository.CredenzialiRepository;
import it.uniroma3.siw.repository.ImmagineRepository;
import it.uniroma3.siw.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;


@Service
public class UtenteService {
    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    CredenzialiRepository credenzialiRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ImmagineRepository immagineRepository;

    public Utente findById(Long id){
        return this.utenteRepository.findById(id).orElse(null);
    }

    public void save(Utente utente){
        this.utenteRepository.save(utente);
    }

    public Utente registerUser(String username, String password, String nome, String cognome, LocalDate dataNascita, MultipartFile fileImmagine) throws IOException {
        // Codifica la password prima di salvarla
        String encodedPassword = passwordEncoder.encode(password);

        // Salva le credenziali
        Credenziali credenziali = new Credenziali();
        credenziali.setUsername(username);
        credenziali.setPassword(encodedPassword);
        credenziali.setRole("ROLE_CHEF");
        this.credenzialiRepository.save(credenziali);

        // Salva l'account
        Utente utente = new Utente();
        utente.setNome(nome);
        utente.setCognome(cognome);
        // manca il setCredenziali all'interno dell'utente
        utente.setDataNascita(dataNascita);
        credenziali.setUtente(utente);

        // Salva il cuoco
        Cuoco cuoco = new Cuoco();
        utente.setCuoco(cuoco);
        cuoco.setNome(nome);
        cuoco.setCognome(cognome);
        cuoco.setDataNascita(dataNascita);
        Immagine immagine = new Immagine(fileImmagine.getBytes());
	    this.immagineRepository.save(immagine);
	    cuoco.setImmagine(immagine);
        // manca il setUtente all'interno del cuoco
        utenteRepository.save(utente);
        return utente;
    }
    
    
    public Utente registerAdmin(String username, String password, String nome, String cognome, LocalDate dataNascita) {
        // Codifica la password prima di salvarla
        String encodedPassword = passwordEncoder.encode(password);

        // Salva le credenziali
        Credenziali credenziali = new Credenziali();
        credenziali.setUsername(username);
        credenziali.setPassword(encodedPassword);
        credenziali.setRole("ROLE_ADMIN");
        this.credenzialiRepository.save(credenziali);

        // Salva l'account
        Utente utente = new Utente();
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setDataNascita(dataNascita);
        credenziali.setUtente(utente);

        utenteRepository.save(utente);
        return utente;
    }
}


