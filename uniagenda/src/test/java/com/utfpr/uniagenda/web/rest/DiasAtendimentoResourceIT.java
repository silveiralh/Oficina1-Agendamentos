package com.utfpr.uniagenda.web.rest;

import com.utfpr.uniagenda.UniAgendaApp;
import com.utfpr.uniagenda.domain.DiasAtendimento;
import com.utfpr.uniagenda.repository.DiasAtendimentoRepository;
import com.utfpr.uniagenda.repository.search.DiasAtendimentoSearchRepository;
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

import com.utfpr.uniagenda.domain.enumeration.Mes;
import com.utfpr.uniagenda.domain.enumeration.DiaMes;
import com.utfpr.uniagenda.domain.enumeration.DiaSemana;
import com.utfpr.uniagenda.domain.enumeration.StatusDia;
/**
 * Integration tests for the {@link DiasAtendimentoResource} REST controller.
 */
@SpringBootTest(classes = UniAgendaApp.class)
public class DiasAtendimentoResourceIT {

    private static final Mes DEFAULT_MES = Mes.Janeiro;
    private static final Mes UPDATED_MES = Mes.Fevereiro;

    private static final DiaMes DEFAULT_DIA_MES = DiaMes.D1;
    private static final DiaMes UPDATED_DIA_MES = DiaMes.D2;

    private static final DiaSemana DEFAULT_DIA_SEMANA = DiaSemana.Segunda_feira;
    private static final DiaSemana UPDATED_DIA_SEMANA = DiaSemana.Terca_feira;

    private static final StatusDia DEFAULT_STATUS_DIA = StatusDia.Disponivel;
    private static final StatusDia UPDATED_STATUS_DIA = StatusDia.Indisponivel;

    @Autowired
    private DiasAtendimentoRepository diasAtendimentoRepository;

    /**
     * This repository is mocked in the com.utfpr.uniagenda.repository.search test package.
     *
     * @see com.utfpr.uniagenda.repository.search.DiasAtendimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private DiasAtendimentoSearchRepository mockDiasAtendimentoSearchRepository;

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

    private MockMvc restDiasAtendimentoMockMvc;

    private DiasAtendimento diasAtendimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiasAtendimentoResource diasAtendimentoResource = new DiasAtendimentoResource(diasAtendimentoRepository, mockDiasAtendimentoSearchRepository);
        this.restDiasAtendimentoMockMvc = MockMvcBuilders.standaloneSetup(diasAtendimentoResource)
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
    public static DiasAtendimento createEntity(EntityManager em) {
        DiasAtendimento diasAtendimento = new DiasAtendimento()
            .mes(DEFAULT_MES)
            .diaMes(DEFAULT_DIA_MES)
            .diaSemana(DEFAULT_DIA_SEMANA)
            .statusDia(DEFAULT_STATUS_DIA);
        return diasAtendimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiasAtendimento createUpdatedEntity(EntityManager em) {
        DiasAtendimento diasAtendimento = new DiasAtendimento()
            .mes(UPDATED_MES)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA)
            .statusDia(UPDATED_STATUS_DIA);
        return diasAtendimento;
    }

    @BeforeEach
    public void initTest() {
        diasAtendimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiasAtendimento() throws Exception {
        int databaseSizeBeforeCreate = diasAtendimentoRepository.findAll().size();

        // Create the DiasAtendimento
        restDiasAtendimentoMockMvc.perform(post("/api/dias-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasAtendimento)))
            .andExpect(status().isCreated());

        // Validate the DiasAtendimento in the database
        List<DiasAtendimento> diasAtendimentoList = diasAtendimentoRepository.findAll();
        assertThat(diasAtendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        DiasAtendimento testDiasAtendimento = diasAtendimentoList.get(diasAtendimentoList.size() - 1);
        assertThat(testDiasAtendimento.getMes()).isEqualTo(DEFAULT_MES);
        assertThat(testDiasAtendimento.getDiaMes()).isEqualTo(DEFAULT_DIA_MES);
        assertThat(testDiasAtendimento.getDiaSemana()).isEqualTo(DEFAULT_DIA_SEMANA);
        assertThat(testDiasAtendimento.getStatusDia()).isEqualTo(DEFAULT_STATUS_DIA);

        // Validate the DiasAtendimento in Elasticsearch
        verify(mockDiasAtendimentoSearchRepository, times(1)).save(testDiasAtendimento);
    }

    @Test
    @Transactional
    public void createDiasAtendimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diasAtendimentoRepository.findAll().size();

        // Create the DiasAtendimento with an existing ID
        diasAtendimento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiasAtendimentoMockMvc.perform(post("/api/dias-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasAtendimento)))
            .andExpect(status().isBadRequest());

        // Validate the DiasAtendimento in the database
        List<DiasAtendimento> diasAtendimentoList = diasAtendimentoRepository.findAll();
        assertThat(diasAtendimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the DiasAtendimento in Elasticsearch
        verify(mockDiasAtendimentoSearchRepository, times(0)).save(diasAtendimento);
    }


    @Test
    @Transactional
    public void getAllDiasAtendimentos() throws Exception {
        // Initialize the database
        diasAtendimentoRepository.saveAndFlush(diasAtendimento);

        // Get all the diasAtendimentoList
        restDiasAtendimentoMockMvc.perform(get("/api/dias-atendimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diasAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.toString())))
            .andExpect(jsonPath("$.[*].diaMes").value(hasItem(DEFAULT_DIA_MES.toString())))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA.toString())))
            .andExpect(jsonPath("$.[*].statusDia").value(hasItem(DEFAULT_STATUS_DIA.toString())));
    }
    
    @Test
    @Transactional
    public void getDiasAtendimento() throws Exception {
        // Initialize the database
        diasAtendimentoRepository.saveAndFlush(diasAtendimento);

        // Get the diasAtendimento
        restDiasAtendimentoMockMvc.perform(get("/api/dias-atendimentos/{id}", diasAtendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diasAtendimento.getId().intValue()))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES.toString()))
            .andExpect(jsonPath("$.diaMes").value(DEFAULT_DIA_MES.toString()))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA.toString()))
            .andExpect(jsonPath("$.statusDia").value(DEFAULT_STATUS_DIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiasAtendimento() throws Exception {
        // Get the diasAtendimento
        restDiasAtendimentoMockMvc.perform(get("/api/dias-atendimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiasAtendimento() throws Exception {
        // Initialize the database
        diasAtendimentoRepository.saveAndFlush(diasAtendimento);

        int databaseSizeBeforeUpdate = diasAtendimentoRepository.findAll().size();

        // Update the diasAtendimento
        DiasAtendimento updatedDiasAtendimento = diasAtendimentoRepository.findById(diasAtendimento.getId()).get();
        // Disconnect from session so that the updates on updatedDiasAtendimento are not directly saved in db
        em.detach(updatedDiasAtendimento);
        updatedDiasAtendimento
            .mes(UPDATED_MES)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA)
            .statusDia(UPDATED_STATUS_DIA);

        restDiasAtendimentoMockMvc.perform(put("/api/dias-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiasAtendimento)))
            .andExpect(status().isOk());

        // Validate the DiasAtendimento in the database
        List<DiasAtendimento> diasAtendimentoList = diasAtendimentoRepository.findAll();
        assertThat(diasAtendimentoList).hasSize(databaseSizeBeforeUpdate);
        DiasAtendimento testDiasAtendimento = diasAtendimentoList.get(diasAtendimentoList.size() - 1);
        assertThat(testDiasAtendimento.getMes()).isEqualTo(UPDATED_MES);
        assertThat(testDiasAtendimento.getDiaMes()).isEqualTo(UPDATED_DIA_MES);
        assertThat(testDiasAtendimento.getDiaSemana()).isEqualTo(UPDATED_DIA_SEMANA);
        assertThat(testDiasAtendimento.getStatusDia()).isEqualTo(UPDATED_STATUS_DIA);

        // Validate the DiasAtendimento in Elasticsearch
        verify(mockDiasAtendimentoSearchRepository, times(1)).save(testDiasAtendimento);
    }

    @Test
    @Transactional
    public void updateNonExistingDiasAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = diasAtendimentoRepository.findAll().size();

        // Create the DiasAtendimento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiasAtendimentoMockMvc.perform(put("/api/dias-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasAtendimento)))
            .andExpect(status().isBadRequest());

        // Validate the DiasAtendimento in the database
        List<DiasAtendimento> diasAtendimentoList = diasAtendimentoRepository.findAll();
        assertThat(diasAtendimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DiasAtendimento in Elasticsearch
        verify(mockDiasAtendimentoSearchRepository, times(0)).save(diasAtendimento);
    }

    @Test
    @Transactional
    public void deleteDiasAtendimento() throws Exception {
        // Initialize the database
        diasAtendimentoRepository.saveAndFlush(diasAtendimento);

        int databaseSizeBeforeDelete = diasAtendimentoRepository.findAll().size();

        // Delete the diasAtendimento
        restDiasAtendimentoMockMvc.perform(delete("/api/dias-atendimentos/{id}", diasAtendimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiasAtendimento> diasAtendimentoList = diasAtendimentoRepository.findAll();
        assertThat(diasAtendimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DiasAtendimento in Elasticsearch
        verify(mockDiasAtendimentoSearchRepository, times(1)).deleteById(diasAtendimento.getId());
    }

    @Test
    @Transactional
    public void searchDiasAtendimento() throws Exception {
        // Initialize the database
        diasAtendimentoRepository.saveAndFlush(diasAtendimento);
        when(mockDiasAtendimentoSearchRepository.search(queryStringQuery("id:" + diasAtendimento.getId())))
            .thenReturn(Collections.singletonList(diasAtendimento));
        // Search the diasAtendimento
        restDiasAtendimentoMockMvc.perform(get("/api/_search/dias-atendimentos?query=id:" + diasAtendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diasAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.toString())))
            .andExpect(jsonPath("$.[*].diaMes").value(hasItem(DEFAULT_DIA_MES.toString())))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA.toString())))
            .andExpect(jsonPath("$.[*].statusDia").value(hasItem(DEFAULT_STATUS_DIA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiasAtendimento.class);
        DiasAtendimento diasAtendimento1 = new DiasAtendimento();
        diasAtendimento1.setId(1L);
        DiasAtendimento diasAtendimento2 = new DiasAtendimento();
        diasAtendimento2.setId(diasAtendimento1.getId());
        assertThat(diasAtendimento1).isEqualTo(diasAtendimento2);
        diasAtendimento2.setId(2L);
        assertThat(diasAtendimento1).isNotEqualTo(diasAtendimento2);
        diasAtendimento1.setId(null);
        assertThat(diasAtendimento1).isNotEqualTo(diasAtendimento2);
    }
}
