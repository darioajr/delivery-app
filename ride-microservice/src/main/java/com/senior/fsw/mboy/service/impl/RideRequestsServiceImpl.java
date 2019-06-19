package com.senior.fsw.mboy.service.impl;

import com.senior.fsw.mboy.service.RideRequestsService;
import com.senior.fsw.mboy.domain.RideRequests;
import com.senior.fsw.mboy.repository.RideRequestsRepository;
import com.senior.fsw.mboy.service.dto.RideRequestsDTO;
import com.senior.fsw.mboy.service.mapper.RideRequestsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RideRequests}.
 */
@Service
@Transactional
public class RideRequestsServiceImpl implements RideRequestsService {

    private final Logger log = LoggerFactory.getLogger(RideRequestsServiceImpl.class);

    private final RideRequestsRepository rideRequestsRepository;

    private final RideRequestsMapper rideRequestsMapper;

    public RideRequestsServiceImpl(RideRequestsRepository rideRequestsRepository, RideRequestsMapper rideRequestsMapper) {
        this.rideRequestsRepository = rideRequestsRepository;
        this.rideRequestsMapper = rideRequestsMapper;
    }

    /**
     * Save a rideRequests.
     *
     * @param rideRequestsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RideRequestsDTO save(RideRequestsDTO rideRequestsDTO) {
        log.debug("Request to save RideRequests : {}", rideRequestsDTO);
        RideRequests rideRequests = rideRequestsMapper.toEntity(rideRequestsDTO);
        rideRequests = rideRequestsRepository.save(rideRequests);
        return rideRequestsMapper.toDto(rideRequests);
    }

    /**
     * Get all the rideRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RideRequestsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RideRequests");
        return rideRequestsRepository.findAll(pageable)
            .map(rideRequestsMapper::toDto);
    }


    /**
     * Get one rideRequests by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RideRequestsDTO> findOne(Long id) {
        log.debug("Request to get RideRequests : {}", id);
        return rideRequestsRepository.findById(id)
            .map(rideRequestsMapper::toDto);
    }

    /**
     * Delete the rideRequests by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RideRequests : {}", id);
        rideRequestsRepository.deleteById(id);
    }
}
