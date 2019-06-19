package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.service.RideRequestsService;
import com.senior.fsw.mboy.web.rest.errors.BadRequestAlertException;
import com.senior.fsw.mboy.service.dto.RideRequestsDTO;

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
 * REST controller for managing {@link com.senior.fsw.mboy.domain.RideRequests}.
 */
@RestController
@RequestMapping("/api")
public class RideRequestsResource {

    private final Logger log = LoggerFactory.getLogger(RideRequestsResource.class);

    private static final String ENTITY_NAME = "rideRideRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RideRequestsService rideRequestsService;

    public RideRequestsResource(RideRequestsService rideRequestsService) {
        this.rideRequestsService = rideRequestsService;
    }

    /**
     * {@code POST  /ride-requests} : Create a new rideRequests.
     *
     * @param rideRequestsDTO the rideRequestsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rideRequestsDTO, or with status {@code 400 (Bad Request)} if the rideRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ride-requests")
    public ResponseEntity<RideRequestsDTO> createRideRequests(@Valid @RequestBody RideRequestsDTO rideRequestsDTO) throws URISyntaxException {
        log.debug("REST request to save RideRequests : {}", rideRequestsDTO);
        if (rideRequestsDTO.getId() != null) {
            throw new BadRequestAlertException("A new rideRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RideRequestsDTO result = rideRequestsService.save(rideRequestsDTO);
        return ResponseEntity.created(new URI("/api/ride-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ride-requests} : Updates an existing rideRequests.
     *
     * @param rideRequestsDTO the rideRequestsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rideRequestsDTO,
     * or with status {@code 400 (Bad Request)} if the rideRequestsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rideRequestsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ride-requests")
    public ResponseEntity<RideRequestsDTO> updateRideRequests(@Valid @RequestBody RideRequestsDTO rideRequestsDTO) throws URISyntaxException {
        log.debug("REST request to update RideRequests : {}", rideRequestsDTO);
        if (rideRequestsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RideRequestsDTO result = rideRequestsService.save(rideRequestsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rideRequestsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ride-requests} : get all the rideRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rideRequests in body.
     */
    @GetMapping("/ride-requests")
    public ResponseEntity<List<RideRequestsDTO>> getAllRideRequests(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of RideRequests");
        Page<RideRequestsDTO> page = rideRequestsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ride-requests/:id} : get the "id" rideRequests.
     *
     * @param id the id of the rideRequestsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rideRequestsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ride-requests/{id}")
    public ResponseEntity<RideRequestsDTO> getRideRequests(@PathVariable Long id) {
        log.debug("REST request to get RideRequests : {}", id);
        Optional<RideRequestsDTO> rideRequestsDTO = rideRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rideRequestsDTO);
    }

    /**
     * {@code DELETE  /ride-requests/:id} : delete the "id" rideRequests.
     *
     * @param id the id of the rideRequestsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ride-requests/{id}")
    public ResponseEntity<Void> deleteRideRequests(@PathVariable Long id) {
        log.debug("REST request to delete RideRequests : {}", id);
        rideRequestsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
