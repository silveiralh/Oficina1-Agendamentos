package com.uniagenda.agenda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.uniagenda.agenda.domain.enumeration.Semestre;

import com.uniagenda.agenda.domain.enumeration.Horario;

import com.uniagenda.agenda.domain.enumeration.DiaMes;

import com.uniagenda.agenda.domain.enumeration.DiaSemana;

/**
 * A DiasAulaAluno.
 */
@Entity
@Table(name = "dias_aula_aluno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DiasAulaAluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "semestre")
    private Semestre semestre;

    @Enumerated(EnumType.STRING)
    @Column(name = "horario")
    private Horario horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_mes")
    private DiaMes diaMes;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @OneToMany(mappedBy = "diasAulaAluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaAluno> sems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public DiasAulaAluno semestre(Semestre semestre) {
        this.semestre = semestre;
        return this;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Horario getHorario() {
        return horario;
    }

    public DiasAulaAluno horario(Horario horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public DiaMes getDiaMes() {
        return diaMes;
    }

    public DiasAulaAluno diaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
        return this;
    }

    public void setDiaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public DiasAulaAluno diaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Set<AgendaAluno> getSems() {
        return sems;
    }

    public DiasAulaAluno sems(Set<AgendaAluno> agendaAlunos) {
        this.sems = agendaAlunos;
        return this;
    }

    public DiasAulaAluno addSem(AgendaAluno agendaAluno) {
        this.sems.add(agendaAluno);
        agendaAluno.setDiasAulaAluno(this);
        return this;
    }

    public DiasAulaAluno removeSem(AgendaAluno agendaAluno) {
        this.sems.remove(agendaAluno);
        agendaAluno.setDiasAulaAluno(null);
        return this;
    }

    public void setSems(Set<AgendaAluno> agendaAlunos) {
        this.sems = agendaAlunos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiasAulaAluno)) {
            return false;
        }
        return id != null && id.equals(((DiasAulaAluno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DiasAulaAluno{" +
            "id=" + getId() +
            ", semestre='" + getSemestre() + "'" +
            ", horario='" + getHorario() + "'" +
            ", diaMes='" + getDiaMes() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            "}";
    }
}
