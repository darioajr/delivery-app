package com.senior.fsw.mboy.service.impl;

import com.senior.fsw.mboy.service.WhitelistedRidersService;
import com.senior.fsw.mboy.domain.WhitelistedRiders;
import com.senior.fsw.mboy.repository.WhitelistedRidersRepository;
import com.senior.fsw.mboy.service.dto.WhitelistedRidersDTO;
import com.senior.fsw.mboy.service.mapper.WhitelistedRidersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WhitelistedRiders}.
 */
@Service
@Transactional
public class WhitelistedRidersServiceImpl implements WhitelistedRidersService {

    private final Logger log = LoggerFactory.getLogger(WhitelistedRidersServiceImpl.class);

    private final WhitelistedRidersRepository whitelistedRidersRepository;

    private final WhitelistedRidersMapper whitelistedRidersMapper;

    public WhitelistedRidersServiceImpl(WhitelistedRidersRepository whitelistedRidersRepository, WhitelistedRidersMapper whitelistedRidersMapper) {
        this.whitelistedRidersRepository = whitelistedRidersRepository;
        this.whitelistedRidersMapper = whitelistedRidersMapper;
    }

    /**
     * Save a whitelistedRiders.
     *
     * @param whitelistedRidersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WhitelistedRidersDTO save(WhitelistedRidersDTO whitelistedRidersDTO) {
        log.debug("Request to save WhitelistedRiders : {}", whitelistedRidersDTO);
        WhitelistedRiders whitelistedRiders = whitelistedRidersMapper.toEntity(whitelistedRidersDTO);
        whitelistedRiders = whitelistedRidersRepository.save(whitelistedRiders);
        return whitelistedRidersMapper.toDto(whitelistedRiders);
    }

    /**
     * Get all the whitelistedRiders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WhitelistedRidersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WhitelistedRiders");
        return whitelistedRidersRepository.findAll(pageable)
            .map(whitelistedRidersMapper::toDto);
    }


    /**
     * Get one whitelistedRiders by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WhitelistedRidersDTO> findOne(Long id) {
        log.debug("Request to get WhitelistedRiders : {}", id);
        return whitelistedRidersRepository.findById(id)
            .map(whitelistedRidersMapper::toDto);
    }

    /**
     * Delete the whitelistedRiders by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WhitelistedRiders : {}", id);
        whitelistedRidersRepository.deleteById(id);
    }
}
