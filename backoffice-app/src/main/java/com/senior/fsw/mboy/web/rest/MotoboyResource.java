package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.service.MotoboyService;
import com.senior.fsw.mboy.web.rest.errors.BadRequestAlertException;
import com.senior.fsw.mboy.service.dto.MotoboyDTO;

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
 * REST controller for managing {@link com.senior.fsw.mboy.domain.Motoboy}.
 */
@RestController
@RequestMapping("/api")
public class MotoboyResource {

    private final Logger log = LoggerFactory.getLogger(MotoboyResource.class);

    private static final String ENTITY_NAME = "motoboy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotoboyService motoboyService;

    public MotoboyResource(MotoboyService motoboyService) {
        this.motoboyService = motoboyService;
    }

    /**
     * {@code POST  /motoboys} : Create a new motoboy.
     *
     * @param motoboyDTO the motoboyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new motoboyDTO, or with status {@code 400 (Bad Request)} if the motoboy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/motoboys")
    public ResponseEntity<MotoboyDTO> createMotoboy(@Valid @RequestBody MotoboyDTO motoboyDTO) throws URISyntaxException {
        log.debug("REST request to save Motoboy : {}", motoboyDTO);
        if (motoboyDTO.getId() != null) {
            throw new BadRequestAlertException("A new motoboy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MotoboyDTO result = motoboyService.save(motoboyDTO);
        return ResponseEntity.created(new URI("/api/motoboys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /motoboys} : Updates an existing motoboy.
     *
     * @param motoboyDTO the motoboyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motoboyDTO,
     * or with status {@code 400 (Bad Request)} if the motoboyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the motoboyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/motoboys")
    public ResponseEntity<MotoboyDTO> updateMotoboy(@Valid @RequestBody MotoboyDTO motoboyDTO) throws URISyntaxException {
        log.debug("REST request to update Motoboy : {}", motoboyDTO);
        if (motoboyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MotoboyDTO result = motoboyService.save(motoboyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, motoboyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /motoboys} : get all the motoboys.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of motoboys in body.
     */
    @GetMapping("/motoboys")
    public ResponseEntity<List<MotoboyDTO>> getAllMotoboys(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Motoboys");
        Page<MotoboyDTO> page = motoboyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /motoboys/:id} : get the "id" motoboy.
     *
     * @param id the id of the motoboyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the motoboyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/motoboys/{id}")
    public ResponseEntity<MotoboyDTO> getMotoboy(@PathVariable Long id) {
        log.debug("REST request to get Motoboy : {}", id);
        Optional<MotoboyDTO> motoboyDTO = motoboyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(motoboyDTO);
    }

    /**
     * {@code DELETE  /motoboys/:id} : delete the "id" motoboy.
     *
     * @param id the id of the motoboyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/motoboys/{id}")
    public ResponseEntity<Void> deleteMotoboy(@PathVariable Long id) {
        log.debug("REST request to delete Motoboy : {}", id);
        motoboyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
