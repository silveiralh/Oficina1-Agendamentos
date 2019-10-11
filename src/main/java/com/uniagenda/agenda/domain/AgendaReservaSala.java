package com.uniagenda.agenda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
