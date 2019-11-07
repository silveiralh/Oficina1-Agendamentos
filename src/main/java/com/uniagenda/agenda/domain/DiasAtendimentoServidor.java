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
 * A DiasAtendimentoServidor.
 */
@Entity
@Table(name = "dias_atendimento_servidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DiasAtendimentoServidor implements Serializable {

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
    @JsonIgnoreProperties("diasAtendimentoServidors")
    private Servidor servidor;

    @OneToMany(mappedBy = "diasAtendimentoServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaServidor> statuses = new HashSet<>();

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

    public DiasAtendimentoServidor mes(Mes mes) {
        this.mes = mes;
        return this;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public DiaMes getDiaMes() {
        return diaMes;
    }

    public DiasAtendimentoServidor diaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
        return this;
    }

    public void setDiaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public DiasAtendimentoServidor diaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public StatusDia getStatusDia() {
        return statusDia;
    }

    public DiasAtendimentoServidor statusDia(StatusDia statusDia) {
        this.statusDia = statusDia;
        return this;
    }

    public void setStatusDia(StatusDia statusDia) {
        this.statusDia = statusDia;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public DiasAtendimentoServidor servidor(Servidor servidor) {
        this.servidor = servidor;
        return this;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public Set<AgendaServidor> getStatuses() {
        return statuses;
    }

    public DiasAtendimentoServidor statuses(Set<AgendaServidor> agendaServidors) {
        this.statuses = agendaServidors;
        return this;
    }

    public DiasAtendimentoServidor addStatus(AgendaServidor agendaServidor) {
        this.statuses.add(agendaServidor);
        agendaServidor.setDiasAtendimentoServidor(this);
        return this;
    }

    public DiasAtendimentoServidor removeStatus(AgendaServidor agendaServidor) {
        this.statuses.remove(agendaServidor);
        agendaServidor.setDiasAtendimentoServidor(null);
        return this;
    }

    public void setStatuses(Set<AgendaServidor> agendaServidors) {
        this.statuses = agendaServidors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiasAtendimentoServidor)) {
            return false;
        }
        return id != null && id.equals(((DiasAtendimentoServidor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DiasAtendimentoServidor{" +
            "id=" + getId() +
            ", mes='" + getMes() + "'" +
            ", diaMes='" + getDiaMes() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", statusDia='" + getStatusDia() + "'" +
            "}";
    }
}
