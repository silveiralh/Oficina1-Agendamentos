package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.AgendaApp;
import com.uniagenda.agenda.domain.Cargo;
import com.uniagenda.agenda.repository.CargoRepository;
import com.uniagenda.agenda.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.uniagenda.agenda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CargoResource} REST controller.
 */
@SpringBootTest(classes = AgendaApp.class)
public class CargoResourceIT {

    private static final String DEFAULT_NOME_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CARGO = "BBBBBBBBBB";

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCargoMockMvc;

    private Cargo cargo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CargoResource cargoResource = new CargoResource(cargoRepository);
        this.restCargoMockMvc = MockMvcBuilders.standaloneSetup(cargoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cargo createEntity(EntityManager em) {
        Cargo cargo = new Cargo()
            .nomeCargo(DEFAULT_NOME_CARGO);
        return cargo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cargo createUpdatedEntity(EntityManager em) {
        Cargo cargo = new Cargo()
            .nomeCargo(UPDATED_NOME_CARGO);
        return cargo;
    }

    @BeforeEach
    public void initTest() {
        cargo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCargo() throws Exception {
        int databaseSizeBeforeCreate = cargoRepository.findAll().size();

        // Create the Cargo
        restCargoMockMvc.perform(post("/api/cargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargo)))
            .andExpect(status().isCreated());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeCreate + 1);
        Cargo testCargo = cargoList.get(cargoList.size() - 1);
        assertThat(testCargo.getNomeCargo()).isEqualTo(DEFAULT_NOME_CARGO);
    }

    @Test
    @Transactional
    public void createCargoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cargoRepository.findAll().size();

        // Create the Cargo with an existing ID
        cargo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCargoMockMvc.perform(post("/api/cargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargo)))
            .andExpect(status().isBadRequest());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCargos() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);

        // Get all the cargoList
        restCargoMockMvc.perform(get("/api/cargos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cargo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeCargo").value(hasItem(DEFAULT_NOME_CARGO.toString())));
    }
    
    @Test
    @Transactional
    public void getCargo() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);

        // Get the cargo
        restCargoMockMvc.perform(get("/api/cargos/{id}", cargo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cargo.getId().intValue()))
            .andExpect(jsonPath("$.nomeCargo").value(DEFAULT_NOME_CARGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCargo() throws Exception {
        // Get the cargo
        restCargoMockMvc.perform(get("/api/cargos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCargo() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);

        int databaseSizeBeforeUpdate = cargoRepository.findAll().size();

        // Update the cargo
        Cargo updatedCargo = cargoRepository.findById(cargo.getId()).get();
        // Disconnect from session so that the updates on updatedCargo are not directly saved in db
        em.detach(updatedCargo);
        updatedCargo
            .nomeCargo(UPDATED_NOME_CARGO);

        restCargoMockMvc.perform(put("/api/cargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCargo)))
            .andExpect(status().isOk());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeUpdate);
        Cargo testCargo = cargoList.get(cargoList.size() - 1);
        assertThat(testCargo.getNomeCargo()).isEqualTo(UPDATED_NOME_CARGO);
    }

    @Test
    @Transactional
    public void updateNonExistingCargo() throws Exception {
        int databaseSizeBeforeUpdate = cargoRepository.findAll().size();

        // Create the Cargo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCargoMockMvc.perform(put("/api/cargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargo)))
            .andExpect(status().isBadRequest());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCargo() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);

        int databaseSizeBeforeDelete = cargoRepository.findAll().size();

        // Delete the cargo
        restCargoMockMvc.perform(delete("/api/cargos/{id}", cargo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cargo.class);
        Cargo cargo1 = new Cargo();
        cargo1.setId(1L);
        Cargo cargo2 = new Cargo();
        cargo2.setId(cargo1.getId());
        assertThat(cargo1).isEqualTo(cargo2);
        cargo2.setId(2L);
        assertThat(cargo1).isNotEqualTo(cargo2);
        cargo1.setId(null);
        assertThat(cargo1).isNotEqualTo(cargo2);
    }
}
