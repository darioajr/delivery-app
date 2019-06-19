package com.senior.fsw.mboy.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.senior.fsw.mboy.domain.Empresa} entity.
 */
public class EmpresaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String razaoSocial;

    private String nomeFanstasia;

    @NotNull
    private String cnpj;

    @NotNull
    private String email;

    @NotNull
    private String contato;

    @NotNull
    private String status;

    @NotNull
    private String recargaPendente;

    @NotNull
    private String possuiBau;

    @NotNull
    private String endereco;

    @NotNull
    private String enderecoNumero;

    @NotNull
    private String enderecoComplemento;

    @NotNull
    private String enderecoBairro;

    @NotNull
    private String cep;


    private Long cidadeId;

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

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFanstasia() {
        return nomeFanstasia;
    }

    public void setNomeFanstasia(String nomeFanstasia) {
        this.nomeFanstasia = nomeFanstasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecargaPendente() {
        return recargaPendente;
    }

    public void setRecargaPendente(String recargaPendente) {
        this.recargaPendente = recargaPendente;
    }

    public String getPossuiBau() {
        return possuiBau;
    }

    public void setPossuiBau(String possuiBau) {
        this.possuiBau = possuiBau;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEnderecoNumero() {
        return enderecoNumero;
    }

    public void setEnderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    public String getEnderecoComplemento() {
        return enderecoComplemento;
    }

    public void setEnderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
    }

    public String getEnderecoBairro() {
        return enderecoBairro;
    }

    public void setEnderecoBairro(String enderecoBairro) {
        this.enderecoBairro = enderecoBairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Long cidadeId) {
        this.cidadeId = cidadeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmpresaDTO empresaDTO = (EmpresaDTO) o;
        if (empresaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empresaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmpresaDTO{" +
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
            ", cidade=" + getCidadeId() +
            "}";
    }
}
