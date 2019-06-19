package com.senior.fsw.mboy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A GPSProcessor.
 */
@Entity
@Table(name = "gps_processor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GPSProcessor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lat", precision = 21, scale = 2)
    private BigDecimal lat;

    @Column(name = "lng", precision = 21, scale = 2)
    private BigDecimal lng;

    @Column(name = "ride")
    private String ride;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public GPSProcessor lat(BigDecimal lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public GPSProcessor lng(BigDecimal lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getRide() {
        return ride;
    }

    public GPSProcessor ride(String ride) {
        this.ride = ride;
        return this;
    }

    public void setRide(String ride) {
        this.ride = ride;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GPSProcessor)) {
            return false;
        }
        return id != null && id.equals(((GPSProcessor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GPSProcessor{" +
            "id=" + getId() +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", ride='" + getRide() + "'" +
            "}";
    }
}
