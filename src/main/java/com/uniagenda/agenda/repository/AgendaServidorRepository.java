package com.uniagenda.agenda.repository;
import com.uniagenda.agenda.domain.AgendaServidor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgendaServidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendaServidorRepository extends JpaRepository<AgendaServidor, Long> {

}
