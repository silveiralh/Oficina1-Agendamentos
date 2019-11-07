package com.uniagenda.agenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.uniagenda.agenda.domain.enumeration.StatusAgenda;

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

    @OneToMany(mappedBy = "agendaServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaAtendimentoServidor> statuses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("agendaServidors")
    private DiasAtendimentoServidor diasAtendimentoServidor;

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

    public DiasAtendimentoServidor getDiasAtendimentoServidor() {
        return diasAtendimentoServidor;
    }

    public AgendaServidor diasAtendimentoServidor(DiasAtendimentoServidor diasAtendimentoServidor) {
        this.diasAtendimentoServidor = diasAtendimentoServidor;
        return this;
    }

    public void setDiasAtendimentoServidor(DiasAtendimentoServidor diasAtendimentoServidor) {
        this.diasAtendimentoServidor = diasAtendimentoServidor;
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
            "}";
    }
}
