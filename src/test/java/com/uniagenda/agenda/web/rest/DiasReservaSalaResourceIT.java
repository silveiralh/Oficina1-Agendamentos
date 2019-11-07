package com.uniagenda.agenda.web.rest;

import com.uniagenda.agenda.AgendaApp;
import com.uniagenda.agenda.domain.DiasReservaSala;
import com.uniagenda.agenda.repository.DiasReservaSalaRepository;
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
 * Integration tests for the {@link DiasReservaSalaResource} REST controller.
 */
@SpringBootTest(classes = AgendaApp.class)
public class DiasReservaSalaResourceIT {

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
    private DiasReservaSalaRepository diasReservaSalaRepository;

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

    private MockMvc restDiasReservaSalaMockMvc;

    private DiasReservaSala diasReservaSala;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiasReservaSalaResource diasReservaSalaResource = new DiasReservaSalaResource(diasReservaSalaRepository);
        this.restDiasReservaSalaMockMvc = MockMvcBuilders.standaloneSetup(diasReservaSalaResource)
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
    public static DiasReservaSala createEntity(EntityManager em) {
        DiasReservaSala diasReservaSala = new DiasReservaSala()
            .status(DEFAULT_STATUS)
            .horario(DEFAULT_HORARIO)
            .diaMes(DEFAULT_DIA_MES)
            .diaSemana(DEFAULT_DIA_SEMANA)
            .mes(DEFAULT_MES);
        return diasReservaSala;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiasReservaSala createUpdatedEntity(EntityManager em) {
        DiasReservaSala diasReservaSala = new DiasReservaSala()
            .status(UPDATED_STATUS)
            .horario(UPDATED_HORARIO)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA)
            .mes(UPDATED_MES);
        return diasReservaSala;
    }

    @BeforeEach
    public void initTest() {
        diasReservaSala = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiasReservaSala() throws Exception {
        int databaseSizeBeforeCreate = diasReservaSalaRepository.findAll().size();

        // Create the DiasReservaSala
        restDiasReservaSalaMockMvc.perform(post("/api/dias-reserva-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasReservaSala)))
            .andExpect(status().isCreated());

        // Validate the DiasReservaSala in the database
        List<DiasReservaSala> diasReservaSalaList = diasReservaSalaRepository.findAll();
        assertThat(diasReservaSalaList).hasSize(databaseSizeBeforeCreate + 1);
        DiasReservaSala testDiasReservaSala = diasReservaSalaList.get(diasReservaSalaList.size() - 1);
        assertThat(testDiasReservaSala.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDiasReservaSala.getHorario()).isEqualTo(DEFAULT_HORARIO);
        assertThat(testDiasReservaSala.getDiaMes()).isEqualTo(DEFAULT_DIA_MES);
        assertThat(testDiasReservaSala.getDiaSemana()).isEqualTo(DEFAULT_DIA_SEMANA);
        assertThat(testDiasReservaSala.getMes()).isEqualTo(DEFAULT_MES);
    }

    @Test
    @Transactional
    public void createDiasReservaSalaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diasReservaSalaRepository.findAll().size();

        // Create the DiasReservaSala with an existing ID
        diasReservaSala.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiasReservaSalaMockMvc.perform(post("/api/dias-reserva-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasReservaSala)))
            .andExpect(status().isBadRequest());

        // Validate the DiasReservaSala in the database
        List<DiasReservaSala> diasReservaSalaList = diasReservaSalaRepository.findAll();
        assertThat(diasReservaSalaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDiasReservaSalas() throws Exception {
        // Initialize the database
        diasReservaSalaRepository.saveAndFlush(diasReservaSala);

        // Get all the diasReservaSalaList
        restDiasReservaSalaMockMvc.perform(get("/api/dias-reserva-salas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diasReservaSala.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())))
            .andExpect(jsonPath("$.[*].diaMes").value(hasItem(DEFAULT_DIA_MES.toString())))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA.toString())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.toString())));
    }
    
    @Test
    @Transactional
    public void getDiasReservaSala() throws Exception {
        // Initialize the database
        diasReservaSalaRepository.saveAndFlush(diasReservaSala);

        // Get the diasReservaSala
        restDiasReservaSalaMockMvc.perform(get("/api/dias-reserva-salas/{id}", diasReservaSala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diasReservaSala.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()))
            .andExpect(jsonPath("$.diaMes").value(DEFAULT_DIA_MES.toString()))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA.toString()))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiasReservaSala() throws Exception {
        // Get the diasReservaSala
        restDiasReservaSalaMockMvc.perform(get("/api/dias-reserva-salas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiasReservaSala() throws Exception {
        // Initialize the database
        diasReservaSalaRepository.saveAndFlush(diasReservaSala);

        int databaseSizeBeforeUpdate = diasReservaSalaRepository.findAll().size();

        // Update the diasReservaSala
        DiasReservaSala updatedDiasReservaSala = diasReservaSalaRepository.findById(diasReservaSala.getId()).get();
        // Disconnect from session so that the updates on updatedDiasReservaSala are not directly saved in db
        em.detach(updatedDiasReservaSala);
        updatedDiasReservaSala
            .status(UPDATED_STATUS)
            .horario(UPDATED_HORARIO)
            .diaMes(UPDATED_DIA_MES)
            .diaSemana(UPDATED_DIA_SEMANA)
            .mes(UPDATED_MES);

        restDiasReservaSalaMockMvc.perform(put("/api/dias-reserva-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiasReservaSala)))
            .andExpect(status().isOk());

        // Validate the DiasReservaSala in the database
        List<DiasReservaSala> diasReservaSalaList = diasReservaSalaRepository.findAll();
        assertThat(diasReservaSalaList).hasSize(databaseSizeBeforeUpdate);
        DiasReservaSala testDiasReservaSala = diasReservaSalaList.get(diasReservaSalaList.size() - 1);
        assertThat(testDiasReservaSala.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDiasReservaSala.getHorario()).isEqualTo(UPDATED_HORARIO);
        assertThat(testDiasReservaSala.getDiaMes()).isEqualTo(UPDATED_DIA_MES);
        assertThat(testDiasReservaSala.getDiaSemana()).isEqualTo(UPDATED_DIA_SEMANA);
        assertThat(testDiasReservaSala.getMes()).isEqualTo(UPDATED_MES);
    }

    @Test
    @Transactional
    public void updateNonExistingDiasReservaSala() throws Exception {
        int databaseSizeBeforeUpdate = diasReservaSalaRepository.findAll().size();

        // Create the DiasReservaSala

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiasReservaSalaMockMvc.perform(put("/api/dias-reserva-salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasReservaSala)))
            .andExpect(status().isBadRequest());

        // Validate the DiasReservaSala in the database
        List<DiasReservaSala> diasReservaSalaList = diasReservaSalaRepository.findAll();
        assertThat(diasReservaSalaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiasReservaSala() throws Exception {
        // Initialize the database
        diasReservaSalaRepository.saveAndFlush(diasReservaSala);

        int databaseSizeBeforeDelete = diasReservaSalaRepository.findAll().size();

        // Delete the diasReservaSala
        restDiasReservaSalaMockMvc.perform(delete("/api/dias-reserva-salas/{id}", diasReservaSala.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiasReservaSala> diasReservaSalaList = diasReservaSalaRepository.findAll();
        assertThat(diasReservaSalaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiasReservaSala.class);
        DiasReservaSala diasReservaSala1 = new DiasReservaSala();
        diasReservaSala1.setId(1L);
        DiasReservaSala diasReservaSala2 = new DiasReservaSala();
        diasReservaSala2.setId(diasReservaSala1.getId());
        assertThat(diasReservaSala1).isEqualTo(diasReservaSala2);
        diasReservaSala2.setId(2L);
        assertThat(diasReservaSala1).isNotEqualTo(diasReservaSala2);
        diasReservaSala1.setId(null);
        assertThat(diasReservaSala1).isNotEqualTo(diasReservaSala2);
    }
}
