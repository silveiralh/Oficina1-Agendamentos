package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.domain.DiasReservaSala;
import com.uniagenda.agenda.repository.DiasReservaSalaRepository;
import com.uniagenda.agenda.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.uniagenda.agenda.domain.DiasReservaSala}.
 */
@RestController
@RequestMapping("/api")
public class DiasReservaSalaResource {

    private final Logger log = LoggerFactory.getLogger(DiasReservaSalaResource.class);

    private static final String ENTITY_NAME = "diasReservaSala";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiasReservaSalaRepository diasReservaSalaRepository;

    public DiasReservaSalaResource(DiasReservaSalaRepository diasReservaSalaRepository) {
        this.diasReservaSalaRepository = diasReservaSalaRepository;
    }

    /**
     * {@code POST  /dias-reserva-salas} : Create a new diasReservaSala.
     *
     * @param diasReservaSala the diasReservaSala to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diasReservaSala, or with status {@code 400 (Bad Request)} if the diasReservaSala has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dias-reserva-salas")
    public ResponseEntity<DiasReservaSala> createDiasReservaSala(@RequestBody DiasReservaSala diasReservaSala) throws URISyntaxException {
        log.debug("REST request to save DiasReservaSala : {}", diasReservaSala);
        if (diasReservaSala.getId() != null) {
            throw new BadRequestAlertException("A new diasReservaSala cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiasReservaSala result = diasReservaSalaRepository.save(diasReservaSala);
        return ResponseEntity.created(new URI("/api/dias-reserva-salas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dias-reserva-salas} : Updates an existing diasReservaSala.
     *
     * @param diasReservaSala the diasReservaSala to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diasReservaSala,
     * or with status {@code 400 (Bad Request)} if the diasReservaSala is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diasReservaSala couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dias-reserva-salas")
    public ResponseEntity<DiasReservaSala> updateDiasReservaSala(@RequestBody DiasReservaSala diasReservaSala) throws URISyntaxException {
        log.debug("REST request to update DiasReservaSala : {}", diasReservaSala);
        if (diasReservaSala.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiasReservaSala result = diasReservaSalaRepository.save(diasReservaSala);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diasReservaSala.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dias-reserva-salas} : get all the diasReservaSalas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diasReservaSalas in body.
     */
    @GetMapping("/dias-reserva-salas")
    public List<DiasReservaSala> getAllDiasReservaSalas() {
        log.debug("REST request to get all DiasReservaSalas");
        return diasReservaSalaRepository.findAll();
    }

    /**
     * {@code GET  /dias-reserva-salas/:id} : get the "id" diasReservaSala.
     *
     * @param id the id of the diasReservaSala to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diasReservaSala, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dias-reserva-salas/{id}")
    public ResponseEntity<DiasReservaSala> getDiasReservaSala(@PathVariable Long id) {
        log.debug("REST request to get DiasReservaSala : {}", id);
        Optional<DiasReservaSala> diasReservaSala = diasReservaSalaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diasReservaSala);
    }

    /**
     * {@code DELETE  /dias-reserva-salas/:id} : delete the "id" diasReservaSala.
     *
     * @param id the id of the diasReservaSala to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dias-reserva-salas/{id}")
    public ResponseEntity<Void> deleteDiasReservaSala(@PathVariable Long id) {
        log.debug("REST request to delete DiasReservaSala : {}", id);
        diasReservaSalaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
