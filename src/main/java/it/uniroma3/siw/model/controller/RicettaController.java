package it.uniroma3.siw.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.repository.CredenzialiRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;
import it.uniroma3.siw.validator.RicettaValidator;

@Controller
public class RicettaController {
	@Autowired
	RicettaService ricettaService;
	@Autowired
	CuocoService cuocoService;
	@Autowired
	RicettaValidator ricettaValidator;
	@Autowired
    IngredienteService ingredienteService;
	@Autowired
	CredenzialiRepository credenzialiRepository;
	
	  @GetMapping("/ricetta/{id}")
	  public String getRicetta(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("ricetta", this.ricettaService.findById(id));
	    return "ricetta.html";
	  }
	  

	  @GetMapping("/ricetta")
	  public String showRicette(Model model) {
	    model.addAttribute("ricette", this.ricettaService.findAll());
	    return "ricette.html";
	  }
	  	    
		
		@PostMapping("/searchRicette")
		public String searchRicette(Model model, @RequestParam String nome) {
			model.addAttribute("ricette", this.ricettaService.findByNome(nome));
			return "foundRicette.html";
		}
		
		
}