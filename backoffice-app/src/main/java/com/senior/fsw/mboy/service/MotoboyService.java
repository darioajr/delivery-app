package com.senior.fsw.mboy.service;

import com.senior.fsw.mboy.service.dto.MotoboyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.senior.fsw.mboy.domain.Motoboy}.
 */
public interface MotoboyService {

    /**
     * Save a motoboy.
     *
     * @param motoboyDTO the entity to save.
     * @return the persisted entity.
     */
    MotoboyDTO save(MotoboyDTO motoboyDTO);

    /**
     * Get all the motoboys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MotoboyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" motoboy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MotoboyDTO> findOne(Long id);

    /**
     * Delete the "id" motoboy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
