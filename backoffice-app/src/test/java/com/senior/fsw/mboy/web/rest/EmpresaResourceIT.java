package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.BackofficeApp;
import com.senior.fsw.mboy.domain.Empresa;
import com.senior.fsw.mboy.repository.EmpresaRepository;
import com.senior.fsw.mboy.service.EmpresaService;
import com.senior.fsw.mboy.service.dto.EmpresaDTO;
import com.senior.fsw.mboy.service.mapper.EmpresaMapper;
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
 * Integration tests for the {@Link EmpresaResource} REST controller.
 */
@SpringBootTest(classes = BackofficeApp.class)
public class EmpresaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_FANSTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_FANSTASIA = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTATO = "AAAAAAAAAA";
    private static final String UPDATED_CONTATO = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RECARGA_PENDENTE = "AAAAAAAAAA";
    private static final String UPDATED_RECARGA_PENDENTE = "BBBBBBBBBB";

    private static final String DEFAULT_POSSUI_BAU = "AAAAAAAAAA";
    private static final String UPDATED_POSSUI_BAU = "BBBBBBBBBB";

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

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private EmpresaService empresaService;

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

    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpresaResource empresaResource = new EmpresaResource(empresaService);
        this.restEmpresaMockMvc = MockMvcBuilders.standaloneSetup(empresaResource)
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
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nome(DEFAULT_NOME)
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .nomeFanstasia(DEFAULT_NOME_FANSTASIA)
            .cnpj(DEFAULT_CNPJ)
            .email(DEFAULT_EMAIL)
            .contato(DEFAULT_CONTATO)
            .status(DEFAULT_STATUS)
            .recargaPendente(DEFAULT_RECARGA_PENDENTE)
            .possuiBau(DEFAULT_POSSUI_BAU)
            .endereco(DEFAULT_ENDERECO)
            .enderecoNumero(DEFAULT_ENDERECO_NUMERO)
            .enderecoComplemento(DEFAULT_ENDERECO_COMPLEMENTO)
            .enderecoBairro(DEFAULT_ENDERECO_BAIRRO)
            .cep(DEFAULT_CEP);
        return empresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nome(UPDATED_NOME)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFanstasia(UPDATED_NOME_FANSTASIA)
            .cnpj(UPDATED_CNPJ)
            .email(UPDATED_EMAIL)
            .contato(UPDATED_CONTATO)
            .status(UPDATED_STATUS)
            .recargaPendente(UPDATED_RECARGA_PENDENTE)
            .possuiBau(UPDATED_POSSUI_BAU)
            .endereco(UPDATED_ENDERECO)
            .enderecoNumero(UPDATED_ENDERECO_NUMERO)
            .enderecoComplemento(UPDATED_ENDERECO_COMPLEMENTO)
            .enderecoBairro(UPDATED_ENDERECO_BAIRRO)
            .cep(UPDATED_CEP);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEmpresa.getRazaoSocial()).isEqualTo(DEFAULT_RAZAO_SOCIAL);
        assertThat(testEmpresa.getNomeFanstasia()).isEqualTo(DEFAULT_NOME_FANSTASIA);
        assertThat(testEmpresa.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testEmpresa.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmpresa.getContato()).isEqualTo(DEFAULT_CONTATO);
        assertThat(testEmpresa.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmpresa.getRecargaPendente()).isEqualTo(DEFAULT_RECARGA_PENDENTE);
        assertThat(testEmpresa.getPossuiBau()).isEqualTo(DEFAULT_POSSUI_BAU);
        assertThat(testEmpresa.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testEmpresa.getEnderecoNumero()).isEqualTo(DEFAULT_ENDERECO_NUMERO);
        assertThat(testEmpresa.getEnderecoComplemento()).isEqualTo(DEFAULT_ENDERECO_COMPLEMENTO);
        assertThat(testEmpresa.getEnderecoBairro()).isEqualTo(DEFAULT_ENDERECO_BAIRRO);
        assertThat(testEmpresa.getCep()).isEqualTo(DEFAULT_CEP);
    }

    @Test
    @Transactional
    public void createEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa with an existing ID
        empresa.setId(1L);
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNome(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRazaoSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setRazaoSocial(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setCnpj(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setEmail(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContatoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setContato(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setStatus(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecargaPendenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setRecargaPendente(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPossuiBauIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setPossuiBau(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setEndereco(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setEnderecoNumero(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoComplementoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setEnderecoComplemento(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setEnderecoBairro(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setCep(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].nomeFanstasia").value(hasItem(DEFAULT_NOME_FANSTASIA.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].contato").value(hasItem(DEFAULT_CONTATO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].recargaPendente").value(hasItem(DEFAULT_RECARGA_PENDENTE.toString())))
            .andExpect(jsonPath("$.[*].possuiBau").value(hasItem(DEFAULT_POSSUI_BAU.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].enderecoNumero").value(hasItem(DEFAULT_ENDERECO_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].enderecoComplemento").value(hasItem(DEFAULT_ENDERECO_COMPLEMENTO.toString())))
            .andExpect(jsonPath("$.[*].enderecoBairro").value(hasItem(DEFAULT_ENDERECO_BAIRRO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())));
    }
    
    @Test
    @Transactional
    public void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL.toString()))
            .andExpect(jsonPath("$.nomeFanstasia").value(DEFAULT_NOME_FANSTASIA.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.contato").value(DEFAULT_CONTATO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.recargaPendente").value(DEFAULT_RECARGA_PENDENTE.toString()))
            .andExpect(jsonPath("$.possuiBau").value(DEFAULT_POSSUI_BAU.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.enderecoNumero").value(DEFAULT_ENDERECO_NUMERO.toString()))
            .andExpect(jsonPath("$.enderecoComplemento").value(DEFAULT_ENDERECO_COMPLEMENTO.toString()))
            .andExpect(jsonPath("$.enderecoBairro").value(DEFAULT_ENDERECO_BAIRRO.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .nome(UPDATED_NOME)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFanstasia(UPDATED_NOME_FANSTASIA)
            .cnpj(UPDATED_CNPJ)
            .email(UPDATED_EMAIL)
            .contato(UPDATED_CONTATO)
            .status(UPDATED_STATUS)
            .recargaPendente(UPDATED_RECARGA_PENDENTE)
            .possuiBau(UPDATED_POSSUI_BAU)
            .endereco(UPDATED_ENDERECO)
            .enderecoNumero(UPDATED_ENDERECO_NUMERO)
            .enderecoComplemento(UPDATED_ENDERECO_COMPLEMENTO)
            .enderecoBairro(UPDATED_ENDERECO_BAIRRO)
            .cep(UPDATED_CEP);
        EmpresaDTO empresaDTO = empresaMapper.toDto(updatedEmpresa);

        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEmpresa.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testEmpresa.getNomeFanstasia()).isEqualTo(UPDATED_NOME_FANSTASIA);
        assertThat(testEmpresa.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testEmpresa.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmpresa.getContato()).isEqualTo(UPDATED_CONTATO);
        assertThat(testEmpresa.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmpresa.getRecargaPendente()).isEqualTo(UPDATED_RECARGA_PENDENTE);
        assertThat(testEmpresa.getPossuiBau()).isEqualTo(UPDATED_POSSUI_BAU);
        assertThat(testEmpresa.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testEmpresa.getEnderecoNumero()).isEqualTo(UPDATED_ENDERECO_NUMERO);
        assertThat(testEmpresa.getEnderecoComplemento()).isEqualTo(UPDATED_ENDERECO_COMPLEMENTO);
        assertThat(testEmpresa.getEnderecoBairro()).isEqualTo(UPDATED_ENDERECO_BAIRRO);
        assertThat(testEmpresa.getCep()).isEqualTo(UPDATED_CEP);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Delete the empresa
        restEmpresaMockMvc.perform(delete("/api/empresas/{id}", empresa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empresa.class);
        Empresa empresa1 = new Empresa();
        empresa1.setId(1L);
        Empresa empresa2 = new Empresa();
        empresa2.setId(empresa1.getId());
        assertThat(empresa1).isEqualTo(empresa2);
        empresa2.setId(2L);
        assertThat(empresa1).isNotEqualTo(empresa2);
        empresa1.setId(null);
        assertThat(empresa1).isNotEqualTo(empresa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpresaDTO.class);
        EmpresaDTO empresaDTO1 = new EmpresaDTO();
        empresaDTO1.setId(1L);
        EmpresaDTO empresaDTO2 = new EmpresaDTO();
        assertThat(empresaDTO1).isNotEqualTo(empresaDTO2);
        empresaDTO2.setId(empresaDTO1.getId());
        assertThat(empresaDTO1).isEqualTo(empresaDTO2);
        empresaDTO2.setId(2L);
        assertThat(empresaDTO1).isNotEqualTo(empresaDTO2);
        empresaDTO1.setId(null);
        assertThat(empresaDTO1).isNotEqualTo(empresaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(empresaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(empresaMapper.fromId(null)).isNull();
    }
}
