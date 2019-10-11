package com.utfpr.uniagenda.web.rest;

import com.utfpr.uniagenda.UniAgendaApp;
import com.utfpr.uniagenda.domain.Aluno;
import com.utfpr.uniagenda.repository.AlunoRepository;
import com.utfpr.uniagenda.repository.search.AlunoSearchRepository;
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

import com.utfpr.uniagenda.domain.enumeration.Curso;
/**
 * Integration tests for the {@link AlunoResource} REST controller.
 */
@SpringBootTest(classes = UniAgendaApp.class)
public class AlunoResourceIT {

    private static final Double DEFAULT_RA_ALUNO = 1D;
    private static final Double UPDATED_RA_ALUNO = 2D;
    private static final Double SMALLER_RA_ALUNO = 1D - 1D;

    private static final String DEFAULT_NOME_ALUNO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_ALUNO = "BBBBBBBBBB";

    private static final Double DEFAULT_PERIODO = 1D;
    private static final Double UPDATED_PERIODO = 2D;
    private static final Double SMALLER_PERIODO = 1D - 1D;

    private static final Curso DEFAULT_CURSO = Curso.Engenharia_Eletrica;
    private static final Curso UPDATED_CURSO = Curso.Engenharia_Computacao;

    @Autowired
    private AlunoRepository alunoRepository;

    /**
     * This repository is mocked in the com.utfpr.uniagenda.repository.search test package.
     *
     * @see com.utfpr.uniagenda.repository.search.AlunoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AlunoSearchRepository mockAlunoSearchRepository;

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

    private MockMvc restAlunoMockMvc;

    private Aluno aluno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlunoResource alunoResource = new AlunoResource(alunoRepository, mockAlunoSearchRepository);
        this.restAlunoMockMvc = MockMvcBuilders.standaloneSetup(alunoResource)
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
    public static Aluno createEntity(EntityManager em) {
        Aluno aluno = new Aluno()
            .raAluno(DEFAULT_RA_ALUNO)
            .nomeAluno(DEFAULT_NOME_ALUNO)
            .periodo(DEFAULT_PERIODO)
            .curso(DEFAULT_CURSO);
        return aluno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createUpdatedEntity(EntityManager em) {
        Aluno aluno = new Aluno()
            .raAluno(UPDATED_RA_ALUNO)
            .nomeAluno(UPDATED_NOME_ALUNO)
            .periodo(UPDATED_PERIODO)
            .curso(UPDATED_CURSO);
        return aluno;
    }

    @BeforeEach
    public void initTest() {
        aluno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAluno() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno
        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aluno)))
            .andExpect(status().isCreated());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeCreate + 1);
        Aluno testAluno = alunoList.get(alunoList.size() - 1);
        assertThat(testAluno.getRaAluno()).isEqualTo(DEFAULT_RA_ALUNO);
        assertThat(testAluno.getNomeAluno()).isEqualTo(DEFAULT_NOME_ALUNO);
        assertThat(testAluno.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testAluno.getCurso()).isEqualTo(DEFAULT_CURSO);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(1)).save(testAluno);
    }

    @Test
    @Transactional
    public void createAlunoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno with an existing ID
        aluno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aluno)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(0)).save(aluno);
    }


    @Test
    @Transactional
    public void getAllAlunos() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].raAluno").value(hasItem(DEFAULT_RA_ALUNO.doubleValue())))
            .andExpect(jsonPath("$.[*].nomeAluno").value(hasItem(DEFAULT_NOME_ALUNO.toString())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.doubleValue())))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO.toString())));
    }
    
    @Test
    @Transactional
    public void getAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aluno.getId().intValue()))
            .andExpect(jsonPath("$.raAluno").value(DEFAULT_RA_ALUNO.doubleValue()))
            .andExpect(jsonPath("$.nomeAluno").value(DEFAULT_NOME_ALUNO.toString()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO.doubleValue()))
            .andExpect(jsonPath("$.curso").value(DEFAULT_CURSO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAluno() throws Exception {
        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Update the aluno
        Aluno updatedAluno = alunoRepository.findById(aluno.getId()).get();
        // Disconnect from session so that the updates on updatedAluno are not directly saved in db
        em.detach(updatedAluno);
        updatedAluno
            .raAluno(UPDATED_RA_ALUNO)
            .nomeAluno(UPDATED_NOME_ALUNO)
            .periodo(UPDATED_PERIODO)
            .curso(UPDATED_CURSO);

        restAlunoMockMvc.perform(put("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAluno)))
            .andExpect(status().isOk());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeUpdate);
        Aluno testAluno = alunoList.get(alunoList.size() - 1);
        assertThat(testAluno.getRaAluno()).isEqualTo(UPDATED_RA_ALUNO);
        assertThat(testAluno.getNomeAluno()).isEqualTo(UPDATED_NOME_ALUNO);
        assertThat(testAluno.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testAluno.getCurso()).isEqualTo(UPDATED_CURSO);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(1)).save(testAluno);
    }

    @Test
    @Transactional
    public void updateNonExistingAluno() throws Exception {
        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Create the Aluno

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlunoMockMvc.perform(put("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aluno)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(0)).save(aluno);
    }

    @Test
    @Transactional
    public void deleteAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        int databaseSizeBeforeDelete = alunoRepository.findAll().size();

        // Delete the aluno
        restAlunoMockMvc.perform(delete("/api/alunos/{id}", aluno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(1)).deleteById(aluno.getId());
    }

    @Test
    @Transactional
    public void searchAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);
        when(mockAlunoSearchRepository.search(queryStringQuery("id:" + aluno.getId())))
            .thenReturn(Collections.singletonList(aluno));
        // Search the aluno
        restAlunoMockMvc.perform(get("/api/_search/alunos?query=id:" + aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].raAluno").value(hasItem(DEFAULT_RA_ALUNO.doubleValue())))
            .andExpect(jsonPath("$.[*].nomeAluno").value(hasItem(DEFAULT_NOME_ALUNO)))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.doubleValue())))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aluno.class);
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        Aluno aluno2 = new Aluno();
        aluno2.setId(aluno1.getId());
        assertThat(aluno1).isEqualTo(aluno2);
        aluno2.setId(2L);
        assertThat(aluno1).isNotEqualTo(aluno2);
        aluno1.setId(null);
        assertThat(aluno1).isNotEqualTo(aluno2);
    }
}
