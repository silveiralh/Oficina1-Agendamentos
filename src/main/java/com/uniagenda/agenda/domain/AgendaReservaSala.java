package com.uniagenda.agenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.uniagenda.agenda.domain.enumeration.StatusAgenda;

/**
 * A AgendaReservaSala.
 */
@Entity
@Table(name = "agenda_reserva_sala")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AgendaReservaSala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAgenda status;

    @ManyToOne
    @JsonIgnoreProperties("agendaReservaSalas")
    private Aluno aluno;

    @ManyToOne
    @JsonIgnoreProperties("agendaReservaSalas")
    private AgendaSala agendaSala;

    @ManyToOne
    @JsonIgnoreProperties("agendaReservaSalas")
    private DiasReservaSala diasReservaSala;

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

    public AgendaReservaSala status(StatusAgenda status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusAgenda status) {
        this.status = status;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public AgendaReservaSala aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public AgendaSala getAgendaSala() {
        return agendaSala;
    }

    public AgendaReservaSala agendaSala(AgendaSala agendaSala) {
        this.agendaSala = agendaSala;
        return this;
    }

    public void setAgendaSala(AgendaSala agendaSala) {
        this.agendaSala = agendaSala;
    }

    public DiasReservaSala getDiasReservaSala() {
        return diasReservaSala;
    }

    public AgendaReservaSala diasReservaSala(DiasReservaSala diasReservaSala) {
        this.diasReservaSala = diasReservaSala;
        return this;
    }

    public void setDiasReservaSala(DiasReservaSala diasReservaSala) {
        this.diasReservaSala = diasReservaSala;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendaReservaSala)) {
            return false;
        }
        return id != null && id.equals(((AgendaReservaSala) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AgendaReservaSala{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
