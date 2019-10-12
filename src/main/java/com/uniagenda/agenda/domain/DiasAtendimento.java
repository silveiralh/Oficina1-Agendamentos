package com.uniagenda.agenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    private Servidor servidor;

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaServidor> mes = new HashSet<>();

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaAluno> mes = new HashSet<>();

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaSala> diaMes = new HashSet<>();

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

    public Servidor getServidor() {
        return servidor;
    }

    public DiasAtendimento servidor(Servidor servidor) {
        this.servidor = servidor;
        return this;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public Set<AgendaServidor> getMes() {
        return mes;
    }

    public DiasAtendimento mes(Set<AgendaServidor> agendaServidors) {
        this.mes = agendaServidors;
        return this;
    }

    public DiasAtendimento addMes(AgendaServidor agendaServidor) {
        this.mes.add(agendaServidor);
        agendaServidor.setDiasAtendimento(this);
        return this;
    }

    public DiasAtendimento removeMes(AgendaServidor agendaServidor) {
        this.mes.remove(agendaServidor);
        agendaServidor.setDiasAtendimento(null);
        return this;
    }

    public void setMes(Set<AgendaServidor> agendaServidors) {
        this.mes = agendaServidors;
    }

    public Set<AgendaAluno> getMes() {
        return mes;
    }

    public DiasAtendimento mes(Set<AgendaAluno> agendaAlunos) {
        this.mes = agendaAlunos;
        return this;
    }

    public DiasAtendimento addMes(AgendaAluno agendaAluno) {
        this.mes.add(agendaAluno);
        agendaAluno.setDiasAtendimento(this);
        return this;
    }

    public DiasAtendimento removeMes(AgendaAluno agendaAluno) {
        this.mes.remove(agendaAluno);
        agendaAluno.setDiasAtendimento(null);
        return this;
    }

    public void setMes(Set<AgendaAluno> agendaAlunos) {
        this.mes = agendaAlunos;
    }

    public Set<AgendaSala> getDiaMes() {
        return diaMes;
    }

    public DiasAtendimento diaMes(Set<AgendaSala> agendaSalas) {
        this.diaMes = agendaSalas;
        return this;
    }

    public DiasAtendimento addDiaMes(AgendaSala agendaSala) {
        this.diaMes.add(agendaSala);
        agendaSala.setDiasAtendimento(this);
        return this;
    }

    public DiasAtendimento removeDiaMes(AgendaSala agendaSala) {
        this.diaMes.remove(agendaSala);
        agendaSala.setDiasAtendimento(null);
        return this;
    }

    public void setDiaMes(Set<AgendaSala> agendaSalas) {
        this.diaMes = agendaSalas;
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
