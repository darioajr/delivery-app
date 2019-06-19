package com.senior.fsw.mboy.service.impl;

import com.senior.fsw.mboy.service.MotoboyService;
import com.senior.fsw.mboy.domain.Motoboy;
import com.senior.fsw.mboy.repository.MotoboyRepository;
import com.senior.fsw.mboy.service.dto.MotoboyDTO;
import com.senior.fsw.mboy.service.mapper.MotoboyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Motoboy}.
 */
@Service
@Transactional
public class MotoboyServiceImpl implements MotoboyService {

    private final Logger log = LoggerFactory.getLogger(MotoboyServiceImpl.class);

    private final MotoboyRepository motoboyRepository;

    private final MotoboyMapper motoboyMapper;

    public MotoboyServiceImpl(MotoboyRepository motoboyRepository, MotoboyMapper motoboyMapper) {
        this.motoboyRepository = motoboyRepository;
        this.motoboyMapper = motoboyMapper;
    }

    /**
     * Save a motoboy.
     *
     * @param motoboyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MotoboyDTO save(MotoboyDTO motoboyDTO) {
        log.debug("Request to save Motoboy : {}", motoboyDTO);
        Motoboy motoboy = motoboyMapper.toEntity(motoboyDTO);
        motoboy = motoboyRepository.save(motoboy);
        return motoboyMapper.toDto(motoboy);
    }

    /**
     * Get all the motoboys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MotoboyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Motoboys");
        return motoboyRepository.findAll(pageable)
            .map(motoboyMapper::toDto);
    }


    /**
     * Get one motoboy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MotoboyDTO> findOne(Long id) {
        log.debug("Request to get Motoboy : {}", id);
        return motoboyRepository.findById(id)
            .map(motoboyMapper::toDto);
    }

    /**
     * Delete the motoboy by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Motoboy : {}", id);
        motoboyRepository.deleteById(id);
    }
}
