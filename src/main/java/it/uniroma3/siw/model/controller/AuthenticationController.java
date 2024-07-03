package it.uniroma3.siw.model.controller;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.service.UtenteService;
import it.uniroma3.siw.validator.CredenzialiValidator;
import it.uniroma3.siw.validator.UtenteValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AuthenticationController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    CredenzialiValidator credenzialiValidator;
    @Autowired
    UtenteValidator utenteValidator;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Nome del file HTML della pagina di login
    }

    @PostMapping("/login")
    public String login() {
        // Questa mappatura è gestita automaticamente da Spring Security
        return "redirect:/welcome"; // Successo di login
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser( @Valid Utente utente,
                               BindingResult utenteBindingResult,
                               @Valid Credenziali credenziali,
                               BindingResult credenzialiBindingResult,
                               @RequestParam("fileImmagine") MultipartFile fileImmagine,
                               Model model) {
        utenteValidator.validate(utente, utenteBindingResult);
        credenzialiValidator.validate(credenziali, credenzialiBindingResult);

        if (utenteBindingResult.hasErrors())  {
        	model.addAttribute("error", "Utente già registrato: ");
            return "register"; // Ritorna la vista di registrazione con gli errori
        }
        
        if (credenzialiBindingResult.hasErrors()) {
        	model.addAttribute("error", "Username già registrato: ");
            return "register"; // Ritorna la vista di registrazione con gli errori
        }
        
     // Verifica se è stato fornito correttamente un file immagine
	    if (fileImmagine.isEmpty()) {
	        model.addAttribute("fileImmagineError", "Inserire un'immagine");
	        return "register";
	    }
	    
        // Verifica del tipo di file (opzionale ma consigliato)
        if (!fileImmagine.getContentType().startsWith("image/")) {
            model.addAttribute("fileImmagineError", "Il file caricato non è un'immagine valida");
            return "register";
        }

        try {
            utenteService.registerUser(credenziali.getUsername(), credenziali.getPassword(), utente.getNome(), utente.getCognome(), utente.getDataNascita(), fileImmagine);
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Errore durante la registrazione: " + e.getMessage());
            return "register";  // Gestisci l'errore durante la registrazione
        }
    }


    @GetMapping("/admin/register")
    public String showRegisterFormAdmin() {
        return "admin/register-admin";
    }

    @PostMapping("/admin/register")
    public String registerUserAdmin(@Valid Utente utente,
                                    BindingResult utenteBindingResult,
                                    @Valid Credenziali credenziali,
                                    BindingResult credenzialiBindingResult,
                                    Model model) {
        utenteValidator.validate(utente, utenteBindingResult);
        credenzialiValidator.validate(credenziali, credenzialiBindingResult);

        if (utenteBindingResult.hasErrors())  {
        	model.addAttribute("error", "Utente già registrato: ");
            return "admin/register-admin"; // Ritorna la vista di registrazione con gli errori
        }
        if (credenzialiBindingResult.hasErrors()) {
        	model.addAttribute("error", "Username già registrato: ");
            return "admin/register-admin"; // Ritorna la vista di registrazione con gli errori
        }

        try {
            utenteService.registerAdmin(credenziali.getUsername(), credenziali.getPassword(), utente.getNome(), utente.getCognome(), utente.getDataNascita());
            return "redirect:/admin/profile";
        } catch (Exception e) {
            model.addAttribute("error", "Errore durante la registrazione: " + e.getMessage());
            return "admin/register-admin";  // Gestisci l'errore durante la registrazione admin
        }
    }

    
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
