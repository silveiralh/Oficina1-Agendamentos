package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.AgendaApp;
import com.uniagenda.agenda.domain.AgendaServidor;
import com.uniagenda.agenda.repository.AgendaServidorRepository;
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
import com.uniagenda.agenda.domain.enumeration.Horario;
import com.uniagenda.agenda.domain.enumeration.DiaMes;
import com.uniagenda.agenda.domain.enumeration.DiaSemana;
import com.uniagenda.agenda.domain.enumeration.StatusDia;
/**
 * Integration tests for the {@link AgendaServidorResource} REST controller.
 */
@SpringBootTest(classes = AgendaApp.class)
public class AgendaServidorResourceIT {

    private static final Mes DEFAULT_MES = Mes.Janeiro;
    private static final Mes UPDATED_MES = Mes.Fevereiro;

    private static final Horario DEFAULT_HORARIO = Horario.H8;
    private static final Horario UPDATED_HORARIO = Horario.H9;

    private static final DiaMes DEFAULT_DIA_MES = DiaMes.D1;
    private static final DiaMes UPDATED_DIA_MES = DiaMes.D2;

    private static final DiaSemana DEFAULT_DIA_SEMANA = DiaSemana.Segunda_feira;
    private static final DiaSemana UPDATED_DIA_SEMANA = DiaSemana.Terca_feira;

    private static final StatusDia DEFAULT_STATUS_DIA = StatusDia.Disponivel;
    private static final StatusDia UPDATED_STATUS_DIA = StatusDia.Indisponivel;

    @Autowired
    private AgendaServidorRepository agendaServidorRepository;

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

    private MockMvc restAgendaServidorMockMvc;

    private AgendaServidor agendaServidor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgendaServidorResource agendaServidorResource = new AgendaServidorResource(agendaServidorRepository);
        this.restAgendaServidorMockMvc = MockMvcBuilders.standaloneSetup(agendaServidorResource)
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
    public static AgendaServidor createEntity(EntityManager em) {
        AgendaServidor agendaServidor = new AgendaServidor()
            .mes(DEFAULT_MES)
            .horario(DEFAULT_HORARIO)
            .diaMes(DEFAULT_DIA_MES)
            .diaSemana(DEFAULT_DIA_SEMANA)
            .statusDia(DEFAULT_STATUS_DIA);
        return agendaServidor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaServidor createUpdatedEntity(EntityManager em) {
        AgendaServidor agendaServidor = new AgendaServidor()
            .mes(UPDATED_MES)
            .horario(UPDATED_HORARIO)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA)
            .statusDia(UPDATED_STATUS_DIA);
        return agendaServidor;
    }

    @BeforeEach
    public void initTest() {
        agendaServidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgendaServidor() throws Exception {
        int databaseSizeBeforeCreate = agendaServidorRepository.findAll().size();

        // Create the AgendaServidor
        restAgendaServidorMockMvc.perform(post("/api/agenda-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaServidor)))
            .andExpect(status().isCreated());

        // Validate the AgendaServidor in the database
        List<AgendaServidor> agendaServidorList = agendaServidorRepository.findAll();
        assertThat(agendaServidorList).hasSize(databaseSizeBeforeCreate + 1);
        AgendaServidor testAgendaServidor = agendaServidorList.get(agendaServidorList.size() - 1);
        assertThat(testAgendaServidor.getMes()).isEqualTo(DEFAULT_MES);
        assertThat(testAgendaServidor.getHorario()).isEqualTo(DEFAULT_HORARIO);
        assertThat(testAgendaServidor.getDiaMes()).isEqualTo(DEFAULT_DIA_MES);
        assertThat(testAgendaServidor.getDiaSemana()).isEqualTo(DEFAULT_DIA_SEMANA);
        assertThat(testAgendaServidor.getStatusDia()).isEqualTo(DEFAULT_STATUS_DIA);
    }

    @Test
    @Transactional
    public void createAgendaServidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agendaServidorRepository.findAll().size();

        // Create the AgendaServidor with an existing ID
        agendaServidor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaServidorMockMvc.perform(post("/api/agenda-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaServidor)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaServidor in the database
        List<AgendaServidor> agendaServidorList = agendaServidorRepository.findAll();
        assertThat(agendaServidorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgendaServidors() throws Exception {
        // Initialize the database
        agendaServidorRepository.saveAndFlush(agendaServidor);

        // Get all the agendaServidorList
        restAgendaServidorMockMvc.perform(get("/api/agenda-servidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaServidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.toString())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())))
            .andExpect(jsonPath("$.[*].diaMes").value(hasItem(DEFAULT_DIA_MES.toString())))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA.toString())))
            .andExpect(jsonPath("$.[*].statusDia").value(hasItem(DEFAULT_STATUS_DIA.toString())));
    }
    
    @Test
    @Transactional
    public void getAgendaServidor() throws Exception {
        // Initialize the database
        agendaServidorRepository.saveAndFlush(agendaServidor);

        // Get the agendaServidor
        restAgendaServidorMockMvc.perform(get("/api/agenda-servidors/{id}", agendaServidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agendaServidor.getId().intValue()))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES.toString()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()))
            .andExpect(jsonPath("$.diaMes").value(DEFAULT_DIA_MES.toString()))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA.toString()))
            .andExpect(jsonPath("$.statusDia").value(DEFAULT_STATUS_DIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgendaServidor() throws Exception {
        // Get the agendaServidor
        restAgendaServidorMockMvc.perform(get("/api/agenda-servidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgendaServidor() throws Exception {
        // Initialize the database
        agendaServidorRepository.saveAndFlush(agendaServidor);

        int databaseSizeBeforeUpdate = agendaServidorRepository.findAll().size();

        // Update the agendaServidor
        AgendaServidor updatedAgendaServidor = agendaServidorRepository.findById(agendaServidor.getId()).get();
        // Disconnect from session so that the updates on updatedAgendaServidor are not directly saved in db
        em.detach(updatedAgendaServidor);
        updatedAgendaServidor
            .mes(UPDATED_MES)
            .horario(UPDATED_HORARIO)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA)
            .statusDia(UPDATED_STATUS_DIA);

        restAgendaServidorMockMvc.perform(put("/api/agenda-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgendaServidor)))
            .andExpect(status().isOk());

        // Validate the AgendaServidor in the database
        List<AgendaServidor> agendaServidorList = agendaServidorRepository.findAll();
        assertThat(agendaServidorList).hasSize(databaseSizeBeforeUpdate);
        AgendaServidor testAgendaServidor = agendaServidorList.get(agendaServidorList.size() - 1);
        assertThat(testAgendaServidor.getMes()).isEqualTo(UPDATED_MES);
        assertThat(testAgendaServidor.getHorario()).isEqualTo(UPDATED_HORARIO);
        assertThat(testAgendaServidor.getDiaMes()).isEqualTo(UPDATED_DIA_MES);
        assertThat(testAgendaServidor.getDiaSemana()).isEqualTo(UPDATED_DIA_SEMANA);
        assertThat(testAgendaServidor.getStatusDia()).isEqualTo(UPDATED_STATUS_DIA);
    }

    @Test
    @Transactional
    public void updateNonExistingAgendaServidor() throws Exception {
        int databaseSizeBeforeUpdate = agendaServidorRepository.findAll().size();

        // Create the AgendaServidor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaServidorMockMvc.perform(put("/api/agenda-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaServidor)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaServidor in the database
        List<AgendaServidor> agendaServidorList = agendaServidorRepository.findAll();
        assertThat(agendaServidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgendaServidor() throws Exception {
        // Initialize the database
        agendaServidorRepository.saveAndFlush(agendaServidor);

        int databaseSizeBeforeDelete = agendaServidorRepository.findAll().size();

        // Delete the agendaServidor
        restAgendaServidorMockMvc.perform(delete("/api/agenda-servidors/{id}", agendaServidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgendaServidor> agendaServidorList = agendaServidorRepository.findAll();
        assertThat(agendaServidorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaServidor.class);
        AgendaServidor agendaServidor1 = new AgendaServidor();
        agendaServidor1.setId(1L);
        AgendaServidor agendaServidor2 = new AgendaServidor();
        agendaServidor2.setId(agendaServidor1.getId());
        assertThat(agendaServidor1).isEqualTo(agendaServidor2);
        agendaServidor2.setId(2L);
        assertThat(agendaServidor1).isNotEqualTo(agendaServidor2);
        agendaServidor1.setId(null);
        assertThat(agendaServidor1).isNotEqualTo(agendaServidor2);
    }
}
