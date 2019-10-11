package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.UniagendaApp;
import com.uniagenda.agenda.domain.AgendaAtendimentoServidor;
import com.uniagenda.agenda.repository.AgendaAtendimentoServidorRepository;
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
 * Integration tests for the {@link AgendaAtendimentoServidorResource} REST controller.
 */
@SpringBootTest(classes = UniagendaApp.class)
public class AgendaAtendimentoServidorResourceIT {

    private static final StatusAgenda DEFAULT_STATUS = StatusAgenda.Livre;
    private static final StatusAgenda UPDATED_STATUS = StatusAgenda.Ocupado;

    @Autowired
    private AgendaAtendimentoServidorRepository agendaAtendimentoServidorRepository;

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

    private MockMvc restAgendaAtendimentoServidorMockMvc;

    private AgendaAtendimentoServidor agendaAtendimentoServidor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgendaAtendimentoServidorResource agendaAtendimentoServidorResource = new AgendaAtendimentoServidorResource(agendaAtendimentoServidorRepository);
        this.restAgendaAtendimentoServidorMockMvc = MockMvcBuilders.standaloneSetup(agendaAtendimentoServidorResource)
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
    public static AgendaAtendimentoServidor createEntity(EntityManager em) {
        AgendaAtendimentoServidor agendaAtendimentoServidor = new AgendaAtendimentoServidor()
            .status(DEFAULT_STATUS);
        return agendaAtendimentoServidor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaAtendimentoServidor createUpdatedEntity(EntityManager em) {
        AgendaAtendimentoServidor agendaAtendimentoServidor = new AgendaAtendimentoServidor()
            .status(UPDATED_STATUS);
        return agendaAtendimentoServidor;
    }

    @BeforeEach
    public void initTest() {
        agendaAtendimentoServidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgendaAtendimentoServidor() throws Exception {
        int databaseSizeBeforeCreate = agendaAtendimentoServidorRepository.findAll().size();

        // Create the AgendaAtendimentoServidor
        restAgendaAtendimentoServidorMockMvc.perform(post("/api/agenda-atendimento-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaAtendimentoServidor)))
            .andExpect(status().isCreated());

        // Validate the AgendaAtendimentoServidor in the database
        List<AgendaAtendimentoServidor> agendaAtendimentoServidorList = agendaAtendimentoServidorRepository.findAll();
        assertThat(agendaAtendimentoServidorList).hasSize(databaseSizeBeforeCreate + 1);
        AgendaAtendimentoServidor testAgendaAtendimentoServidor = agendaAtendimentoServidorList.get(agendaAtendimentoServidorList.size() - 1);
        assertThat(testAgendaAtendimentoServidor.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAgendaAtendimentoServidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agendaAtendimentoServidorRepository.findAll().size();

        // Create the AgendaAtendimentoServidor with an existing ID
        agendaAtendimentoServidor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaAtendimentoServidorMockMvc.perform(post("/api/agenda-atendimento-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaAtendimentoServidor)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaAtendimentoServidor in the database
        List<AgendaAtendimentoServidor> agendaAtendimentoServidorList = agendaAtendimentoServidorRepository.findAll();
        assertThat(agendaAtendimentoServidorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgendaAtendimentoServidors() throws Exception {
        // Initialize the database
        agendaAtendimentoServidorRepository.saveAndFlush(agendaAtendimentoServidor);

        // Get all the agendaAtendimentoServidorList
        restAgendaAtendimentoServidorMockMvc.perform(get("/api/agenda-atendimento-servidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaAtendimentoServidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getAgendaAtendimentoServidor() throws Exception {
        // Initialize the database
        agendaAtendimentoServidorRepository.saveAndFlush(agendaAtendimentoServidor);

        // Get the agendaAtendimentoServidor
        restAgendaAtendimentoServidorMockMvc.perform(get("/api/agenda-atendimento-servidors/{id}", agendaAtendimentoServidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agendaAtendimentoServidor.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgendaAtendimentoServidor() throws Exception {
        // Get the agendaAtendimentoServidor
        restAgendaAtendimentoServidorMockMvc.perform(get("/api/agenda-atendimento-servidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgendaAtendimentoServidor() throws Exception {
        // Initialize the database
        agendaAtendimentoServidorRepository.saveAndFlush(agendaAtendimentoServidor);

        int databaseSizeBeforeUpdate = agendaAtendimentoServidorRepository.findAll().size();

        // Update the agendaAtendimentoServidor
        AgendaAtendimentoServidor updatedAgendaAtendimentoServidor = agendaAtendimentoServidorRepository.findById(agendaAtendimentoServidor.getId()).get();
        // Disconnect from session so that the updates on updatedAgendaAtendimentoServidor are not directly saved in db
        em.detach(updatedAgendaAtendimentoServidor);
        updatedAgendaAtendimentoServidor
            .status(UPDATED_STATUS);

        restAgendaAtendimentoServidorMockMvc.perform(put("/api/agenda-atendimento-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgendaAtendimentoServidor)))
            .andExpect(status().isOk());

        // Validate the AgendaAtendimentoServidor in the database
        List<AgendaAtendimentoServidor> agendaAtendimentoServidorList = agendaAtendimentoServidorRepository.findAll();
        assertThat(agendaAtendimentoServidorList).hasSize(databaseSizeBeforeUpdate);
        AgendaAtendimentoServidor testAgendaAtendimentoServidor = agendaAtendimentoServidorList.get(agendaAtendimentoServidorList.size() - 1);
        assertThat(testAgendaAtendimentoServidor.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAgendaAtendimentoServidor() throws Exception {
        int databaseSizeBeforeUpdate = agendaAtendimentoServidorRepository.findAll().size();

        // Create the AgendaAtendimentoServidor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaAtendimentoServidorMockMvc.perform(put("/api/agenda-atendimento-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaAtendimentoServidor)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaAtendimentoServidor in the database
        List<AgendaAtendimentoServidor> agendaAtendimentoServidorList = agendaAtendimentoServidorRepository.findAll();
        assertThat(agendaAtendimentoServidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgendaAtendimentoServidor() throws Exception {
        // Initialize the database
        agendaAtendimentoServidorRepository.saveAndFlush(agendaAtendimentoServidor);

        int databaseSizeBeforeDelete = agendaAtendimentoServidorRepository.findAll().size();

        // Delete the agendaAtendimentoServidor
        restAgendaAtendimentoServidorMockMvc.perform(delete("/api/agenda-atendimento-servidors/{id}", agendaAtendimentoServidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgendaAtendimentoServidor> agendaAtendimentoServidorList = agendaAtendimentoServidorRepository.findAll();
        assertThat(agendaAtendimentoServidorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaAtendimentoServidor.class);
        AgendaAtendimentoServidor agendaAtendimentoServidor1 = new AgendaAtendimentoServidor();
        agendaAtendimentoServidor1.setId(1L);
        AgendaAtendimentoServidor agendaAtendimentoServidor2 = new AgendaAtendimentoServidor();
        agendaAtendimentoServidor2.setId(agendaAtendimentoServidor1.getId());
        assertThat(agendaAtendimentoServidor1).isEqualTo(agendaAtendimentoServidor2);
        agendaAtendimentoServidor2.setId(2L);
        assertThat(agendaAtendimentoServidor1).isNotEqualTo(agendaAtendimentoServidor2);
        agendaAtendimentoServidor1.setId(null);
        assertThat(agendaAtendimentoServidor1).isNotEqualTo(agendaAtendimentoServidor2);
    }
}
