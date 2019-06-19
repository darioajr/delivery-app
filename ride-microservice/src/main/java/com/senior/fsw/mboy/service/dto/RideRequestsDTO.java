package com.senior.fsw.mboy.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.senior.fsw.mboy.domain.RideRequests} entity.
 */
public class RideRequestsDTO implements Serializable {

    private Long id;

    private String driver;

    @NotNull
    private String passenger;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RideRequestsDTO rideRequestsDTO = (RideRequestsDTO) o;
        if (rideRequestsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rideRequestsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RideRequestsDTO{" +
            "id=" + getId() +
            ", driver='" + getDriver() + "'" +
            ", passenger='" + getPassenger() + "'" +
            "}";
    }
}
