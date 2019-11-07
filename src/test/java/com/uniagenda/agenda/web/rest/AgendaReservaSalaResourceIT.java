package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.AgendaApp;
import com.uniagenda.agenda.domain.AgendaReservaSala;
import com.uniagenda.agenda.repository.AgendaReservaSalaRepository;
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

import com.uniagenda.agenda.domain.enumeration.StatusAgenda;
/**
 * Integration tests for the {@link AgendaReservaSalaResource} REST controller.
 */
@SpringBootTest(classes = AgendaApp.class)
public class AgendaReservaSalaResourceIT {

    private static final StatusAgenda DEFAULT_STATUS = StatusAgenda.Livre;
    private static final StatusAgenda UPDATED_STATUS = StatusAgenda.Ocupado;

    @Autowired
    private AgendaReservaSalaRepository agendaReservaSalaRepository;

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

    private MockMvc restAgendaReservaSalaMockMvc;

    private AgendaReservaSala agendaReservaSala;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgendaReservaSalaResource agendaReservaSalaResource = new AgendaReservaSalaResource(agendaReservaSalaRepository);
        this.restAgendaReservaSalaMockMvc = MockMvcBuilders.standaloneSetup(agendaReservaSalaResource)
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
    public static AgendaReservaSala createEntity(EntityManager em) {
        AgendaReservaSala agendaReservaSala = new AgendaReservaSala()
            .status(DEFAULT_STATUS);
        return agendaReservaSala;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaReservaSala createUpdatedEntity(EntityManager em) {
        AgendaReservaSala agendaReservaSala = new AgendaReservaSala()
            .status(UPDATED_STATUS);
        return agendaReservaSala;
    }

    @BeforeEach
    public void initTest() {
        agendaReservaSala = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgendaReservaSala() throws Exception {
        int databaseSizeBeforeCreate = agendaReservaSalaRepository.findAll().size();

        // Create the AgendaReservaSala
        restAgendaReservaSalaMockMvc.perform(post("/api/agenda-reserva-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaReservaSala)))
            .andExpect(status().isCreated());

        // Validate the AgendaReservaSala in the database
        List<AgendaReservaSala> agendaReservaSalaList = agendaReservaSalaRepository.findAll();
        assertThat(agendaReservaSalaList).hasSize(databaseSizeBeforeCreate + 1);
        AgendaReservaSala testAgendaReservaSala = agendaReservaSalaList.get(agendaReservaSalaList.size() - 1);
        assertThat(testAgendaReservaSala.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAgendaReservaSalaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agendaReservaSalaRepository.findAll().size();

        // Create the AgendaReservaSala with an existing ID
        agendaReservaSala.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaReservaSalaMockMvc.perform(post("/api/agenda-reserva-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaReservaSala)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaReservaSala in the database
        List<AgendaReservaSala> agendaReservaSalaList = agendaReservaSalaRepository.findAll();
        assertThat(agendaReservaSalaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgendaReservaSalas() throws Exception {
        // Initialize the database
        agendaReservaSalaRepository.saveAndFlush(agendaReservaSala);

        // Get all the agendaReservaSalaList
        restAgendaReservaSalaMockMvc.perform(get("/api/agenda-reserva-salas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaReservaSala.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getAgendaReservaSala() throws Exception {
        // Initialize the database
        agendaReservaSalaRepository.saveAndFlush(agendaReservaSala);

        // Get the agendaReservaSala
        restAgendaReservaSalaMockMvc.perform(get("/api/agenda-reserva-salas/{id}", agendaReservaSala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agendaReservaSala.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgendaReservaSala() throws Exception {
        // Get the agendaReservaSala
        restAgendaReservaSalaMockMvc.perform(get("/api/agenda-reserva-salas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgendaReservaSala() throws Exception {
        // Initialize the database
        agendaReservaSalaRepository.saveAndFlush(agendaReservaSala);

        int databaseSizeBeforeUpdate = agendaReservaSalaRepository.findAll().size();

        // Update the agendaReservaSala
        AgendaReservaSala updatedAgendaReservaSala = agendaReservaSalaRepository.findById(agendaReservaSala.getId()).get();
        // Disconnect from session so that the updates on updatedAgendaReservaSala are not directly saved in db
        em.detach(updatedAgendaReservaSala);
        updatedAgendaReservaSala
            .status(UPDATED_STATUS);

        restAgendaReservaSalaMockMvc.perform(put("/api/agenda-reserva-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgendaReservaSala)))
            .andExpect(status().isOk());

        // Validate the AgendaReservaSala in the database
        List<AgendaReservaSala> agendaReservaSalaList = agendaReservaSalaRepository.findAll();
        assertThat(agendaReservaSalaList).hasSize(databaseSizeBeforeUpdate);
        AgendaReservaSala testAgendaReservaSala = agendaReservaSalaList.get(agendaReservaSalaList.size() - 1);
        assertThat(testAgendaReservaSala.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAgendaReservaSala() throws Exception {
        int databaseSizeBeforeUpdate = agendaReservaSalaRepository.findAll().size();

        // Create the AgendaReservaSala

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaReservaSalaMockMvc.perform(put("/api/agenda-reserva-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaReservaSala)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaReservaSala in the database
        List<AgendaReservaSala> agendaReservaSalaList = agendaReservaSalaRepository.findAll();
        assertThat(agendaReservaSalaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgendaReservaSala() throws Exception {
        // Initialize the database
        agendaReservaSalaRepository.saveAndFlush(agendaReservaSala);

        int databaseSizeBeforeDelete = agendaReservaSalaRepository.findAll().size();

        // Delete the agendaReservaSala
        restAgendaReservaSalaMockMvc.perform(delete("/api/agenda-reserva-salas/{id}", agendaReservaSala.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgendaReservaSala> agendaReservaSalaList = agendaReservaSalaRepository.findAll();
        assertThat(agendaReservaSalaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaReservaSala.class);
        AgendaReservaSala agendaReservaSala1 = new AgendaReservaSala();
        agendaReservaSala1.setId(1L);
        AgendaReservaSala agendaReservaSala2 = new AgendaReservaSala();
        agendaReservaSala2.setId(agendaReservaSala1.getId());
        assertThat(agendaReservaSala1).isEqualTo(agendaReservaSala2);
        agendaReservaSala2.setId(2L);
        assertThat(agendaReservaSala1).isNotEqualTo(agendaReservaSala2);
        agendaReservaSala1.setId(null);
        assertThat(agendaReservaSala1).isNotEqualTo(agendaReservaSala2);
    }
}
