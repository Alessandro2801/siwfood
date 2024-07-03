package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CredenzialiService {
    @Autowired
    private CredenzialiRepository credenzialiRepository;

    public Optional<Credenziali> getCredentials(Long id){
        return this.credenzialiRepository.findById(id);
    }
    
    public Credenziali getCredentials(String username){
        return this.credenzialiRepository.findByUsername(username);
    }
    
    public void save(Credenziali credenziali){
        this.credenzialiRepository.save(credenziali);
    }

}
