package com.utfpr.uniagenda.repository;
import com.utfpr.uniagenda.domain.AgendaSala;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgendaSala entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendaSalaRepository extends JpaRepository<AgendaSala, Long> {

}
