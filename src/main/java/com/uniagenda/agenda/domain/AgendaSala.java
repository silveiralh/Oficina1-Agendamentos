package com.uniagenda.agenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.uniagenda.agenda.domain.enumeration.StatusAgenda;

import com.uniagenda.agenda.domain.enumeration.Horario;

/**
 * A AgendaSala.
 */
@Entity
@Table(name = "agenda_sala")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AgendaSala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAgenda status;

    @Enumerated(EnumType.STRING)
    @Column(name = "horario")
    private Horario horario;

    @OneToMany(mappedBy = "agendaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sala> codigoSalas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("agendaSalas")
    private AgendaReservaSala agendaReservaSala;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusAgenda getStatus() {
        return status;
    }

    public AgendaSala status(StatusAgenda status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusAgenda status) {
        this.status = status;
    }

    public Horario getHorario() {
        return horario;
    }

    public AgendaSala horario(Horario horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Set<Sala> getCodigoSalas() {
        return codigoSalas;
    }

    public AgendaSala codigoSalas(Set<Sala> salas) {
        this.codigoSalas = salas;
        return this;
    }

    public AgendaSala addCodigoSala(Sala sala) {
        this.codigoSalas.add(sala);
        sala.setAgendaSala(this);
        return this;
    }

    public AgendaSala removeCodigoSala(Sala sala) {
        this.codigoSalas.remove(sala);
        sala.setAgendaSala(null);
        return this;
    }

    public void setCodigoSalas(Set<Sala> salas) {
        this.codigoSalas = salas;
    }

    public AgendaReservaSala getAgendaReservaSala() {
        return agendaReservaSala;
    }

    public AgendaSala agendaReservaSala(AgendaReservaSala agendaReservaSala) {
        this.agendaReservaSala = agendaReservaSala;
        return this;
    }

    public void setAgendaReservaSala(AgendaReservaSala agendaReservaSala) {
        this.agendaReservaSala = agendaReservaSala;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendaSala)) {
            return false;
        }
        return id != null && id.equals(((AgendaSala) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AgendaSala{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", horario='" + getHorario() + "'" +
            "}";
    }
}
