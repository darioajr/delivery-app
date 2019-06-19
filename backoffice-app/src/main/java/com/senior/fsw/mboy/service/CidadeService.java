package com.senior.fsw.mboy.service;

import com.senior.fsw.mboy.service.dto.CidadeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.senior.fsw.mboy.domain.Cidade}.
 */
public interface CidadeService {

    /**
     * Save a cidade.
     *
     * @param cidadeDTO the entity to save.
     * @return the persisted entity.
     */
    CidadeDTO save(CidadeDTO cidadeDTO);

    /**
     * Get all the cidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CidadeDTO> findAll(Pageable pageable);
    /**
     * Get all the CidadeDTO where Empresa is {@code null}.
     *
     * @return the list of entities.
     */
    List<CidadeDTO> findAllWhereEmpresaIsNull();
    /**
     * Get all the CidadeDTO where Motoboy is {@code null}.
     *
     * @return the list of entities.
     */
    List<CidadeDTO> findAllWhereMotoboyIsNull();


    /**
     * Get the "id" cidade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CidadeDTO> findOne(Long id);

    /**
     * Delete the "id" cidade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
