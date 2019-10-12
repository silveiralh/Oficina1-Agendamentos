package com.uniagenda.agenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

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

    @ManyToOne
    @JsonIgnoreProperties("agendaAlunos")
    private Aluno aluno;

    @ManyToOne
    @JsonIgnoreProperties("agendaAlunos")
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

    public DiasAtendimento getDiasAtendimento() {
        return diasAtendimento;
    }

    public AgendaAluno diasAtendimento(DiasAtendimento diasAtendimento) {
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
