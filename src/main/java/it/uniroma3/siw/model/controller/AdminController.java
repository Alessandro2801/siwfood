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
import it.uniroma3.siw.validator.UtenteValidator;
import jakarta.validation.Valid;;

@Controller
public class AdminController {

	@Autowired
	CredenzialiRepository credenzialiRepository;
	@Autowired
	CuocoService cuocoService; 
	@Autowired
	CuocoValidator cuocoValidator;
	@Autowired
	RicettaValidator ricettaValidator;
	@Autowired
	RicettaService ricettaService;
	@Autowired
	IngredienteService ingredienteService;
	@Autowired
	UtenteService utenteService;
	@Autowired
	ImmagineRepository immagineRepository;
	@Autowired
	IngredienteRepository ingredienteRepository;
	@Autowired
	UtenteValidator utenteValidator;



	// metodo per visualzzare il profilo dell'amministratore
	@GetMapping("/admin/profile") 
	public String showChefProfile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			if(credenziali != null) {
				model.addAttribute("credenziali", credenziali);
			}
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}
		}
		return "admin/profilo-admin.html";
	}

	// metodo che visualizza il form per la modifica dei dati anagrafici dell'amministratore
	@GetMapping("/admin/modify")
	public String showModifyChefForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utenteCorrente = credenziali.getUtente();
			if (utenteCorrente != null) {
				model.addAttribute("utenteCorrente", utenteCorrente);
				model.addAttribute("utente", utenteCorrente);
			}
		}
		return "admin/formModifyAdmin.html";
	}


	// metodo che modifica i dati anagrafici dell'amministartore
	@PostMapping("admin/modify")
	public String modifyChef( @Valid @ModelAttribute("utente") Utente utente, BindingResult bindingResult, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utenteCorrente = credenziali.getUtente();
			if (utenteCorrente != null) {
				this.utenteValidator.validate(utente, bindingResult);
				if (!bindingResult.hasErrors()) {
					utenteCorrente.setNome(utente.getNome());
					utenteCorrente.setCognome(utente.getCognome());
					utenteCorrente.setDataNascita(utente.getDataNascita());
					this.utenteService.save(utenteCorrente);
					return "redirect:/admin/profile";
				}
				model.addAttribute("utenteCorrente", utenteCorrente);
				model.addAttribute("utente", utenteCorrente);
				return "admin/formModifyAdmin.html";
			}
			return "";

		}
		return "";
	}
	
	

	@GetMapping("/admin/cuoco/{id}")
	public String getCuoco(@PathVariable Long id, Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && authentication.isAuthenticated()) {
	            String username = authentication.getName();
	            Credenziali credenziali = credenzialiRepository.findByUsername(username);
	            Utente utente = credenziali.getUtente();
	            if (utente != null) {
	                model.addAttribute("utente", utente);
	            }
	        }

	        Cuoco cuoco = cuocoService.findById(id);
	        model.addAttribute("cuoco", cuoco);
	        return "admin/cuoco-admin.html";
	}



	// metodo per visualizzare una ricetta tramite il suo id
	@GetMapping("/admin/ricetta/{id}")
	public String getRicetta(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Ricetta ricetta = this.ricettaService.findById(id);
        model.addAttribute("ricetta", ricetta);
        return "admin/ricetta-admin.html";
	}


	// metodo per visualizzare la lista di cuochi
	@GetMapping("/admin/cuoco")
	public String showCuochi(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		List<Cuoco> cuochi = (List<Cuoco>) this.cuocoService.findAll();
		model.addAttribute("cuochi", cuochi);
		return "admin/cuochi-admin.html";
	}


	// metodo per visualizzare la lista delle ricette
	@GetMapping("/admin/ricetta")
	public String showRicette(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		List<Ricetta> ricette = (List<Ricetta>) this.ricettaService.findAll();
		model.addAttribute("ricette", ricette);
		return "admin/ricette-admin.html";
	}

	// metodo che restituisce la lista di cuochi in base alla ricerca per cognome effettuata
	@PostMapping("/admin/searchCuochi")
	public String searchCuochi(Model model, @RequestParam String cognome) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		List<Cuoco> cuochi = (List<Cuoco>) this.cuocoService.findByCognome(cognome);
		model.addAttribute("cuochi", cuochi);
		return "admin/foundCuochi-admin.html";
	}


	// metodo che restituisce la lista delle ricette in base alla ricerca per nome effettuata
	@PostMapping("/admin/searchRicette")
	public String searchRicette(Model model, @RequestParam String nome) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		List<Ricetta> ricette = (List<Ricetta>) this.ricettaService.findByNome(nome);
		model.addAttribute("ricette", ricette);
		return "admin/foundRicette-admin.html";
	}

	// metodo che visualizza il form per modificare tutti i cuochi presente nel sistema
	@GetMapping("/admin/formManagerCuochi")
	public String showManagerCuochi(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		List<Cuoco> cuochi = (List<Cuoco>) this.cuocoService.findAll();
		model.addAttribute("cuochi", cuochi);
		return "admin/formManagerCuochi-admin.html";
	}


	// metodo che visualizza il form per modificare tutte le ricette presente nel sistema
	@GetMapping("/admin/formManagerRicette")
	public String showManagerRicette(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		List<Ricetta> ricette = (List<Ricetta>) this.ricettaService.findAll();
		model.addAttribute("ricette", ricette);
		return "admin/formManagerRicette-admin.html";
	}


	// metodo per visualizzare il form per l'inserimento di un nuovo cuoco
	@GetMapping("/admin/formNewCuoco")
	public String formNewCuoco(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		model.addAttribute("cuoco", new Cuoco());
		return "admin/formNewCuoco-admin.html";
	}


	// metodo per visualizzare il form per l'inserimento di una nuova ricetta
	@GetMapping("/admin/formNewRicetta")
	public String formNewRicetta(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		model.addAttribute("ricetta", new Ricetta());
		model.addAttribute("cuochi", this.cuocoService.findAll());
		return "admin/formNewRicetta-admin.html";
	}


	// metodo che salva un nuovo cuoco nel sistema
	@PostMapping("/admin/NewCuoco")
	public String newCuoco(@Valid @ModelAttribute("cuoco") Cuoco cuoco, @RequestParam("fileImmagine") MultipartFile fileImmagine,
							BindingResult bindingResult, Model model) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		this.cuocoValidator.validate(cuoco, bindingResult);
		if(bindingResult.hasErrors()) {
			return "admin/formNewCuoco-admin.html";
		}
		
		// Verifica se è stato fornito correttamente un file immagine
	    if (fileImmagine.isEmpty()) {
	        model.addAttribute("fileImmagineError", "Inserire un'immagine");
	        return "admin/formNewCuoco-admin.html";
	    }
	    // Verifica del tipo di file (opzionale ma consigliato)
        if (!fileImmagine.getContentType().startsWith("image/")) {
            model.addAttribute("fileImmagineError", "Il file caricato non è un'immagine valida");
            return "admin/formNewCuoco-admin.html";
        }	    	
	    	// Caricamento dell'immagine e salvataggio del cuoco
	        Immagine immagine = new Immagine(fileImmagine.getBytes());
	        this.immagineRepository.save(immagine);        
	        cuoco.setImmagine(immagine);
	        
	        this.cuocoService.save(cuoco);
			model.addAttribute("cuoco", cuoco);
			return "redirect:/admin/cuoco/"+cuoco.getId();  
	}

	// metodo che salva una nuova ricetta all'interno del sistema
	@PostMapping("/admin/NewRicetta")
	public String newRicetta(@Valid @ModelAttribute("ricetta") Ricetta ricetta, 
	                         @RequestParam("fileImmagine") MultipartFile fileImmagine, 
	                         BindingResult bindingResult, Model model) throws IOException {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.isAuthenticated()) {
	        String username = authentication.getName();
	        Credenziali credenziali = credenzialiRepository.findByUsername(username);
	        Utente utente = credenziali.getUtente();
	        if (utente != null) {
	            model.addAttribute("utente", utente);
	        }
	    }

	    // Validazione della ricetta
	    this.ricettaValidator.validate(ricetta, bindingResult);
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("cuochi", this.cuocoService.findAll());
	        return "admin/formNewRicetta-admin.html";
	    }

	    // Verifica se è stato fornito correttamente un file immagine
	    if (fileImmagine.isEmpty()) {
	        model.addAttribute("fileImmagineError", "Inserire un'immagine");
	        model.addAttribute("cuochi", cuocoService.findAll());
	        return "admin/formNewRicetta-admin.html";
	    }
	    
        // Verifica del tipo di file (opzionale ma consigliato)
        if (!fileImmagine.getContentType().startsWith("image/")) {
            model.addAttribute("fileImmagineError", "Il file caricato non è un'immagine valida");
            model.addAttribute("cuochi", cuocoService.findAll());
            return "admin/formNewRicetta-admin.html";
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
		return "redirect:/admin/ricetta/"+ricetta.getId();
	}

	
	
	// metodo che visualizza il form per la cancellazione di un cuoco 
	@GetMapping("/admin/cuoco/{id}/delete")
	public String showDeleteCuocoConfirmation(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Cuoco cuoco = this.cuocoService.findById(id);
		model.addAttribute("cuoco", cuoco);
		return "admin/confirmDeleteCuoco-admin.html";
	}


	// metodo che visualizza il form per la cancellazione di una ricetta 
	@GetMapping("/admin/ricetta/{id}/delete")
	public String showDeleteRicettaConfirmation(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Ricetta ricetta = this.ricettaService.findById(id);
		model.addAttribute("ricetta", ricetta);
		return "admin/confirmDeleteRicetta-admin.html";
	}


	// metodo che elimina un cuoco dalla pagina che visualizza la lista di tutti i cuochi del sistema
	@PostMapping("/admin/cuoco/{id}/delete")
	public String deleteCuoco(@PathVariable Long id, Model model) {
		this.cuocoService.deleteById(id);
		return "redirect:/admin/formManagerCuochi";
	}


	// metodo che elimina una ricetta dalla pagina che visualizza la lista di tutte le ricette del sistema
	@PostMapping("/admin/ricetta/{id}/delete")
	public String deleteRicetta(@PathVariable Long id, Model model) {
		this.ricettaService.deleteById(id);
		return "redirect:/admin/formManagerRicette";
	}


	// Mostra il form per modificare un cuoco esistente
	@GetMapping("/admin/cuoco/{id}/modify")
	public String showModifyCuocoForm(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Cuoco cuoco = this.cuocoService.findById(id);
		model.addAttribute("cuoco", cuoco);
		return "admin/formModifyCuoco-admin.html";
	}


	// Mostra il form per modificare una ricetta esistente
	@GetMapping("/admin/ricetta/{id}/modify")
	public String showModifyRicettaForm(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Ricetta ricetta = this.ricettaService.findById(id);
		model.addAttribute("ricetta", ricetta);
		return "admin/formModifyRicetta-admin.html";
	}


	// Salva le modifiche di un cuoco dalla pagina web che visualizza la lista di tutti i cuochi del sistema
	@PostMapping("/admin/cuoco/{id}/modify")
	public String modifyCuoco(@PathVariable Long id, @Valid @ModelAttribute("cuoco") Cuoco cuoco, BindingResult bindingResult, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Cuoco existingCuoco = this.cuocoService.findById(id);
		this.cuocoValidator.validate(cuoco, bindingResult);
		if (!bindingResult.hasErrors()) {
			existingCuoco.setNome(cuoco.getNome());
			existingCuoco.setCognome(cuoco.getCognome());
			existingCuoco.setDataNascita(cuoco.getDataNascita());
			this.cuocoService.save(existingCuoco);
			return "redirect:/admin/formManagerCuochi";
		}
		model.addAttribute("cuoco", existingCuoco);
		return "admin/formModifyCuoco-admin.html";
	}


	// Salva le modifiche di una ricetta dalla pagina web che visualizza la lista di tutte le ricette del sistema
	@PostMapping("/admin/ricetta/{id}/modify")
	public String modifyRicetta(@PathVariable Long id, @Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
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
			return "redirect:/admin/formManagerRicette";
		}
		model.addAttribute("ricetta", existingRicetta);
		return "admin/formModifyRicetta-admin.html";
	}

	// metodo che visualizza il form per la gestione di un cuoco
	@GetMapping("/admin/cuoco/{id}/formManagerCuoco")
	public String showManagerCuoco(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Cuoco cuoco = this.cuocoService.findById(id);
		model.addAttribute("cuoco", cuoco);
		return "admin/formManagerCuoco-admin.html";
	}



	// metodo che visualizza il form per la conferma della cancellazione di un cuoco
	@GetMapping("/admin/cuoco/{id}/delete2")
	public String showDeleteCuocoConfirmation2(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Cuoco cuoco = this.cuocoService.findById(id);
		model.addAttribute("cuoco", cuoco);
		return "admin/confirmDeleteCuoco2-admin.html";
	}

	// metodo che visualizza il form per la conferma della cancellazione di una ricetta
	@GetMapping("/admin/ricetta/{id}/delete2")
	public String showDeleteRicettaConfirmation2(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Ricetta ricetta = this.ricettaService.findById(id);
		model.addAttribute("ricetta", ricetta);
		return "admin/confirmDeleteRicetta2-admin.html";
	}



	// metodo che elimina un cuoco dalla pagina personale del cuoco
	@PostMapping("/admin/cuoco/{id}/delete2")
	public String deleteCuoco2(@PathVariable Long id) {
		this.cuocoService.deleteById(id);
		return "redirect:/admin/cuoco";
	}


	// metodo che elimina una ricetta dalla pagina personale della ricetta
	@PostMapping("/admin/ricetta/{id}/delete2")
	public String deleteRicetta2(@PathVariable Long id) {
		this.ricettaService.deleteById(id);
		return "redirect:/admin/ricetta";
	}


	// Mostra il form per modificare un cuoco esistente 
	@GetMapping("/admin/cuoco/{id}/modify2")
	public String showModifyCuocoForm2(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Cuoco cuoco = this.cuocoService.findById(id);
		model.addAttribute("cuoco", cuoco);
		return "admin/formModifyCuoco2-admin.html";
	}


	// Mostra il form per modificare una ricetta esistente 
	@GetMapping("/admin/ricetta/{id}/modify2")
	public String showModifyRicettaForm2(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Ricetta ricetta = this.ricettaService.findById(id);
		model.addAttribute("ricetta", ricetta);
		return "admin/formModifyRicetta2-admin.html";
	}


	// Salva le modifiche del cuoco dalla pagina web del cuoco
	@PostMapping("/admin/cuoco/{id}/modify2")
	public String modifyCuoco2(@PathVariable Long id, @Valid @ModelAttribute("cuoco") Cuoco cuoco, BindingResult bindingResult, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Cuoco existingCuoco = this.cuocoService.findById(id);
		this.cuocoValidator.validate(cuoco, bindingResult);
		if (!bindingResult.hasErrors()) {
			existingCuoco.setNome(cuoco.getNome());
			existingCuoco.setCognome(cuoco.getCognome());
			existingCuoco.setDataNascita(cuoco.getDataNascita());
			this.cuocoService.save(existingCuoco);
			return "redirect:/admin/cuoco/" + existingCuoco.getId();
		}
		model.addAttribute("cuoco", existingCuoco);
		return "admin/formModifyCuoco2-admin.html";
	}


	// Salva le modifiche della ricetta dalla pagina web della ricetta
	@PostMapping("/admin/ricetta/{id}/modify2")
	public String modifyRicetta2(@PathVariable Long id, @Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
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
			return "redirect:/admin/ricetta/" + existingRicetta.getId();
		}
		model.addAttribute("ricetta", existingRicetta);
		return "admin/formModifyRicetta2-admin.html";
	}


	// metodo che visualizza il form per la conferma della cancellazione di una delle ricette di un cuoco
	@GetMapping("/admin/cuoco/{cuocoId}/ricetta/{ricettaId}/delete")
	public String showDeleteRicettaConfirmation(@PathVariable Long cuocoId, @PathVariable Long ricettaId, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Cuoco cuoco = this.cuocoService.findById(cuocoId);
		Ricetta ricetta = this.ricettaService.findById(ricettaId);
		model.addAttribute("cuoco", cuoco);
		model.addAttribute("ricetta", ricetta);
		return "admin/confirmDeleteRicettaOfCuoco-admin.html";
	}


	// metodo che elimina una delle ricette di un cuoco
	@PostMapping("/admin/cuoco/{cuocoId}/ricetta/{ricettaId}/delete")
	public String eliminaRicetta(@PathVariable Long cuocoId, @PathVariable Long ricettaId) {
		this.ricettaService.deleteById(ricettaId);
		return "redirect:/admin/cuoco/" + cuocoId + "/formManagerCuoco";
	}


	// metodo che visualizza il form per la modifica di una delle ricette di un cuoco
	@GetMapping("/admin/cuoco/{cuocoId}/ricetta/{ricettaId}/modify")
	public String showModifyRicettaOfCuocoForm(@PathVariable Long cuocoId, @PathVariable Long ricettaId, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Cuoco cuoco = this.cuocoService.findById(cuocoId);
		Ricetta ricetta = this.ricettaService.findById(ricettaId);
		model.addAttribute("cuoco", cuoco);
		model.addAttribute("ricetta", ricetta);
		return "admin/formModifyRicettaOfCuoco-admin.html";
	}


	// metodo che salva le modifiche di una delle ricette di un cuoco
	@PostMapping("/admin/cuoco/{cuocoId}/ricetta/{ricettaId}/modify")
	public String modifyRicettaOfCuoco(@PathVariable Long cuocoId, @PathVariable Long ricettaId,
			@Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult bindingResult, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Credenziali credenziali = credenzialiRepository.findByUsername(username);
			Utente utente = credenziali.getUtente();
			if (utente != null) {
				model.addAttribute("utente", utente);
			}

		}
		Ricetta existingRicetta = this.ricettaService.findById(ricettaId);
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
			return "redirect:/admin/cuoco/" + cuocoId + "/formManagerCuoco";
		}
		model.addAttribute("ricetta", existingRicetta);
		model.addAttribute("cuoco", this.cuocoService.findById(cuocoId));
		return "admin/formModifyRicettaOfCuoco-admin.html";
	}


}
