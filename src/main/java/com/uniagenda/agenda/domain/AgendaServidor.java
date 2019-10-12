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
 * A AgendaServidor.
 */
@Entity
@Table(name = "agenda_servidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AgendaServidor implements Serializable {

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
    @JsonIgnoreProperties("agendaServidors")
    private Servidor servidor;

    @OneToMany(mappedBy = "agendaServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaAtendimentoServidor> codSiapes = new HashSet<>();

    @OneToMany(mappedBy = "agendaServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaAtendimentoServidor> statuses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("agendaServidors")
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

    public AgendaServidor status(StatusAgenda status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusAgenda status) {
        this.status = status;
    }

    public Horario getHorario() {
        return horario;
    }

    public AgendaServidor horario(Horario horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public AgendaServidor servidor(Servidor servidor) {
        this.servidor = servidor;
        return this;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public Set<AgendaAtendimentoServidor> getCodSiapes() {
        return codSiapes;
    }

    public AgendaServidor codSiapes(Set<AgendaAtendimentoServidor> agendaAtendimentoServidors) {
        this.codSiapes = agendaAtendimentoServidors;
        return this;
    }

    public AgendaServidor addCodSiape(AgendaAtendimentoServidor agendaAtendimentoServidor) {
        this.codSiapes.add(agendaAtendimentoServidor);
        agendaAtendimentoServidor.setAgendaServidor(this);
        return this;
    }

    public AgendaServidor removeCodSiape(AgendaAtendimentoServidor agendaAtendimentoServidor) {
        this.codSiapes.remove(agendaAtendimentoServidor);
        agendaAtendimentoServidor.setAgendaServidor(null);
        return this;
    }

    public void setCodSiapes(Set<AgendaAtendimentoServidor> agendaAtendimentoServidors) {
        this.codSiapes = agendaAtendimentoServidors;
    }

    public Set<AgendaAtendimentoServidor> getStatuses() {
        return statuses;
    }

    public AgendaServidor statuses(Set<AgendaAtendimentoServidor> agendaAtendimentoServidors) {
        this.statuses = agendaAtendimentoServidors;
        return this;
    }

    public AgendaServidor addStatus(AgendaAtendimentoServidor agendaAtendimentoServidor) {
        this.statuses.add(agendaAtendimentoServidor);
        agendaAtendimentoServidor.setAgendaServidor(this);
        return this;
    }

    public AgendaServidor removeStatus(AgendaAtendimentoServidor agendaAtendimentoServidor) {
        this.statuses.remove(agendaAtendimentoServidor);
        agendaAtendimentoServidor.setAgendaServidor(null);
        return this;
    }

    public void setStatuses(Set<AgendaAtendimentoServidor> agendaAtendimentoServidors) {
        this.statuses = agendaAtendimentoServidors;
    }

    public DiasAtendimento getDiasAtendimento() {
        return diasAtendimento;
    }

    public AgendaServidor diasAtendimento(DiasAtendimento diasAtendimento) {
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
        if (!(o instanceof AgendaServidor)) {
            return false;
        }
        return id != null && id.equals(((AgendaServidor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AgendaServidor{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", horario='" + getHorario() + "'" +
            "}";
    }
}
