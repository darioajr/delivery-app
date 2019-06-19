package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.service.EmpresaService;
import com.senior.fsw.mboy.web.rest.errors.BadRequestAlertException;
import com.senior.fsw.mboy.service.dto.EmpresaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.senior.fsw.mboy.domain.Empresa}.
 */
@RestController
@RequestMapping("/api")
public class EmpresaResource {

    private final Logger log = LoggerFactory.getLogger(EmpresaResource.class);

    private static final String ENTITY_NAME = "empresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpresaService empresaService;

    public EmpresaResource(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * {@code POST  /empresas} : Create a new empresa.
     *
     * @param empresaDTO the empresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empresaDTO, or with status {@code 400 (Bad Request)} if the empresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empresas")
    public ResponseEntity<EmpresaDTO> createEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) throws URISyntaxException {
        log.debug("REST request to save Empresa : {}", empresaDTO);
        if (empresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new empresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpresaDTO result = empresaService.save(empresaDTO);
        return ResponseEntity.created(new URI("/api/empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empresas} : Updates an existing empresa.
     *
     * @param empresaDTO the empresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresaDTO,
     * or with status {@code 400 (Bad Request)} if the empresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empresas")
    public ResponseEntity<EmpresaDTO> updateEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) throws URISyntaxException {
        log.debug("REST request to update Empresa : {}", empresaDTO);
        if (empresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmpresaDTO result = empresaService.save(empresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empresas} : get all the empresas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empresas in body.
     */
    @GetMapping("/empresas")
    public ResponseEntity<List<EmpresaDTO>> getAllEmpresas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Empresas");
        Page<EmpresaDTO> page = empresaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /empresas/:id} : get the "id" empresa.
     *
     * @param id the id of the empresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empresas/{id}")
    public ResponseEntity<EmpresaDTO> getEmpresa(@PathVariable Long id) {
        log.debug("REST request to get Empresa : {}", id);
        Optional<EmpresaDTO> empresaDTO = empresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empresaDTO);
    }

    /**
     * {@code DELETE  /empresas/:id} : delete the "id" empresa.
     *
     * @param id the id of the empresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empresas/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        log.debug("REST request to delete Empresa : {}", id);
        empresaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
