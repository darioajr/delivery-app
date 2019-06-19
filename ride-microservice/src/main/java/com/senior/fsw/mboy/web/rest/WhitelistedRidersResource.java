package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.service.WhitelistedRidersService;
import com.senior.fsw.mboy.web.rest.errors.BadRequestAlertException;
import com.senior.fsw.mboy.service.dto.WhitelistedRidersDTO;

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
 * REST controller for managing {@link com.senior.fsw.mboy.domain.WhitelistedRiders}.
 */
@RestController
@RequestMapping("/api")
public class WhitelistedRidersResource {

    private final Logger log = LoggerFactory.getLogger(WhitelistedRidersResource.class);

    private static final String ENTITY_NAME = "rideWhitelistedRiders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WhitelistedRidersService whitelistedRidersService;

    public WhitelistedRidersResource(WhitelistedRidersService whitelistedRidersService) {
        this.whitelistedRidersService = whitelistedRidersService;
    }

    /**
     * {@code POST  /whitelisted-riders} : Create a new whitelistedRiders.
     *
     * @param whitelistedRidersDTO the whitelistedRidersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new whitelistedRidersDTO, or with status {@code 400 (Bad Request)} if the whitelistedRiders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/whitelisted-riders")
    public ResponseEntity<WhitelistedRidersDTO> createWhitelistedRiders(@Valid @RequestBody WhitelistedRidersDTO whitelistedRidersDTO) throws URISyntaxException {
        log.debug("REST request to save WhitelistedRiders : {}", whitelistedRidersDTO);
        if (whitelistedRidersDTO.getId() != null) {
            throw new BadRequestAlertException("A new whitelistedRiders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WhitelistedRidersDTO result = whitelistedRidersService.save(whitelistedRidersDTO);
        return ResponseEntity.created(new URI("/api/whitelisted-riders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /whitelisted-riders} : Updates an existing whitelistedRiders.
     *
     * @param whitelistedRidersDTO the whitelistedRidersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated whitelistedRidersDTO,
     * or with status {@code 400 (Bad Request)} if the whitelistedRidersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the whitelistedRidersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/whitelisted-riders")
    public ResponseEntity<WhitelistedRidersDTO> updateWhitelistedRiders(@Valid @RequestBody WhitelistedRidersDTO whitelistedRidersDTO) throws URISyntaxException {
        log.debug("REST request to update WhitelistedRiders : {}", whitelistedRidersDTO);
        if (whitelistedRidersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WhitelistedRidersDTO result = whitelistedRidersService.save(whitelistedRidersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, whitelistedRidersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /whitelisted-riders} : get all the whitelistedRiders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of whitelistedRiders in body.
     */
    @GetMapping("/whitelisted-riders")
    public ResponseEntity<List<WhitelistedRidersDTO>> getAllWhitelistedRiders(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of WhitelistedRiders");
        Page<WhitelistedRidersDTO> page = whitelistedRidersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /whitelisted-riders/:id} : get the "id" whitelistedRiders.
     *
     * @param id the id of the whitelistedRidersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the whitelistedRidersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/whitelisted-riders/{id}")
    public ResponseEntity<WhitelistedRidersDTO> getWhitelistedRiders(@PathVariable Long id) {
        log.debug("REST request to get WhitelistedRiders : {}", id);
        Optional<WhitelistedRidersDTO> whitelistedRidersDTO = whitelistedRidersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(whitelistedRidersDTO);
    }

    /**
     * {@code DELETE  /whitelisted-riders/:id} : delete the "id" whitelistedRiders.
     *
     * @param id the id of the whitelistedRidersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/whitelisted-riders/{id}")
    public ResponseEntity<Void> deleteWhitelistedRiders(@PathVariable Long id) {
        log.debug("REST request to delete WhitelistedRiders : {}", id);
        whitelistedRidersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
