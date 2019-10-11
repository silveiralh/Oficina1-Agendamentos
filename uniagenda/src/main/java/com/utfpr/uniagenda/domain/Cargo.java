package com.utfpr.uniagenda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Cargo.
 */
@Entity
@Table(name = "cargo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cargo")
public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "nome_cargo")
    private String nomeCargo;

    @ManyToOne
    @JsonIgnoreProperties("cargos")
    private Servidor servidor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public Cargo nomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
        return this;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public Cargo servidor(Servidor servidor) {
        this.servidor = servidor;
        return this;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cargo)) {
            return false;
        }
        return id != null && id.equals(((Cargo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cargo{" +
            "id=" + getId() +
            ", nomeCargo='" + getNomeCargo() + "'" +
            "}";
    }
}
