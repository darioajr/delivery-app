package com.senior.fsw.mboy.service.impl;

import com.senior.fsw.mboy.service.GPSProcessorService;
import com.senior.fsw.mboy.domain.GPSProcessor;
import com.senior.fsw.mboy.repository.GPSProcessorRepository;
import com.senior.fsw.mboy.service.dto.GPSProcessorDTO;
import com.senior.fsw.mboy.service.mapper.GPSProcessorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GPSProcessor}.
 */
@Service
@Transactional
public class GPSProcessorServiceImpl implements GPSProcessorService {

    private final Logger log = LoggerFactory.getLogger(GPSProcessorServiceImpl.class);

    private final GPSProcessorRepository gPSProcessorRepository;

    private final GPSProcessorMapper gPSProcessorMapper;

    public GPSProcessorServiceImpl(GPSProcessorRepository gPSProcessorRepository, GPSProcessorMapper gPSProcessorMapper) {
        this.gPSProcessorRepository = gPSProcessorRepository;
        this.gPSProcessorMapper = gPSProcessorMapper;
    }

    /**
     * Save a gPSProcessor.
     *
     * @param gPSProcessorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GPSProcessorDTO save(GPSProcessorDTO gPSProcessorDTO) {
        log.debug("Request to save GPSProcessor : {}", gPSProcessorDTO);
        GPSProcessor gPSProcessor = gPSProcessorMapper.toEntity(gPSProcessorDTO);
        gPSProcessor = gPSProcessorRepository.save(gPSProcessor);
        return gPSProcessorMapper.toDto(gPSProcessor);
    }

    /**
     * Get all the gPSProcessors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GPSProcessorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GPSProcessors");
        return gPSProcessorRepository.findAll(pageable)
            .map(gPSProcessorMapper::toDto);
    }


    /**
     * Get one gPSProcessor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GPSProcessorDTO> findOne(Long id) {
        log.debug("Request to get GPSProcessor : {}", id);
        return gPSProcessorRepository.findById(id)
            .map(gPSProcessorMapper::toDto);
    }

    /**
     * Delete the gPSProcessor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GPSProcessor : {}", id);
        gPSProcessorRepository.deleteById(id);
    }
}
