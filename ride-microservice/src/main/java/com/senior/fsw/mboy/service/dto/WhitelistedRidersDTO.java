package com.senior.fsw.mboy.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.senior.fsw.mboy.domain.WhitelistedRiders} entity.
 */
public class WhitelistedRidersDTO implements Serializable {

    private Long id;

    @NotNull
    private String passanger;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassanger() {
        return passanger;
    }

    public void setPassanger(String passanger) {
        this.passanger = passanger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WhitelistedRidersDTO whitelistedRidersDTO = (WhitelistedRidersDTO) o;
        if (whitelistedRidersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), whitelistedRidersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WhitelistedRidersDTO{" +
            "id=" + getId() +
            ", passanger='" + getPassanger() + "'" +
            "}";
    }
}
