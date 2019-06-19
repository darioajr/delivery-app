package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.service.RideConfirmationsService;
import com.senior.fsw.mboy.web.rest.errors.BadRequestAlertException;
import com.senior.fsw.mboy.service.dto.RideConfirmationsDTO;

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
 * REST controller for managing {@link com.senior.fsw.mboy.domain.RideConfirmations}.
 */
@RestController
@RequestMapping("/api")
public class RideConfirmationsResource {

    private final Logger log = LoggerFactory.getLogger(RideConfirmationsResource.class);

    private static final String ENTITY_NAME = "rideRideConfirmations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RideConfirmationsService rideConfirmationsService;

    public RideConfirmationsResource(RideConfirmationsService rideConfirmationsService) {
        this.rideConfirmationsService = rideConfirmationsService;
    }

    /**
     * {@code POST  /ride-confirmations} : Create a new rideConfirmations.
     *
     * @param rideConfirmationsDTO the rideConfirmationsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rideConfirmationsDTO, or with status {@code 400 (Bad Request)} if the rideConfirmations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ride-confirmations")
    public ResponseEntity<RideConfirmationsDTO> createRideConfirmations(@Valid @RequestBody RideConfirmationsDTO rideConfirmationsDTO) throws URISyntaxException {
        log.debug("REST request to save RideConfirmations : {}", rideConfirmationsDTO);
        if (rideConfirmationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new rideConfirmations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RideConfirmationsDTO result = rideConfirmationsService.save(rideConfirmationsDTO);
        return ResponseEntity.created(new URI("/api/ride-confirmations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ride-confirmations} : Updates an existing rideConfirmations.
     *
     * @param rideConfirmationsDTO the rideConfirmationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rideConfirmationsDTO,
     * or with status {@code 400 (Bad Request)} if the rideConfirmationsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rideConfirmationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ride-confirmations")
    public ResponseEntity<RideConfirmationsDTO> updateRideConfirmations(@Valid @RequestBody RideConfirmationsDTO rideConfirmationsDTO) throws URISyntaxException {
        log.debug("REST request to update RideConfirmations : {}", rideConfirmationsDTO);
        if (rideConfirmationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RideConfirmationsDTO result = rideConfirmationsService.save(rideConfirmationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rideConfirmationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ride-confirmations} : get all the rideConfirmations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rideConfirmations in body.
     */
    @GetMapping("/ride-confirmations")
    public ResponseEntity<List<RideConfirmationsDTO>> getAllRideConfirmations(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of RideConfirmations");
        Page<RideConfirmationsDTO> page = rideConfirmationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ride-confirmations/:id} : get the "id" rideConfirmations.
     *
     * @param id the id of the rideConfirmationsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rideConfirmationsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ride-confirmations/{id}")
    public ResponseEntity<RideConfirmationsDTO> getRideConfirmations(@PathVariable Long id) {
        log.debug("REST request to get RideConfirmations : {}", id);
        Optional<RideConfirmationsDTO> rideConfirmationsDTO = rideConfirmationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rideConfirmationsDTO);
    }

    /**
     * {@code DELETE  /ride-confirmations/:id} : delete the "id" rideConfirmations.
     *
     * @param id the id of the rideConfirmationsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ride-confirmations/{id}")
    public ResponseEntity<Void> deleteRideConfirmations(@PathVariable Long id) {
        log.debug("REST request to delete RideConfirmations : {}", id);
        rideConfirmationsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
