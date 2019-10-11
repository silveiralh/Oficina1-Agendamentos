package com.utfpr.uniagenda.web.rest;

import com.utfpr.uniagenda.UniAgendaApp;
import com.utfpr.uniagenda.domain.Sala;
import com.utfpr.uniagenda.repository.SalaRepository;
import com.utfpr.uniagenda.repository.search.SalaSearchRepository;
import com.utfpr.uniagenda.web.rest.errors.ExceptionTranslator;

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
import java.util.Collections;
import java.util.List;

import static com.utfpr.uniagenda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SalaResource} REST controller.
 */
@SpringBootTest(classes = UniAgendaApp.class)
public class SalaResourceIT {

    private static final String DEFAULT_NOME_SALA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_SALA = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_SALA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_SALA = "BBBBBBBBBB";

    @Autowired
    private SalaRepository salaRepository;

    /**
     * This repository is mocked in the com.utfpr.uniagenda.repository.search test package.
     *
     * @see com.utfpr.uniagenda.repository.search.SalaSearchRepositoryMockConfiguration
     */
    @Autowired
    private SalaSearchRepository mockSalaSearchRepository;

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

    private MockMvc restSalaMockMvc;

    private Sala sala;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalaResource salaResource = new SalaResource(salaRepository, mockSalaSearchRepository);
        this.restSalaMockMvc = MockMvcBuilders.standaloneSetup(salaResource)
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
    public static Sala createEntity(EntityManager em) {
        Sala sala = new Sala()
            .nomeSala(DEFAULT_NOME_SALA)
            .codigoSala(DEFAULT_CODIGO_SALA);
        return sala;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sala createUpdatedEntity(EntityManager em) {
        Sala sala = new Sala()
            .nomeSala(UPDATED_NOME_SALA)
            .codigoSala(UPDATED_CODIGO_SALA);
        return sala;
    }

    @BeforeEach
    public void initTest() {
        sala = createEntity(em);
    }

    @Test
    @Transactional
    public void createSala() throws Exception {
        int databaseSizeBeforeCreate = salaRepository.findAll().size();

        // Create the Sala
        restSalaMockMvc.perform(post("/api/salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sala)))
            .andExpect(status().isCreated());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeCreate + 1);
        Sala testSala = salaList.get(salaList.size() - 1);
        assertThat(testSala.getNomeSala()).isEqualTo(DEFAULT_NOME_SALA);
        assertThat(testSala.getCodigoSala()).isEqualTo(DEFAULT_CODIGO_SALA);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(1)).save(testSala);
    }

    @Test
    @Transactional
    public void createSalaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salaRepository.findAll().size();

        // Create the Sala with an existing ID
        sala.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalaMockMvc.perform(post("/api/salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sala)))
            .andExpect(status().isBadRequest());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(0)).save(sala);
    }


    @Test
    @Transactional
    public void getAllSalas() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        // Get all the salaList
        restSalaMockMvc.perform(get("/api/salas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sala.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeSala").value(hasItem(DEFAULT_NOME_SALA.toString())))
            .andExpect(jsonPath("$.[*].codigoSala").value(hasItem(DEFAULT_CODIGO_SALA.toString())));
    }
    
    @Test
    @Transactional
    public void getSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        // Get the sala
        restSalaMockMvc.perform(get("/api/salas/{id}", sala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sala.getId().intValue()))
            .andExpect(jsonPath("$.nomeSala").value(DEFAULT_NOME_SALA.toString()))
            .andExpect(jsonPath("$.codigoSala").value(DEFAULT_CODIGO_SALA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSala() throws Exception {
        // Get the sala
        restSalaMockMvc.perform(get("/api/salas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        int databaseSizeBeforeUpdate = salaRepository.findAll().size();

        // Update the sala
        Sala updatedSala = salaRepository.findById(sala.getId()).get();
        // Disconnect from session so that the updates on updatedSala are not directly saved in db
        em.detach(updatedSala);
        updatedSala
            .nomeSala(UPDATED_NOME_SALA)
            .codigoSala(UPDATED_CODIGO_SALA);

        restSalaMockMvc.perform(put("/api/salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSala)))
            .andExpect(status().isOk());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeUpdate);
        Sala testSala = salaList.get(salaList.size() - 1);
        assertThat(testSala.getNomeSala()).isEqualTo(UPDATED_NOME_SALA);
        assertThat(testSala.getCodigoSala()).isEqualTo(UPDATED_CODIGO_SALA);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(1)).save(testSala);
    }

    @Test
    @Transactional
    public void updateNonExistingSala() throws Exception {
        int databaseSizeBeforeUpdate = salaRepository.findAll().size();

        // Create the Sala

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaMockMvc.perform(put("/api/salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sala)))
            .andExpect(status().isBadRequest());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(0)).save(sala);
    }

    @Test
    @Transactional
    public void deleteSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        int databaseSizeBeforeDelete = salaRepository.findAll().size();

        // Delete the sala
        restSalaMockMvc.perform(delete("/api/salas/{id}", sala.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(1)).deleteById(sala.getId());
    }

    @Test
    @Transactional
    public void searchSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);
        when(mockSalaSearchRepository.search(queryStringQuery("id:" + sala.getId())))
            .thenReturn(Collections.singletonList(sala));
        // Search the sala
        restSalaMockMvc.perform(get("/api/_search/salas?query=id:" + sala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sala.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeSala").value(hasItem(DEFAULT_NOME_SALA)))
            .andExpect(jsonPath("$.[*].codigoSala").value(hasItem(DEFAULT_CODIGO_SALA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sala.class);
        Sala sala1 = new Sala();
        sala1.setId(1L);
        Sala sala2 = new Sala();
        sala2.setId(sala1.getId());
        assertThat(sala1).isEqualTo(sala2);
        sala2.setId(2L);
        assertThat(sala1).isNotEqualTo(sala2);
        sala1.setId(null);
        assertThat(sala1).isNotEqualTo(sala2);
    }
}
