package com.utfpr.uniagenda.web.rest;

import com.utfpr.uniagenda.domain.Cargo;
import com.utfpr.uniagenda.repository.CargoRepository;
import com.utfpr.uniagenda.repository.search.CargoSearchRepository;
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
 * REST controller for managing {@link com.utfpr.uniagenda.domain.Cargo}.
 */
@RestController
@RequestMapping("/api")
public class CargoResource {

    private final Logger log = LoggerFactory.getLogger(CargoResource.class);

    private static final String ENTITY_NAME = "cargo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CargoRepository cargoRepository;

    private final CargoSearchRepository cargoSearchRepository;

    public CargoResource(CargoRepository cargoRepository, CargoSearchRepository cargoSearchRepository) {
        this.cargoRepository = cargoRepository;
        this.cargoSearchRepository = cargoSearchRepository;
    }

    /**
     * {@code POST  /cargos} : Create a new cargo.
     *
     * @param cargo the cargo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cargo, or with status {@code 400 (Bad Request)} if the cargo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cargos")
    public ResponseEntity<Cargo> createCargo(@RequestBody Cargo cargo) throws URISyntaxException {
        log.debug("REST request to save Cargo : {}", cargo);
        if (cargo.getId() != null) {
            throw new BadRequestAlertException("A new cargo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cargo result = cargoRepository.save(cargo);
        cargoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cargos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cargos} : Updates an existing cargo.
     *
     * @param cargo the cargo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cargo,
     * or with status {@code 400 (Bad Request)} if the cargo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cargo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cargos")
    public ResponseEntity<Cargo> updateCargo(@RequestBody Cargo cargo) throws URISyntaxException {
        log.debug("REST request to update Cargo : {}", cargo);
        if (cargo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cargo result = cargoRepository.save(cargo);
        cargoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cargo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cargos} : get all the cargos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cargos in body.
     */
    @GetMapping("/cargos")
    public List<Cargo> getAllCargos() {
        log.debug("REST request to get all Cargos");
        return cargoRepository.findAll();
    }

    /**
     * {@code GET  /cargos/:id} : get the "id" cargo.
     *
     * @param id the id of the cargo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cargo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cargos/{id}")
    public ResponseEntity<Cargo> getCargo(@PathVariable Long id) {
        log.debug("REST request to get Cargo : {}", id);
        Optional<Cargo> cargo = cargoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cargo);
    }

    /**
     * {@code DELETE  /cargos/:id} : delete the "id" cargo.
     *
     * @param id the id of the cargo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cargos/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        log.debug("REST request to delete Cargo : {}", id);
        cargoRepository.deleteById(id);
        cargoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cargos?query=:query} : search for the cargo corresponding
     * to the query.
     *
     * @param query the query of the cargo search.
     * @return the result of the search.
     */
    @GetMapping("/_search/cargos")
    public List<Cargo> searchCargos(@RequestParam String query) {
        log.debug("REST request to search Cargos for query {}", query);
        return StreamSupport
            .stream(cargoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
