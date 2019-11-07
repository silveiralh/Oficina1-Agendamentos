package com.uniagenda.agenda.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.uniagenda.agenda.domain.enumeration.Curso;

/**
 * A Aluno.
 */
@Entity
@Table(name = "aluno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ra_aluno")
    private Double raAluno;

    @Column(name = "nome_aluno")
    private String nomeAluno;

    @Column(name = "periodo")
    private Double periodo;

    @Enumerated(EnumType.STRING)
    @Column(name = "curso")
    private Curso curso;

    @OneToMany(mappedBy = "aluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaAluno> raAlunos = new HashSet<>();

    @OneToMany(mappedBy = "aluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaReservaSala> raAlunos1 = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRaAluno() {
        return raAluno;
    }

    public Aluno raAluno(Double raAluno) {
        this.raAluno = raAluno;
        return this;
    }

    public void setRaAluno(Double raAluno) {
        this.raAluno = raAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public Aluno nomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
        return this;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public Double getPeriodo() {
        return periodo;
    }

    public Aluno periodo(Double periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(Double periodo) {
        this.periodo = periodo;
    }

    public Curso getCurso() {
        return curso;
    }

    public Aluno curso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Set<AgendaAluno> getRaAlunos() {
        return raAlunos;
    }

    public Aluno raAlunos(Set<AgendaAluno> agendaAlunos) {
        this.raAlunos = agendaAlunos;
        return this;
    }

    public Aluno addRaAluno(AgendaAluno agendaAluno) {
        this.raAlunos.add(agendaAluno);
        agendaAluno.setAluno(this);
        return this;
    }

    public Aluno removeRaAluno(AgendaAluno agendaAluno) {
        this.raAlunos.remove(agendaAluno);
        agendaAluno.setAluno(null);
        return this;
    }

    public void setRaAlunos(Set<AgendaAluno> agendaAlunos) {
        this.raAlunos = agendaAlunos;
    }

    public Set<AgendaReservaSala> getRaAlunos1() {
        return raAlunos1;
    }

    public Aluno raAlunos1(Set<AgendaReservaSala> agendaReservaSalas) {
        this.raAlunos1 = agendaReservaSalas;
        return this;
    }

    public Aluno addRaAluno(AgendaReservaSala agendaReservaSala) {
        this.raAlunos1.add(agendaReservaSala);
        agendaReservaSala.setAluno(this);
        return this;
    }

    public Aluno removeRaAluno(AgendaReservaSala agendaReservaSala) {
        this.raAlunos1.remove(agendaReservaSala);
        agendaReservaSala.setAluno(null);
        return this;
    }

    public void setRaAlunos1(Set<AgendaReservaSala> agendaReservaSalas) {
        this.raAlunos1 = agendaReservaSalas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aluno)) {
            return false;
        }
        return id != null && id.equals(((Aluno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Aluno{" +
            "id=" + getId() +
            ", raAluno=" + getRaAluno() +
            ", nomeAluno='" + getNomeAluno() + "'" +
            ", periodo=" + getPeriodo() +
            ", curso='" + getCurso() + "'" +
            "}";
    }
}
