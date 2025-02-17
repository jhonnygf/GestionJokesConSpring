package com.jonathangf.gestion.spring.jokes.mvc.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Clase Flags que representa la tabla flags de la base de datos.
 */
@Entity
@Table(name = "flags")
public class Flags implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "flag", nullable = false)
    private String flag;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "flagses")
    @JsonIgnoreProperties("flagses")
    private Set<Jokes> jokeses = new HashSet<>();

    public Flags() {}
    
    public Flags(String flag) {
		this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Set<Jokes> getJokeses() {
        return jokeses;
    }

    public void setJokeses(Set<Jokes> jokeses) {
        this.jokeses = jokeses;
    }
}
