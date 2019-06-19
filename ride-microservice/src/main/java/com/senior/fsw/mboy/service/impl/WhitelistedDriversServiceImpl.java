package com.senior.fsw.mboy.service.impl;

import com.senior.fsw.mboy.service.WhitelistedDriversService;
import com.senior.fsw.mboy.domain.WhitelistedDrivers;
import com.senior.fsw.mboy.repository.WhitelistedDriversRepository;
import com.senior.fsw.mboy.service.dto.WhitelistedDriversDTO;
import com.senior.fsw.mboy.service.mapper.WhitelistedDriversMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WhitelistedDrivers}.
 */
@Service
@Transactional
public class WhitelistedDriversServiceImpl implements WhitelistedDriversService {

    private final Logger log = LoggerFactory.getLogger(WhitelistedDriversServiceImpl.class);

    private final WhitelistedDriversRepository whitelistedDriversRepository;

    private final WhitelistedDriversMapper whitelistedDriversMapper;

    public WhitelistedDriversServiceImpl(WhitelistedDriversRepository whitelistedDriversRepository, WhitelistedDriversMapper whitelistedDriversMapper) {
        this.whitelistedDriversRepository = whitelistedDriversRepository;
        this.whitelistedDriversMapper = whitelistedDriversMapper;
    }

    /**
     * Save a whitelistedDrivers.
     *
     * @param whitelistedDriversDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WhitelistedDriversDTO save(WhitelistedDriversDTO whitelistedDriversDTO) {
        log.debug("Request to save WhitelistedDrivers : {}", whitelistedDriversDTO);
        WhitelistedDrivers whitelistedDrivers = whitelistedDriversMapper.toEntity(whitelistedDriversDTO);
        whitelistedDrivers = whitelistedDriversRepository.save(whitelistedDrivers);
        return whitelistedDriversMapper.toDto(whitelistedDrivers);
    }

    /**
     * Get all the whitelistedDrivers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WhitelistedDriversDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WhitelistedDrivers");
        return whitelistedDriversRepository.findAll(pageable)
            .map(whitelistedDriversMapper::toDto);
    }


    /**
     * Get one whitelistedDrivers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WhitelistedDriversDTO> findOne(Long id) {
        log.debug("Request to get WhitelistedDrivers : {}", id);
        return whitelistedDriversRepository.findById(id)
            .map(whitelistedDriversMapper::toDto);
    }

    /**
     * Delete the whitelistedDrivers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WhitelistedDrivers : {}", id);
        whitelistedDriversRepository.deleteById(id);
    }
}
