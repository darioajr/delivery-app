package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.RideApp;
import com.senior.fsw.mboy.domain.WhitelistedRiders;
import com.senior.fsw.mboy.repository.WhitelistedRidersRepository;
import com.senior.fsw.mboy.service.WhitelistedRidersService;
import com.senior.fsw.mboy.service.dto.WhitelistedRidersDTO;
import com.senior.fsw.mboy.service.mapper.WhitelistedRidersMapper;
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
 * Integration tests for the {@Link WhitelistedRidersResource} REST controller.
 */
@SpringBootTest(classes = RideApp.class)
public class WhitelistedRidersResourceIT {

    private static final String DEFAULT_PASSANGER = "AAAAAAAAAA";
    private static final String UPDATED_PASSANGER = "BBBBBBBBBB";

    @Autowired
    private WhitelistedRidersRepository whitelistedRidersRepository;

    @Autowired
    private WhitelistedRidersMapper whitelistedRidersMapper;

    @Autowired
    private WhitelistedRidersService whitelistedRidersService;

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

    private MockMvc restWhitelistedRidersMockMvc;

    private WhitelistedRiders whitelistedRiders;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WhitelistedRidersResource whitelistedRidersResource = new WhitelistedRidersResource(whitelistedRidersService);
        this.restWhitelistedRidersMockMvc = MockMvcBuilders.standaloneSetup(whitelistedRidersResource)
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
    public static WhitelistedRiders createEntity(EntityManager em) {
        WhitelistedRiders whitelistedRiders = new WhitelistedRiders()
            .passanger(DEFAULT_PASSANGER);
        return whitelistedRiders;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WhitelistedRiders createUpdatedEntity(EntityManager em) {
        WhitelistedRiders whitelistedRiders = new WhitelistedRiders()
            .passanger(UPDATED_PASSANGER);
        return whitelistedRiders;
    }

    @BeforeEach
    public void initTest() {
        whitelistedRiders = createEntity(em);
    }

    @Test
    @Transactional
    public void createWhitelistedRiders() throws Exception {
        int databaseSizeBeforeCreate = whitelistedRidersRepository.findAll().size();

        // Create the WhitelistedRiders
        WhitelistedRidersDTO whitelistedRidersDTO = whitelistedRidersMapper.toDto(whitelistedRiders);
        restWhitelistedRidersMockMvc.perform(post("/api/whitelisted-riders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whitelistedRidersDTO)))
            .andExpect(status().isCreated());

        // Validate the WhitelistedRiders in the database
        List<WhitelistedRiders> whitelistedRidersList = whitelistedRidersRepository.findAll();
        assertThat(whitelistedRidersList).hasSize(databaseSizeBeforeCreate + 1);
        WhitelistedRiders testWhitelistedRiders = whitelistedRidersList.get(whitelistedRidersList.size() - 1);
        assertThat(testWhitelistedRiders.getPassanger()).isEqualTo(DEFAULT_PASSANGER);
    }

    @Test
    @Transactional
    public void createWhitelistedRidersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = whitelistedRidersRepository.findAll().size();

        // Create the WhitelistedRiders with an existing ID
        whitelistedRiders.setId(1L);
        WhitelistedRidersDTO whitelistedRidersDTO = whitelistedRidersMapper.toDto(whitelistedRiders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWhitelistedRidersMockMvc.perform(post("/api/whitelisted-riders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whitelistedRidersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WhitelistedRiders in the database
        List<WhitelistedRiders> whitelistedRidersList = whitelistedRidersRepository.findAll();
        assertThat(whitelistedRidersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPassangerIsRequired() throws Exception {
        int databaseSizeBeforeTest = whitelistedRidersRepository.findAll().size();
        // set the field null
        whitelistedRiders.setPassanger(null);

        // Create the WhitelistedRiders, which fails.
        WhitelistedRidersDTO whitelistedRidersDTO = whitelistedRidersMapper.toDto(whitelistedRiders);

        restWhitelistedRidersMockMvc.perform(post("/api/whitelisted-riders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whitelistedRidersDTO)))
            .andExpect(status().isBadRequest());

        List<WhitelistedRiders> whitelistedRidersList = whitelistedRidersRepository.findAll();
        assertThat(whitelistedRidersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWhitelistedRiders() throws Exception {
        // Initialize the database
        whitelistedRidersRepository.saveAndFlush(whitelistedRiders);

        // Get all the whitelistedRidersList
        restWhitelistedRidersMockMvc.perform(get("/api/whitelisted-riders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(whitelistedRiders.getId().intValue())))
            .andExpect(jsonPath("$.[*].passanger").value(hasItem(DEFAULT_PASSANGER.toString())));
    }
    
    @Test
    @Transactional
    public void getWhitelistedRiders() throws Exception {
        // Initialize the database
        whitelistedRidersRepository.saveAndFlush(whitelistedRiders);

        // Get the whitelistedRiders
        restWhitelistedRidersMockMvc.perform(get("/api/whitelisted-riders/{id}", whitelistedRiders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(whitelistedRiders.getId().intValue()))
            .andExpect(jsonPath("$.passanger").value(DEFAULT_PASSANGER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWhitelistedRiders() throws Exception {
        // Get the whitelistedRiders
        restWhitelistedRidersMockMvc.perform(get("/api/whitelisted-riders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWhitelistedRiders() throws Exception {
        // Initialize the database
        whitelistedRidersRepository.saveAndFlush(whitelistedRiders);

        int databaseSizeBeforeUpdate = whitelistedRidersRepository.findAll().size();

        // Update the whitelistedRiders
        WhitelistedRiders updatedWhitelistedRiders = whitelistedRidersRepository.findById(whitelistedRiders.getId()).get();
        // Disconnect from session so that the updates on updatedWhitelistedRiders are not directly saved in db
        em.detach(updatedWhitelistedRiders);
        updatedWhitelistedRiders
            .passanger(UPDATED_PASSANGER);
        WhitelistedRidersDTO whitelistedRidersDTO = whitelistedRidersMapper.toDto(updatedWhitelistedRiders);

        restWhitelistedRidersMockMvc.perform(put("/api/whitelisted-riders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whitelistedRidersDTO)))
            .andExpect(status().isOk());

        // Validate the WhitelistedRiders in the database
        List<WhitelistedRiders> whitelistedRidersList = whitelistedRidersRepository.findAll();
        assertThat(whitelistedRidersList).hasSize(databaseSizeBeforeUpdate);
        WhitelistedRiders testWhitelistedRiders = whitelistedRidersList.get(whitelistedRidersList.size() - 1);
        assertThat(testWhitelistedRiders.getPassanger()).isEqualTo(UPDATED_PASSANGER);
    }

    @Test
    @Transactional
    public void updateNonExistingWhitelistedRiders() throws Exception {
        int databaseSizeBeforeUpdate = whitelistedRidersRepository.findAll().size();

        // Create the WhitelistedRiders
        WhitelistedRidersDTO whitelistedRidersDTO = whitelistedRidersMapper.toDto(whitelistedRiders);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWhitelistedRidersMockMvc.perform(put("/api/whitelisted-riders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whitelistedRidersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WhitelistedRiders in the database
        List<WhitelistedRiders> whitelistedRidersList = whitelistedRidersRepository.findAll();
        assertThat(whitelistedRidersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWhitelistedRiders() throws Exception {
        // Initialize the database
        whitelistedRidersRepository.saveAndFlush(whitelistedRiders);

        int databaseSizeBeforeDelete = whitelistedRidersRepository.findAll().size();

        // Delete the whitelistedRiders
        restWhitelistedRidersMockMvc.perform(delete("/api/whitelisted-riders/{id}", whitelistedRiders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<WhitelistedRiders> whitelistedRidersList = whitelistedRidersRepository.findAll();
        assertThat(whitelistedRidersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WhitelistedRiders.class);
        WhitelistedRiders whitelistedRiders1 = new WhitelistedRiders();
        whitelistedRiders1.setId(1L);
        WhitelistedRiders whitelistedRiders2 = new WhitelistedRiders();
        whitelistedRiders2.setId(whitelistedRiders1.getId());
        assertThat(whitelistedRiders1).isEqualTo(whitelistedRiders2);
        whitelistedRiders2.setId(2L);
        assertThat(whitelistedRiders1).isNotEqualTo(whitelistedRiders2);
        whitelistedRiders1.setId(null);
        assertThat(whitelistedRiders1).isNotEqualTo(whitelistedRiders2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WhitelistedRidersDTO.class);
        WhitelistedRidersDTO whitelistedRidersDTO1 = new WhitelistedRidersDTO();
        whitelistedRidersDTO1.setId(1L);
        WhitelistedRidersDTO whitelistedRidersDTO2 = new WhitelistedRidersDTO();
        assertThat(whitelistedRidersDTO1).isNotEqualTo(whitelistedRidersDTO2);
        whitelistedRidersDTO2.setId(whitelistedRidersDTO1.getId());
        assertThat(whitelistedRidersDTO1).isEqualTo(whitelistedRidersDTO2);
        whitelistedRidersDTO2.setId(2L);
        assertThat(whitelistedRidersDTO1).isNotEqualTo(whitelistedRidersDTO2);
        whitelistedRidersDTO1.setId(null);
        assertThat(whitelistedRidersDTO1).isNotEqualTo(whitelistedRidersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(whitelistedRidersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(whitelistedRidersMapper.fromId(null)).isNull();
    }
}
