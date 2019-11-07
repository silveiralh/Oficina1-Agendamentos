package com.uniagenda.agenda.repository;
import com.uniagenda.agenda.domain.DiasReservaSala;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DiasReservaSala entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiasReservaSalaRepository extends JpaRepository<DiasReservaSala, Long> {

}
