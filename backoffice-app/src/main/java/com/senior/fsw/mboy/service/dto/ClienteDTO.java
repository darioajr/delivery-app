package com.senior.fsw.mboy.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.senior.fsw.mboy.domain.Cliente} entity.
 */
public class ClienteDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String sobrenome;

    @NotNull
    private String email;

    @NotNull
    private String contato;

    @NotNull
    private String status;

    @NotNull
    private LocalDate data;


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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (clienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sobrenome='" + getSobrenome() + "'" +
            ", email='" + getEmail() + "'" +
            ", contato='" + getContato() + "'" +
            ", status='" + getStatus() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
