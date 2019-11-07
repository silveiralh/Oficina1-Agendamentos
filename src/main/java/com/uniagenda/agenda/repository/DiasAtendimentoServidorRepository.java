package com.uniagenda.agenda.repository;
import com.uniagenda.agenda.domain.DiasAtendimentoServidor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DiasAtendimentoServidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiasAtendimentoServidorRepository extends JpaRepository<DiasAtendimentoServidor, Long> {

}
