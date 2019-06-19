package com.senior.fsw.mboy.service.mapper;

import com.senior.fsw.mboy.domain.*;
import com.senior.fsw.mboy.service.dto.CidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cidade} and its DTO {@link CidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CidadeMapper extends EntityMapper<CidadeDTO, Cidade> {


    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "motoboy", ignore = true)
    Cidade toEntity(CidadeDTO cidadeDTO);

    default Cidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cidade cidade = new Cidade();
        cidade.setId(id);
        return cidade;
    }
}
