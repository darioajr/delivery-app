package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.RideApp;
import com.senior.fsw.mboy.domain.WhitelistedDrivers;
import com.senior.fsw.mboy.repository.WhitelistedDriversRepository;
import com.senior.fsw.mboy.service.WhitelistedDriversService;
import com.senior.fsw.mboy.service.dto.WhitelistedDriversDTO;
import com.senior.fsw.mboy.service.mapper.WhitelistedDriversMapper;
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
 * Integration tests for the {@Link WhitelistedDriversResource} REST controller.
 */
@SpringBootTest(classes = RideApp.class)
public class WhitelistedDriversResourceIT {

    private static final String DEFAULT_DRIVER = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER = "BBBBBBBBBB";

    @Autowired
    private WhitelistedDriversRepository whitelistedDriversRepository;

    @Autowired
    private WhitelistedDriversMapper whitelistedDriversMapper;

    @Autowired
    private WhitelistedDriversService whitelistedDriversService;

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

    private MockMvc restWhitelistedDriversMockMvc;

    private WhitelistedDrivers whitelistedDrivers;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WhitelistedDriversResource whitelistedDriversResource = new WhitelistedDriversResource(whitelistedDriversService);
        this.restWhitelistedDriversMockMvc = MockMvcBuilders.standaloneSetup(whitelistedDriversResource)
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
    public static WhitelistedDrivers createEntity(EntityManager em) {
        WhitelistedDrivers whitelistedDrivers = new WhitelistedDrivers()
            .driver(DEFAULT_DRIVER);
        return whitelistedDrivers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WhitelistedDrivers createUpdatedEntity(EntityManager em) {
        WhitelistedDrivers whitelistedDrivers = new WhitelistedDrivers()
            .driver(UPDATED_DRIVER);
        return whitelistedDrivers;
    }

    @BeforeEach
    public void initTest() {
        whitelistedDrivers = createEntity(em);
    }

    @Test
    @Transactional
    public void createWhitelistedDrivers() throws Exception {
        int databaseSizeBeforeCreate = whitelistedDriversRepository.findAll().size();

        // Create the WhitelistedDrivers
        WhitelistedDriversDTO whitelistedDriversDTO = whitelistedDriversMapper.toDto(whitelistedDrivers);
        restWhitelistedDriversMockMvc.perform(post("/api/whitelisted-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whitelistedDriversDTO)))
            .andExpect(status().isCreated());

        // Validate the WhitelistedDrivers in the database
        List<WhitelistedDrivers> whitelistedDriversList = whitelistedDriversRepository.findAll();
        assertThat(whitelistedDriversList).hasSize(databaseSizeBeforeCreate + 1);
        WhitelistedDrivers testWhitelistedDrivers = whitelistedDriversList.get(whitelistedDriversList.size() - 1);
        assertThat(testWhitelistedDrivers.getDriver()).isEqualTo(DEFAULT_DRIVER);
    }

    @Test
    @Transactional
    public void createWhitelistedDriversWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = whitelistedDriversRepository.findAll().size();

        // Create the WhitelistedDrivers with an existing ID
        whitelistedDrivers.setId(1L);
        WhitelistedDriversDTO whitelistedDriversDTO = whitelistedDriversMapper.toDto(whitelistedDrivers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWhitelistedDriversMockMvc.perform(post("/api/whitelisted-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whitelistedDriversDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WhitelistedDrivers in the database
        List<WhitelistedDrivers> whitelistedDriversList = whitelistedDriversRepository.findAll();
        assertThat(whitelistedDriversList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDriverIsRequired() throws Exception {
        int databaseSizeBeforeTest = whitelistedDriversRepository.findAll().size();
        // set the field null
        whitelistedDrivers.setDriver(null);

        // Create the WhitelistedDrivers, which fails.
        WhitelistedDriversDTO whitelistedDriversDTO = whitelistedDriversMapper.toDto(whitelistedDrivers);

        restWhitelistedDriversMockMvc.perform(post("/api/whitelisted-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whitelistedDriversDTO)))
            .andExpect(status().isBadRequest());

        List<WhitelistedDrivers> whitelistedDriversList = whitelistedDriversRepository.findAll();
        assertThat(whitelistedDriversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWhitelistedDrivers() throws Exception {
        // Initialize the database
        whitelistedDriversRepository.saveAndFlush(whitelistedDrivers);

        // Get all the whitelistedDriversList
        restWhitelistedDriversMockMvc.perform(get("/api/whitelisted-drivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(whitelistedDrivers.getId().intValue())))
            .andExpect(jsonPath("$.[*].driver").value(hasItem(DEFAULT_DRIVER.toString())));
    }
    
    @Test
    @Transactional
    public void getWhitelistedDrivers() throws Exception {
        // Initialize the database
        whitelistedDriversRepository.saveAndFlush(whitelistedDrivers);

        // Get the whitelistedDrivers
        restWhitelistedDriversMockMvc.perform(get("/api/whitelisted-drivers/{id}", whitelistedDrivers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(whitelistedDrivers.getId().intValue()))
            .andExpect(jsonPath("$.driver").value(DEFAULT_DRIVER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWhitelistedDrivers() throws Exception {
        // Get the whitelistedDrivers
        restWhitelistedDriversMockMvc.perform(get("/api/whitelisted-drivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWhitelistedDrivers() throws Exception {
        // Initialize the database
        whitelistedDriversRepository.saveAndFlush(whitelistedDrivers);

        int databaseSizeBeforeUpdate = whitelistedDriversRepository.findAll().size();

        // Update the whitelistedDrivers
        WhitelistedDrivers updatedWhitelistedDrivers = whitelistedDriversRepository.findById(whitelistedDrivers.getId()).get();
        // Disconnect from session so that the updates on updatedWhitelistedDrivers are not directly saved in db
        em.detach(updatedWhitelistedDrivers);
        updatedWhitelistedDrivers
            .driver(UPDATED_DRIVER);
        WhitelistedDriversDTO whitelistedDriversDTO = whitelistedDriversMapper.toDto(updatedWhitelistedDrivers);

        restWhitelistedDriversMockMvc.perform(put("/api/whitelisted-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whitelistedDriversDTO)))
            .andExpect(status().isOk());

        // Validate the WhitelistedDrivers in the database
        List<WhitelistedDrivers> whitelistedDriversList = whitelistedDriversRepository.findAll();
        assertThat(whitelistedDriversList).hasSize(databaseSizeBeforeUpdate);
        WhitelistedDrivers testWhitelistedDrivers = whitelistedDriversList.get(whitelistedDriversList.size() - 1);
        assertThat(testWhitelistedDrivers.getDriver()).isEqualTo(UPDATED_DRIVER);
    }

    @Test
    @Transactional
    public void updateNonExistingWhitelistedDrivers() throws Exception {
        int databaseSizeBeforeUpdate = whitelistedDriversRepository.findAll().size();

        // Create the WhitelistedDrivers
        WhitelistedDriversDTO whitelistedDriversDTO = whitelistedDriversMapper.toDto(whitelistedDrivers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWhitelistedDriversMockMvc.perform(put("/api/whitelisted-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whitelistedDriversDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WhitelistedDrivers in the database
        List<WhitelistedDrivers> whitelistedDriversList = whitelistedDriversRepository.findAll();
        assertThat(whitelistedDriversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWhitelistedDrivers() throws Exception {
        // Initialize the database
        whitelistedDriversRepository.saveAndFlush(whitelistedDrivers);

        int databaseSizeBeforeDelete = whitelistedDriversRepository.findAll().size();

        // Delete the whitelistedDrivers
        restWhitelistedDriversMockMvc.perform(delete("/api/whitelisted-drivers/{id}", whitelistedDrivers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<WhitelistedDrivers> whitelistedDriversList = whitelistedDriversRepository.findAll();
        assertThat(whitelistedDriversList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WhitelistedDrivers.class);
        WhitelistedDrivers whitelistedDrivers1 = new WhitelistedDrivers();
        whitelistedDrivers1.setId(1L);
        WhitelistedDrivers whitelistedDrivers2 = new WhitelistedDrivers();
        whitelistedDrivers2.setId(whitelistedDrivers1.getId());
        assertThat(whitelistedDrivers1).isEqualTo(whitelistedDrivers2);
        whitelistedDrivers2.setId(2L);
        assertThat(whitelistedDrivers1).isNotEqualTo(whitelistedDrivers2);
        whitelistedDrivers1.setId(null);
        assertThat(whitelistedDrivers1).isNotEqualTo(whitelistedDrivers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WhitelistedDriversDTO.class);
        WhitelistedDriversDTO whitelistedDriversDTO1 = new WhitelistedDriversDTO();
        whitelistedDriversDTO1.setId(1L);
        WhitelistedDriversDTO whitelistedDriversDTO2 = new WhitelistedDriversDTO();
        assertThat(whitelistedDriversDTO1).isNotEqualTo(whitelistedDriversDTO2);
        whitelistedDriversDTO2.setId(whitelistedDriversDTO1.getId());
        assertThat(whitelistedDriversDTO1).isEqualTo(whitelistedDriversDTO2);
        whitelistedDriversDTO2.setId(2L);
        assertThat(whitelistedDriversDTO1).isNotEqualTo(whitelistedDriversDTO2);
        whitelistedDriversDTO1.setId(null);
        assertThat(whitelistedDriversDTO1).isNotEqualTo(whitelistedDriversDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(whitelistedDriversMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(whitelistedDriversMapper.fromId(null)).isNull();
    }
}
