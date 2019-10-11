package com.utfpr.uniagenda.repository;
import com.utfpr.uniagenda.domain.Servidor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Servidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {

}
