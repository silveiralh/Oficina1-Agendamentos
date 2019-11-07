package com.uniagenda.agenda.repository;
import com.uniagenda.agenda.domain.DiasAulaAluno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DiasAulaAluno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiasAulaAlunoRepository extends JpaRepository<DiasAulaAluno, Long> {

}
