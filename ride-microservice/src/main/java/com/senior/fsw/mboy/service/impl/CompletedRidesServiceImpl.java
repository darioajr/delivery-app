package com.senior.fsw.mboy.service.impl;

import com.senior.fsw.mboy.service.CompletedRidesService;
import com.senior.fsw.mboy.domain.CompletedRides;
import com.senior.fsw.mboy.repository.CompletedRidesRepository;
import com.senior.fsw.mboy.service.dto.CompletedRidesDTO;
import com.senior.fsw.mboy.service.mapper.CompletedRidesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompletedRides}.
 */
@Service
@Transactional
public class CompletedRidesServiceImpl implements CompletedRidesService {

    private final Logger log = LoggerFactory.getLogger(CompletedRidesServiceImpl.class);

    private final CompletedRidesRepository completedRidesRepository;

    private final CompletedRidesMapper completedRidesMapper;

    public CompletedRidesServiceImpl(CompletedRidesRepository completedRidesRepository, CompletedRidesMapper completedRidesMapper) {
        this.completedRidesRepository = completedRidesRepository;
        this.completedRidesMapper = completedRidesMapper;
    }

    /**
     * Save a completedRides.
     *
     * @param completedRidesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompletedRidesDTO save(CompletedRidesDTO completedRidesDTO) {
        log.debug("Request to save CompletedRides : {}", completedRidesDTO);
        CompletedRides completedRides = completedRidesMapper.toEntity(completedRidesDTO);
        completedRides = completedRidesRepository.save(completedRides);
        return completedRidesMapper.toDto(completedRides);
    }

    /**
     * Get all the completedRides.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompletedRidesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompletedRides");
        return completedRidesRepository.findAll(pageable)
            .map(completedRidesMapper::toDto);
    }


    /**
     * Get one completedRides by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompletedRidesDTO> findOne(Long id) {
        log.debug("Request to get CompletedRides : {}", id);
        return completedRidesRepository.findById(id)
            .map(completedRidesMapper::toDto);
    }

    /**
     * Delete the completedRides by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompletedRides : {}", id);
        completedRidesRepository.deleteById(id);
    }
}
