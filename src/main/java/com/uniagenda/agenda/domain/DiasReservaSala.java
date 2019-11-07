package com.uniagenda.agenda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.uniagenda.agenda.domain.enumeration.StatusDia;

import com.uniagenda.agenda.domain.enumeration.Horario;

import com.uniagenda.agenda.domain.enumeration.DiaMes;

import com.uniagenda.agenda.domain.enumeration.DiaSemana;

import com.uniagenda.agenda.domain.enumeration.Mes;

/**
 * A DiasReservaSala.
 */
@Entity
@Table(name = "dias_reserva_sala")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DiasReservaSala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusDia status;

    @Enumerated(EnumType.STRING)
    @Column(name = "horario")
    private Horario horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_mes")
    private DiaMes diaMes;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "mes")
    private Mes mes;

    @OneToMany(mappedBy = "diasReservaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaReservaSala> statuses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusDia getStatus() {
        return status;
    }

    public DiasReservaSala status(StatusDia status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusDia status) {
        this.status = status;
    }

    public Horario getHorario() {
        return horario;
    }

    public DiasReservaSala horario(Horario horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public DiaMes getDiaMes() {
        return diaMes;
    }

    public DiasReservaSala diaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
        return this;
    }

    public void setDiaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public DiasReservaSala diaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Mes getMes() {
        return mes;
    }

    public DiasReservaSala mes(Mes mes) {
        this.mes = mes;
        return this;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public Set<AgendaReservaSala> getStatuses() {
        return statuses;
    }

    public DiasReservaSala statuses(Set<AgendaReservaSala> agendaReservaSalas) {
        this.statuses = agendaReservaSalas;
        return this;
    }

    public DiasReservaSala addStatus(AgendaReservaSala agendaReservaSala) {
        this.statuses.add(agendaReservaSala);
        agendaReservaSala.setDiasReservaSala(this);
        return this;
    }

    public DiasReservaSala removeStatus(AgendaReservaSala agendaReservaSala) {
        this.statuses.remove(agendaReservaSala);
        agendaReservaSala.setDiasReservaSala(null);
        return this;
    }

    public void setStatuses(Set<AgendaReservaSala> agendaReservaSalas) {
        this.statuses = agendaReservaSalas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiasReservaSala)) {
            return false;
        }
        return id != null && id.equals(((DiasReservaSala) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DiasReservaSala{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", horario='" + getHorario() + "'" +
            ", diaMes='" + getDiaMes() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", mes='" + getMes() + "'" +
            "}";
    }
}
