package com.senior.fsw.mboy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CompletedRides.
 */
@Entity
@Table(name = "completed_rides")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompletedRides implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ride", nullable = false)
    private String ride;

    @NotNull
    @Column(name = "driver", nullable = false)
    private String driver;

    @NotNull
    @Column(name = "passenger", nullable = false)
    private String passenger;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Long startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Long endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRide() {
        return ride;
    }

    public CompletedRides ride(String ride) {
        this.ride = ride;
        return this;
    }

    public void setRide(String ride) {
        this.ride = ride;
    }

    public String getDriver() {
        return driver;
    }

    public CompletedRides driver(String driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPassenger() {
        return passenger;
    }

    public CompletedRides passenger(String passenger) {
        this.passenger = passenger;
        return this;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public Long getStartTime() {
        return startTime;
    }

    public CompletedRides startTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public CompletedRides endTime(Long endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompletedRides)) {
            return false;
        }
        return id != null && id.equals(((CompletedRides) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompletedRides{" +
            "id=" + getId() +
            ", ride='" + getRide() + "'" +
            ", driver='" + getDriver() + "'" +
            ", passenger='" + getPassenger() + "'" +
            ", startTime=" + getStartTime() +
            ", endTime=" + getEndTime() +
            "}";
    }
}
