package com.utfpr.uniagenda.repository;
import com.utfpr.uniagenda.domain.AgendaReservaSala;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgendaReservaSala entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendaReservaSalaRepository extends JpaRepository<AgendaReservaSala, Long> {

}
