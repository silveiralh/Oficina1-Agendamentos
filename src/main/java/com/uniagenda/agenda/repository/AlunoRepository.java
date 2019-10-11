package com.uniagenda.agenda.repository;
import com.uniagenda.agenda.domain.Aluno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Aluno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
