package com.senior.fsw.mboy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A WhitelistedRiders.
 */
@Entity
@Table(name = "whitelisted_riders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WhitelistedRiders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "passanger", nullable = false)
    private String passanger;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassanger() {
        return passanger;
    }

    public WhitelistedRiders passanger(String passanger) {
        this.passanger = passanger;
        return this;
    }

    public void setPassanger(String passanger) {
        this.passanger = passanger;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WhitelistedRiders)) {
            return false;
        }
        return id != null && id.equals(((WhitelistedRiders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WhitelistedRiders{" +
            "id=" + getId() +
            ", passanger='" + getPassanger() + "'" +
            "}";
    }
}
