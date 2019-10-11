package com.utfpr.uniagenda.web.rest;

import com.utfpr.uniagenda.domain.DiasAtendimento;
import com.utfpr.uniagenda.repository.DiasAtendimentoRepository;
import com.utfpr.uniagenda.repository.search.DiasAtendimentoSearchRepository;
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
 * REST controller for managing {@link com.utfpr.uniagenda.domain.DiasAtendimento}.
 */
@RestController
@RequestMapping("/api")
public class DiasAtendimentoResource {

    private final Logger log = LoggerFactory.getLogger(DiasAtendimentoResource.class);

    private static final String ENTITY_NAME = "diasAtendimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiasAtendimentoRepository diasAtendimentoRepository;

    private final DiasAtendimentoSearchRepository diasAtendimentoSearchRepository;

    public DiasAtendimentoResource(DiasAtendimentoRepository diasAtendimentoRepository, DiasAtendimentoSearchRepository diasAtendimentoSearchRepository) {
        this.diasAtendimentoRepository = diasAtendimentoRepository;
        this.diasAtendimentoSearchRepository = diasAtendimentoSearchRepository;
    }

    /**
     * {@code POST  /dias-atendimentos} : Create a new diasAtendimento.
     *
     * @param diasAtendimento the diasAtendimento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diasAtendimento, or with status {@code 400 (Bad Request)} if the diasAtendimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dias-atendimentos")
    public ResponseEntity<DiasAtendimento> createDiasAtendimento(@RequestBody DiasAtendimento diasAtendimento) throws URISyntaxException {
        log.debug("REST request to save DiasAtendimento : {}", diasAtendimento);
        if (diasAtendimento.getId() != null) {
            throw new BadRequestAlertException("A new diasAtendimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiasAtendimento result = diasAtendimentoRepository.save(diasAtendimento);
        diasAtendimentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dias-atendimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dias-atendimentos} : Updates an existing diasAtendimento.
     *
     * @param diasAtendimento the diasAtendimento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diasAtendimento,
     * or with status {@code 400 (Bad Request)} if the diasAtendimento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diasAtendimento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dias-atendimentos")
    public ResponseEntity<DiasAtendimento> updateDiasAtendimento(@RequestBody DiasAtendimento diasAtendimento) throws URISyntaxException {
        log.debug("REST request to update DiasAtendimento : {}", diasAtendimento);
        if (diasAtendimento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiasAtendimento result = diasAtendimentoRepository.save(diasAtendimento);
        diasAtendimentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diasAtendimento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dias-atendimentos} : get all the diasAtendimentos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diasAtendimentos in body.
     */
    @GetMapping("/dias-atendimentos")
    public List<DiasAtendimento> getAllDiasAtendimentos() {
        log.debug("REST request to get all DiasAtendimentos");
        return diasAtendimentoRepository.findAll();
    }

    /**
     * {@code GET  /dias-atendimentos/:id} : get the "id" diasAtendimento.
     *
     * @param id the id of the diasAtendimento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diasAtendimento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dias-atendimentos/{id}")
    public ResponseEntity<DiasAtendimento> getDiasAtendimento(@PathVariable Long id) {
        log.debug("REST request to get DiasAtendimento : {}", id);
        Optional<DiasAtendimento> diasAtendimento = diasAtendimentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diasAtendimento);
    }

    /**
     * {@code DELETE  /dias-atendimentos/:id} : delete the "id" diasAtendimento.
     *
     * @param id the id of the diasAtendimento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dias-atendimentos/{id}")
    public ResponseEntity<Void> deleteDiasAtendimento(@PathVariable Long id) {
        log.debug("REST request to delete DiasAtendimento : {}", id);
        diasAtendimentoRepository.deleteById(id);
        diasAtendimentoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/dias-atendimentos?query=:query} : search for the diasAtendimento corresponding
     * to the query.
     *
     * @param query the query of the diasAtendimento search.
     * @return the result of the search.
     */
    @GetMapping("/_search/dias-atendimentos")
    public List<DiasAtendimento> searchDiasAtendimentos(@RequestParam String query) {
        log.debug("REST request to search DiasAtendimentos for query {}", query);
        return StreamSupport
            .stream(diasAtendimentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
