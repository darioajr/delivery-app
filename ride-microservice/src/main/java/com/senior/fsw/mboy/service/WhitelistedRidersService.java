package com.senior.fsw.mboy.service;

import com.senior.fsw.mboy.service.dto.WhitelistedRidersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.senior.fsw.mboy.domain.WhitelistedRiders}.
 */
public interface WhitelistedRidersService {

    /**
     * Save a whitelistedRiders.
     *
     * @param whitelistedRidersDTO the entity to save.
     * @return the persisted entity.
     */
    WhitelistedRidersDTO save(WhitelistedRidersDTO whitelistedRidersDTO);

    /**
     * Get all the whitelistedRiders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WhitelistedRidersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" whitelistedRiders.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WhitelistedRidersDTO> findOne(Long id);

    /**
     * Delete the "id" whitelistedRiders.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
