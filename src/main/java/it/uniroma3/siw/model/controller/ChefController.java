package it.uniroma3.siw.model.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredenzialiRepository;
import it.uniroma3.siw.repository.ImmagineRepository;
import it.uniroma3.siw.repository.IngredienteRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;
import it.uniroma3.siw.service.UtenteService;
import it.uniroma3.siw.validator.CuocoValidator;
import it.uniroma3.siw.validator.RicettaValidator;
import jakarta.validation.Valid;

@Controller
public class ChefController {
	
	@Autowired
	CredenzialiRepository credenzialiRepository;
	@Autowired
	CuocoService cuocoService;
	@Autowired
	RicettaService ricettaService;
	@Autowired
	CuocoValidator cuocoValidator;
	@Autowired
	RicettaValidator ricettaValidator;
	@Autowired
	IngredienteService ingredienteService;
	@Autowired
	UtenteService utenteService;
	@Autowired
	ImmagineRepository immagineRepository;
	@Autowired
	IngredienteRepository ingredienteRepository;
	
	// metodo per visualzzare il profilo dello chef
    @GetMapping("/chef/profile") 
    public String showChefProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Credenziali credenziali = credenzialiRepository.findByUsername(username);
            Utente utente = credenziali.getUtente();
            Cuoco cuocoCorrente = utente.getCuoco();
            if (cuocoCorrente != null) {
	            model.addAttribute("cuocoCorrente", cuocoCorrente);;
            }
        }
        return "chef/profilo-chef.html";
    }
    
    // metodo per visualizzare il form per la modifica del profilo dello chef
    @GetMapping("/chef/formManagerProfile") 
    public String showChefformManagerProfile(Model model) {
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
        return "chef/formManagerProfilo-chef.html";
    }
    
 // metodo per visualizzare un cuoco tramite il suo id
    @GetMapping("/chef/cuoco/{id}")
	  public String chefGetCuoco(@PathVariable Long id, Model model) {
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
	       Cuoco cuoco = cuocoService.findById(id);
	       model.addAttribute("cuoco", cuoco);
	       return "chef/cuoco-chef.html";
	  }
    
 // metodo per visualizzare una ricetta tramite il suo id
    @GetMapping("/chef/ricetta/{id}")
	  public String chefGetRicetta(@PathVariable("id") Long id, Model model) {
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
	       Ricetta ricetta = this.ricettaService.findById(id);
			model.addAttribute("ricetta", ricetta);
			return "chef/ricetta-chef.html";
	  }  
    
 // metodo per visualizzare l'elenco dei cuochi salvati nel sistema
    @GetMapping("/chef/cuoco") 
	  public String chefShowCuochi(Model model) {
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
	       List<Cuoco> cuochi = (List<Cuoco>) this.cuocoService.findAll();
			model.addAttribute("cuochi", cuochi);
			return "chef/cuochi-chef.html";
	  }
    
    // metodo per visualizzare l'elenco delle ricette salvate nel sistema
    @GetMapping("/chef/ricetta")
	  public String chefShowRicette(Model model) {
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
	       List<Ricetta> ricette = (List<Ricetta>) this.ricettaService.findAll();
			model.addAttribute("ricette", ricette);
			return "chef/ricette-chef.html";
	  }
    
    // metodo per vedere la lista dei cuochi che corrispondo ai requisiti della ricerca
	  @PostMapping("/chef/searchCuochi")
	    public String chefSearchCuochi(Model model, @RequestParam String cognome) {
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
	       List<Cuoco> cuochi = (List<Cuoco>) this.cuocoService.findByCognome(cognome);
			model.addAttribute("cuochi", cuochi);
			return "chef/foundCuochi-chef.html";
		}

	    // metodo per vedere la lista delle ricette che corrispondo ai requisiti della ricerca
		@PostMapping("/chef/searchRicette")
		public String chefSearchRicette(Model model, @RequestParam String nome) {
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
		       List<Ricetta> ricette = (List<Ricetta>) this.ricettaService.findByNome(nome);
				model.addAttribute("ricette", ricette);
				return "chef/foundRicette-chef.html";
		}
		
		// metodo che visualizza il form per la modifica dei dati anagrafici dello chef
		@GetMapping("/chef/modify")
		public String showModifyChefForm(Model model) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		       if (authentication != null && authentication.isAuthenticated()) {
		           String username = authentication.getName();
		           Credenziali credenziali = credenzialiRepository.findByUsername(username);
		           Utente utente = credenziali.getUtente();
		           Cuoco cuocoCorrente = utente.getCuoco();
		           if (cuocoCorrente != null) {
		               model.addAttribute("cuocoCorrente", cuocoCorrente);
		               model.addAttribute("cuoco", cuocoCorrente);
		           }
		       }
		       return "chef/formModifyChef.html";
		}
		
		// metodo che modifica i dati anagrafici dello chef
		@PostMapping("chef/modify")
		public String modifyChef(@Valid @ModelAttribute("cuoco") Cuoco cuoco, BindingResult bindingResult, Model model) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		       if (authentication != null && authentication.isAuthenticated()) {
		           String username = authentication.getName();
		           Credenziali credenziali = credenzialiRepository.findByUsername(username);
		           Utente utente = credenziali.getUtente();
		           Cuoco cuocoCorrente = utente.getCuoco();
		           if (cuocoCorrente != null) {
				       this.cuocoValidator.validate(cuoco, bindingResult);
					      if (!bindingResult.hasErrors()) {
					    	  cuocoCorrente.setNome(cuoco.getNome());
					    	  cuocoCorrente.setCognome(cuoco.getCognome());
					    	  cuocoCorrente.setDataNascita(cuoco.getDataNascita());
						      this.cuocoService.save(cuocoCorrente);
						      utente.setNome(cuoco.getNome());
						      utente.setCognome(cuoco.getCognome());
						      utente.setDataNascita(cuoco.getDataNascita());
						      this.utenteService.save(utente);
						      return "redirect:/chef/formManagerProfile";
					      }
					      	model.addAttribute("cuocoCorrente", cuocoCorrente);
					      	model.addAttribute("cuoco", cuocoCorrente);
					        return "chef/formModifyChef.html";
		           }
		           return "";
		    
		       }
		       return "";
		}
		
	    // metodo per la visualizzazione del form per l'inserimento di una nuova ricetta dello chef
		@GetMapping("/chef/formNewRicetta")
		  public String showFormNewRicetta(Model model) {
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
			  model.addAttribute("ricetta", new Ricetta());
			  return "/chef/formNewRicetta-chef.html";
		  }
		
		@PostMapping("/chef/NewRicetta")
		public String newRicetta(@Valid @ModelAttribute("ricetta") Ricetta ricetta, 
		                         @RequestParam("fileImmagine") MultipartFile fileImmagine, 
		                         BindingResult bindingResult, Model model) throws IOException {
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    if (authentication != null && authentication.isAuthenticated()) {
		        String username = authentication.getName();
		        Credenziali credenziali = credenzialiRepository.findByUsername(username);
		        Utente utente = credenziali.getUtente();
		        Cuoco cuocoCorrente = utente.getCuoco();
		        if (cuocoCorrente != null) {
		            model.addAttribute("cuocoCorrente", cuocoCorrente);
		            ricetta.setCuoco(cuocoCorrente);
		        }    
		    }
		    
		    // Validazione della ricetta
		    this.ricettaValidator.validate(ricetta, bindingResult);
		    if (bindingResult.hasErrors()) {
		        return "chef/formNewRicetta-chef.html";
		    }

		    // Verifica se è stato fornito correttamente un file immagine
		    if (fileImmagine.isEmpty()) {
		        model.addAttribute("fileImmagineError", "Inserire un'immagine");
		        return "chef/formNewRicetta-chef.html";
		    }
		    
		    // Verifica del tipo di file (opzionale ma consigliato)
		    if (!fileImmagine.getContentType().startsWith("image/")) {
		        model.addAttribute("fileImmagineError", "Il file caricato non è un'immagine valida");
		        return "chef/formNewRicetta-chef.html";
		    }

		    // Caricamento dell'immagine e salvataggio della nuova ricetta dello chef
		    Immagine immagine = new Immagine(fileImmagine.getBytes());
		    this.immagineRepository.save(immagine);        
		    ricetta.setImmagine(immagine);
		    
		    // Associa ogni ingrediente alla ricetta salvata
		    for (Ingrediente ingrediente : ricetta.getIngredienti()) {
		        ingrediente.setRicetta(ricetta);
		    }
		    
		    // Salvataggio della ricetta
		    this.ricettaService.save(ricetta);

		    // Salva tutti gli ingredienti
		    for (Ingrediente ingrediente : ricetta.getIngredienti()) {
		        this.ingredienteRepository.save(ingrediente);
		    }

		    model.addAttribute("ricetta", ricetta);
		    return "redirect:/chef/ricetta/" + ricetta.getId();
		}

		
		// metodo che visualizza il form per la conferma della cancellazione di una delle ricette dello chef
		@GetMapping("/chef/ricetta/{id}/delete")
		  public String showDeleteRicettaConfirmation(@PathVariable Long id, Model model) {
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
		      Ricetta ricetta = this.ricettaService.findById(id);
		      model.addAttribute("ricetta", ricetta);
		      return "chef/confirmDeleteRicetta-chef.html";
		  }
		
		// metodo che elimina una delle ricette dello chef
		@PostMapping("/chef/ricetta/{id}/delete")
		public String eliminaRicetta(@PathVariable Long id) {
			this.ricettaService.deleteById(id);
			return "redirect:/chef/formManagerProfile";
		}
		
		// metodo che visualizza il form per la modifica di una delle ricette dello chef
		@GetMapping("/chef/ricetta/{id}/modify")
		public String showModifyRicettaChefForm(@PathVariable Long id, Model model) {
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
			Ricetta ricetta = this.ricettaService.findById(id);
		      model.addAttribute("ricetta", ricetta);
		      return "chef/formModifyRicetta-chef.html";
		  }
		
		// metodo che modofica una ricetta dello chef con i suoi dati e la sua lista di ingredienti
		@PostMapping("/chef/ricetta/{id}/modify")
		  public String modifyRicetta(@PathVariable Long id, @Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult, Model model) {
	    	  Ricetta existingRicetta = this.ricettaService.findById(id);
	    	  this.ricettaValidator.validate(ricetta, bindingResult);  
		      if (!bindingResult.hasErrors()) {
		    	  existingRicetta.setNome(ricetta.getNome());
		    	  existingRicetta.setDescrizione(ricetta.getDescrizione());
		    	// Aggiornamento degli ingredienti
		          for (Ingrediente ingrediente : ricetta.getIngredienti()) {
		              if (ingrediente.isToRemove()) {
		                  if (ingrediente.getId() != null) {
		                      this.ingredienteService.deleteById(ingrediente.getId());
		                  }
		              } else if (ingrediente.getId() == null) {
		                  ingrediente.setRicetta(existingRicetta);
		                  this.ingredienteService.save(ingrediente);
		              } else {
		                  Ingrediente existingIngrediente = this.ingredienteService.findById(ingrediente.getId());
		                  existingIngrediente.setNome(ingrediente.getNome());
		                  existingIngrediente.setQuantita(ingrediente.getQuantita());
		                  this.ingredienteService.save(existingIngrediente);
		              }
		          }
			      this.ricettaService.save(existingRicetta);
			      return "redirect:/chef/formManagerProfile";
		      }
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
		      	model.addAttribute("ricetta", existingRicetta);
		        return "chef/formModifyRicetta-chef.html";
		  }
		
		
		
}
