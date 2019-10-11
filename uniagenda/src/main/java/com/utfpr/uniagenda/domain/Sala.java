package com.utfpr.uniagenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Sala.
 */
@Entity
@Table(name = "sala")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sala")
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "nome_sala")
    private String nomeSala;

    @Column(name = "codigo_sala")
    private String codigoSala;

    @ManyToOne
    @JsonIgnoreProperties("salas")
    private AgendaSala agendaSala;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeSala() {
        return nomeSala;
    }

    public Sala nomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
        return this;
    }

    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public Sala codigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
        return this;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
    }

    public AgendaSala getAgendaSala() {
        return agendaSala;
    }

    public Sala agendaSala(AgendaSala agendaSala) {
        this.agendaSala = agendaSala;
        return this;
    }

    public void setAgendaSala(AgendaSala agendaSala) {
        this.agendaSala = agendaSala;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sala)) {
            return false;
        }
        return id != null && id.equals(((Sala) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sala{" +
            "id=" + getId() +
            ", nomeSala='" + getNomeSala() + "'" +
            ", codigoSala='" + getCodigoSala() + "'" +
            "}";
    }
}
