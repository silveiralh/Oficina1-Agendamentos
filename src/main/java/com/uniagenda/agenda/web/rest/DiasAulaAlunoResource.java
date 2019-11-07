package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.domain.DiasAulaAluno;
import com.uniagenda.agenda.repository.DiasAulaAlunoRepository;
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
 * REST controller for managing {@link com.uniagenda.agenda.domain.DiasAulaAluno}.
 */
@RestController
@RequestMapping("/api")
public class DiasAulaAlunoResource {

    private final Logger log = LoggerFactory.getLogger(DiasAulaAlunoResource.class);

    private static final String ENTITY_NAME = "diasAulaAluno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiasAulaAlunoRepository diasAulaAlunoRepository;

    public DiasAulaAlunoResource(DiasAulaAlunoRepository diasAulaAlunoRepository) {
        this.diasAulaAlunoRepository = diasAulaAlunoRepository;
    }

    /**
     * {@code POST  /dias-aula-alunos} : Create a new diasAulaAluno.
     *
     * @param diasAulaAluno the diasAulaAluno to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diasAulaAluno, or with status {@code 400 (Bad Request)} if the diasAulaAluno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dias-aula-alunos")
    public ResponseEntity<DiasAulaAluno> createDiasAulaAluno(@RequestBody DiasAulaAluno diasAulaAluno) throws URISyntaxException {
        log.debug("REST request to save DiasAulaAluno : {}", diasAulaAluno);
        if (diasAulaAluno.getId() != null) {
            throw new BadRequestAlertException("A new diasAulaAluno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiasAulaAluno result = diasAulaAlunoRepository.save(diasAulaAluno);
        return ResponseEntity.created(new URI("/api/dias-aula-alunos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dias-aula-alunos} : Updates an existing diasAulaAluno.
     *
     * @param diasAulaAluno the diasAulaAluno to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diasAulaAluno,
     * or with status {@code 400 (Bad Request)} if the diasAulaAluno is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diasAulaAluno couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dias-aula-alunos")
    public ResponseEntity<DiasAulaAluno> updateDiasAulaAluno(@RequestBody DiasAulaAluno diasAulaAluno) throws URISyntaxException {
        log.debug("REST request to update DiasAulaAluno : {}", diasAulaAluno);
        if (diasAulaAluno.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiasAulaAluno result = diasAulaAlunoRepository.save(diasAulaAluno);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diasAulaAluno.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dias-aula-alunos} : get all the diasAulaAlunos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diasAulaAlunos in body.
     */
    @GetMapping("/dias-aula-alunos")
    public List<DiasAulaAluno> getAllDiasAulaAlunos() {
        log.debug("REST request to get all DiasAulaAlunos");
        return diasAulaAlunoRepository.findAll();
    }

    /**
     * {@code GET  /dias-aula-alunos/:id} : get the "id" diasAulaAluno.
     *
     * @param id the id of the diasAulaAluno to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diasAulaAluno, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dias-aula-alunos/{id}")
    public ResponseEntity<DiasAulaAluno> getDiasAulaAluno(@PathVariable Long id) {
        log.debug("REST request to get DiasAulaAluno : {}", id);
        Optional<DiasAulaAluno> diasAulaAluno = diasAulaAlunoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diasAulaAluno);
    }

    /**
     * {@code DELETE  /dias-aula-alunos/:id} : delete the "id" diasAulaAluno.
     *
     * @param id the id of the diasAulaAluno to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dias-aula-alunos/{id}")
    public ResponseEntity<Void> deleteDiasAulaAluno(@PathVariable Long id) {
        log.debug("REST request to delete DiasAulaAluno : {}", id);
        diasAulaAlunoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
