package com.senior.fsw.mboy.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.senior.fsw.mboy.domain.GPSProcessor} entity.
 */
public class GPSProcessorDTO implements Serializable {

    private Long id;

    private BigDecimal lat;

    private BigDecimal lng;

    private String ride;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getRide() {
        return ride;
    }

    public void setRide(String ride) {
        this.ride = ride;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GPSProcessorDTO gPSProcessorDTO = (GPSProcessorDTO) o;
        if (gPSProcessorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gPSProcessorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GPSProcessorDTO{" +
            "id=" + getId() +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", ride='" + getRide() + "'" +
            "}";
    }
}
