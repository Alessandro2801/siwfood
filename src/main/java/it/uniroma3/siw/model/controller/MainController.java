package it.uniroma3.siw.model.controller;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.CredenzialiRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private CredenzialiRepository credenzialiRepository;
    @Autowired
    RicettaService ricettaService;
    @Autowired
    CuocoService cuocoService;

    @GetMapping("/")
    public String home(Model model) {
        // Recupera le prime 3 ricette ordinate per ID
        List<Ricetta> ricette = (List<Ricetta>) ricettaService.findTop3();
        // Recupera le prime 3 cuochi
        List<Cuoco> cuochi = (List<Cuoco>) cuocoService.findTop3();
        
        // Aggiungi le liste al modello
        model.addAttribute("ricette", ricette);
        model.addAttribute("cuochi", cuochi);
        
        return "index";
    }

    @GetMapping("/admin/index")
    public String showAdminIndex(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Credenziali credenziali = credenzialiRepository.findByUsername(username);
            Utente utente = credenziali.getUtente();
            if (utente != null) {
                model.addAttribute("utente", utente);
            }

        }
     // Recupera le prime 3 ricette ordinate per ID
        List<Ricetta> ricette = (List<Ricetta>) ricettaService.findTop3();
        // Recupera le prime 3 cuochi
        List<Cuoco> cuochi = (List<Cuoco>) cuocoService.findTop3();
        
        // Aggiungi le liste al modello
        model.addAttribute("ricette", ricette);
        model.addAttribute("cuochi", cuochi);
        return "admin/index-admin";
    }

    @GetMapping("/chef/index")
    public String showChefIndex(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Credenziali credenziali = credenzialiRepository.findByUsername(username);
            Utente utente = credenziali.getUtente();
            Cuoco cuocoCorrente = utente.getCuoco();
            if (cuocoCorrente != null) {
                model.addAttribute("cuocoCorrente", cuocoCorrente);
            }

        }
     // Recupera le prime 3 ricette ordinate per ID
        List<Ricetta> ricette = (List<Ricetta>) ricettaService.findTop3();
        // Recupera le prime 3 cuochi
        List<Cuoco> cuochi = (List<Cuoco>) cuocoService.findTop3();
        
        // Aggiungi le liste al modello
        model.addAttribute("ricette", ricette);
        model.addAttribute("cuochi", cuochi);
        return "chef/index-chef";
    }   
    
    
}