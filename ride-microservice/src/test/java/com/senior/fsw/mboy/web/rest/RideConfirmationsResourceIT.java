package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.RideApp;
import com.senior.fsw.mboy.domain.RideConfirmations;
import com.senior.fsw.mboy.repository.RideConfirmationsRepository;
import com.senior.fsw.mboy.service.RideConfirmationsService;
import com.senior.fsw.mboy.service.dto.RideConfirmationsDTO;
import com.senior.fsw.mboy.service.mapper.RideConfirmationsMapper;
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
 * Integration tests for the {@Link RideConfirmationsResource} REST controller.
 */
@SpringBootTest(classes = RideApp.class)
public class RideConfirmationsResourceIT {

    private static final String DEFAULT_PASSENGER = "AAAAAAAAAA";
    private static final String UPDATED_PASSENGER = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVER = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER = "BBBBBBBBBB";

    @Autowired
    private RideConfirmationsRepository rideConfirmationsRepository;

    @Autowired
    private RideConfirmationsMapper rideConfirmationsMapper;

    @Autowired
    private RideConfirmationsService rideConfirmationsService;

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

    private MockMvc restRideConfirmationsMockMvc;

    private RideConfirmations rideConfirmations;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RideConfirmationsResource rideConfirmationsResource = new RideConfirmationsResource(rideConfirmationsService);
        this.restRideConfirmationsMockMvc = MockMvcBuilders.standaloneSetup(rideConfirmationsResource)
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
    public static RideConfirmations createEntity(EntityManager em) {
        RideConfirmations rideConfirmations = new RideConfirmations()
            .passenger(DEFAULT_PASSENGER)
            .driver(DEFAULT_DRIVER);
        return rideConfirmations;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RideConfirmations createUpdatedEntity(EntityManager em) {
        RideConfirmations rideConfirmations = new RideConfirmations()
            .passenger(UPDATED_PASSENGER)
            .driver(UPDATED_DRIVER);
        return rideConfirmations;
    }

    @BeforeEach
    public void initTest() {
        rideConfirmations = createEntity(em);
    }

    @Test
    @Transactional
    public void createRideConfirmations() throws Exception {
        int databaseSizeBeforeCreate = rideConfirmationsRepository.findAll().size();

        // Create the RideConfirmations
        RideConfirmationsDTO rideConfirmationsDTO = rideConfirmationsMapper.toDto(rideConfirmations);
        restRideConfirmationsMockMvc.perform(post("/api/ride-confirmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideConfirmationsDTO)))
            .andExpect(status().isCreated());

        // Validate the RideConfirmations in the database
        List<RideConfirmations> rideConfirmationsList = rideConfirmationsRepository.findAll();
        assertThat(rideConfirmationsList).hasSize(databaseSizeBeforeCreate + 1);
        RideConfirmations testRideConfirmations = rideConfirmationsList.get(rideConfirmationsList.size() - 1);
        assertThat(testRideConfirmations.getPassenger()).isEqualTo(DEFAULT_PASSENGER);
        assertThat(testRideConfirmations.getDriver()).isEqualTo(DEFAULT_DRIVER);
    }

    @Test
    @Transactional
    public void createRideConfirmationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rideConfirmationsRepository.findAll().size();

        // Create the RideConfirmations with an existing ID
        rideConfirmations.setId(1L);
        RideConfirmationsDTO rideConfirmationsDTO = rideConfirmationsMapper.toDto(rideConfirmations);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRideConfirmationsMockMvc.perform(post("/api/ride-confirmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideConfirmationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RideConfirmations in the database
        List<RideConfirmations> rideConfirmationsList = rideConfirmationsRepository.findAll();
        assertThat(rideConfirmationsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPassengerIsRequired() throws Exception {
        int databaseSizeBeforeTest = rideConfirmationsRepository.findAll().size();
        // set the field null
        rideConfirmations.setPassenger(null);

        // Create the RideConfirmations, which fails.
        RideConfirmationsDTO rideConfirmationsDTO = rideConfirmationsMapper.toDto(rideConfirmations);

        restRideConfirmationsMockMvc.perform(post("/api/ride-confirmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideConfirmationsDTO)))
            .andExpect(status().isBadRequest());

        List<RideConfirmations> rideConfirmationsList = rideConfirmationsRepository.findAll();
        assertThat(rideConfirmationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDriverIsRequired() throws Exception {
        int databaseSizeBeforeTest = rideConfirmationsRepository.findAll().size();
        // set the field null
        rideConfirmations.setDriver(null);

        // Create the RideConfirmations, which fails.
        RideConfirmationsDTO rideConfirmationsDTO = rideConfirmationsMapper.toDto(rideConfirmations);

        restRideConfirmationsMockMvc.perform(post("/api/ride-confirmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideConfirmationsDTO)))
            .andExpect(status().isBadRequest());

        List<RideConfirmations> rideConfirmationsList = rideConfirmationsRepository.findAll();
        assertThat(rideConfirmationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRideConfirmations() throws Exception {
        // Initialize the database
        rideConfirmationsRepository.saveAndFlush(rideConfirmations);

        // Get all the rideConfirmationsList
        restRideConfirmationsMockMvc.perform(get("/api/ride-confirmations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rideConfirmations.getId().intValue())))
            .andExpect(jsonPath("$.[*].passenger").value(hasItem(DEFAULT_PASSENGER.toString())))
            .andExpect(jsonPath("$.[*].driver").value(hasItem(DEFAULT_DRIVER.toString())));
    }
    
    @Test
    @Transactional
    public void getRideConfirmations() throws Exception {
        // Initialize the database
        rideConfirmationsRepository.saveAndFlush(rideConfirmations);

        // Get the rideConfirmations
        restRideConfirmationsMockMvc.perform(get("/api/ride-confirmations/{id}", rideConfirmations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rideConfirmations.getId().intValue()))
            .andExpect(jsonPath("$.passenger").value(DEFAULT_PASSENGER.toString()))
            .andExpect(jsonPath("$.driver").value(DEFAULT_DRIVER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRideConfirmations() throws Exception {
        // Get the rideConfirmations
        restRideConfirmationsMockMvc.perform(get("/api/ride-confirmations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRideConfirmations() throws Exception {
        // Initialize the database
        rideConfirmationsRepository.saveAndFlush(rideConfirmations);

        int databaseSizeBeforeUpdate = rideConfirmationsRepository.findAll().size();

        // Update the rideConfirmations
        RideConfirmations updatedRideConfirmations = rideConfirmationsRepository.findById(rideConfirmations.getId()).get();
        // Disconnect from session so that the updates on updatedRideConfirmations are not directly saved in db
        em.detach(updatedRideConfirmations);
        updatedRideConfirmations
            .passenger(UPDATED_PASSENGER)
            .driver(UPDATED_DRIVER);
        RideConfirmationsDTO rideConfirmationsDTO = rideConfirmationsMapper.toDto(updatedRideConfirmations);

        restRideConfirmationsMockMvc.perform(put("/api/ride-confirmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideConfirmationsDTO)))
            .andExpect(status().isOk());

        // Validate the RideConfirmations in the database
        List<RideConfirmations> rideConfirmationsList = rideConfirmationsRepository.findAll();
        assertThat(rideConfirmationsList).hasSize(databaseSizeBeforeUpdate);
        RideConfirmations testRideConfirmations = rideConfirmationsList.get(rideConfirmationsList.size() - 1);
        assertThat(testRideConfirmations.getPassenger()).isEqualTo(UPDATED_PASSENGER);
        assertThat(testRideConfirmations.getDriver()).isEqualTo(UPDATED_DRIVER);
    }

    @Test
    @Transactional
    public void updateNonExistingRideConfirmations() throws Exception {
        int databaseSizeBeforeUpdate = rideConfirmationsRepository.findAll().size();

        // Create the RideConfirmations
        RideConfirmationsDTO rideConfirmationsDTO = rideConfirmationsMapper.toDto(rideConfirmations);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRideConfirmationsMockMvc.perform(put("/api/ride-confirmations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideConfirmationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RideConfirmations in the database
        List<RideConfirmations> rideConfirmationsList = rideConfirmationsRepository.findAll();
        assertThat(rideConfirmationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRideConfirmations() throws Exception {
        // Initialize the database
        rideConfirmationsRepository.saveAndFlush(rideConfirmations);

        int databaseSizeBeforeDelete = rideConfirmationsRepository.findAll().size();

        // Delete the rideConfirmations
        restRideConfirmationsMockMvc.perform(delete("/api/ride-confirmations/{id}", rideConfirmations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<RideConfirmations> rideConfirmationsList = rideConfirmationsRepository.findAll();
        assertThat(rideConfirmationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RideConfirmations.class);
        RideConfirmations rideConfirmations1 = new RideConfirmations();
        rideConfirmations1.setId(1L);
        RideConfirmations rideConfirmations2 = new RideConfirmations();
        rideConfirmations2.setId(rideConfirmations1.getId());
        assertThat(rideConfirmations1).isEqualTo(rideConfirmations2);
        rideConfirmations2.setId(2L);
        assertThat(rideConfirmations1).isNotEqualTo(rideConfirmations2);
        rideConfirmations1.setId(null);
        assertThat(rideConfirmations1).isNotEqualTo(rideConfirmations2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RideConfirmationsDTO.class);
        RideConfirmationsDTO rideConfirmationsDTO1 = new RideConfirmationsDTO();
        rideConfirmationsDTO1.setId(1L);
        RideConfirmationsDTO rideConfirmationsDTO2 = new RideConfirmationsDTO();
        assertThat(rideConfirmationsDTO1).isNotEqualTo(rideConfirmationsDTO2);
        rideConfirmationsDTO2.setId(rideConfirmationsDTO1.getId());
        assertThat(rideConfirmationsDTO1).isEqualTo(rideConfirmationsDTO2);
        rideConfirmationsDTO2.setId(2L);
        assertThat(rideConfirmationsDTO1).isNotEqualTo(rideConfirmationsDTO2);
        rideConfirmationsDTO1.setId(null);
        assertThat(rideConfirmationsDTO1).isNotEqualTo(rideConfirmationsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rideConfirmationsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rideConfirmationsMapper.fromId(null)).isNull();
    }
}
