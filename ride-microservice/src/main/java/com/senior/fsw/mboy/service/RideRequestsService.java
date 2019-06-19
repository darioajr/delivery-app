package com.senior.fsw.mboy.service;

import com.senior.fsw.mboy.service.dto.RideRequestsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.senior.fsw.mboy.domain.RideRequests}.
 */
public interface RideRequestsService {

    /**
     * Save a rideRequests.
     *
     * @param rideRequestsDTO the entity to save.
     * @return the persisted entity.
     */
    RideRequestsDTO save(RideRequestsDTO rideRequestsDTO);

    /**
     * Get all the rideRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RideRequestsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rideRequests.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RideRequestsDTO> findOne(Long id);

    /**
     * Delete the "id" rideRequests.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
