package it.uniroma3.siw.model;

import java.util.*;import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Ricetta {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ricetta_generator")
    @SequenceGenerator(name="ricetta_generator", sequenceName = "ricetta_seq", allocationSize=1)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String descrizione;
	@ManyToOne
	private Cuoco cuoco;
	@OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingrediente> ingredienti;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Immagine immagine;
	
	// COSTRUTTORI
	public Ricetta() {
		this.ingredienti = new ArrayList<Ingrediente>();
	}
	
	public Ricetta(String nome, Cuoco cuoco) {
		this.nome = nome;
		this.cuoco = cuoco;
		this.ingredienti = new ArrayList<Ingrediente>();
	}
	

	// METODI GETTER E SETTER
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cuoco getCuoco() {
		return cuoco;
	}

	public void setCuoco(Cuoco cuoco) {
		this.cuoco = cuoco;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
    public Immagine getImmagine() {
        return immagine;
    }

    public void setImmagine(Immagine immagine) {
        this.immagine = immagine;
    }

	// METODI EQUALS E HASHCODE
	@Override
	public int hashCode() {
		return Objects.hash(id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ricetta other = (Ricetta) obj;
		return Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}
	
	
	
	
	
}
