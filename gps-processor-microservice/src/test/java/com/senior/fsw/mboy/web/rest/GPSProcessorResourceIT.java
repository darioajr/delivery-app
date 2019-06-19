package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.GpsprocessorApp;
import com.senior.fsw.mboy.domain.GPSProcessor;
import com.senior.fsw.mboy.repository.GPSProcessorRepository;
import com.senior.fsw.mboy.service.GPSProcessorService;
import com.senior.fsw.mboy.service.dto.GPSProcessorDTO;
import com.senior.fsw.mboy.service.mapper.GPSProcessorMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static com.senior.fsw.mboy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link GPSProcessorResource} REST controller.
 */
@SpringBootTest(classes = GpsprocessorApp.class)
public class GPSProcessorResourceIT {

    private static final BigDecimal DEFAULT_LAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LNG = new BigDecimal(1);
    private static final BigDecimal UPDATED_LNG = new BigDecimal(2);

    private static final String DEFAULT_RIDE = "AAAAAAAAAA";
    private static final String UPDATED_RIDE = "BBBBBBBBBB";

    @Autowired
    private GPSProcessorRepository gPSProcessorRepository;

    @Autowired
    private GPSProcessorMapper gPSProcessorMapper;

    @Autowired
    private GPSProcessorService gPSProcessorService;

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

    private MockMvc restGPSProcessorMockMvc;

    private GPSProcessor gPSProcessor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GPSProcessorResource gPSProcessorResource = new GPSProcessorResource(gPSProcessorService);
        this.restGPSProcessorMockMvc = MockMvcBuilders.standaloneSetup(gPSProcessorResource)
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
    public static GPSProcessor createEntity(EntityManager em) {
        GPSProcessor gPSProcessor = new GPSProcessor()
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG)
            .ride(DEFAULT_RIDE);
        return gPSProcessor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GPSProcessor createUpdatedEntity(EntityManager em) {
        GPSProcessor gPSProcessor = new GPSProcessor()
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .ride(UPDATED_RIDE);
        return gPSProcessor;
    }

    @BeforeEach
    public void initTest() {
        gPSProcessor = createEntity(em);
    }

    @Test
    @Transactional
    public void createGPSProcessor() throws Exception {
        int databaseSizeBeforeCreate = gPSProcessorRepository.findAll().size();

        // Create the GPSProcessor
        GPSProcessorDTO gPSProcessorDTO = gPSProcessorMapper.toDto(gPSProcessor);
        restGPSProcessorMockMvc.perform(post("/api/gps-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSProcessorDTO)))
            .andExpect(status().isCreated());

        // Validate the GPSProcessor in the database
        List<GPSProcessor> gPSProcessorList = gPSProcessorRepository.findAll();
        assertThat(gPSProcessorList).hasSize(databaseSizeBeforeCreate + 1);
        GPSProcessor testGPSProcessor = gPSProcessorList.get(gPSProcessorList.size() - 1);
        assertThat(testGPSProcessor.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testGPSProcessor.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testGPSProcessor.getRide()).isEqualTo(DEFAULT_RIDE);
    }

    @Test
    @Transactional
    public void createGPSProcessorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gPSProcessorRepository.findAll().size();

        // Create the GPSProcessor with an existing ID
        gPSProcessor.setId(1L);
        GPSProcessorDTO gPSProcessorDTO = gPSProcessorMapper.toDto(gPSProcessor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGPSProcessorMockMvc.perform(post("/api/gps-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSProcessorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GPSProcessor in the database
        List<GPSProcessor> gPSProcessorList = gPSProcessorRepository.findAll();
        assertThat(gPSProcessorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGPSProcessors() throws Exception {
        // Initialize the database
        gPSProcessorRepository.saveAndFlush(gPSProcessor);

        // Get all the gPSProcessorList
        restGPSProcessorMockMvc.perform(get("/api/gps-processors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gPSProcessor.getId().intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.intValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.intValue())))
            .andExpect(jsonPath("$.[*].ride").value(hasItem(DEFAULT_RIDE.toString())));
    }
    
    @Test
    @Transactional
    public void getGPSProcessor() throws Exception {
        // Initialize the database
        gPSProcessorRepository.saveAndFlush(gPSProcessor);

        // Get the gPSProcessor
        restGPSProcessorMockMvc.perform(get("/api/gps-processors/{id}", gPSProcessor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gPSProcessor.getId().intValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.intValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.intValue()))
            .andExpect(jsonPath("$.ride").value(DEFAULT_RIDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGPSProcessor() throws Exception {
        // Get the gPSProcessor
        restGPSProcessorMockMvc.perform(get("/api/gps-processors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGPSProcessor() throws Exception {
        // Initialize the database
        gPSProcessorRepository.saveAndFlush(gPSProcessor);

        int databaseSizeBeforeUpdate = gPSProcessorRepository.findAll().size();

        // Update the gPSProcessor
        GPSProcessor updatedGPSProcessor = gPSProcessorRepository.findById(gPSProcessor.getId()).get();
        // Disconnect from session so that the updates on updatedGPSProcessor are not directly saved in db
        em.detach(updatedGPSProcessor);
        updatedGPSProcessor
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .ride(UPDATED_RIDE);
        GPSProcessorDTO gPSProcessorDTO = gPSProcessorMapper.toDto(updatedGPSProcessor);

        restGPSProcessorMockMvc.perform(put("/api/gps-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSProcessorDTO)))
            .andExpect(status().isOk());

        // Validate the GPSProcessor in the database
        List<GPSProcessor> gPSProcessorList = gPSProcessorRepository.findAll();
        assertThat(gPSProcessorList).hasSize(databaseSizeBeforeUpdate);
        GPSProcessor testGPSProcessor = gPSProcessorList.get(gPSProcessorList.size() - 1);
        assertThat(testGPSProcessor.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testGPSProcessor.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testGPSProcessor.getRide()).isEqualTo(UPDATED_RIDE);
    }

    @Test
    @Transactional
    public void updateNonExistingGPSProcessor() throws Exception {
        int databaseSizeBeforeUpdate = gPSProcessorRepository.findAll().size();

        // Create the GPSProcessor
        GPSProcessorDTO gPSProcessorDTO = gPSProcessorMapper.toDto(gPSProcessor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGPSProcessorMockMvc.perform(put("/api/gps-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPSProcessorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GPSProcessor in the database
        List<GPSProcessor> gPSProcessorList = gPSProcessorRepository.findAll();
        assertThat(gPSProcessorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGPSProcessor() throws Exception {
        // Initialize the database
        gPSProcessorRepository.saveAndFlush(gPSProcessor);

        int databaseSizeBeforeDelete = gPSProcessorRepository.findAll().size();

        // Delete the gPSProcessor
        restGPSProcessorMockMvc.perform(delete("/api/gps-processors/{id}", gPSProcessor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<GPSProcessor> gPSProcessorList = gPSProcessorRepository.findAll();
        assertThat(gPSProcessorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GPSProcessor.class);
        GPSProcessor gPSProcessor1 = new GPSProcessor();
        gPSProcessor1.setId(1L);
        GPSProcessor gPSProcessor2 = new GPSProcessor();
        gPSProcessor2.setId(gPSProcessor1.getId());
        assertThat(gPSProcessor1).isEqualTo(gPSProcessor2);
        gPSProcessor2.setId(2L);
        assertThat(gPSProcessor1).isNotEqualTo(gPSProcessor2);
        gPSProcessor1.setId(null);
        assertThat(gPSProcessor1).isNotEqualTo(gPSProcessor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GPSProcessorDTO.class);
        GPSProcessorDTO gPSProcessorDTO1 = new GPSProcessorDTO();
        gPSProcessorDTO1.setId(1L);
        GPSProcessorDTO gPSProcessorDTO2 = new GPSProcessorDTO();
        assertThat(gPSProcessorDTO1).isNotEqualTo(gPSProcessorDTO2);
        gPSProcessorDTO2.setId(gPSProcessorDTO1.getId());
        assertThat(gPSProcessorDTO1).isEqualTo(gPSProcessorDTO2);
        gPSProcessorDTO2.setId(2L);
        assertThat(gPSProcessorDTO1).isNotEqualTo(gPSProcessorDTO2);
        gPSProcessorDTO1.setId(null);
        assertThat(gPSProcessorDTO1).isNotEqualTo(gPSProcessorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gPSProcessorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gPSProcessorMapper.fromId(null)).isNull();
    }
}
