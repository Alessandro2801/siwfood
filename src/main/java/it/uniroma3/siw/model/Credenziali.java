package it.uniroma3.siw.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Credenziali {

    public static final String DEFAULT_ROLE = "DEFAULT";
    public static final String ADMIN_ROLE = "ADMIN";

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credenziali_generator")
    @SequenceGenerator(name="credenziali_generator", sequenceName = "credenziali_seq", allocationSize=1)
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String role;
    @OneToOne(cascade = CascadeType.ALL)
    private Utente utente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

