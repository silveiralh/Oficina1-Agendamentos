package com.uniagenda.agenda.repository;
import com.uniagenda.agenda.domain.AgendaAluno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgendaAluno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendaAlunoRepository extends JpaRepository<AgendaAluno, Long> {

}
