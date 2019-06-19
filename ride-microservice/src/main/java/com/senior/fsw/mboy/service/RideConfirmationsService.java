package com.senior.fsw.mboy.service;

import com.senior.fsw.mboy.service.dto.RideConfirmationsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.senior.fsw.mboy.domain.RideConfirmations}.
 */
public interface RideConfirmationsService {

    /**
     * Save a rideConfirmations.
     *
     * @param rideConfirmationsDTO the entity to save.
     * @return the persisted entity.
     */
    RideConfirmationsDTO save(RideConfirmationsDTO rideConfirmationsDTO);

    /**
     * Get all the rideConfirmations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RideConfirmationsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rideConfirmations.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RideConfirmationsDTO> findOne(Long id);

    /**
     * Delete the "id" rideConfirmations.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
