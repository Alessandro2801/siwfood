package it.uniroma3.siw.model;

import java.time.LocalDate;import java.util.*;import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Cuoco {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuoco_generator")
    @SequenceGenerator(name="cuoco_generator", sequenceName = "cuoco_seq", allocationSize=1)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	@Column(name = "data_nascita")
	@NotNull
	@Past
	private LocalDate dataNascita;
	@OneToMany(mappedBy = "cuoco", cascade = CascadeType.ALL, orphanRemoval = true) // nella parte many dell'associazione bidirezionale ci deve essere la FK della parte inversa
	private List<Ricetta> ricette;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Immagine immagine;
	
	// COSTRUTTORI
	public Cuoco() {
		this.ricette = new ArrayList<Ricetta>();
	}
	
	public Cuoco(String nome, String cognome, LocalDate data) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = data;
		this.ricette = new ArrayList<Ricetta>();
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}


	public List<Ricetta> getRicette() {
		return ricette;
	}

	public void setRicette(List<Ricetta> ricette) {
		this.ricette = ricette;
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
		return Objects.hash(cognome, dataNascita, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuoco other = (Cuoco) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataNascita, other.dataNascita)
				&& Objects.equals(nome, other.nome);
	}
	
	

	
	
	
	
	
	
}
