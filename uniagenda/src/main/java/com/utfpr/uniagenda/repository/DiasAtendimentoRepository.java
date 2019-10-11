package com.utfpr.uniagenda.repository;
import com.utfpr.uniagenda.domain.DiasAtendimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DiasAtendimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiasAtendimentoRepository extends JpaRepository<DiasAtendimento, Long> {

}
