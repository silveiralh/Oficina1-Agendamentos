package com.uniagenda.agenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.uniagenda.agenda.domain.enumeration.Mes;

import com.uniagenda.agenda.domain.enumeration.DiaMes;

import com.uniagenda.agenda.domain.enumeration.DiaSemana;

import com.uniagenda.agenda.domain.enumeration.StatusDia;

/**
 * A DiasAtendimento.
 */
@Entity
@Table(name = "dias_atendimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DiasAtendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mes")
    private Mes mes;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_mes")
    private DiaMes diaMes;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_dia")
    private StatusDia statusDia;

    @ManyToOne
    @JsonIgnoreProperties("diasAtendimentos")
    private AgendaAluno agendaAluno;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mes getMes() {
        return mes;
    }

    public DiasAtendimento mes(Mes mes) {
        this.mes = mes;
        return this;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public DiaMes getDiaMes() {
        return diaMes;
    }

    public DiasAtendimento diaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
        return this;
    }

    public void setDiaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public DiasAtendimento diaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public StatusDia getStatusDia() {
        return statusDia;
    }

    public DiasAtendimento statusDia(StatusDia statusDia) {
        this.statusDia = statusDia;
        return this;
    }

    public void setStatusDia(StatusDia statusDia) {
        this.statusDia = statusDia;
    }

    public AgendaAluno getAgendaAluno() {
        return agendaAluno;
    }

    public DiasAtendimento agendaAluno(AgendaAluno agendaAluno) {
        this.agendaAluno = agendaAluno;
        return this;
    }

    public void setAgendaAluno(AgendaAluno agendaAluno) {
        this.agendaAluno = agendaAluno;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiasAtendimento)) {
            return false;
        }
        return id != null && id.equals(((DiasAtendimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DiasAtendimento{" +
            "id=" + getId() +
            ", mes='" + getMes() + "'" +
            ", diaMes='" + getDiaMes() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", statusDia='" + getStatusDia() + "'" +
            "}";
    }
}
