package com.senior.fsw.mboy.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.senior.fsw.mboy.domain.enumeration.SiglaEstado;

/**
 * A DTO for the {@link com.senior.fsw.mboy.domain.Cidade} entity.
 */
public class CidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private SiglaEstado estado;


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

    public SiglaEstado getEstado() {
        return estado;
    }

    public void setEstado(SiglaEstado estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CidadeDTO cidadeDTO = (CidadeDTO) o;
        if (cidadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cidadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CidadeDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
