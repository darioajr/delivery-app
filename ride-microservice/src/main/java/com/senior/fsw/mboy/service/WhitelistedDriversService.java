package com.senior.fsw.mboy.service;

import com.senior.fsw.mboy.service.dto.WhitelistedDriversDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.senior.fsw.mboy.domain.WhitelistedDrivers}.
 */
public interface WhitelistedDriversService {

    /**
     * Save a whitelistedDrivers.
     *
     * @param whitelistedDriversDTO the entity to save.
     * @return the persisted entity.
     */
    WhitelistedDriversDTO save(WhitelistedDriversDTO whitelistedDriversDTO);

    /**
     * Get all the whitelistedDrivers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WhitelistedDriversDTO> findAll(Pageable pageable);


    /**
     * Get the "id" whitelistedDrivers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WhitelistedDriversDTO> findOne(Long id);

    /**
     * Delete the "id" whitelistedDrivers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
