package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.AgendaApp;
import com.uniagenda.agenda.domain.DiasAulaAluno;
import com.uniagenda.agenda.repository.DiasAulaAlunoRepository;
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

import com.uniagenda.agenda.domain.enumeration.Semestre;
import com.uniagenda.agenda.domain.enumeration.Horario;
import com.uniagenda.agenda.domain.enumeration.DiaMes;
import com.uniagenda.agenda.domain.enumeration.DiaSemana;
/**
 * Integration tests for the {@link DiasAulaAlunoResource} REST controller.
 */
@SpringBootTest(classes = AgendaApp.class)
public class DiasAulaAlunoResourceIT {

    private static final Semestre DEFAULT_SEMESTRE = Semestre.Semestre;
    private static final Semestre UPDATED_SEMESTRE = Semestre.Semestre;

    private static final Horario DEFAULT_HORARIO = Horario.H8;
    private static final Horario UPDATED_HORARIO = Horario.H9;

    private static final DiaMes DEFAULT_DIA_MES = DiaMes.D1;
    private static final DiaMes UPDATED_DIA_MES = DiaMes.D2;

    private static final DiaSemana DEFAULT_DIA_SEMANA = DiaSemana.Segunda_feira;
    private static final DiaSemana UPDATED_DIA_SEMANA = DiaSemana.Terca_feira;

    @Autowired
    private DiasAulaAlunoRepository diasAulaAlunoRepository;

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

    private MockMvc restDiasAulaAlunoMockMvc;

    private DiasAulaAluno diasAulaAluno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiasAulaAlunoResource diasAulaAlunoResource = new DiasAulaAlunoResource(diasAulaAlunoRepository);
        this.restDiasAulaAlunoMockMvc = MockMvcBuilders.standaloneSetup(diasAulaAlunoResource)
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
    public static DiasAulaAluno createEntity(EntityManager em) {
        DiasAulaAluno diasAulaAluno = new DiasAulaAluno()
            .semestre(DEFAULT_SEMESTRE)
            .horario(DEFAULT_HORARIO)
            .diaMes(DEFAULT_DIA_MES)
            .diaSemana(DEFAULT_DIA_SEMANA);
        return diasAulaAluno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiasAulaAluno createUpdatedEntity(EntityManager em) {
        DiasAulaAluno diasAulaAluno = new DiasAulaAluno()
            .semestre(UPDATED_SEMESTRE)
            .horario(UPDATED_HORARIO)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA);
        return diasAulaAluno;
    }

    @BeforeEach
    public void initTest() {
        diasAulaAluno = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiasAulaAluno() throws Exception {
        int databaseSizeBeforeCreate = diasAulaAlunoRepository.findAll().size();

        // Create the DiasAulaAluno
        restDiasAulaAlunoMockMvc.perform(post("/api/dias-aula-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasAulaAluno)))
            .andExpect(status().isCreated());

        // Validate the DiasAulaAluno in the database
        List<DiasAulaAluno> diasAulaAlunoList = diasAulaAlunoRepository.findAll();
        assertThat(diasAulaAlunoList).hasSize(databaseSizeBeforeCreate + 1);
        DiasAulaAluno testDiasAulaAluno = diasAulaAlunoList.get(diasAulaAlunoList.size() - 1);
        assertThat(testDiasAulaAluno.getSemestre()).isEqualTo(DEFAULT_SEMESTRE);
        assertThat(testDiasAulaAluno.getHorario()).isEqualTo(DEFAULT_HORARIO);
        assertThat(testDiasAulaAluno.getDiaMes()).isEqualTo(DEFAULT_DIA_MES);
        assertThat(testDiasAulaAluno.getDiaSemana()).isEqualTo(DEFAULT_DIA_SEMANA);
    }

    @Test
    @Transactional
    public void createDiasAulaAlunoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diasAulaAlunoRepository.findAll().size();

        // Create the DiasAulaAluno with an existing ID
        diasAulaAluno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiasAulaAlunoMockMvc.perform(post("/api/dias-aula-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasAulaAluno)))
            .andExpect(status().isBadRequest());

        // Validate the DiasAulaAluno in the database
        List<DiasAulaAluno> diasAulaAlunoList = diasAulaAlunoRepository.findAll();
        assertThat(diasAulaAlunoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDiasAulaAlunos() throws Exception {
        // Initialize the database
        diasAulaAlunoRepository.saveAndFlush(diasAulaAluno);

        // Get all the diasAulaAlunoList
        restDiasAulaAlunoMockMvc.perform(get("/api/dias-aula-alunos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diasAulaAluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].semestre").value(hasItem(DEFAULT_SEMESTRE.toString())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())))
            .andExpect(jsonPath("$.[*].diaMes").value(hasItem(DEFAULT_DIA_MES.toString())))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA.toString())));
    }
    
    @Test
    @Transactional
    public void getDiasAulaAluno() throws Exception {
        // Initialize the database
        diasAulaAlunoRepository.saveAndFlush(diasAulaAluno);

        // Get the diasAulaAluno
        restDiasAulaAlunoMockMvc.perform(get("/api/dias-aula-alunos/{id}", diasAulaAluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diasAulaAluno.getId().intValue()))
            .andExpect(jsonPath("$.semestre").value(DEFAULT_SEMESTRE.toString()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()))
            .andExpect(jsonPath("$.diaMes").value(DEFAULT_DIA_MES.toString()))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiasAulaAluno() throws Exception {
        // Get the diasAulaAluno
        restDiasAulaAlunoMockMvc.perform(get("/api/dias-aula-alunos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiasAulaAluno() throws Exception {
        // Initialize the database
        diasAulaAlunoRepository.saveAndFlush(diasAulaAluno);

        int databaseSizeBeforeUpdate = diasAulaAlunoRepository.findAll().size();

        // Update the diasAulaAluno
        DiasAulaAluno updatedDiasAulaAluno = diasAulaAlunoRepository.findById(diasAulaAluno.getId()).get();
        // Disconnect from session so that the updates on updatedDiasAulaAluno are not directly saved in db
        em.detach(updatedDiasAulaAluno);
        updatedDiasAulaAluno
            .semestre(UPDATED_SEMESTRE)
            .horario(UPDATED_HORARIO)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA);

        restDiasAulaAlunoMockMvc.perform(put("/api/dias-aula-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiasAulaAluno)))
            .andExpect(status().isOk());

        // Validate the DiasAulaAluno in the database
        List<DiasAulaAluno> diasAulaAlunoList = diasAulaAlunoRepository.findAll();
        assertThat(diasAulaAlunoList).hasSize(databaseSizeBeforeUpdate);
        DiasAulaAluno testDiasAulaAluno = diasAulaAlunoList.get(diasAulaAlunoList.size() - 1);
        assertThat(testDiasAulaAluno.getSemestre()).isEqualTo(UPDATED_SEMESTRE);
        assertThat(testDiasAulaAluno.getHorario()).isEqualTo(UPDATED_HORARIO);
        assertThat(testDiasAulaAluno.getDiaMes()).isEqualTo(UPDATED_DIA_MES);
        assertThat(testDiasAulaAluno.getDiaSemana()).isEqualTo(UPDATED_DIA_SEMANA);
    }

    @Test
    @Transactional
    public void updateNonExistingDiasAulaAluno() throws Exception {
        int databaseSizeBeforeUpdate = diasAulaAlunoRepository.findAll().size();

        // Create the DiasAulaAluno

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiasAulaAlunoMockMvc.perform(put("/api/dias-aula-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasAulaAluno)))
            .andExpect(status().isBadRequest());

        // Validate the DiasAulaAluno in the database
        List<DiasAulaAluno> diasAulaAlunoList = diasAulaAlunoRepository.findAll();
        assertThat(diasAulaAlunoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiasAulaAluno() throws Exception {
        // Initialize the database
        diasAulaAlunoRepository.saveAndFlush(diasAulaAluno);

        int databaseSizeBeforeDelete = diasAulaAlunoRepository.findAll().size();

        // Delete the diasAulaAluno
        restDiasAulaAlunoMockMvc.perform(delete("/api/dias-aula-alunos/{id}", diasAulaAluno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiasAulaAluno> diasAulaAlunoList = diasAulaAlunoRepository.findAll();
        assertThat(diasAulaAlunoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiasAulaAluno.class);
        DiasAulaAluno diasAulaAluno1 = new DiasAulaAluno();
        diasAulaAluno1.setId(1L);
        DiasAulaAluno diasAulaAluno2 = new DiasAulaAluno();
        diasAulaAluno2.setId(diasAulaAluno1.getId());
        assertThat(diasAulaAluno1).isEqualTo(diasAulaAluno2);
        diasAulaAluno2.setId(2L);
        assertThat(diasAulaAluno1).isNotEqualTo(diasAulaAluno2);
        diasAulaAluno1.setId(null);
        assertThat(diasAulaAluno1).isNotEqualTo(diasAulaAluno2);
    }
}
