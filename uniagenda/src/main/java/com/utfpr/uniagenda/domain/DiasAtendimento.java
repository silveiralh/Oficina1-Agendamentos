package com.utfpr.uniagenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.utfpr.uniagenda.domain.enumeration.Mes;

import com.utfpr.uniagenda.domain.enumeration.DiaMes;

import com.utfpr.uniagenda.domain.enumeration.DiaSemana;

import com.utfpr.uniagenda.domain.enumeration.StatusDia;

/**
 * A DiasAtendimento.
 */
@Entity
@Table(name = "dias_atendimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "diasatendimento")
public class DiasAtendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mes")
    private Mes mes;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_mes")
    private DiaMes diaMes;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_dia")
    private StatusDia statusDia;

    @ManyToOne
    @JsonIgnoreProperties("diasAtendimentos")
    private AgendaServidor agendaServidor;

 

    @ManyToOne
    @JsonIgnoreProperties("diasAtendimentos")
    private AgendaAluno agendaAluno;

   

    @ManyToOne
    @JsonIgnoreProperties("diasAtendimentos")
    private AgendaSala agendaSala;

   

    @OneToMany(mappedBy = "diasAtendimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Servidor> codSiapes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mes getMes() {
        return mes;
    }

    public DiasAtendimento mes(Mes mes) {
        this.mes = mes;
        return this;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public DiaMes getDiaMes() {
        return diaMes;
    }

    public DiasAtendimento diaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
        return this;
    }

    public void setDiaMes(DiaMes diaMes) {
        this.diaMes = diaMes;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public DiasAtendimento diaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public StatusDia getStatusDia() {
        return statusDia;
    }

    public DiasAtendimento statusDia(StatusDia statusDia) {
        this.statusDia = statusDia;
        return this;
    }

    public void setStatusDia(StatusDia statusDia) {
        this.statusDia = statusDia;
    }

    public AgendaServidor getAgendaServidor() {
        return agendaServidor;
    }

    public DiasAtendimento agendaServidor(AgendaServidor agendaServidor) {
        this.agendaServidor = agendaServidor;
        return this;
    }

    public void setAgendaServidor(AgendaServidor agendaServidor) {
        this.agendaServidor = agendaServidor;
    }

   

    public AgendaAluno getAgendaAluno() {
        return agendaAluno;
    }

    public DiasAtendimento agendaAluno(AgendaAluno agendaAluno) {
        this.agendaAluno = agendaAluno;
        return this;
    }

    public void setAgendaAluno(AgendaAluno agendaAluno) {
        this.agendaAluno = agendaAluno;
    }

   

    public AgendaSala getAgendaSala() {
        return agendaSala;
    }

    public DiasAtendimento agendaSala(AgendaSala agendaSala) {
        this.agendaSala = agendaSala;
        return this;
    }

    public void setAgendaSala(AgendaSala agendaSala) {
        this.agendaSala = agendaSala;
    }

    

    public Set<Servidor> getCodSiapes() {
        return codSiapes;
    }

    public DiasAtendimento codSiapes(Set<Servidor> servidors) {
        this.codSiapes = servidors;
        return this;
    }

    public DiasAtendimento addCodSiape(Servidor servidor) {
        this.codSiapes.add(servidor);
        servidor.setDiasAtendimento(this);
        return this;
    }

    public DiasAtendimento removeCodSiape(Servidor servidor) {
        this.codSiapes.remove(servidor);
        servidor.setDiasAtendimento(null);
        return this;
    }

    public void setCodSiapes(Set<Servidor> servidors) {
        this.codSiapes = servidors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiasAtendimento)) {
            return false;
        }
        return id != null && id.equals(((DiasAtendimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DiasAtendimento{" +
            "id=" + getId() +
            ", mes='" + getMes() + "'" +
            ", diaMes='" + getDiaMes() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", statusDia='" + getStatusDia() + "'" +
            "}";
    }
}
