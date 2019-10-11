package com.uniagenda.agenda.repository;
import com.uniagenda.agenda.domain.DiasAtendimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DiasAtendimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiasAtendimentoRepository extends JpaRepository<DiasAtendimento, Long> {

}
