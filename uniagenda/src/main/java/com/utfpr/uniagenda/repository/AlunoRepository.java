package com.utfpr.uniagenda.repository;
import com.utfpr.uniagenda.domain.Aluno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Aluno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
