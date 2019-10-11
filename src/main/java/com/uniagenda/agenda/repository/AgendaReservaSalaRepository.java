package com.uniagenda.agenda.repository;
import com.uniagenda.agenda.domain.AgendaReservaSala;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgendaReservaSala entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendaReservaSalaRepository extends JpaRepository<AgendaReservaSala, Long> {

}
