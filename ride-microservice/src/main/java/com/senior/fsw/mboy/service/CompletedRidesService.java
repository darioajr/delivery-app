package com.senior.fsw.mboy.service;

import com.senior.fsw.mboy.service.dto.CompletedRidesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.senior.fsw.mboy.domain.CompletedRides}.
 */
public interface CompletedRidesService {

    /**
     * Save a completedRides.
     *
     * @param completedRidesDTO the entity to save.
     * @return the persisted entity.
     */
    CompletedRidesDTO save(CompletedRidesDTO completedRidesDTO);

    /**
     * Get all the completedRides.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompletedRidesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" completedRides.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompletedRidesDTO> findOne(Long id);

    /**
     * Delete the "id" completedRides.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
