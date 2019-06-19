package com.senior.fsw.mboy.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.senior.fsw.mboy.domain.enumeration.SiglaEstado;

/**
 * A Cidade.
 */
@Entity
@Table(name = "cidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private SiglaEstado estado;

    @OneToOne(mappedBy = "cidade")
    @JsonIgnore
    private Empresa empresa;

    @OneToOne(mappedBy = "cidade")
    @JsonIgnore
    private Motoboy motoboy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Cidade nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SiglaEstado getEstado() {
        return estado;
    }

    public Cidade estado(SiglaEstado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(SiglaEstado estado) {
        this.estado = estado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Cidade empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Motoboy getMotoboy() {
        return motoboy;
    }

    public Cidade motoboy(Motoboy motoboy) {
        this.motoboy = motoboy;
        return this;
    }

    public void setMotoboy(Motoboy motoboy) {
        this.motoboy = motoboy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cidade)) {
            return false;
        }
        return id != null && id.equals(((Cidade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cidade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
