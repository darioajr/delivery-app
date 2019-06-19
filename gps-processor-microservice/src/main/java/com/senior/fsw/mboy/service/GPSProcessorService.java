package com.senior.fsw.mboy.service;

import com.senior.fsw.mboy.service.dto.GPSProcessorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.senior.fsw.mboy.domain.GPSProcessor}.
 */
public interface GPSProcessorService {

    /**
     * Save a gPSProcessor.
     *
     * @param gPSProcessorDTO the entity to save.
     * @return the persisted entity.
     */
    GPSProcessorDTO save(GPSProcessorDTO gPSProcessorDTO);

    /**
     * Get all the gPSProcessors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GPSProcessorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" gPSProcessor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GPSProcessorDTO> findOne(Long id);

    /**
     * Delete the "id" gPSProcessor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
