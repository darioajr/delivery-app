package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.BackofficeApp;
import com.senior.fsw.mboy.domain.Motoboy;
import com.senior.fsw.mboy.repository.MotoboyRepository;
import com.senior.fsw.mboy.service.MotoboyService;
import com.senior.fsw.mboy.service.dto.MotoboyDTO;
import com.senior.fsw.mboy.service.mapper.MotoboyMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.senior.fsw.mboy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link MotoboyResource} REST controller.
 */
@SpringBootTest(classes = BackofficeApp.class)
public class MotoboyResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME = "BBBBBBBBBB";

    private static final String DEFAULT_CNH = "AAAAAAAAAA";
    private static final String UPDATED_CNH = "BBBBBBBBBB";

    private static final String DEFAULT_PLACA = "AAAAAAAAAA";
    private static final String UPDATED_PLACA = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTATO = "AAAAAAAAAA";
    private static final String UPDATED_CONTATO = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MotoboyRepository motoboyRepository;

    @Autowired
    private MotoboyMapper motoboyMapper;

    @Autowired
    private MotoboyService motoboyService;

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

    private MockMvc restMotoboyMockMvc;

    private Motoboy motoboy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MotoboyResource motoboyResource = new MotoboyResource(motoboyService);
        this.restMotoboyMockMvc = MockMvcBuilders.standaloneSetup(motoboyResource)
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
    public static Motoboy createEntity(EntityManager em) {
        Motoboy motoboy = new Motoboy()
            .nome(DEFAULT_NOME)
            .sobrenome(DEFAULT_SOBRENOME)
            .cnh(DEFAULT_CNH)
            .placa(DEFAULT_PLACA)
            .email(DEFAULT_EMAIL)
            .contato(DEFAULT_CONTATO)
            .endereco(DEFAULT_ENDERECO)
            .enderecoNumero(DEFAULT_ENDERECO_NUMERO)
            .enderecoComplemento(DEFAULT_ENDERECO_COMPLEMENTO)
            .enderecoBairro(DEFAULT_ENDERECO_BAIRRO)
            .cep(DEFAULT_CEP)
            .status(DEFAULT_STATUS)
            .data(DEFAULT_DATA);
        return motoboy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Motoboy createUpdatedEntity(EntityManager em) {
        Motoboy motoboy = new Motoboy()
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .cnh(UPDATED_CNH)
            .placa(UPDATED_PLACA)
            .email(UPDATED_EMAIL)
            .contato(UPDATED_CONTATO)
            .endereco(UPDATED_ENDERECO)
            .enderecoNumero(UPDATED_ENDERECO_NUMERO)
            .enderecoComplemento(UPDATED_ENDERECO_COMPLEMENTO)
            .enderecoBairro(UPDATED_ENDERECO_BAIRRO)
            .cep(UPDATED_CEP)
            .status(UPDATED_STATUS)
            .data(UPDATED_DATA);
        return motoboy;
    }

    @BeforeEach
    public void initTest() {
        motoboy = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotoboy() throws Exception {
        int databaseSizeBeforeCreate = motoboyRepository.findAll().size();

        // Create the Motoboy
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);
        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isCreated());

        // Validate the Motoboy in the database
        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeCreate + 1);
        Motoboy testMotoboy = motoboyList.get(motoboyList.size() - 1);
        assertThat(testMotoboy.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMotoboy.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testMotoboy.getCnh()).isEqualTo(DEFAULT_CNH);
        assertThat(testMotoboy.getPlaca()).isEqualTo(DEFAULT_PLACA);
        assertThat(testMotoboy.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMotoboy.getContato()).isEqualTo(DEFAULT_CONTATO);
        assertThat(testMotoboy.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testMotoboy.getEnderecoNumero()).isEqualTo(DEFAULT_ENDERECO_NUMERO);
        assertThat(testMotoboy.getEnderecoComplemento()).isEqualTo(DEFAULT_ENDERECO_COMPLEMENTO);
        assertThat(testMotoboy.getEnderecoBairro()).isEqualTo(DEFAULT_ENDERECO_BAIRRO);
        assertThat(testMotoboy.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testMotoboy.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMotoboy.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createMotoboyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motoboyRepository.findAll().size();

        // Create the Motoboy with an existing ID
        motoboy.setId(1L);
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Motoboy in the database
        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setNome(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSobrenomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setSobrenome(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCnhIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setCnh(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlacaIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setPlaca(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setEmail(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContatoIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setContato(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setEndereco(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setEnderecoNumero(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoComplementoIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setEnderecoComplemento(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setEnderecoBairro(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setCep(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setStatus(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = motoboyRepository.findAll().size();
        // set the field null
        motoboy.setData(null);

        // Create the Motoboy, which fails.
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        restMotoboyMockMvc.perform(post("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMotoboys() throws Exception {
        // Initialize the database
        motoboyRepository.saveAndFlush(motoboy);

        // Get all the motoboyList
        restMotoboyMockMvc.perform(get("/api/motoboys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motoboy.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME.toString())))
            .andExpect(jsonPath("$.[*].cnh").value(hasItem(DEFAULT_CNH.toString())))
            .andExpect(jsonPath("$.[*].placa").value(hasItem(DEFAULT_PLACA.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].contato").value(hasItem(DEFAULT_CONTATO.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].enderecoNumero").value(hasItem(DEFAULT_ENDERECO_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].enderecoComplemento").value(hasItem(DEFAULT_ENDERECO_COMPLEMENTO.toString())))
            .andExpect(jsonPath("$.[*].enderecoBairro").value(hasItem(DEFAULT_ENDERECO_BAIRRO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getMotoboy() throws Exception {
        // Initialize the database
        motoboyRepository.saveAndFlush(motoboy);

        // Get the motoboy
        restMotoboyMockMvc.perform(get("/api/motoboys/{id}", motoboy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(motoboy.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME.toString()))
            .andExpect(jsonPath("$.cnh").value(DEFAULT_CNH.toString()))
            .andExpect(jsonPath("$.placa").value(DEFAULT_PLACA.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.contato").value(DEFAULT_CONTATO.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.enderecoNumero").value(DEFAULT_ENDERECO_NUMERO.toString()))
            .andExpect(jsonPath("$.enderecoComplemento").value(DEFAULT_ENDERECO_COMPLEMENTO.toString()))
            .andExpect(jsonPath("$.enderecoBairro").value(DEFAULT_ENDERECO_BAIRRO.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMotoboy() throws Exception {
        // Get the motoboy
        restMotoboyMockMvc.perform(get("/api/motoboys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotoboy() throws Exception {
        // Initialize the database
        motoboyRepository.saveAndFlush(motoboy);

        int databaseSizeBeforeUpdate = motoboyRepository.findAll().size();

        // Update the motoboy
        Motoboy updatedMotoboy = motoboyRepository.findById(motoboy.getId()).get();
        // Disconnect from session so that the updates on updatedMotoboy are not directly saved in db
        em.detach(updatedMotoboy);
        updatedMotoboy
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .cnh(UPDATED_CNH)
            .placa(UPDATED_PLACA)
            .email(UPDATED_EMAIL)
            .contato(UPDATED_CONTATO)
            .endereco(UPDATED_ENDERECO)
            .enderecoNumero(UPDATED_ENDERECO_NUMERO)
            .enderecoComplemento(UPDATED_ENDERECO_COMPLEMENTO)
            .enderecoBairro(UPDATED_ENDERECO_BAIRRO)
            .cep(UPDATED_CEP)
            .status(UPDATED_STATUS)
            .data(UPDATED_DATA);
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(updatedMotoboy);

        restMotoboyMockMvc.perform(put("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isOk());

        // Validate the Motoboy in the database
        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeUpdate);
        Motoboy testMotoboy = motoboyList.get(motoboyList.size() - 1);
        assertThat(testMotoboy.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMotoboy.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testMotoboy.getCnh()).isEqualTo(UPDATED_CNH);
        assertThat(testMotoboy.getPlaca()).isEqualTo(UPDATED_PLACA);
        assertThat(testMotoboy.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMotoboy.getContato()).isEqualTo(UPDATED_CONTATO);
        assertThat(testMotoboy.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testMotoboy.getEnderecoNumero()).isEqualTo(UPDATED_ENDERECO_NUMERO);
        assertThat(testMotoboy.getEnderecoComplemento()).isEqualTo(UPDATED_ENDERECO_COMPLEMENTO);
        assertThat(testMotoboy.getEnderecoBairro()).isEqualTo(UPDATED_ENDERECO_BAIRRO);
        assertThat(testMotoboy.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testMotoboy.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMotoboy.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingMotoboy() throws Exception {
        int databaseSizeBeforeUpdate = motoboyRepository.findAll().size();

        // Create the Motoboy
        MotoboyDTO motoboyDTO = motoboyMapper.toDto(motoboy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotoboyMockMvc.perform(put("/api/motoboys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motoboyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Motoboy in the database
        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMotoboy() throws Exception {
        // Initialize the database
        motoboyRepository.saveAndFlush(motoboy);

        int databaseSizeBeforeDelete = motoboyRepository.findAll().size();

        // Delete the motoboy
        restMotoboyMockMvc.perform(delete("/api/motoboys/{id}", motoboy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Motoboy> motoboyList = motoboyRepository.findAll();
        assertThat(motoboyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Motoboy.class);
        Motoboy motoboy1 = new Motoboy();
        motoboy1.setId(1L);
        Motoboy motoboy2 = new Motoboy();
        motoboy2.setId(motoboy1.getId());
        assertThat(motoboy1).isEqualTo(motoboy2);
        motoboy2.setId(2L);
        assertThat(motoboy1).isNotEqualTo(motoboy2);
        motoboy1.setId(null);
        assertThat(motoboy1).isNotEqualTo(motoboy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotoboyDTO.class);
        MotoboyDTO motoboyDTO1 = new MotoboyDTO();
        motoboyDTO1.setId(1L);
        MotoboyDTO motoboyDTO2 = new MotoboyDTO();
        assertThat(motoboyDTO1).isNotEqualTo(motoboyDTO2);
        motoboyDTO2.setId(motoboyDTO1.getId());
        assertThat(motoboyDTO1).isEqualTo(motoboyDTO2);
        motoboyDTO2.setId(2L);
        assertThat(motoboyDTO1).isNotEqualTo(motoboyDTO2);
        motoboyDTO1.setId(null);
        assertThat(motoboyDTO1).isNotEqualTo(motoboyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(motoboyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(motoboyMapper.fromId(null)).isNull();
    }
}
