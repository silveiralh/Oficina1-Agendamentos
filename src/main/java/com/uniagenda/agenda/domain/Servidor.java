package com.uniagenda.agenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Servidor.
 */
@Entity
@Table(name = "servidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Servidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod_siape")
    private Double codSiape;

    @Column(name = "nome_servidor")
    private String nomeServidor;

    @ManyToOne
    @JsonIgnoreProperties("servidors")
    private Cargo cargo;

    @OneToMany(mappedBy = "servidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DiasAtendimento> codSiapes = new HashSet<>();

    @OneToMany(mappedBy = "servidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgendaServidor> codSiapes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCodSiape() {
        return codSiape;
    }

    public Servidor codSiape(Double codSiape) {
        this.codSiape = codSiape;
        return this;
    }

    public void setCodSiape(Double codSiape) {
        this.codSiape = codSiape;
    }

    public String getNomeServidor() {
        return nomeServidor;
    }

    public Servidor nomeServidor(String nomeServidor) {
        this.nomeServidor = nomeServidor;
        return this;
    }

    public void setNomeServidor(String nomeServidor) {
        this.nomeServidor = nomeServidor;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Servidor cargo(Cargo cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Set<DiasAtendimento> getCodSiapes() {
        return codSiapes;
    }

    public Servidor codSiapes(Set<DiasAtendimento> diasAtendimentos) {
        this.codSiapes = diasAtendimentos;
        return this;
    }

    public Servidor addCodSiape(DiasAtendimento diasAtendimento) {
        this.codSiapes.add(diasAtendimento);
        diasAtendimento.setServidor(this);
        return this;
    }

    public Servidor removeCodSiape(DiasAtendimento diasAtendimento) {
        this.codSiapes.remove(diasAtendimento);
        diasAtendimento.setServidor(null);
        return this;
    }

    public void setCodSiapes(Set<DiasAtendimento> diasAtendimentos) {
        this.codSiapes = diasAtendimentos;
    }

    public Set<AgendaServidor> getCodSiapes() {
        return codSiapes;
    }

    public Servidor codSiapes(Set<AgendaServidor> agendaServidors) {
        this.codSiapes = agendaServidors;
        return this;
    }

    public Servidor addCodSiape(AgendaServidor agendaServidor) {
        this.codSiapes.add(agendaServidor);
        agendaServidor.setServidor(this);
        return this;
    }

    public Servidor removeCodSiape(AgendaServidor agendaServidor) {
        this.codSiapes.remove(agendaServidor);
        agendaServidor.setServidor(null);
        return this;
    }

    public void setCodSiapes(Set<AgendaServidor> agendaServidors) {
        this.codSiapes = agendaServidors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Servidor)) {
            return false;
        }
        return id != null && id.equals(((Servidor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Servidor{" +
            "id=" + getId() +
            ", codSiape=" + getCodSiape() +
            ", nomeServidor='" + getNomeServidor() + "'" +
            "}";
    }
}
