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
 * A AgendaAluno.
 */
@Entity
@Table(name = "agenda_aluno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AgendaAluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAgenda status;

    @ManyToOne
    @JsonIgnoreProperties("agendaAlunos")
    private Aluno aluno;

    @OneToMany(mappedBy = "agendaAluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaAtendimentoServidor> statuses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("agendaAlunos")
    private DiasAulaAluno diasAulaAluno;

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

    public AgendaAluno status(StatusAgenda status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusAgenda status) {
        this.status = status;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public AgendaAluno aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Set<AgendaAtendimentoServidor> getStatuses() {
        return statuses;
    }

    public AgendaAluno statuses(Set<AgendaAtendimentoServidor> agendaAtendimentoServidors) {
        this.statuses = agendaAtendimentoServidors;
        return this;
    }

    public AgendaAluno addStatus(AgendaAtendimentoServidor agendaAtendimentoServidor) {
        this.statuses.add(agendaAtendimentoServidor);
        agendaAtendimentoServidor.setAgendaAluno(this);
        return this;
    }

    public AgendaAluno removeStatus(AgendaAtendimentoServidor agendaAtendimentoServidor) {
        this.statuses.remove(agendaAtendimentoServidor);
        agendaAtendimentoServidor.setAgendaAluno(null);
        return this;
    }

    public void setStatuses(Set<AgendaAtendimentoServidor> agendaAtendimentoServidors) {
        this.statuses = agendaAtendimentoServidors;
    }

    public DiasAulaAluno getDiasAulaAluno() {
        return diasAulaAluno;
    }

    public AgendaAluno diasAulaAluno(DiasAulaAluno diasAulaAluno) {
        this.diasAulaAluno = diasAulaAluno;
        return this;
    }

    public void setDiasAulaAluno(DiasAulaAluno diasAulaAluno) {
        this.diasAulaAluno = diasAulaAluno;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendaAluno)) {
            return false;
        }
        return id != null && id.equals(((AgendaAluno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AgendaAluno{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
