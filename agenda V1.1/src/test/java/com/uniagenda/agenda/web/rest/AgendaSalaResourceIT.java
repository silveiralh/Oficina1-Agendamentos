package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.AgendaApp;
import com.uniagenda.agenda.domain.AgendaSala;
import com.uniagenda.agenda.repository.AgendaSalaRepository;
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

import com.uniagenda.agenda.domain.enumeration.StatusDia;
import com.uniagenda.agenda.domain.enumeration.Horario;
import com.uniagenda.agenda.domain.enumeration.DiaMes;
import com.uniagenda.agenda.domain.enumeration.DiaSemana;
import com.uniagenda.agenda.domain.enumeration.Mes;
/**
 * Integration tests for the {@link AgendaSalaResource} REST controller.
 */
@SpringBootTest(classes = AgendaApp.class)
public class AgendaSalaResourceIT {

    private static final StatusDia DEFAULT_STATUS = StatusDia.Disponivel;
    private static final StatusDia UPDATED_STATUS = StatusDia.Indisponivel;

    private static final Horario DEFAULT_HORARIO = Horario.H8;
    private static final Horario UPDATED_HORARIO = Horario.H9;

    private static final DiaMes DEFAULT_DIA_MES = DiaMes.D1;
    private static final DiaMes UPDATED_DIA_MES = DiaMes.D2;

    private static final DiaSemana DEFAULT_DIA_SEMANA = DiaSemana.Segunda_feira;
    private static final DiaSemana UPDATED_DIA_SEMANA = DiaSemana.Terca_feira;

    private static final Mes DEFAULT_MES = Mes.Janeiro;
    private static final Mes UPDATED_MES = Mes.Fevereiro;

    @Autowired
    private AgendaSalaRepository agendaSalaRepository;

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

    private MockMvc restAgendaSalaMockMvc;

    private AgendaSala agendaSala;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgendaSalaResource agendaSalaResource = new AgendaSalaResource(agendaSalaRepository);
        this.restAgendaSalaMockMvc = MockMvcBuilders.standaloneSetup(agendaSalaResource)
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
    public static AgendaSala createEntity(EntityManager em) {
        AgendaSala agendaSala = new AgendaSala()
            .status(DEFAULT_STATUS)
            .horario(DEFAULT_HORARIO)
            .diaMes(DEFAULT_DIA_MES)
            .diaSemana(DEFAULT_DIA_SEMANA)
            .mes(DEFAULT_MES);
        return agendaSala;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaSala createUpdatedEntity(EntityManager em) {
        AgendaSala agendaSala = new AgendaSala()
            .status(UPDATED_STATUS)
            .horario(UPDATED_HORARIO)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA)
            .mes(UPDATED_MES);
        return agendaSala;
    }

    @BeforeEach
    public void initTest() {
        agendaSala = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgendaSala() throws Exception {
        int databaseSizeBeforeCreate = agendaSalaRepository.findAll().size();

        // Create the AgendaSala
        restAgendaSalaMockMvc.perform(post("/api/agenda-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaSala)))
            .andExpect(status().isCreated());

        // Validate the AgendaSala in the database
        List<AgendaSala> agendaSalaList = agendaSalaRepository.findAll();
        assertThat(agendaSalaList).hasSize(databaseSizeBeforeCreate + 1);
        AgendaSala testAgendaSala = agendaSalaList.get(agendaSalaList.size() - 1);
        assertThat(testAgendaSala.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAgendaSala.getHorario()).isEqualTo(DEFAULT_HORARIO);
        assertThat(testAgendaSala.getDiaMes()).isEqualTo(DEFAULT_DIA_MES);
        assertThat(testAgendaSala.getDiaSemana()).isEqualTo(DEFAULT_DIA_SEMANA);
        assertThat(testAgendaSala.getMes()).isEqualTo(DEFAULT_MES);
    }

    @Test
    @Transactional
    public void createAgendaSalaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agendaSalaRepository.findAll().size();

        // Create the AgendaSala with an existing ID
        agendaSala.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaSalaMockMvc.perform(post("/api/agenda-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaSala)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaSala in the database
        List<AgendaSala> agendaSalaList = agendaSalaRepository.findAll();
        assertThat(agendaSalaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgendaSalas() throws Exception {
        // Initialize the database
        agendaSalaRepository.saveAndFlush(agendaSala);

        // Get all the agendaSalaList
        restAgendaSalaMockMvc.perform(get("/api/agenda-salas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaSala.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())))
            .andExpect(jsonPath("$.[*].diaMes").value(hasItem(DEFAULT_DIA_MES.toString())))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA.toString())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.toString())));
    }
    
    @Test
    @Transactional
    public void getAgendaSala() throws Exception {
        // Initialize the database
        agendaSalaRepository.saveAndFlush(agendaSala);

        // Get the agendaSala
        restAgendaSalaMockMvc.perform(get("/api/agenda-salas/{id}", agendaSala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agendaSala.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()))
            .andExpect(jsonPath("$.diaMes").value(DEFAULT_DIA_MES.toString()))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA.toString()))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgendaSala() throws Exception {
        // Get the agendaSala
        restAgendaSalaMockMvc.perform(get("/api/agenda-salas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgendaSala() throws Exception {
        // Initialize the database
        agendaSalaRepository.saveAndFlush(agendaSala);

        int databaseSizeBeforeUpdate = agendaSalaRepository.findAll().size();

        // Update the agendaSala
        AgendaSala updatedAgendaSala = agendaSalaRepository.findById(agendaSala.getId()).get();
        // Disconnect from session so that the updates on updatedAgendaSala are not directly saved in db
        em.detach(updatedAgendaSala);
        updatedAgendaSala
            .status(UPDATED_STATUS)
            .horario(UPDATED_HORARIO)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA)
            .mes(UPDATED_MES);

        restAgendaSalaMockMvc.perform(put("/api/agenda-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgendaSala)))
            .andExpect(status().isOk());

        // Validate the AgendaSala in the database
        List<AgendaSala> agendaSalaList = agendaSalaRepository.findAll();
        assertThat(agendaSalaList).hasSize(databaseSizeBeforeUpdate);
        AgendaSala testAgendaSala = agendaSalaList.get(agendaSalaList.size() - 1);
        assertThat(testAgendaSala.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAgendaSala.getHorario()).isEqualTo(UPDATED_HORARIO);
        assertThat(testAgendaSala.getDiaMes()).isEqualTo(UPDATED_DIA_MES);
        assertThat(testAgendaSala.getDiaSemana()).isEqualTo(UPDATED_DIA_SEMANA);
        assertThat(testAgendaSala.getMes()).isEqualTo(UPDATED_MES);
    }

    @Test
    @Transactional
    public void updateNonExistingAgendaSala() throws Exception {
        int databaseSizeBeforeUpdate = agendaSalaRepository.findAll().size();

        // Create the AgendaSala

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaSalaMockMvc.perform(put("/api/agenda-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaSala)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaSala in the database
        List<AgendaSala> agendaSalaList = agendaSalaRepository.findAll();
        assertThat(agendaSalaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgendaSala() throws Exception {
        // Initialize the database
        agendaSalaRepository.saveAndFlush(agendaSala);

        int databaseSizeBeforeDelete = agendaSalaRepository.findAll().size();

        // Delete the agendaSala
        restAgendaSalaMockMvc.perform(delete("/api/agenda-salas/{id}", agendaSala.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgendaSala> agendaSalaList = agendaSalaRepository.findAll();
        assertThat(agendaSalaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaSala.class);
        AgendaSala agendaSala1 = new AgendaSala();
        agendaSala1.setId(1L);
        AgendaSala agendaSala2 = new AgendaSala();
        agendaSala2.setId(agendaSala1.getId());
        assertThat(agendaSala1).isEqualTo(agendaSala2);
        agendaSala2.setId(2L);
        assertThat(agendaSala1).isNotEqualTo(agendaSala2);
        agendaSala1.setId(null);
        assertThat(agendaSala1).isNotEqualTo(agendaSala2);
    }
}
