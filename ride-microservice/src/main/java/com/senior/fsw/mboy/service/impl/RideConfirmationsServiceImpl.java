package com.senior.fsw.mboy.service.impl;

import com.senior.fsw.mboy.service.RideConfirmationsService;
import com.senior.fsw.mboy.domain.RideConfirmations;
import com.senior.fsw.mboy.repository.RideConfirmationsRepository;
import com.senior.fsw.mboy.service.dto.RideConfirmationsDTO;
import com.senior.fsw.mboy.service.mapper.RideConfirmationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RideConfirmations}.
 */
@Service
@Transactional
public class RideConfirmationsServiceImpl implements RideConfirmationsService {

    private final Logger log = LoggerFactory.getLogger(RideConfirmationsServiceImpl.class);

    private final RideConfirmationsRepository rideConfirmationsRepository;

    private final RideConfirmationsMapper rideConfirmationsMapper;

    public RideConfirmationsServiceImpl(RideConfirmationsRepository rideConfirmationsRepository, RideConfirmationsMapper rideConfirmationsMapper) {
        this.rideConfirmationsRepository = rideConfirmationsRepository;
        this.rideConfirmationsMapper = rideConfirmationsMapper;
    }

    /**
     * Save a rideConfirmations.
     *
     * @param rideConfirmationsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RideConfirmationsDTO save(RideConfirmationsDTO rideConfirmationsDTO) {
        log.debug("Request to save RideConfirmations : {}", rideConfirmationsDTO);
        RideConfirmations rideConfirmations = rideConfirmationsMapper.toEntity(rideConfirmationsDTO);
        rideConfirmations = rideConfirmationsRepository.save(rideConfirmations);
        return rideConfirmationsMapper.toDto(rideConfirmations);
    }

    /**
     * Get all the rideConfirmations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RideConfirmationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RideConfirmations");
        return rideConfirmationsRepository.findAll(pageable)
            .map(rideConfirmationsMapper::toDto);
    }


    /**
     * Get one rideConfirmations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RideConfirmationsDTO> findOne(Long id) {
        log.debug("Request to get RideConfirmations : {}", id);
        return rideConfirmationsRepository.findById(id)
            .map(rideConfirmationsMapper::toDto);
    }

    /**
     * Delete the rideConfirmations by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RideConfirmations : {}", id);
        rideConfirmationsRepository.deleteById(id);
    }
}
