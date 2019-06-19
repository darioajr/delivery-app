package com.senior.fsw.mboy.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.senior.fsw.mboy.domain.Motoboy} entity.
 */
public class MotoboyDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String sobrenome;

    @NotNull
    private String cnh;

    @NotNull
    private String placa;

    @NotNull
    private String email;

    @NotNull
    private String contato;

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

    @NotNull
    private String status;

    @NotNull
    private LocalDate data;


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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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

        MotoboyDTO motoboyDTO = (MotoboyDTO) o;
        if (motoboyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), motoboyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MotoboyDTO{" +
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
            ", cidade=" + getCidadeId() +
            "}";
    }
}
