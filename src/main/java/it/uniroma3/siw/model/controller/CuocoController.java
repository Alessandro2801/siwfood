package it.uniroma3.siw.model.controller;

import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CredenzialiRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;
import it.uniroma3.siw.validator.CuocoValidator;

@Controller
public class CuocoController {
	@Autowired
	CuocoService cuocoService;
	@Autowired
	CuocoValidator cuocoValidator;
	@Autowired
	RicettaService ricettaService;
	@Autowired 
	IngredienteService ingredienteService;
	@Autowired
	CredenzialiRepository credenzialiRepository;
	
	  @GetMapping("/cuoco/{id}")
	  public String getCuoco(@PathVariable Long id, Model model) {
	    model.addAttribute("cuoco", this.cuocoService.findById(id));
	    return "cuoco.html";
	  }
	  
	  
	  @GetMapping("/cuoco")
	  public String showCuochi(Model model) {
	    model.addAttribute("cuochi", this.cuocoService.findAll());
	    return "cuochi.html";
	  }
	  
	  		
	  @PostMapping("/searchCuochi")
	    public String searchCuochi(Model model, @RequestParam String cognome) {
		    model.addAttribute("cuochi", this.cuocoService.findByCognome(cognome));
			return "foundCuochi.html";
		}
	  
	  
		
}
