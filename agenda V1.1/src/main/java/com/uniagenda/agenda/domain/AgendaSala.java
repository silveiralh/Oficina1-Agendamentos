package com.uniagenda.agenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne
    @JsonIgnoreProperties("agendaSalas")
    private Sala sala;

    @OneToMany(mappedBy = "agendaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaReservaSala> ids = new HashSet<>();

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

    public AgendaSala status(StatusDia status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusDia status) {
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

    public DiaMes getDiaMes() {
        return diaMes;
    }

    public AgendaSala diaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
        return this;
    }

    public void setDiaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public AgendaSala diaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Mes getMes() {
        return mes;
    }

    public AgendaSala mes(Mes mes) {
        this.mes = mes;
        return this;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public Sala getSala() {
        return sala;
    }

    public AgendaSala sala(Sala sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Set<AgendaReservaSala> getIds() {
        return ids;
    }

    public AgendaSala ids(Set<AgendaReservaSala> agendaReservaSalas) {
        this.ids = agendaReservaSalas;
        return this;
    }

    public AgendaSala addId(AgendaReservaSala agendaReservaSala) {
        this.ids.add(agendaReservaSala);
        agendaReservaSala.setAgendaSala(this);
        return this;
    }

    public AgendaSala removeId(AgendaReservaSala agendaReservaSala) {
        this.ids.remove(agendaReservaSala);
        agendaReservaSala.setAgendaSala(null);
        return this;
    }

    public void setIds(Set<AgendaReservaSala> agendaReservaSalas) {
        this.ids = agendaReservaSalas;
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
            ", diaMes='" + getDiaMes() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", mes='" + getMes() + "'" +
            "}";
    }
}
