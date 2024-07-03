package it.uniroma3.siw.model;

import jakarta.persistence.*;

@Entity
public class Immagine {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "immagine_generator")
    @SequenceGenerator(name="immagine_generator", sequenceName = "immagine_seq", allocationSize=1)
    private Long id;

    private byte[] bytes;

    public Immagine(){

    }

    public Immagine(byte[] bytes){
        this.bytes = bytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
