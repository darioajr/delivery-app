package com.senior.fsw.mboy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(name = "nome_fanstasia")
    private String nomeFanstasia;

    @NotNull
    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "contato", nullable = false)
    private String contato;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "recarga_pendente", nullable = false)
    private String recargaPendente;

    @NotNull
    @Column(name = "possui_bau", nullable = false)
    private String possuiBau;

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

    public Empresa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public Empresa razaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFanstasia() {
        return nomeFanstasia;
    }

    public Empresa nomeFanstasia(String nomeFanstasia) {
        this.nomeFanstasia = nomeFanstasia;
        return this;
    }

    public void setNomeFanstasia(String nomeFanstasia) {
        this.nomeFanstasia = nomeFanstasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Empresa cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public Empresa email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContato() {
        return contato;
    }

    public Empresa contato(String contato) {
        this.contato = contato;
        return this;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getStatus() {
        return status;
    }

    public Empresa status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecargaPendente() {
        return recargaPendente;
    }

    public Empresa recargaPendente(String recargaPendente) {
        this.recargaPendente = recargaPendente;
        return this;
    }

    public void setRecargaPendente(String recargaPendente) {
        this.recargaPendente = recargaPendente;
    }

    public String getPossuiBau() {
        return possuiBau;
    }

    public Empresa possuiBau(String possuiBau) {
        this.possuiBau = possuiBau;
        return this;
    }

    public void setPossuiBau(String possuiBau) {
        this.possuiBau = possuiBau;
    }

    public String getEndereco() {
        return endereco;
    }

    public Empresa endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEnderecoNumero() {
        return enderecoNumero;
    }

    public Empresa enderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
        return this;
    }

    public void setEnderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    public String getEnderecoComplemento() {
        return enderecoComplemento;
    }

    public Empresa enderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
        return this;
    }

    public void setEnderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
    }

    public String getEnderecoBairro() {
        return enderecoBairro;
    }

    public Empresa enderecoBairro(String enderecoBairro) {
        this.enderecoBairro = enderecoBairro;
        return this;
    }

    public void setEnderecoBairro(String enderecoBairro) {
        this.enderecoBairro = enderecoBairro;
    }

    public String getCep() {
        return cep;
    }

    public Empresa cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public Empresa cidade(Cidade cidade) {
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
        if (!(o instanceof Empresa)) {
            return false;
        }
        return id != null && id.equals(((Empresa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", nomeFanstasia='" + getNomeFanstasia() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", email='" + getEmail() + "'" +
            ", contato='" + getContato() + "'" +
            ", status='" + getStatus() + "'" +
            ", recargaPendente='" + getRecargaPendente() + "'" +
            ", possuiBau='" + getPossuiBau() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", enderecoNumero='" + getEnderecoNumero() + "'" +
            ", enderecoComplemento='" + getEnderecoComplemento() + "'" +
            ", enderecoBairro='" + getEnderecoBairro() + "'" +
            ", cep='" + getCep() + "'" +
            "}";
    }
}
