package com.uniagenda.agenda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.uniagenda.agenda.domain.enumeration.StatusAgenda;

import com.uniagenda.agenda.domain.enumeration.Horario;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "horario")
    private Horario horario;

    @OneToMany(mappedBy = "agendaAluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DiasAtendimento> statusDias = new HashSet<>();

    @OneToMany(mappedBy = "agendaAluno")
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

    public AgendaAluno status(StatusAgenda status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusAgenda status) {
        this.status = status;
    }

    public Horario getHorario() {
        return horario;
    }

    public AgendaAluno horario(Horario horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Set<DiasAtendimento> getStatusDias() {
        return statusDias;
    }

    public AgendaAluno statusDias(Set<DiasAtendimento> diasAtendimentos) {
        this.statusDias = diasAtendimentos;
        return this;
    }

    public AgendaAluno addStatusDia(DiasAtendimento diasAtendimento) {
        this.statusDias.add(diasAtendimento);
        diasAtendimento.setAgendaAluno(this);
        return this;
    }

    public AgendaAluno removeStatusDia(DiasAtendimento diasAtendimento) {
        this.statusDias.remove(diasAtendimento);
        diasAtendimento.setAgendaAluno(null);
        return this;
    }

    public void setStatusDias(Set<DiasAtendimento> diasAtendimentos) {
        this.statusDias = diasAtendimentos;
    }

    public Set<Aluno> getRaAlunos() {
        return raAlunos;
    }

    public AgendaAluno raAlunos(Set<Aluno> alunos) {
        this.raAlunos = alunos;
        return this;
    }

    public AgendaAluno addRaAluno(Aluno aluno) {
        this.raAlunos.add(aluno);
        aluno.setAgendaAluno(this);
        return this;
    }

    public AgendaAluno removeRaAluno(Aluno aluno) {
        this.raAlunos.remove(aluno);
        aluno.setAgendaAluno(null);
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
            ", horario='" + getHorario() + "'" +
            "}";
    }
}
