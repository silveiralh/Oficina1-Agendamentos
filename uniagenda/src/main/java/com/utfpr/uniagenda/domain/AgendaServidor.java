package com.utfpr.uniagenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.utfpr.uniagenda.domain.enumeration.StatusAgenda;

import com.utfpr.uniagenda.domain.enumeration.Horario;

/**
 * A AgendaServidor.
 */
@Entity
@Table(name = "agenda_servidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "agendaservidor")
public class AgendaServidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAgenda status;

    @Enumerated(EnumType.STRING)
    @Column(name = "horario")
    private Horario horario;

    @ManyToOne
    @JsonIgnoreProperties("agendaServidors")
    private AgendaAtendimentoServidor agendaAtendimentoServidor;

  

    @OneToMany(mappedBy = "agendaServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Servidor> codSiapes = new HashSet<>();

    @OneToMany(mappedBy = "agendaServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DiasAtendimento> mes = new HashSet<>();

    @OneToMany(mappedBy = "agendaServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DiasAtendimento> diaMes = new HashSet<>();

    @OneToMany(mappedBy = "agendaServidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DiasAtendimento> statusDias = new HashSet<>();

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

    public AgendaServidor status(StatusAgenda status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusAgenda status) {
        this.status = status;
    }

    public Horario getHorario() {
        return horario;
    }

    public AgendaServidor horario(Horario horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public AgendaAtendimentoServidor getAgendaAtendimentoServidor() {
        return agendaAtendimentoServidor;
    }

    public AgendaServidor agendaAtendimentoServidor(AgendaAtendimentoServidor agendaAtendimentoServidor) {
        this.agendaAtendimentoServidor = agendaAtendimentoServidor;
        return this;
    }

    public void setAgendaAtendimentoServidor(AgendaAtendimentoServidor agendaAtendimentoServidor) {
        this.agendaAtendimentoServidor = agendaAtendimentoServidor;
    }


    public Set<Servidor> getCodSiapes() {
        return codSiapes;
    }

    public AgendaServidor codSiapes(Set<Servidor> servidors) {
        this.codSiapes = servidors;
        return this;
    }

    public AgendaServidor addCodSiape(Servidor servidor) {
        this.codSiapes.add(servidor);
        servidor.setAgendaServidor(this);
        return this;
    }

    public AgendaServidor removeCodSiape(Servidor servidor) {
        this.codSiapes.remove(servidor);
        servidor.setAgendaServidor(null);
        return this;
    }

    public void setCodSiapes(Set<Servidor> servidors) {
        this.codSiapes = servidors;
    }

    public Set<DiasAtendimento> getMes() {
        return mes;
    }

    public AgendaServidor mes(Set<DiasAtendimento> diasAtendimentos) {
        this.mes = diasAtendimentos;
        return this;
    }

    public AgendaServidor addMes(DiasAtendimento diasAtendimento) {
        this.mes.add(diasAtendimento);
        diasAtendimento.setAgendaServidor(this);
        return this;
    }

    public AgendaServidor removeMes(DiasAtendimento diasAtendimento) {
        this.mes.remove(diasAtendimento);
        diasAtendimento.setAgendaServidor(null);
        return this;
    }

    public void setMes(Set<DiasAtendimento> diasAtendimentos) {
        this.mes = diasAtendimentos;
    }

    public Set<DiasAtendimento> getDiaMes() {
        return diaMes;
    }

    public AgendaServidor diaMes(Set<DiasAtendimento> diasAtendimentos) {
        this.diaMes = diasAtendimentos;
        return this;
    }

    public AgendaServidor addDiaMes(DiasAtendimento diasAtendimento) {
        this.diaMes.add(diasAtendimento);
        diasAtendimento.setAgendaServidor(this);
        return this;
    }

    public AgendaServidor removeDiaMes(DiasAtendimento diasAtendimento) {
        this.diaMes.remove(diasAtendimento);
        diasAtendimento.setAgendaServidor(null);
        return this;
    }

    public void setDiaMes(Set<DiasAtendimento> diasAtendimentos) {
        this.diaMes = diasAtendimentos;
    }

    public Set<DiasAtendimento> getStatusDias() {
        return statusDias;
    }

    public AgendaServidor statusDias(Set<DiasAtendimento> diasAtendimentos) {
        this.statusDias = diasAtendimentos;
        return this;
    }

    public AgendaServidor addStatusDia(DiasAtendimento diasAtendimento) {
        this.statusDias.add(diasAtendimento);
        diasAtendimento.setAgendaServidor(this);
        return this;
    }

    public AgendaServidor removeStatusDia(DiasAtendimento diasAtendimento) {
        this.statusDias.remove(diasAtendimento);
        diasAtendimento.setAgendaServidor(null);
        return this;
    }

    public void setStatusDias(Set<DiasAtendimento> diasAtendimentos) {
        this.statusDias = diasAtendimentos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendaServidor)) {
            return false;
        }
        return id != null && id.equals(((AgendaServidor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AgendaServidor{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", horario='" + getHorario() + "'" +
            "}";
    }
}
