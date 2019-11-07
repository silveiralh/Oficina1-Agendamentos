package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.AgendaApp;
import com.uniagenda.agenda.domain.DiasAtendimentoServidor;
import com.uniagenda.agenda.repository.DiasAtendimentoServidorRepository;
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

import com.uniagenda.agenda.domain.enumeration.Mes;
import com.uniagenda.agenda.domain.enumeration.DiaMes;
import com.uniagenda.agenda.domain.enumeration.DiaSemana;
import com.uniagenda.agenda.domain.enumeration.StatusDia;
/**
 * Integration tests for the {@link DiasAtendimentoServidorResource} REST controller.
 */
@SpringBootTest(classes = AgendaApp.class)
public class DiasAtendimentoServidorResourceIT {

    private static final Mes DEFAULT_MES = Mes.Janeiro;
    private static final Mes UPDATED_MES = Mes.Fevereiro;

    private static final DiaMes DEFAULT_DIA_MES = DiaMes.D1;
    private static final DiaMes UPDATED_DIA_MES = DiaMes.D2;

    private static final DiaSemana DEFAULT_DIA_SEMANA = DiaSemana.Segunda_feira;
    private static final DiaSemana UPDATED_DIA_SEMANA = DiaSemana.Terca_feira;

    private static final StatusDia DEFAULT_STATUS_DIA = StatusDia.Disponivel;
    private static final StatusDia UPDATED_STATUS_DIA = StatusDia.Indisponivel;

    @Autowired
    private DiasAtendimentoServidorRepository diasAtendimentoServidorRepository;

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

    private MockMvc restDiasAtendimentoServidorMockMvc;

    private DiasAtendimentoServidor diasAtendimentoServidor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiasAtendimentoServidorResource diasAtendimentoServidorResource = new DiasAtendimentoServidorResource(diasAtendimentoServidorRepository);
        this.restDiasAtendimentoServidorMockMvc = MockMvcBuilders.standaloneSetup(diasAtendimentoServidorResource)
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
    public static DiasAtendimentoServidor createEntity(EntityManager em) {
        DiasAtendimentoServidor diasAtendimentoServidor = new DiasAtendimentoServidor()
            .mes(DEFAULT_MES)
            .diaMes(DEFAULT_DIA_MES)
            .diaSemana(DEFAULT_DIA_SEMANA)
            .statusDia(DEFAULT_STATUS_DIA);
        return diasAtendimentoServidor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiasAtendimentoServidor createUpdatedEntity(EntityManager em) {
        DiasAtendimentoServidor diasAtendimentoServidor = new DiasAtendimentoServidor()
            .mes(UPDATED_MES)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA)
            .statusDia(UPDATED_STATUS_DIA);
        return diasAtendimentoServidor;
    }

    @BeforeEach
    public void initTest() {
        diasAtendimentoServidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiasAtendimentoServidor() throws Exception {
        int databaseSizeBeforeCreate = diasAtendimentoServidorRepository.findAll().size();

        // Create the DiasAtendimentoServidor
        restDiasAtendimentoServidorMockMvc.perform(post("/api/dias-atendimento-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasAtendimentoServidor)))
            .andExpect(status().isCreated());

        // Validate the DiasAtendimentoServidor in the database
        List<DiasAtendimentoServidor> diasAtendimentoServidorList = diasAtendimentoServidorRepository.findAll();
        assertThat(diasAtendimentoServidorList).hasSize(databaseSizeBeforeCreate + 1);
        DiasAtendimentoServidor testDiasAtendimentoServidor = diasAtendimentoServidorList.get(diasAtendimentoServidorList.size() - 1);
        assertThat(testDiasAtendimentoServidor.getMes()).isEqualTo(DEFAULT_MES);
        assertThat(testDiasAtendimentoServidor.getDiaMes()).isEqualTo(DEFAULT_DIA_MES);
        assertThat(testDiasAtendimentoServidor.getDiaSemana()).isEqualTo(DEFAULT_DIA_SEMANA);
        assertThat(testDiasAtendimentoServidor.getStatusDia()).isEqualTo(DEFAULT_STATUS_DIA);
    }

    @Test
    @Transactional
    public void createDiasAtendimentoServidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diasAtendimentoServidorRepository.findAll().size();

        // Create the DiasAtendimentoServidor with an existing ID
        diasAtendimentoServidor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiasAtendimentoServidorMockMvc.perform(post("/api/dias-atendimento-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasAtendimentoServidor)))
            .andExpect(status().isBadRequest());

        // Validate the DiasAtendimentoServidor in the database
        List<DiasAtendimentoServidor> diasAtendimentoServidorList = diasAtendimentoServidorRepository.findAll();
        assertThat(diasAtendimentoServidorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDiasAtendimentoServidors() throws Exception {
        // Initialize the database
        diasAtendimentoServidorRepository.saveAndFlush(diasAtendimentoServidor);

        // Get all the diasAtendimentoServidorList
        restDiasAtendimentoServidorMockMvc.perform(get("/api/dias-atendimento-servidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diasAtendimentoServidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.toString())))
            .andExpect(jsonPath("$.[*].diaMes").value(hasItem(DEFAULT_DIA_MES.toString())))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA.toString())))
            .andExpect(jsonPath("$.[*].statusDia").value(hasItem(DEFAULT_STATUS_DIA.toString())));
    }
    
    @Test
    @Transactional
    public void getDiasAtendimentoServidor() throws Exception {
        // Initialize the database
        diasAtendimentoServidorRepository.saveAndFlush(diasAtendimentoServidor);

        // Get the diasAtendimentoServidor
        restDiasAtendimentoServidorMockMvc.perform(get("/api/dias-atendimento-servidors/{id}", diasAtendimentoServidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diasAtendimentoServidor.getId().intValue()))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES.toString()))
            .andExpect(jsonPath("$.diaMes").value(DEFAULT_DIA_MES.toString()))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA.toString()))
            .andExpect(jsonPath("$.statusDia").value(DEFAULT_STATUS_DIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiasAtendimentoServidor() throws Exception {
        // Get the diasAtendimentoServidor
        restDiasAtendimentoServidorMockMvc.perform(get("/api/dias-atendimento-servidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiasAtendimentoServidor() throws Exception {
        // Initialize the database
        diasAtendimentoServidorRepository.saveAndFlush(diasAtendimentoServidor);

        int databaseSizeBeforeUpdate = diasAtendimentoServidorRepository.findAll().size();

        // Update the diasAtendimentoServidor
        DiasAtendimentoServidor updatedDiasAtendimentoServidor = diasAtendimentoServidorRepository.findById(diasAtendimentoServidor.getId()).get();
        // Disconnect from session so that the updates on updatedDiasAtendimentoServidor are not directly saved in db
        em.detach(updatedDiasAtendimentoServidor);
        updatedDiasAtendimentoServidor
            .mes(UPDATED_MES)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA)
            .statusDia(UPDATED_STATUS_DIA);

        restDiasAtendimentoServidorMockMvc.perform(put("/api/dias-atendimento-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiasAtendimentoServidor)))
            .andExpect(status().isOk());

        // Validate the DiasAtendimentoServidor in the database
        List<DiasAtendimentoServidor> diasAtendimentoServidorList = diasAtendimentoServidorRepository.findAll();
        assertThat(diasAtendimentoServidorList).hasSize(databaseSizeBeforeUpdate);
        DiasAtendimentoServidor testDiasAtendimentoServidor = diasAtendimentoServidorList.get(diasAtendimentoServidorList.size() - 1);
        assertThat(testDiasAtendimentoServidor.getMes()).isEqualTo(UPDATED_MES);
        assertThat(testDiasAtendimentoServidor.getDiaMes()).isEqualTo(UPDATED_DIA_MES);
        assertThat(testDiasAtendimentoServidor.getDiaSemana()).isEqualTo(UPDATED_DIA_SEMANA);
        assertThat(testDiasAtendimentoServidor.getStatusDia()).isEqualTo(UPDATED_STATUS_DIA);
    }

    @Test
    @Transactional
    public void updateNonExistingDiasAtendimentoServidor() throws Exception {
        int databaseSizeBeforeUpdate = diasAtendimentoServidorRepository.findAll().size();

        // Create the DiasAtendimentoServidor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiasAtendimentoServidorMockMvc.perform(put("/api/dias-atendimento-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasAtendimentoServidor)))
            .andExpect(status().isBadRequest());

        // Validate the DiasAtendimentoServidor in the database
        List<DiasAtendimentoServidor> diasAtendimentoServidorList = diasAtendimentoServidorRepository.findAll();
        assertThat(diasAtendimentoServidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiasAtendimentoServidor() throws Exception {
        // Initialize the database
        diasAtendimentoServidorRepository.saveAndFlush(diasAtendimentoServidor);

        int databaseSizeBeforeDelete = diasAtendimentoServidorRepository.findAll().size();

        // Delete the diasAtendimentoServidor
        restDiasAtendimentoServidorMockMvc.perform(delete("/api/dias-atendimento-servidors/{id}", diasAtendimentoServidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiasAtendimentoServidor> diasAtendimentoServidorList = diasAtendimentoServidorRepository.findAll();
        assertThat(diasAtendimentoServidorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiasAtendimentoServidor.class);
        DiasAtendimentoServidor diasAtendimentoServidor1 = new DiasAtendimentoServidor();
        diasAtendimentoServidor1.setId(1L);
        DiasAtendimentoServidor diasAtendimentoServidor2 = new DiasAtendimentoServidor();
        diasAtendimentoServidor2.setId(diasAtendimentoServidor1.getId());
        assertThat(diasAtendimentoServidor1).isEqualTo(diasAtendimentoServidor2);
        diasAtendimentoServidor2.setId(2L);
        assertThat(diasAtendimentoServidor1).isNotEqualTo(diasAtendimentoServidor2);
        diasAtendimentoServidor1.setId(null);
        assertThat(diasAtendimentoServidor1).isNotEqualTo(diasAtendimentoServidor2);
    }
}
