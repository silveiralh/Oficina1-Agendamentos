package com.utfpr.uniagenda.web.rest;

import com.utfpr.uniagenda.domain.AgendaServidor;
import com.utfpr.uniagenda.repository.AgendaServidorRepository;
import com.utfpr.uniagenda.repository.search.AgendaServidorSearchRepository;
import com.utfpr.uniagenda.web.rest.errors.BadRequestAlertException;

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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.utfpr.uniagenda.domain.AgendaServidor}.
 */
@RestController
@RequestMapping("/api")
public class AgendaServidorResource {

    private final Logger log = LoggerFactory.getLogger(AgendaServidorResource.class);

    private static final String ENTITY_NAME = "agendaServidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendaServidorRepository agendaServidorRepository;

    private final AgendaServidorSearchRepository agendaServidorSearchRepository;

    public AgendaServidorResource(AgendaServidorRepository agendaServidorRepository, AgendaServidorSearchRepository agendaServidorSearchRepository) {
        this.agendaServidorRepository = agendaServidorRepository;
        this.agendaServidorSearchRepository = agendaServidorSearchRepository;
    }

    /**
     * {@code POST  /agenda-servidors} : Create a new agendaServidor.
     *
     * @param agendaServidor the agendaServidor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendaServidor, or with status {@code 400 (Bad Request)} if the agendaServidor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agenda-servidors")
    public ResponseEntity<AgendaServidor> createAgendaServidor(@RequestBody AgendaServidor agendaServidor) throws URISyntaxException {
        log.debug("REST request to save AgendaServidor : {}", agendaServidor);
        if (agendaServidor.getId() != null) {
            throw new BadRequestAlertException("A new agendaServidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgendaServidor result = agendaServidorRepository.save(agendaServidor);
        agendaServidorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/agenda-servidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agenda-servidors} : Updates an existing agendaServidor.
     *
     * @param agendaServidor the agendaServidor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaServidor,
     * or with status {@code 400 (Bad Request)} if the agendaServidor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendaServidor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agenda-servidors")
    public ResponseEntity<AgendaServidor> updateAgendaServidor(@RequestBody AgendaServidor agendaServidor) throws URISyntaxException {
        log.debug("REST request to update AgendaServidor : {}", agendaServidor);
        if (agendaServidor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgendaServidor result = agendaServidorRepository.save(agendaServidor);
        agendaServidorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaServidor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agenda-servidors} : get all the agendaServidors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agendaServidors in body.
     */
    @GetMapping("/agenda-servidors")
    public List<AgendaServidor> getAllAgendaServidors() {
        log.debug("REST request to get all AgendaServidors");
        return agendaServidorRepository.findAll();
    }

    /**
     * {@code GET  /agenda-servidors/:id} : get the "id" agendaServidor.
     *
     * @param id the id of the agendaServidor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendaServidor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agenda-servidors/{id}")
    public ResponseEntity<AgendaServidor> getAgendaServidor(@PathVariable Long id) {
        log.debug("REST request to get AgendaServidor : {}", id);
        Optional<AgendaServidor> agendaServidor = agendaServidorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agendaServidor);
    }

    /**
     * {@code DELETE  /agenda-servidors/:id} : delete the "id" agendaServidor.
     *
     * @param id the id of the agendaServidor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agenda-servidors/{id}")
    public ResponseEntity<Void> deleteAgendaServidor(@PathVariable Long id) {
        log.debug("REST request to delete AgendaServidor : {}", id);
        agendaServidorRepository.deleteById(id);
        agendaServidorSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/agenda-servidors?query=:query} : search for the agendaServidor corresponding
     * to the query.
     *
     * @param query the query of the agendaServidor search.
     * @return the result of the search.
     */
    @GetMapping("/_search/agenda-servidors")
    public List<AgendaServidor> searchAgendaServidors(@RequestParam String query) {
        log.debug("REST request to search AgendaServidors for query {}", query);
        return StreamSupport
            .stream(agendaServidorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
