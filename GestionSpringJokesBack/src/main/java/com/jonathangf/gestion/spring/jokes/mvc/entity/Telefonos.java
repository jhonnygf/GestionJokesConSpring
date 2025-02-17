package com.jonathangf.gestion.spring.jokes.mvc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Clase Telefonos representa los telefonos que se usaron en la primera vez que
 * aparecio un joke
 */
@Entity
@Table(name = "telefonos")
public class Telefonos implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "numero", nullable = false)
	private String numero;

	@ManyToOne
	@JoinColumn(name = "id_primera", nullable = false)
	private PrimeraVez primeraVez;

	public Telefonos() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public PrimeraVez getPrimeraVez() {
		return primeraVez;
	}

	public void setPrimeraVez(PrimeraVez primeraVez) {
		this.primeraVez = primeraVez;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
