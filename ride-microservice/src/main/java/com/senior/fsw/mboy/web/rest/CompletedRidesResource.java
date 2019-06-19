package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.service.CompletedRidesService;
import com.senior.fsw.mboy.web.rest.errors.BadRequestAlertException;
import com.senior.fsw.mboy.service.dto.CompletedRidesDTO;

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
 * REST controller for managing {@link com.senior.fsw.mboy.domain.CompletedRides}.
 */
@RestController
@RequestMapping("/api")
public class CompletedRidesResource {

    private final Logger log = LoggerFactory.getLogger(CompletedRidesResource.class);

    private static final String ENTITY_NAME = "rideCompletedRides";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompletedRidesService completedRidesService;

    public CompletedRidesResource(CompletedRidesService completedRidesService) {
        this.completedRidesService = completedRidesService;
    }

    /**
     * {@code POST  /completed-rides} : Create a new completedRides.
     *
     * @param completedRidesDTO the completedRidesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new completedRidesDTO, or with status {@code 400 (Bad Request)} if the completedRides has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/completed-rides")
    public ResponseEntity<CompletedRidesDTO> createCompletedRides(@Valid @RequestBody CompletedRidesDTO completedRidesDTO) throws URISyntaxException {
        log.debug("REST request to save CompletedRides : {}", completedRidesDTO);
        if (completedRidesDTO.getId() != null) {
            throw new BadRequestAlertException("A new completedRides cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompletedRidesDTO result = completedRidesService.save(completedRidesDTO);
        return ResponseEntity.created(new URI("/api/completed-rides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /completed-rides} : Updates an existing completedRides.
     *
     * @param completedRidesDTO the completedRidesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated completedRidesDTO,
     * or with status {@code 400 (Bad Request)} if the completedRidesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the completedRidesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/completed-rides")
    public ResponseEntity<CompletedRidesDTO> updateCompletedRides(@Valid @RequestBody CompletedRidesDTO completedRidesDTO) throws URISyntaxException {
        log.debug("REST request to update CompletedRides : {}", completedRidesDTO);
        if (completedRidesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompletedRidesDTO result = completedRidesService.save(completedRidesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, completedRidesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /completed-rides} : get all the completedRides.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of completedRides in body.
     */
    @GetMapping("/completed-rides")
    public ResponseEntity<List<CompletedRidesDTO>> getAllCompletedRides(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of CompletedRides");
        Page<CompletedRidesDTO> page = completedRidesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /completed-rides/:id} : get the "id" completedRides.
     *
     * @param id the id of the completedRidesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the completedRidesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/completed-rides/{id}")
    public ResponseEntity<CompletedRidesDTO> getCompletedRides(@PathVariable Long id) {
        log.debug("REST request to get CompletedRides : {}", id);
        Optional<CompletedRidesDTO> completedRidesDTO = completedRidesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(completedRidesDTO);
    }

    /**
     * {@code DELETE  /completed-rides/:id} : delete the "id" completedRides.
     *
     * @param id the id of the completedRidesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/completed-rides/{id}")
    public ResponseEntity<Void> deleteCompletedRides(@PathVariable Long id) {
        log.debug("REST request to delete CompletedRides : {}", id);
        completedRidesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
