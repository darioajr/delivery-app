package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.RideApp;
import com.senior.fsw.mboy.domain.RideRequests;
import com.senior.fsw.mboy.repository.RideRequestsRepository;
import com.senior.fsw.mboy.service.RideRequestsService;
import com.senior.fsw.mboy.service.dto.RideRequestsDTO;
import com.senior.fsw.mboy.service.mapper.RideRequestsMapper;
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
 * Integration tests for the {@Link RideRequestsResource} REST controller.
 */
@SpringBootTest(classes = RideApp.class)
public class RideRequestsResourceIT {

    private static final String DEFAULT_DRIVER = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER = "BBBBBBBBBB";

    private static final String DEFAULT_PASSENGER = "AAAAAAAAAA";
    private static final String UPDATED_PASSENGER = "BBBBBBBBBB";

    @Autowired
    private RideRequestsRepository rideRequestsRepository;

    @Autowired
    private RideRequestsMapper rideRequestsMapper;

    @Autowired
    private RideRequestsService rideRequestsService;

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

    private MockMvc restRideRequestsMockMvc;

    private RideRequests rideRequests;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RideRequestsResource rideRequestsResource = new RideRequestsResource(rideRequestsService);
        this.restRideRequestsMockMvc = MockMvcBuilders.standaloneSetup(rideRequestsResource)
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
    public static RideRequests createEntity(EntityManager em) {
        RideRequests rideRequests = new RideRequests()
            .driver(DEFAULT_DRIVER)
            .passenger(DEFAULT_PASSENGER);
        return rideRequests;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RideRequests createUpdatedEntity(EntityManager em) {
        RideRequests rideRequests = new RideRequests()
            .driver(UPDATED_DRIVER)
            .passenger(UPDATED_PASSENGER);
        return rideRequests;
    }

    @BeforeEach
    public void initTest() {
        rideRequests = createEntity(em);
    }

    @Test
    @Transactional
    public void createRideRequests() throws Exception {
        int databaseSizeBeforeCreate = rideRequestsRepository.findAll().size();

        // Create the RideRequests
        RideRequestsDTO rideRequestsDTO = rideRequestsMapper.toDto(rideRequests);
        restRideRequestsMockMvc.perform(post("/api/ride-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideRequestsDTO)))
            .andExpect(status().isCreated());

        // Validate the RideRequests in the database
        List<RideRequests> rideRequestsList = rideRequestsRepository.findAll();
        assertThat(rideRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        RideRequests testRideRequests = rideRequestsList.get(rideRequestsList.size() - 1);
        assertThat(testRideRequests.getDriver()).isEqualTo(DEFAULT_DRIVER);
        assertThat(testRideRequests.getPassenger()).isEqualTo(DEFAULT_PASSENGER);
    }

    @Test
    @Transactional
    public void createRideRequestsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rideRequestsRepository.findAll().size();

        // Create the RideRequests with an existing ID
        rideRequests.setId(1L);
        RideRequestsDTO rideRequestsDTO = rideRequestsMapper.toDto(rideRequests);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRideRequestsMockMvc.perform(post("/api/ride-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RideRequests in the database
        List<RideRequests> rideRequestsList = rideRequestsRepository.findAll();
        assertThat(rideRequestsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPassengerIsRequired() throws Exception {
        int databaseSizeBeforeTest = rideRequestsRepository.findAll().size();
        // set the field null
        rideRequests.setPassenger(null);

        // Create the RideRequests, which fails.
        RideRequestsDTO rideRequestsDTO = rideRequestsMapper.toDto(rideRequests);

        restRideRequestsMockMvc.perform(post("/api/ride-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideRequestsDTO)))
            .andExpect(status().isBadRequest());

        List<RideRequests> rideRequestsList = rideRequestsRepository.findAll();
        assertThat(rideRequestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRideRequests() throws Exception {
        // Initialize the database
        rideRequestsRepository.saveAndFlush(rideRequests);

        // Get all the rideRequestsList
        restRideRequestsMockMvc.perform(get("/api/ride-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rideRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].driver").value(hasItem(DEFAULT_DRIVER.toString())))
            .andExpect(jsonPath("$.[*].passenger").value(hasItem(DEFAULT_PASSENGER.toString())));
    }
    
    @Test
    @Transactional
    public void getRideRequests() throws Exception {
        // Initialize the database
        rideRequestsRepository.saveAndFlush(rideRequests);

        // Get the rideRequests
        restRideRequestsMockMvc.perform(get("/api/ride-requests/{id}", rideRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rideRequests.getId().intValue()))
            .andExpect(jsonPath("$.driver").value(DEFAULT_DRIVER.toString()))
            .andExpect(jsonPath("$.passenger").value(DEFAULT_PASSENGER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRideRequests() throws Exception {
        // Get the rideRequests
        restRideRequestsMockMvc.perform(get("/api/ride-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRideRequests() throws Exception {
        // Initialize the database
        rideRequestsRepository.saveAndFlush(rideRequests);

        int databaseSizeBeforeUpdate = rideRequestsRepository.findAll().size();

        // Update the rideRequests
        RideRequests updatedRideRequests = rideRequestsRepository.findById(rideRequests.getId()).get();
        // Disconnect from session so that the updates on updatedRideRequests are not directly saved in db
        em.detach(updatedRideRequests);
        updatedRideRequests
            .driver(UPDATED_DRIVER)
            .passenger(UPDATED_PASSENGER);
        RideRequestsDTO rideRequestsDTO = rideRequestsMapper.toDto(updatedRideRequests);

        restRideRequestsMockMvc.perform(put("/api/ride-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideRequestsDTO)))
            .andExpect(status().isOk());

        // Validate the RideRequests in the database
        List<RideRequests> rideRequestsList = rideRequestsRepository.findAll();
        assertThat(rideRequestsList).hasSize(databaseSizeBeforeUpdate);
        RideRequests testRideRequests = rideRequestsList.get(rideRequestsList.size() - 1);
        assertThat(testRideRequests.getDriver()).isEqualTo(UPDATED_DRIVER);
        assertThat(testRideRequests.getPassenger()).isEqualTo(UPDATED_PASSENGER);
    }

    @Test
    @Transactional
    public void updateNonExistingRideRequests() throws Exception {
        int databaseSizeBeforeUpdate = rideRequestsRepository.findAll().size();

        // Create the RideRequests
        RideRequestsDTO rideRequestsDTO = rideRequestsMapper.toDto(rideRequests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRideRequestsMockMvc.perform(put("/api/ride-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rideRequestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RideRequests in the database
        List<RideRequests> rideRequestsList = rideRequestsRepository.findAll();
        assertThat(rideRequestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRideRequests() throws Exception {
        // Initialize the database
        rideRequestsRepository.saveAndFlush(rideRequests);

        int databaseSizeBeforeDelete = rideRequestsRepository.findAll().size();

        // Delete the rideRequests
        restRideRequestsMockMvc.perform(delete("/api/ride-requests/{id}", rideRequests.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<RideRequests> rideRequestsList = rideRequestsRepository.findAll();
        assertThat(rideRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RideRequests.class);
        RideRequests rideRequests1 = new RideRequests();
        rideRequests1.setId(1L);
        RideRequests rideRequests2 = new RideRequests();
        rideRequests2.setId(rideRequests1.getId());
        assertThat(rideRequests1).isEqualTo(rideRequests2);
        rideRequests2.setId(2L);
        assertThat(rideRequests1).isNotEqualTo(rideRequests2);
        rideRequests1.setId(null);
        assertThat(rideRequests1).isNotEqualTo(rideRequests2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RideRequestsDTO.class);
        RideRequestsDTO rideRequestsDTO1 = new RideRequestsDTO();
        rideRequestsDTO1.setId(1L);
        RideRequestsDTO rideRequestsDTO2 = new RideRequestsDTO();
        assertThat(rideRequestsDTO1).isNotEqualTo(rideRequestsDTO2);
        rideRequestsDTO2.setId(rideRequestsDTO1.getId());
        assertThat(rideRequestsDTO1).isEqualTo(rideRequestsDTO2);
        rideRequestsDTO2.setId(2L);
        assertThat(rideRequestsDTO1).isNotEqualTo(rideRequestsDTO2);
        rideRequestsDTO1.setId(null);
        assertThat(rideRequestsDTO1).isNotEqualTo(rideRequestsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rideRequestsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rideRequestsMapper.fromId(null)).isNull();
    }
}
