package com.senior.fsw.mboy.service.impl;

import com.senior.fsw.mboy.service.CidadeService;
import com.senior.fsw.mboy.domain.Cidade;
import com.senior.fsw.mboy.repository.CidadeRepository;
import com.senior.fsw.mboy.service.dto.CidadeDTO;
import com.senior.fsw.mboy.service.mapper.CidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Cidade}.
 */
@Service
@Transactional
public class CidadeServiceImpl implements CidadeService {

    private final Logger log = LoggerFactory.getLogger(CidadeServiceImpl.class);

    private final CidadeRepository cidadeRepository;

    private final CidadeMapper cidadeMapper;

    public CidadeServiceImpl(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeMapper = cidadeMapper;
    }

    /**
     * Save a cidade.
     *
     * @param cidadeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CidadeDTO save(CidadeDTO cidadeDTO) {
        log.debug("Request to save Cidade : {}", cidadeDTO);
        Cidade cidade = cidadeMapper.toEntity(cidadeDTO);
        cidade = cidadeRepository.save(cidade);
        return cidadeMapper.toDto(cidade);
    }

    /**
     * Get all the cidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cidades");
        return cidadeRepository.findAll(pageable)
            .map(cidadeMapper::toDto);
    }



    /**
    *  Get all the cidades where Empresa is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CidadeDTO> findAllWhereEmpresaIsNull() {
        log.debug("Request to get all cidades where Empresa is null");
        return StreamSupport
            .stream(cidadeRepository.findAll().spliterator(), false)
            .filter(cidade -> cidade.getEmpresa() == null)
            .map(cidadeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the cidades where Motoboy is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CidadeDTO> findAllWhereMotoboyIsNull() {
        log.debug("Request to get all cidades where Motoboy is null");
        return StreamSupport
            .stream(cidadeRepository.findAll().spliterator(), false)
            .filter(cidade -> cidade.getMotoboy() == null)
            .map(cidadeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CidadeDTO> findOne(Long id) {
        log.debug("Request to get Cidade : {}", id);
        return cidadeRepository.findById(id)
            .map(cidadeMapper::toDto);
    }

    /**
     * Delete the cidade by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cidade : {}", id);
        cidadeRepository.deleteById(id);
    }
}
