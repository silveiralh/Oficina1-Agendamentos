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

    @ManyToOne
    @JsonIgnoreProperties("agendaSalas")
    private Sala sala;

    @OneToMany(mappedBy = "agendaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaReservaSala> horarios = new HashSet<>();

    @OneToMany(mappedBy = "agendaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaReservaSala> diaMes = new HashSet<>();

    @OneToMany(mappedBy = "agendaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaReservaSala> statuses = new HashSet<>();

    @OneToMany(mappedBy = "agendaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaReservaSala> codigoSalas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("agendaSalas")
    private DiasAtendimento diasAtendimento;

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

    public Set<AgendaReservaSala> getHorarios() {
        return horarios;
    }

    public AgendaSala horarios(Set<AgendaReservaSala> agendaReservaSalas) {
        this.horarios = agendaReservaSalas;
        return this;
    }

    public AgendaSala addHorario(AgendaReservaSala agendaReservaSala) {
        this.horarios.add(agendaReservaSala);
        agendaReservaSala.setAgendaSala(this);
        return this;
    }

    public AgendaSala removeHorario(AgendaReservaSala agendaReservaSala) {
        this.horarios.remove(agendaReservaSala);
        agendaReservaSala.setAgendaSala(null);
        return this;
    }

    public void setHorarios(Set<AgendaReservaSala> agendaReservaSalas) {
        this.horarios = agendaReservaSalas;
    }

    public Set<AgendaReservaSala> getDiaMes() {
        return diaMes;
    }

    public AgendaSala diaMes(Set<AgendaReservaSala> agendaReservaSalas) {
        this.diaMes = agendaReservaSalas;
        return this;
    }

    public AgendaSala addDiaMes(AgendaReservaSala agendaReservaSala) {
        this.diaMes.add(agendaReservaSala);
        agendaReservaSala.setAgendaSala(this);
        return this;
    }

    public AgendaSala removeDiaMes(AgendaReservaSala agendaReservaSala) {
        this.diaMes.remove(agendaReservaSala);
        agendaReservaSala.setAgendaSala(null);
        return this;
    }

    public void setDiaMes(Set<AgendaReservaSala> agendaReservaSalas) {
        this.diaMes = agendaReservaSalas;
    }

    public Set<AgendaReservaSala> getStatuses() {
        return statuses;
    }

    public AgendaSala statuses(Set<AgendaReservaSala> agendaReservaSalas) {
        this.statuses = agendaReservaSalas;
        return this;
    }

    public AgendaSala addStatus(AgendaReservaSala agendaReservaSala) {
        this.statuses.add(agendaReservaSala);
        agendaReservaSala.setAgendaSala(this);
        return this;
    }

    public AgendaSala removeStatus(AgendaReservaSala agendaReservaSala) {
        this.statuses.remove(agendaReservaSala);
        agendaReservaSala.setAgendaSala(null);
        return this;
    }

    public void setStatuses(Set<AgendaReservaSala> agendaReservaSalas) {
        this.statuses = agendaReservaSalas;
    }

    public Set<AgendaReservaSala> getCodigoSalas() {
        return codigoSalas;
    }

    public AgendaSala codigoSalas(Set<AgendaReservaSala> agendaReservaSalas) {
        this.codigoSalas = agendaReservaSalas;
        return this;
    }

    public AgendaSala addCodigoSala(AgendaReservaSala agendaReservaSala) {
        this.codigoSalas.add(agendaReservaSala);
        agendaReservaSala.setAgendaSala(this);
        return this;
    }

    public AgendaSala removeCodigoSala(AgendaReservaSala agendaReservaSala) {
        this.codigoSalas.remove(agendaReservaSala);
        agendaReservaSala.setAgendaSala(null);
        return this;
    }

    public void setCodigoSalas(Set<AgendaReservaSala> agendaReservaSalas) {
        this.codigoSalas = agendaReservaSalas;
    }

    public DiasAtendimento getDiasAtendimento() {
        return diasAtendimento;
    }

    public AgendaSala diasAtendimento(DiasAtendimento diasAtendimento) {
        this.diasAtendimento = diasAtendimento;
        return this;
    }

    public void setDiasAtendimento(DiasAtendimento diasAtendimento) {
        this.diasAtendimento = diasAtendimento;
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
