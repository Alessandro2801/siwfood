package it.uniroma3.siw.model;


import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utente_generator")
    @SequenceGenerator(name="utente_generator", sequenceName = "utente_seq", allocationSize=1)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	@Column(name = "data_nascita")
	@NotNull
	@Past
	private LocalDate dataNascita;
    @OneToOne(cascade = CascadeType.ALL)
    private Cuoco cuoco;
	
	// COSTRUTTORI
		public Utente() {
			
		}

		public Utente(String nome, String cognome) {
			this.nome = nome;
			this.cognome = cognome;
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
		
	    public void setCuoco(Cuoco cuoco) {
	        this.cuoco = cuoco;
	    }

	    public Cuoco getCuoco() {
	        return cuoco;
	    }
	    
		
	    // METODI EQUALS E HAASHCODE
	    
		@Override
		public int hashCode() {
			return Objects.hash(cognome, nome);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Utente other = (Utente) obj;
			return Objects.equals(cognome, other.cognome) && Objects.equals(nome, other.nome);
		}
}
