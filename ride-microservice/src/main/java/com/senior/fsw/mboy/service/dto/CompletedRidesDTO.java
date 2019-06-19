package com.senior.fsw.mboy.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.senior.fsw.mboy.domain.CompletedRides} entity.
 */
public class CompletedRidesDTO implements Serializable {

    private Long id;

    @NotNull
    private String ride;

    @NotNull
    private String driver;

    @NotNull
    private String passenger;

    @NotNull
    private Long startTime;

    @NotNull
    private Long endTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRide() {
        return ride;
    }

    public void setRide(String ride) {
        this.ride = ride;
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

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompletedRidesDTO completedRidesDTO = (CompletedRidesDTO) o;
        if (completedRidesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), completedRidesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompletedRidesDTO{" +
            "id=" + getId() +
            ", ride='" + getRide() + "'" +
            ", driver='" + getDriver() + "'" +
            ", passenger='" + getPassenger() + "'" +
            ", startTime=" + getStartTime() +
            ", endTime=" + getEndTime() +
            "}";
    }
}
