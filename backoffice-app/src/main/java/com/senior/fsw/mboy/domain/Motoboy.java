package com.senior.fsw.mboy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Motoboy.
 */
@Entity
@Table(name = "motoboy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Motoboy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;

    @NotNull
    @Column(name = "cnh", nullable = false)
    private String cnh;

    @NotNull
    @Column(name = "placa", nullable = false)
    private String placa;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "contato", nullable = false)
    private String contato;

    @NotNull
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @NotNull
    @Column(name = "endereco_numero", nullable = false)
    private String enderecoNumero;

    @NotNull
    @Column(name = "endereco_complemento", nullable = false)
    private String enderecoComplemento;

    @NotNull
    @Column(name = "endereco_bairro", nullable = false)
    private String enderecoBairro;

    @NotNull
    @Column(name = "cep", nullable = false)
    private String cep;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @OneToOne
    @JoinColumn(unique = true)
    private Cidade cidade;

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

    public Motoboy nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Motoboy sobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
        return this;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCnh() {
        return cnh;
    }

    public Motoboy cnh(String cnh) {
        this.cnh = cnh;
        return this;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getPlaca() {
        return placa;
    }

    public Motoboy placa(String placa) {
        this.placa = placa;
        return this;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEmail() {
        return email;
    }

    public Motoboy email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContato() {
        return contato;
    }

    public Motoboy contato(String contato) {
        this.contato = contato;
        return this;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEndereco() {
        return endereco;
    }

    public Motoboy endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEnderecoNumero() {
        return enderecoNumero;
    }

    public Motoboy enderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
        return this;
    }

    public void setEnderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    public String getEnderecoComplemento() {
        return enderecoComplemento;
    }

    public Motoboy enderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
        return this;
    }

    public void setEnderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
    }

    public String getEnderecoBairro() {
        return enderecoBairro;
    }

    public Motoboy enderecoBairro(String enderecoBairro) {
        this.enderecoBairro = enderecoBairro;
        return this;
    }

    public void setEnderecoBairro(String enderecoBairro) {
        this.enderecoBairro = enderecoBairro;
    }

    public String getCep() {
        return cep;
    }

    public Motoboy cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getStatus() {
        return status;
    }

    public Motoboy status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getData() {
        return data;
    }

    public Motoboy data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public Motoboy cidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Motoboy)) {
            return false;
        }
        return id != null && id.equals(((Motoboy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Motoboy{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sobrenome='" + getSobrenome() + "'" +
            ", cnh='" + getCnh() + "'" +
            ", placa='" + getPlaca() + "'" +
            ", email='" + getEmail() + "'" +
            ", contato='" + getContato() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", enderecoNumero='" + getEnderecoNumero() + "'" +
            ", enderecoComplemento='" + getEnderecoComplemento() + "'" +
            ", enderecoBairro='" + getEnderecoBairro() + "'" +
            ", cep='" + getCep() + "'" +
            ", status='" + getStatus() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
