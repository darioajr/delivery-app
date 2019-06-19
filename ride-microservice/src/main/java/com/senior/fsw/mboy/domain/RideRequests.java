package com.senior.fsw.mboy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RideRequests.
 */
@Entity
@Table(name = "ride_requests")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RideRequests implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver")
    private String driver;

    @NotNull
    @Column(name = "passenger", nullable = false)
    private String passenger;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriver() {
        return driver;
    }

    public RideRequests driver(String driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPassenger() {
        return passenger;
    }

    public RideRequests passenger(String passenger) {
        this.passenger = passenger;
        return this;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RideRequests)) {
            return false;
        }
        return id != null && id.equals(((RideRequests) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RideRequests{" +
            "id=" + getId() +
            ", driver='" + getDriver() + "'" +
            ", passenger='" + getPassenger() + "'" +
            "}";
    }
}
