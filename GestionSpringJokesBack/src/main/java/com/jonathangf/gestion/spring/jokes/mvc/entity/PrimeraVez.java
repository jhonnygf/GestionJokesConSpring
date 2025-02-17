package com.jonathangf.gestion.spring.jokes.mvc.entity;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
/**
 * Clase PrimeraVez representa la primera vez que aparecio un joke
 */
@Entity
@Table(name = "primera_vez")
public class PrimeraVez implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "programa", nullable = false)
    private String programa;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @OneToOne
    @JoinColumn(name = "id_joke", unique = true)
    private Jokes joke;

    @OneToMany(mappedBy = "primeraVez", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefonos> telefonos;
    
	public PrimeraVez() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public LocalDate getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Jokes getJoke() {
		return joke;
	}

	public void setJoke(Jokes joke) {
		this.joke = joke;
	}

	public List<Telefonos> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<Telefonos> telefonos) {
		this.telefonos = telefonos;
	}
	
	
	
}
