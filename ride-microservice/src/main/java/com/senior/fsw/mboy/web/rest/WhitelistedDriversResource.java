package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.service.WhitelistedDriversService;
import com.senior.fsw.mboy.web.rest.errors.BadRequestAlertException;
import com.senior.fsw.mboy.service.dto.WhitelistedDriversDTO;

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
 * REST controller for managing {@link com.senior.fsw.mboy.domain.WhitelistedDrivers}.
 */
@RestController
@RequestMapping("/api")
public class WhitelistedDriversResource {

    private final Logger log = LoggerFactory.getLogger(WhitelistedDriversResource.class);

    private static final String ENTITY_NAME = "rideWhitelistedDrivers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WhitelistedDriversService whitelistedDriversService;

    public WhitelistedDriversResource(WhitelistedDriversService whitelistedDriversService) {
        this.whitelistedDriversService = whitelistedDriversService;
    }

    /**
     * {@code POST  /whitelisted-drivers} : Create a new whitelistedDrivers.
     *
     * @param whitelistedDriversDTO the whitelistedDriversDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new whitelistedDriversDTO, or with status {@code 400 (Bad Request)} if the whitelistedDrivers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/whitelisted-drivers")
    public ResponseEntity<WhitelistedDriversDTO> createWhitelistedDrivers(@Valid @RequestBody WhitelistedDriversDTO whitelistedDriversDTO) throws URISyntaxException {
        log.debug("REST request to save WhitelistedDrivers : {}", whitelistedDriversDTO);
        if (whitelistedDriversDTO.getId() != null) {
            throw new BadRequestAlertException("A new whitelistedDrivers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WhitelistedDriversDTO result = whitelistedDriversService.save(whitelistedDriversDTO);
        return ResponseEntity.created(new URI("/api/whitelisted-drivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /whitelisted-drivers} : Updates an existing whitelistedDrivers.
     *
     * @param whitelistedDriversDTO the whitelistedDriversDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated whitelistedDriversDTO,
     * or with status {@code 400 (Bad Request)} if the whitelistedDriversDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the whitelistedDriversDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/whitelisted-drivers")
    public ResponseEntity<WhitelistedDriversDTO> updateWhitelistedDrivers(@Valid @RequestBody WhitelistedDriversDTO whitelistedDriversDTO) throws URISyntaxException {
        log.debug("REST request to update WhitelistedDrivers : {}", whitelistedDriversDTO);
        if (whitelistedDriversDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WhitelistedDriversDTO result = whitelistedDriversService.save(whitelistedDriversDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, whitelistedDriversDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /whitelisted-drivers} : get all the whitelistedDrivers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of whitelistedDrivers in body.
     */
    @GetMapping("/whitelisted-drivers")
    public ResponseEntity<List<WhitelistedDriversDTO>> getAllWhitelistedDrivers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of WhitelistedDrivers");
        Page<WhitelistedDriversDTO> page = whitelistedDriversService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /whitelisted-drivers/:id} : get the "id" whitelistedDrivers.
     *
     * @param id the id of the whitelistedDriversDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the whitelistedDriversDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/whitelisted-drivers/{id}")
    public ResponseEntity<WhitelistedDriversDTO> getWhitelistedDrivers(@PathVariable Long id) {
        log.debug("REST request to get WhitelistedDrivers : {}", id);
        Optional<WhitelistedDriversDTO> whitelistedDriversDTO = whitelistedDriversService.findOne(id);
        return ResponseUtil.wrapOrNotFound(whitelistedDriversDTO);
    }

    /**
     * {@code DELETE  /whitelisted-drivers/:id} : delete the "id" whitelistedDrivers.
     *
     * @param id the id of the whitelistedDriversDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/whitelisted-drivers/{id}")
    public ResponseEntity<Void> deleteWhitelistedDrivers(@PathVariable Long id) {
        log.debug("REST request to delete WhitelistedDrivers : {}", id);
        whitelistedDriversService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
