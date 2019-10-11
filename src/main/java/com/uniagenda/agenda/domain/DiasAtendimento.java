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
    private Set<AgendaServidor> diaMes = new HashSet<>();

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaServidor> statusDias = new HashSet<>();

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaAluno> mes = new HashSet<>();

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaAluno> diaMes = new HashSet<>();

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaAluno> statusDias = new HashSet<>();

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaSala> mes = new HashSet<>();

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaSala> diaMes = new HashSet<>();

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaSala> statusDias = new HashSet<>();

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

    public Set<AgendaServidor> getDiaMes() {
        return diaMes;
    }

    public DiasAtendimento diaMes(Set<AgendaServidor> agendaServidors) {
        this.diaMes = agendaServidors;
        return this;
    }

    public DiasAtendimento addDiaMes(AgendaServidor agendaServidor) {
        this.diaMes.add(agendaServidor);
        agendaServidor.setDiasAtendimento(this);
        return this;
    }

    public DiasAtendimento removeDiaMes(AgendaServidor agendaServidor) {
        this.diaMes.remove(agendaServidor);
        agendaServidor.setDiasAtendimento(null);
        return this;
    }

    public void setDiaMes(Set<AgendaServidor> agendaServidors) {
        this.diaMes = agendaServidors;
    }

    public Set<AgendaServidor> getStatusDias() {
        return statusDias;
    }

    public DiasAtendimento statusDias(Set<AgendaServidor> agendaServidors) {
        this.statusDias = agendaServidors;
        return this;
    }

    public DiasAtendimento addStatusDia(AgendaServidor agendaServidor) {
        this.statusDias.add(agendaServidor);
        agendaServidor.setDiasAtendimento(this);
        return this;
    }

    public DiasAtendimento removeStatusDia(AgendaServidor agendaServidor) {
        this.statusDias.remove(agendaServidor);
        agendaServidor.setDiasAtendimento(null);
        return this;
    }

    public void setStatusDias(Set<AgendaServidor> agendaServidors) {
        this.statusDias = agendaServidors;
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

    public Set<AgendaAluno> getDiaMes() {
        return diaMes;
    }

    public DiasAtendimento diaMes(Set<AgendaAluno> agendaAlunos) {
        this.diaMes = agendaAlunos;
        return this;
    }

    public DiasAtendimento addDiaMes(AgendaAluno agendaAluno) {
        this.diaMes.add(agendaAluno);
        agendaAluno.setDiasAtendimento(this);
        return this;
    }

    public DiasAtendimento removeDiaMes(AgendaAluno agendaAluno) {
        this.diaMes.remove(agendaAluno);
        agendaAluno.setDiasAtendimento(null);
        return this;
    }

    public void setDiaMes(Set<AgendaAluno> agendaAlunos) {
        this.diaMes = agendaAlunos;
    }

    public Set<AgendaAluno> getStatusDias() {
        return statusDias;
    }

    public DiasAtendimento statusDias(Set<AgendaAluno> agendaAlunos) {
        this.statusDias = agendaAlunos;
        return this;
    }

    public DiasAtendimento addStatusDia(AgendaAluno agendaAluno) {
        this.statusDias.add(agendaAluno);
        agendaAluno.setDiasAtendimento(this);
        return this;
    }

    public DiasAtendimento removeStatusDia(AgendaAluno agendaAluno) {
        this.statusDias.remove(agendaAluno);
        agendaAluno.setDiasAtendimento(null);
        return this;
    }

    public void setStatusDias(Set<AgendaAluno> agendaAlunos) {
        this.statusDias = agendaAlunos;
    }

    public Set<AgendaSala> getMes() {
        return mes;
    }

    public DiasAtendimento mes(Set<AgendaSala> agendaSalas) {
        this.mes = agendaSalas;
        return this;
    }

    public DiasAtendimento addMes(AgendaSala agendaSala) {
        this.mes.add(agendaSala);
        agendaSala.setDiasAtendimento(this);
        return this;
    }

    public DiasAtendimento removeMes(AgendaSala agendaSala) {
        this.mes.remove(agendaSala);
        agendaSala.setDiasAtendimento(null);
        return this;
    }

    public void setMes(Set<AgendaSala> agendaSalas) {
        this.mes = agendaSalas;
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

    public Set<AgendaSala> getStatusDias() {
        return statusDias;
    }

    public DiasAtendimento statusDias(Set<AgendaSala> agendaSalas) {
        this.statusDias = agendaSalas;
        return this;
    }

    public DiasAtendimento addStatusDia(AgendaSala agendaSala) {
        this.statusDias.add(agendaSala);
        agendaSala.setDiasAtendimento(this);
        return this;
    }

    public DiasAtendimento removeStatusDia(AgendaSala agendaSala) {
        this.statusDias.remove(agendaSala);
        agendaSala.setDiasAtendimento(null);
        return this;
    }

    public void setStatusDias(Set<AgendaSala> agendaSalas) {
        this.statusDias = agendaSalas;
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
