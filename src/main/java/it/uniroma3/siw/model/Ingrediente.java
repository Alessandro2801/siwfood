package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ricetta_ingredienti")
public class Ingrediente {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingrediente_generator")
    @SequenceGenerator(name="ingrediente_generator", sequenceName = "ingrediente_seq", allocationSize=1)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String quantita;
	@ManyToOne
	private Ricetta ricetta;
	@Transient
	private boolean toRemove = false;
	
	// COSTRUTTORI
	public Ingrediente() {
		
	}

	public Ingrediente(String nome, String quantita, Ricetta ricetta) {
		this.nome = nome;
		this.quantita = quantita;
		this.ricetta = ricetta;		
	}
	
	// METODI GETTER E SETTER
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getQuantita() {
		return quantita;
	}

	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}

	public Ricetta getRicetta() {
		return ricetta;
	}

	public void setRicetta(Ricetta ricetta) {
		this.ricetta = ricetta;
	}
	
	public boolean isToRemove() {
		return toRemove;
	}
	
	public void setToRemove(boolean toRemove) {
		this.toRemove = toRemove;
	}
	
	// METODI EQUALS E HASHCODE
	@Override
	public int hashCode() {
		return Objects.hash(nome, quantita, ricetta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(quantita, other.quantita)
				&& Objects.equals(ricetta, other.ricetta);
	}
	
	
	
}
