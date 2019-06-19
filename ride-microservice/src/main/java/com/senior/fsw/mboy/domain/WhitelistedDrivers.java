package com.senior.fsw.mboy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A WhitelistedDrivers.
 */
@Entity
@Table(name = "whitelisted_drivers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WhitelistedDrivers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "driver", nullable = false)
    private String driver;

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

    public WhitelistedDrivers driver(String driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WhitelistedDrivers)) {
            return false;
        }
        return id != null && id.equals(((WhitelistedDrivers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WhitelistedDrivers{" +
            "id=" + getId() +
            ", driver='" + getDriver() + "'" +
            "}";
    }
}
