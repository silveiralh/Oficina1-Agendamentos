package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.domain.AgendaAluno;
import com.uniagenda.agenda.repository.AgendaAlunoRepository;
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
 * REST controller for managing {@link com.uniagenda.agenda.domain.AgendaAluno}.
 */
@RestController
@RequestMapping("/api")
public class AgendaAlunoResource {

    private final Logger log = LoggerFactory.getLogger(AgendaAlunoResource.class);

    private static final String ENTITY_NAME = "agendaAluno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendaAlunoRepository agendaAlunoRepository;

    public AgendaAlunoResource(AgendaAlunoRepository agendaAlunoRepository) {
        this.agendaAlunoRepository = agendaAlunoRepository;
    }

    /**
     * {@code POST  /agenda-alunos} : Create a new agendaAluno.
     *
     * @param agendaAluno the agendaAluno to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendaAluno, or with status {@code 400 (Bad Request)} if the agendaAluno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agenda-alunos")
    public ResponseEntity<AgendaAluno> createAgendaAluno(@RequestBody AgendaAluno agendaAluno) throws URISyntaxException {
        log.debug("REST request to save AgendaAluno : {}", agendaAluno);
        if (agendaAluno.getId() != null) {
            throw new BadRequestAlertException("A new agendaAluno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgendaAluno result = agendaAlunoRepository.save(agendaAluno);
        return ResponseEntity.created(new URI("/api/agenda-alunos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agenda-alunos} : Updates an existing agendaAluno.
     *
     * @param agendaAluno the agendaAluno to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaAluno,
     * or with status {@code 400 (Bad Request)} if the agendaAluno is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendaAluno couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agenda-alunos")
    public ResponseEntity<AgendaAluno> updateAgendaAluno(@RequestBody AgendaAluno agendaAluno) throws URISyntaxException {
        log.debug("REST request to update AgendaAluno : {}", agendaAluno);
        if (agendaAluno.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgendaAluno result = agendaAlunoRepository.save(agendaAluno);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agendaAluno.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agenda-alunos} : get all the agendaAlunos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agendaAlunos in body.
     */
    @GetMapping("/agenda-alunos")
    public List<AgendaAluno> getAllAgendaAlunos() {
        log.debug("REST request to get all AgendaAlunos");
        return agendaAlunoRepository.findAll();
    }

    /**
     * {@code GET  /agenda-alunos/:id} : get the "id" agendaAluno.
     *
     * @param id the id of the agendaAluno to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendaAluno, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agenda-alunos/{id}")
    public ResponseEntity<AgendaAluno> getAgendaAluno(@PathVariable Long id) {
        log.debug("REST request to get AgendaAluno : {}", id);
        Optional<AgendaAluno> agendaAluno = agendaAlunoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agendaAluno);
    }

    /**
     * {@code DELETE  /agenda-alunos/:id} : delete the "id" agendaAluno.
     *
     * @param id the id of the agendaAluno to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agenda-alunos/{id}")
    public ResponseEntity<Void> deleteAgendaAluno(@PathVariable Long id) {
        log.debug("REST request to delete AgendaAluno : {}", id);
        agendaAlunoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
