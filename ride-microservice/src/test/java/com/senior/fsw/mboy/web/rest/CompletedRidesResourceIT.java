package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.RideApp;
import com.senior.fsw.mboy.domain.CompletedRides;
import com.senior.fsw.mboy.repository.CompletedRidesRepository;
import com.senior.fsw.mboy.service.CompletedRidesService;
import com.senior.fsw.mboy.service.dto.CompletedRidesDTO;
import com.senior.fsw.mboy.service.mapper.CompletedRidesMapper;
import com.senior.fsw.mboy.web.rest.errors.ExceptionTranslator;

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

import static com.senior.fsw.mboy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CompletedRidesResource} REST controller.
 */
@SpringBootTest(classes = RideApp.class)
public class CompletedRidesResourceIT {

    private static final String DEFAULT_RIDE = "AAAAAAAAAA";
    private static final String UPDATED_RIDE = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVER = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER = "BBBBBBBBBB";

    private static final String DEFAULT_PASSENGER = "AAAAAAAAAA";
    private static final String UPDATED_PASSENGER = "BBBBBBBBBB";

    private static final Long DEFAULT_START_TIME = 1L;
    private static final Long UPDATED_START_TIME = 2L;

    private static final Long DEFAULT_END_TIME = 1L;
    private static final Long UPDATED_END_TIME = 2L;

    @Autowired
    private CompletedRidesRepository completedRidesRepository;

    @Autowired
    private CompletedRidesMapper completedRidesMapper;

    @Autowired
    private CompletedRidesService completedRidesService;

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

    private MockMvc restCompletedRidesMockMvc;

    private CompletedRides completedRides;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompletedRidesResource completedRidesResource = new CompletedRidesResource(completedRidesService);
        this.restCompletedRidesMockMvc = MockMvcBuilders.standaloneSetup(completedRidesResource)
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
    public static CompletedRides createEntity(EntityManager em) {
        CompletedRides completedRides = new CompletedRides()
            .ride(DEFAULT_RIDE)
            .driver(DEFAULT_DRIVER)
            .passenger(DEFAULT_PASSENGER)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return completedRides;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompletedRides createUpdatedEntity(EntityManager em) {
        CompletedRides completedRides = new CompletedRides()
            .ride(UPDATED_RIDE)
            .driver(UPDATED_DRIVER)
            .passenger(UPDATED_PASSENGER)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return completedRides;
    }

    @BeforeEach
    public void initTest() {
        completedRides = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompletedRides() throws Exception {
        int databaseSizeBeforeCreate = completedRidesRepository.findAll().size();

        // Create the CompletedRides
        CompletedRidesDTO completedRidesDTO = completedRidesMapper.toDto(completedRides);
        restCompletedRidesMockMvc.perform(post("/api/completed-rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(completedRidesDTO)))
            .andExpect(status().isCreated());

        // Validate the CompletedRides in the database
        List<CompletedRides> completedRidesList = completedRidesRepository.findAll();
        assertThat(completedRidesList).hasSize(databaseSizeBeforeCreate + 1);
        CompletedRides testCompletedRides = completedRidesList.get(completedRidesList.size() - 1);
        assertThat(testCompletedRides.getRide()).isEqualTo(DEFAULT_RIDE);
        assertThat(testCompletedRides.getDriver()).isEqualTo(DEFAULT_DRIVER);
        assertThat(testCompletedRides.getPassenger()).isEqualTo(DEFAULT_PASSENGER);
        assertThat(testCompletedRides.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCompletedRides.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createCompletedRidesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = completedRidesRepository.findAll().size();

        // Create the CompletedRides with an existing ID
        completedRides.setId(1L);
        CompletedRidesDTO completedRidesDTO = completedRidesMapper.toDto(completedRides);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompletedRidesMockMvc.perform(post("/api/completed-rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(completedRidesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompletedRides in the database
        List<CompletedRides> completedRidesList = completedRidesRepository.findAll();
        assertThat(completedRidesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRideIsRequired() throws Exception {
        int databaseSizeBeforeTest = completedRidesRepository.findAll().size();
        // set the field null
        completedRides.setRide(null);

        // Create the CompletedRides, which fails.
        CompletedRidesDTO completedRidesDTO = completedRidesMapper.toDto(completedRides);

        restCompletedRidesMockMvc.perform(post("/api/completed-rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(completedRidesDTO)))
            .andExpect(status().isBadRequest());

        List<CompletedRides> completedRidesList = completedRidesRepository.findAll();
        assertThat(completedRidesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDriverIsRequired() throws Exception {
        int databaseSizeBeforeTest = completedRidesRepository.findAll().size();
        // set the field null
        completedRides.setDriver(null);

        // Create the CompletedRides, which fails.
        CompletedRidesDTO completedRidesDTO = completedRidesMapper.toDto(completedRides);

        restCompletedRidesMockMvc.perform(post("/api/completed-rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(completedRidesDTO)))
            .andExpect(status().isBadRequest());

        List<CompletedRides> completedRidesList = completedRidesRepository.findAll();
        assertThat(completedRidesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassengerIsRequired() throws Exception {
        int databaseSizeBeforeTest = completedRidesRepository.findAll().size();
        // set the field null
        completedRides.setPassenger(null);

        // Create the CompletedRides, which fails.
        CompletedRidesDTO completedRidesDTO = completedRidesMapper.toDto(completedRides);

        restCompletedRidesMockMvc.perform(post("/api/completed-rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(completedRidesDTO)))
            .andExpect(status().isBadRequest());

        List<CompletedRides> completedRidesList = completedRidesRepository.findAll();
        assertThat(completedRidesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = completedRidesRepository.findAll().size();
        // set the field null
        completedRides.setStartTime(null);

        // Create the CompletedRides, which fails.
        CompletedRidesDTO completedRidesDTO = completedRidesMapper.toDto(completedRides);

        restCompletedRidesMockMvc.perform(post("/api/completed-rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(completedRidesDTO)))
            .andExpect(status().isBadRequest());

        List<CompletedRides> completedRidesList = completedRidesRepository.findAll();
        assertThat(completedRidesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = completedRidesRepository.findAll().size();
        // set the field null
        completedRides.setEndTime(null);

        // Create the CompletedRides, which fails.
        CompletedRidesDTO completedRidesDTO = completedRidesMapper.toDto(completedRides);

        restCompletedRidesMockMvc.perform(post("/api/completed-rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(completedRidesDTO)))
            .andExpect(status().isBadRequest());

        List<CompletedRides> completedRidesList = completedRidesRepository.findAll();
        assertThat(completedRidesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompletedRides() throws Exception {
        // Initialize the database
        completedRidesRepository.saveAndFlush(completedRides);

        // Get all the completedRidesList
        restCompletedRidesMockMvc.perform(get("/api/completed-rides?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(completedRides.getId().intValue())))
            .andExpect(jsonPath("$.[*].ride").value(hasItem(DEFAULT_RIDE.toString())))
            .andExpect(jsonPath("$.[*].driver").value(hasItem(DEFAULT_DRIVER.toString())))
            .andExpect(jsonPath("$.[*].passenger").value(hasItem(DEFAULT_PASSENGER.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.intValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompletedRides() throws Exception {
        // Initialize the database
        completedRidesRepository.saveAndFlush(completedRides);

        // Get the completedRides
        restCompletedRidesMockMvc.perform(get("/api/completed-rides/{id}", completedRides.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(completedRides.getId().intValue()))
            .andExpect(jsonPath("$.ride").value(DEFAULT_RIDE.toString()))
            .andExpect(jsonPath("$.driver").value(DEFAULT_DRIVER.toString()))
            .andExpect(jsonPath("$.passenger").value(DEFAULT_PASSENGER.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.intValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompletedRides() throws Exception {
        // Get the completedRides
        restCompletedRidesMockMvc.perform(get("/api/completed-rides/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompletedRides() throws Exception {
        // Initialize the database
        completedRidesRepository.saveAndFlush(completedRides);

        int databaseSizeBeforeUpdate = completedRidesRepository.findAll().size();

        // Update the completedRides
        CompletedRides updatedCompletedRides = completedRidesRepository.findById(completedRides.getId()).get();
        // Disconnect from session so that the updates on updatedCompletedRides are not directly saved in db
        em.detach(updatedCompletedRides);
        updatedCompletedRides
            .ride(UPDATED_RIDE)
            .driver(UPDATED_DRIVER)
            .passenger(UPDATED_PASSENGER)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        CompletedRidesDTO completedRidesDTO = completedRidesMapper.toDto(updatedCompletedRides);

        restCompletedRidesMockMvc.perform(put("/api/completed-rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(completedRidesDTO)))
            .andExpect(status().isOk());

        // Validate the CompletedRides in the database
        List<CompletedRides> completedRidesList = completedRidesRepository.findAll();
        assertThat(completedRidesList).hasSize(databaseSizeBeforeUpdate);
        CompletedRides testCompletedRides = completedRidesList.get(completedRidesList.size() - 1);
        assertThat(testCompletedRides.getRide()).isEqualTo(UPDATED_RIDE);
        assertThat(testCompletedRides.getDriver()).isEqualTo(UPDATED_DRIVER);
        assertThat(testCompletedRides.getPassenger()).isEqualTo(UPDATED_PASSENGER);
        assertThat(testCompletedRides.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCompletedRides.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompletedRides() throws Exception {
        int databaseSizeBeforeUpdate = completedRidesRepository.findAll().size();

        // Create the CompletedRides
        CompletedRidesDTO completedRidesDTO = completedRidesMapper.toDto(completedRides);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompletedRidesMockMvc.perform(put("/api/completed-rides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(completedRidesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompletedRides in the database
        List<CompletedRides> completedRidesList = completedRidesRepository.findAll();
        assertThat(completedRidesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompletedRides() throws Exception {
        // Initialize the database
        completedRidesRepository.saveAndFlush(completedRides);

        int databaseSizeBeforeDelete = completedRidesRepository.findAll().size();

        // Delete the completedRides
        restCompletedRidesMockMvc.perform(delete("/api/completed-rides/{id}", completedRides.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CompletedRides> completedRidesList = completedRidesRepository.findAll();
        assertThat(completedRidesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompletedRides.class);
        CompletedRides completedRides1 = new CompletedRides();
        completedRides1.setId(1L);
        CompletedRides completedRides2 = new CompletedRides();
        completedRides2.setId(completedRides1.getId());
        assertThat(completedRides1).isEqualTo(completedRides2);
        completedRides2.setId(2L);
        assertThat(completedRides1).isNotEqualTo(completedRides2);
        completedRides1.setId(null);
        assertThat(completedRides1).isNotEqualTo(completedRides2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompletedRidesDTO.class);
        CompletedRidesDTO completedRidesDTO1 = new CompletedRidesDTO();
        completedRidesDTO1.setId(1L);
        CompletedRidesDTO completedRidesDTO2 = new CompletedRidesDTO();
        assertThat(completedRidesDTO1).isNotEqualTo(completedRidesDTO2);
        completedRidesDTO2.setId(completedRidesDTO1.getId());
        assertThat(completedRidesDTO1).isEqualTo(completedRidesDTO2);
        completedRidesDTO2.setId(2L);
        assertThat(completedRidesDTO1).isNotEqualTo(completedRidesDTO2);
        completedRidesDTO1.setId(null);
        assertThat(completedRidesDTO1).isNotEqualTo(completedRidesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(completedRidesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(completedRidesMapper.fromId(null)).isNull();
    }
}
