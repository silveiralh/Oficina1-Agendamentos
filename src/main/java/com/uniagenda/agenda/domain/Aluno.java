package com.uniagenda.agenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

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

    @ManyToOne
    @JsonIgnoreProperties("alunos")
    private AgendaAtendimentoServidor agendaAtendimentoServidor;

    @ManyToOne
    @JsonIgnoreProperties("alunos")
    private AgendaAluno agendaAluno;

    @ManyToOne
    @JsonIgnoreProperties("alunos")
    private AgendaReservaSala agendaReservaSala;

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

    public AgendaAtendimentoServidor getAgendaAtendimentoServidor() {
        return agendaAtendimentoServidor;
    }

    public Aluno agendaAtendimentoServidor(AgendaAtendimentoServidor agendaAtendimentoServidor) {
        this.agendaAtendimentoServidor = agendaAtendimentoServidor;
        return this;
    }

    public void setAgendaAtendimentoServidor(AgendaAtendimentoServidor agendaAtendimentoServidor) {
        this.agendaAtendimentoServidor = agendaAtendimentoServidor;
    }

    public AgendaAluno getAgendaAluno() {
        return agendaAluno;
    }

    public Aluno agendaAluno(AgendaAluno agendaAluno) {
        this.agendaAluno = agendaAluno;
        return this;
    }

    public void setAgendaAluno(AgendaAluno agendaAluno) {
        this.agendaAluno = agendaAluno;
    }

    public AgendaReservaSala getAgendaReservaSala() {
        return agendaReservaSala;
    }

    public Aluno agendaReservaSala(AgendaReservaSala agendaReservaSala) {
        this.agendaReservaSala = agendaReservaSala;
        return this;
    }

    public void setAgendaReservaSala(AgendaReservaSala agendaReservaSala) {
        this.agendaReservaSala = agendaReservaSala;
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
