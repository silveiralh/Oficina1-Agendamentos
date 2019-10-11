package com.uniagenda.agenda.repository;
import com.uniagenda.agenda.domain.AgendaSala;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgendaSala entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendaSalaRepository extends JpaRepository<AgendaSala, Long> {

}
