package com.utfpr.uniagenda.repository;
import com.utfpr.uniagenda.domain.AgendaAtendimentoServidor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgendaAtendimentoServidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendaAtendimentoServidorRepository extends JpaRepository<AgendaAtendimentoServidor, Long> {

}
