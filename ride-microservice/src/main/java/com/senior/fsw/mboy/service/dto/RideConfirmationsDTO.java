package com.senior.fsw.mboy.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.senior.fsw.mboy.domain.RideConfirmations} entity.
 */
public class RideConfirmationsDTO implements Serializable {

    private Long id;

    @NotNull
    private String passenger;

    @NotNull
    private String driver;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RideConfirmationsDTO rideConfirmationsDTO = (RideConfirmationsDTO) o;
        if (rideConfirmationsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rideConfirmationsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RideConfirmationsDTO{" +
            "id=" + getId() +
            ", passenger='" + getPassenger() + "'" +
            ", driver='" + getDriver() + "'" +
            "}";
    }
}
