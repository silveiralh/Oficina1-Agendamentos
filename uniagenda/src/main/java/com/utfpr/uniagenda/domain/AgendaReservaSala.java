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
 * A AgendaReservaSala.
 */
@Entity
@Table(name = "agenda_reserva_sala")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "agendareservasala")
public class AgendaReservaSala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAgenda status;

    @OneToMany(mappedBy = "agendaReservaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaSala> horarios = new HashSet<>();

    @OneToMany(mappedBy = "agendaReservaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaSala> mes = new HashSet<>();

    @OneToMany(mappedBy = "agendaReservaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaSala> diaMes = new HashSet<>();

    @OneToMany(mappedBy = "agendaReservaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaSala> statuses = new HashSet<>();

    @OneToMany(mappedBy = "agendaReservaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aluno> raAlunos = new HashSet<>();

    @OneToMany(mappedBy = "agendaReservaSala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaSala> codigoSalas = new HashSet<>();

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

    public Set<AgendaSala> getHorarios() {
        return horarios;
    }

    public AgendaReservaSala horarios(Set<AgendaSala> agendaSalas) {
        this.horarios = agendaSalas;
        return this;
    }

    public AgendaReservaSala addHorario(AgendaSala agendaSala) {
        this.horarios.add(agendaSala);
        agendaSala.setAgendaReservaSala(this);
        return this;
    }

    public AgendaReservaSala removeHorario(AgendaSala agendaSala) {
        this.horarios.remove(agendaSala);
        agendaSala.setAgendaReservaSala(null);
        return this;
    }

    public void setHorarios(Set<AgendaSala> agendaSalas) {
        this.horarios = agendaSalas;
    }

    public Set<AgendaSala> getMes() {
        return mes;
    }

    public AgendaReservaSala mes(Set<AgendaSala> agendaSalas) {
        this.mes = agendaSalas;
        return this;
    }

    public AgendaReservaSala addMes(AgendaSala agendaSala) {
        this.mes.add(agendaSala);
        agendaSala.setAgendaReservaSala(this);
        return this;
    }

    public AgendaReservaSala removeMes(AgendaSala agendaSala) {
        this.mes.remove(agendaSala);
        agendaSala.setAgendaReservaSala(null);
        return this;
    }

    public void setMes(Set<AgendaSala> agendaSalas) {
        this.mes = agendaSalas;
    }

    public Set<AgendaSala> getDiaMes() {
        return diaMes;
    }

    public AgendaReservaSala diaMes(Set<AgendaSala> agendaSalas) {
        this.diaMes = agendaSalas;
        return this;
    }

    public AgendaReservaSala addDiaMes(AgendaSala agendaSala) {
        this.diaMes.add(agendaSala);
        agendaSala.setAgendaReservaSala(this);
        return this;
    }

    public AgendaReservaSala removeDiaMes(AgendaSala agendaSala) {
        this.diaMes.remove(agendaSala);
        agendaSala.setAgendaReservaSala(null);
        return this;
    }

    public void setDiaMes(Set<AgendaSala> agendaSalas) {
        this.diaMes = agendaSalas;
    }

    public Set<AgendaSala> getStatuses() {
        return statuses;
    }

    public AgendaReservaSala statuses(Set<AgendaSala> agendaSalas) {
        this.statuses = agendaSalas;
        return this;
    }

    public AgendaReservaSala addStatus(AgendaSala agendaSala) {
        this.statuses.add(agendaSala);
        agendaSala.setAgendaReservaSala(this);
        return this;
    }

    public AgendaReservaSala removeStatus(AgendaSala agendaSala) {
        this.statuses.remove(agendaSala);
        agendaSala.setAgendaReservaSala(null);
        return this;
    }

    public void setStatuses(Set<AgendaSala> agendaSalas) {
        this.statuses = agendaSalas;
    }

    public Set<Aluno> getRaAlunos() {
        return raAlunos;
    }

    public AgendaReservaSala raAlunos(Set<Aluno> alunos) {
        this.raAlunos = alunos;
        return this;
    }

    public AgendaReservaSala addRaAluno(Aluno aluno) {
        this.raAlunos.add(aluno);
        aluno.setAgendaReservaSala(this);
        return this;
    }

    public AgendaReservaSala removeRaAluno(Aluno aluno) {
        this.raAlunos.remove(aluno);
        aluno.setAgendaReservaSala(null);
        return this;
    }

    public void setRaAlunos(Set<Aluno> alunos) {
        this.raAlunos = alunos;
    }

    public Set<AgendaSala> getCodigoSalas() {
        return codigoSalas;
    }

    public AgendaReservaSala codigoSalas(Set<AgendaSala> agendaSalas) {
        this.codigoSalas = agendaSalas;
        return this;
    }

    public AgendaReservaSala addCodigoSala(AgendaSala agendaSala) {
        this.codigoSalas.add(agendaSala);
        agendaSala.setAgendaReservaSala(this);
        return this;
    }

    public AgendaReservaSala removeCodigoSala(AgendaSala agendaSala) {
        this.codigoSalas.remove(agendaSala);
        agendaSala.setAgendaReservaSala(null);
        return this;
    }

    public void setCodigoSalas(Set<AgendaSala> agendaSalas) {
        this.codigoSalas = agendaSalas;
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
