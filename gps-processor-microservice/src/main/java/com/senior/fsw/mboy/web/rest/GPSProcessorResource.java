package com.senior.fsw.mboy.web.rest;

import com.senior.fsw.mboy.service.GPSProcessorService;
import com.senior.fsw.mboy.web.rest.errors.BadRequestAlertException;
import com.senior.fsw.mboy.service.dto.GPSProcessorDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.senior.fsw.mboy.domain.GPSProcessor}.
 */
@RestController
@RequestMapping("/api")
public class GPSProcessorResource {

    private final Logger log = LoggerFactory.getLogger(GPSProcessorResource.class);

    private static final String ENTITY_NAME = "gpsprocessorGpsProcessor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GPSProcessorService gPSProcessorService;

    public GPSProcessorResource(GPSProcessorService gPSProcessorService) {
        this.gPSProcessorService = gPSProcessorService;
    }

    /**
     * {@code POST  /gps-processors} : Create a new gPSProcessor.
     *
     * @param gPSProcessorDTO the gPSProcessorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gPSProcessorDTO, or with status {@code 400 (Bad Request)} if the gPSProcessor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gps")
    public ResponseEntity<GPSProcessorDTO> createGPSProcessor(@RequestBody GPSProcessorDTO gPSProcessorDTO) throws URISyntaxException {
        log.debug("REST request to save GPSProcessor : {}", gPSProcessorDTO);
        if (gPSProcessorDTO.getId() != null) {
            throw new BadRequestAlertException("A new gPSProcessor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GPSProcessorDTO result = gPSProcessorService.save(gPSProcessorDTO);
        return ResponseEntity.created(new URI("/api/gps-processors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gps-processors} : Updates an existing gPSProcessor.
     *
     * @param gPSProcessorDTO the gPSProcessorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gPSProcessorDTO,
     * or with status {@code 400 (Bad Request)} if the gPSProcessorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gPSProcessorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gps")
    public ResponseEntity<GPSProcessorDTO> updateGPSProcessor(@RequestBody GPSProcessorDTO gPSProcessorDTO) throws URISyntaxException {
        log.debug("REST request to update GPSProcessor : {}", gPSProcessorDTO);
        if (gPSProcessorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GPSProcessorDTO result = gPSProcessorService.save(gPSProcessorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gPSProcessorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gps-processors} : get all the gPSProcessors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gPSProcessors in body.
     */
    @GetMapping("/gps")
    public ResponseEntity<List<GPSProcessorDTO>> getAllGPSProcessors(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of GPSProcessors");
        Page<GPSProcessorDTO> page = gPSProcessorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gps-processors/:id} : get the "id" gPSProcessor.
     *
     * @param id the id of the gPSProcessorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gPSProcessorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gps/{id}")
    public ResponseEntity<GPSProcessorDTO> getGPSProcessor(@PathVariable Long id) {
        log.debug("REST request to get GPSProcessor : {}", id);
        Optional<GPSProcessorDTO> gPSProcessorDTO = gPSProcessorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gPSProcessorDTO);
    }

    /**
     * {@code DELETE  /gps-processors/:id} : delete the "id" gPSProcessor.
     *
     * @param id the id of the gPSProcessorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gps/{id}")
    public ResponseEntity<Void> deleteGPSProcessor(@PathVariable Long id) {
        log.debug("REST request to delete GPSProcessor : {}", id);
        gPSProcessorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
