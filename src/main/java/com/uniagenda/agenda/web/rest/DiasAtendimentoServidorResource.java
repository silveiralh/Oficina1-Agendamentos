package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.domain.DiasAtendimentoServidor;
import com.uniagenda.agenda.repository.DiasAtendimentoServidorRepository;
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
 * REST controller for managing {@link com.uniagenda.agenda.domain.DiasAtendimentoServidor}.
 */
@RestController
@RequestMapping("/api")
public class DiasAtendimentoServidorResource {

    private final Logger log = LoggerFactory.getLogger(DiasAtendimentoServidorResource.class);

    private static final String ENTITY_NAME = "diasAtendimentoServidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiasAtendimentoServidorRepository diasAtendimentoServidorRepository;

    public DiasAtendimentoServidorResource(DiasAtendimentoServidorRepository diasAtendimentoServidorRepository) {
        this.diasAtendimentoServidorRepository = diasAtendimentoServidorRepository;
    }

    /**
     * {@code POST  /dias-atendimento-servidors} : Create a new diasAtendimentoServidor.
     *
     * @param diasAtendimentoServidor the diasAtendimentoServidor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diasAtendimentoServidor, or with status {@code 400 (Bad Request)} if the diasAtendimentoServidor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dias-atendimento-servidors")
    public ResponseEntity<DiasAtendimentoServidor> createDiasAtendimentoServidor(@RequestBody DiasAtendimentoServidor diasAtendimentoServidor) throws URISyntaxException {
        log.debug("REST request to save DiasAtendimentoServidor : {}", diasAtendimentoServidor);
        if (diasAtendimentoServidor.getId() != null) {
            throw new BadRequestAlertException("A new diasAtendimentoServidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiasAtendimentoServidor result = diasAtendimentoServidorRepository.save(diasAtendimentoServidor);
        return ResponseEntity.created(new URI("/api/dias-atendimento-servidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dias-atendimento-servidors} : Updates an existing diasAtendimentoServidor.
     *
     * @param diasAtendimentoServidor the diasAtendimentoServidor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diasAtendimentoServidor,
     * or with status {@code 400 (Bad Request)} if the diasAtendimentoServidor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diasAtendimentoServidor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dias-atendimento-servidors")
    public ResponseEntity<DiasAtendimentoServidor> updateDiasAtendimentoServidor(@RequestBody DiasAtendimentoServidor diasAtendimentoServidor) throws URISyntaxException {
        log.debug("REST request to update DiasAtendimentoServidor : {}", diasAtendimentoServidor);
        if (diasAtendimentoServidor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiasAtendimentoServidor result = diasAtendimentoServidorRepository.save(diasAtendimentoServidor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diasAtendimentoServidor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dias-atendimento-servidors} : get all the diasAtendimentoServidors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diasAtendimentoServidors in body.
     */
    @GetMapping("/dias-atendimento-servidors")
    public List<DiasAtendimentoServidor> getAllDiasAtendimentoServidors() {
        log.debug("REST request to get all DiasAtendimentoServidors");
        return diasAtendimentoServidorRepository.findAll();
    }

    /**
     * {@code GET  /dias-atendimento-servidors/:id} : get the "id" diasAtendimentoServidor.
     *
     * @param id the id of the diasAtendimentoServidor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diasAtendimentoServidor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dias-atendimento-servidors/{id}")
    public ResponseEntity<DiasAtendimentoServidor> getDiasAtendimentoServidor(@PathVariable Long id) {
        log.debug("REST request to get DiasAtendimentoServidor : {}", id);
        Optional<DiasAtendimentoServidor> diasAtendimentoServidor = diasAtendimentoServidorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diasAtendimentoServidor);
    }

    /**
     * {@code DELETE  /dias-atendimento-servidors/:id} : delete the "id" diasAtendimentoServidor.
     *
     * @param id the id of the diasAtendimentoServidor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dias-atendimento-servidors/{id}")
    public ResponseEntity<Void> deleteDiasAtendimentoServidor(@PathVariable Long id) {
        log.debug("REST request to delete DiasAtendimentoServidor : {}", id);
        diasAtendimentoServidorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
