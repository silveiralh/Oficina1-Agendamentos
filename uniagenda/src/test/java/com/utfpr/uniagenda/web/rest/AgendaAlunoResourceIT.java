package com.utfpr.uniagenda.web.rest;

import com.utfpr.uniagenda.UniAgendaApp;
import com.utfpr.uniagenda.domain.AgendaAluno;
import com.utfpr.uniagenda.repository.AgendaAlunoRepository;
import com.utfpr.uniagenda.repository.search.AgendaAlunoSearchRepository;
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

import com.utfpr.uniagenda.domain.enumeration.StatusAgenda;
import com.utfpr.uniagenda.domain.enumeration.Horario;
/**
 * Integration tests for the {@link AgendaAlunoResource} REST controller.
 */
@SpringBootTest(classes = UniAgendaApp.class)
public class AgendaAlunoResourceIT {

    private static final StatusAgenda DEFAULT_STATUS = StatusAgenda.Livre;
    private static final StatusAgenda UPDATED_STATUS = StatusAgenda.Ocupado;

    private static final Horario DEFAULT_HORARIO = Horario.H8;
    private static final Horario UPDATED_HORARIO = Horario.H9;

    @Autowired
    private AgendaAlunoRepository agendaAlunoRepository;

    /**
     * This repository is mocked in the com.utfpr.uniagenda.repository.search test package.
     *
     * @see com.utfpr.uniagenda.repository.search.AgendaAlunoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AgendaAlunoSearchRepository mockAgendaAlunoSearchRepository;

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

    private MockMvc restAgendaAlunoMockMvc;

    private AgendaAluno agendaAluno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgendaAlunoResource agendaAlunoResource = new AgendaAlunoResource(agendaAlunoRepository, mockAgendaAlunoSearchRepository);
        this.restAgendaAlunoMockMvc = MockMvcBuilders.standaloneSetup(agendaAlunoResource)
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
    public static AgendaAluno createEntity(EntityManager em) {
        AgendaAluno agendaAluno = new AgendaAluno()
            .status(DEFAULT_STATUS)
            .horario(DEFAULT_HORARIO);
        return agendaAluno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaAluno createUpdatedEntity(EntityManager em) {
        AgendaAluno agendaAluno = new AgendaAluno()
            .status(UPDATED_STATUS)
            .horario(UPDATED_HORARIO);
        return agendaAluno;
    }

    @BeforeEach
    public void initTest() {
        agendaAluno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgendaAluno() throws Exception {
        int databaseSizeBeforeCreate = agendaAlunoRepository.findAll().size();

        // Create the AgendaAluno
        restAgendaAlunoMockMvc.perform(post("/api/agenda-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaAluno)))
            .andExpect(status().isCreated());

        // Validate the AgendaAluno in the database
        List<AgendaAluno> agendaAlunoList = agendaAlunoRepository.findAll();
        assertThat(agendaAlunoList).hasSize(databaseSizeBeforeCreate + 1);
        AgendaAluno testAgendaAluno = agendaAlunoList.get(agendaAlunoList.size() - 1);
        assertThat(testAgendaAluno.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAgendaAluno.getHorario()).isEqualTo(DEFAULT_HORARIO);

        // Validate the AgendaAluno in Elasticsearch
        verify(mockAgendaAlunoSearchRepository, times(1)).save(testAgendaAluno);
    }

    @Test
    @Transactional
    public void createAgendaAlunoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agendaAlunoRepository.findAll().size();

        // Create the AgendaAluno with an existing ID
        agendaAluno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaAlunoMockMvc.perform(post("/api/agenda-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaAluno)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaAluno in the database
        List<AgendaAluno> agendaAlunoList = agendaAlunoRepository.findAll();
        assertThat(agendaAlunoList).hasSize(databaseSizeBeforeCreate);

        // Validate the AgendaAluno in Elasticsearch
        verify(mockAgendaAlunoSearchRepository, times(0)).save(agendaAluno);
    }


    @Test
    @Transactional
    public void getAllAgendaAlunos() throws Exception {
        // Initialize the database
        agendaAlunoRepository.saveAndFlush(agendaAluno);

        // Get all the agendaAlunoList
        restAgendaAlunoMockMvc.perform(get("/api/agenda-alunos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaAluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getAgendaAluno() throws Exception {
        // Initialize the database
        agendaAlunoRepository.saveAndFlush(agendaAluno);

        // Get the agendaAluno
        restAgendaAlunoMockMvc.perform(get("/api/agenda-alunos/{id}", agendaAluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agendaAluno.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgendaAluno() throws Exception {
        // Get the agendaAluno
        restAgendaAlunoMockMvc.perform(get("/api/agenda-alunos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgendaAluno() throws Exception {
        // Initialize the database
        agendaAlunoRepository.saveAndFlush(agendaAluno);

        int databaseSizeBeforeUpdate = agendaAlunoRepository.findAll().size();

        // Update the agendaAluno
        AgendaAluno updatedAgendaAluno = agendaAlunoRepository.findById(agendaAluno.getId()).get();
        // Disconnect from session so that the updates on updatedAgendaAluno are not directly saved in db
        em.detach(updatedAgendaAluno);
        updatedAgendaAluno
            .status(UPDATED_STATUS)
            .horario(UPDATED_HORARIO);

        restAgendaAlunoMockMvc.perform(put("/api/agenda-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgendaAluno)))
            .andExpect(status().isOk());

        // Validate the AgendaAluno in the database
        List<AgendaAluno> agendaAlunoList = agendaAlunoRepository.findAll();
        assertThat(agendaAlunoList).hasSize(databaseSizeBeforeUpdate);
        AgendaAluno testAgendaAluno = agendaAlunoList.get(agendaAlunoList.size() - 1);
        assertThat(testAgendaAluno.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAgendaAluno.getHorario()).isEqualTo(UPDATED_HORARIO);

        // Validate the AgendaAluno in Elasticsearch
        verify(mockAgendaAlunoSearchRepository, times(1)).save(testAgendaAluno);
    }

    @Test
    @Transactional
    public void updateNonExistingAgendaAluno() throws Exception {
        int databaseSizeBeforeUpdate = agendaAlunoRepository.findAll().size();

        // Create the AgendaAluno

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaAlunoMockMvc.perform(put("/api/agenda-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaAluno)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaAluno in the database
        List<AgendaAluno> agendaAlunoList = agendaAlunoRepository.findAll();
        assertThat(agendaAlunoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AgendaAluno in Elasticsearch
        verify(mockAgendaAlunoSearchRepository, times(0)).save(agendaAluno);
    }

    @Test
    @Transactional
    public void deleteAgendaAluno() throws Exception {
        // Initialize the database
        agendaAlunoRepository.saveAndFlush(agendaAluno);

        int databaseSizeBeforeDelete = agendaAlunoRepository.findAll().size();

        // Delete the agendaAluno
        restAgendaAlunoMockMvc.perform(delete("/api/agenda-alunos/{id}", agendaAluno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgendaAluno> agendaAlunoList = agendaAlunoRepository.findAll();
        assertThat(agendaAlunoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AgendaAluno in Elasticsearch
        verify(mockAgendaAlunoSearchRepository, times(1)).deleteById(agendaAluno.getId());
    }

    @Test
    @Transactional
    public void searchAgendaAluno() throws Exception {
        // Initialize the database
        agendaAlunoRepository.saveAndFlush(agendaAluno);
        when(mockAgendaAlunoSearchRepository.search(queryStringQuery("id:" + agendaAluno.getId())))
            .thenReturn(Collections.singletonList(agendaAluno));
        // Search the agendaAluno
        restAgendaAlunoMockMvc.perform(get("/api/_search/agenda-alunos?query=id:" + agendaAluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaAluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaAluno.class);
        AgendaAluno agendaAluno1 = new AgendaAluno();
        agendaAluno1.setId(1L);
        AgendaAluno agendaAluno2 = new AgendaAluno();
        agendaAluno2.setId(agendaAluno1.getId());
        assertThat(agendaAluno1).isEqualTo(agendaAluno2);
        agendaAluno2.setId(2L);
        assertThat(agendaAluno1).isNotEqualTo(agendaAluno2);
        agendaAluno1.setId(null);
        assertThat(agendaAluno1).isNotEqualTo(agendaAluno2);
    }
}
