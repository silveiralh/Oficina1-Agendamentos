package com.utfpr.uniagenda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.utfpr.uniagenda.domain.enumeration.StatusAgenda;

/**
 * A AgendaAtendimentoServidor.
 */
@Entity
@Table(name = "agenda_atendimento_servidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "agendaatendimentoservidor")
public class AgendaAtendimentoServidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAgenda status;

    @OneToMany(mappedBy = "agendaAtendimentoServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaServidor> codSiapes = new HashSet<>();

    @OneToMany(mappedBy = "agendaAtendimentoServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaServidor> horarios = new HashSet<>();

    @OneToMany(mappedBy = "agendaAtendimentoServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaServidor> mes = new HashSet<>();

    @OneToMany(mappedBy = "agendaAtendimentoServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaServidor> diaMes = new HashSet<>();

    @OneToMany(mappedBy = "agendaAtendimentoServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaServidor> statuses = new HashSet<>();

    @OneToMany(mappedBy = "agendaAtendimentoServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aluno> raAlunos = new HashSet<>();

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

    public AgendaAtendimentoServidor status(StatusAgenda status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusAgenda status) {
        this.status = status;
    }

    public Set<AgendaServidor> getCodSiapes() {
        return codSiapes;
    }

    public AgendaAtendimentoServidor codSiapes(Set<AgendaServidor> agendaServidors) {
        this.codSiapes = agendaServidors;
        return this;
    }

    public AgendaAtendimentoServidor addCodSiape(AgendaServidor agendaServidor) {
        this.codSiapes.add(agendaServidor);
        agendaServidor.setAgendaAtendimentoServidor(this);
        return this;
    }

    public AgendaAtendimentoServidor removeCodSiape(AgendaServidor agendaServidor) {
        this.codSiapes.remove(agendaServidor);
        agendaServidor.setAgendaAtendimentoServidor(null);
        return this;
    }

    public void setCodSiapes(Set<AgendaServidor> agendaServidors) {
        this.codSiapes = agendaServidors;
    }

    public Set<AgendaServidor> getHorarios() {
        return horarios;
    }

    public AgendaAtendimentoServidor horarios(Set<AgendaServidor> agendaServidors) {
        this.horarios = agendaServidors;
        return this;
    }

    public AgendaAtendimentoServidor addHorario(AgendaServidor agendaServidor) {
        this.horarios.add(agendaServidor);
        agendaServidor.setAgendaAtendimentoServidor(this);
        return this;
    }

    public AgendaAtendimentoServidor removeHorario(AgendaServidor agendaServidor) {
        this.horarios.remove(agendaServidor);
        agendaServidor.setAgendaAtendimentoServidor(null);
        return this;
    }

    public void setHorarios(Set<AgendaServidor> agendaServidors) {
        this.horarios = agendaServidors;
    }

    public Set<AgendaServidor> getMes() {
        return mes;
    }

    public AgendaAtendimentoServidor mes(Set<AgendaServidor> agendaServidors) {
        this.mes = agendaServidors;
        return this;
    }

    public AgendaAtendimentoServidor addMes(AgendaServidor agendaServidor) {
        this.mes.add(agendaServidor);
        agendaServidor.setAgendaAtendimentoServidor(this);
        return this;
    }

    public AgendaAtendimentoServidor removeMes(AgendaServidor agendaServidor) {
        this.mes.remove(agendaServidor);
        agendaServidor.setAgendaAtendimentoServidor(null);
        return this;
    }

    public void setMes(Set<AgendaServidor> agendaServidors) {
        this.mes = agendaServidors;
    }

    public Set<AgendaServidor> getDiaMes() {
        return diaMes;
    }

    public AgendaAtendimentoServidor diaMes(Set<AgendaServidor> agendaServidors) {
        this.diaMes = agendaServidors;
        return this;
    }

    public AgendaAtendimentoServidor addDiaMes(AgendaServidor agendaServidor) {
        this.diaMes.add(agendaServidor);
        agendaServidor.setAgendaAtendimentoServidor(this);
        return this;
    }

    public AgendaAtendimentoServidor removeDiaMes(AgendaServidor agendaServidor) {
        this.diaMes.remove(agendaServidor);
        agendaServidor.setAgendaAtendimentoServidor(null);
        return this;
    }

    public void setDiaMes(Set<AgendaServidor> agendaServidors) {
        this.diaMes = agendaServidors;
    }

    public Set<AgendaServidor> getStatuses() {
        return statuses;
    }

    public AgendaAtendimentoServidor statuses(Set<AgendaServidor> agendaServidors) {
        this.statuses = agendaServidors;
        return this;
    }

    public AgendaAtendimentoServidor addStatus(AgendaServidor agendaServidor) {
        this.statuses.add(agendaServidor);
        agendaServidor.setAgendaAtendimentoServidor(this);
        return this;
    }

    public AgendaAtendimentoServidor removeStatus(AgendaServidor agendaServidor) {
        this.statuses.remove(agendaServidor);
        agendaServidor.setAgendaAtendimentoServidor(null);
        return this;
    }

    public void setStatuses(Set<AgendaServidor> agendaServidors) {
        this.statuses = agendaServidors;
    }

    public Set<Aluno> getRaAlunos() {
        return raAlunos;
    }

    public AgendaAtendimentoServidor raAlunos(Set<Aluno> alunos) {
        this.raAlunos = alunos;
        return this;
    }

    public AgendaAtendimentoServidor addRaAluno(Aluno aluno) {
        this.raAlunos.add(aluno);
        aluno.setAgendaAtendimentoServidor(this);
        return this;
    }

    public AgendaAtendimentoServidor removeRaAluno(Aluno aluno) {
        this.raAlunos.remove(aluno);
        aluno.setAgendaAtendimentoServidor(null);
        return this;
    }

    public void setRaAlunos(Set<Aluno> alunos) {
        this.raAlunos = alunos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendaAtendimentoServidor)) {
            return false;
        }
        return id != null && id.equals(((AgendaAtendimentoServidor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AgendaAtendimentoServidor{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
