package com.senior.fsw.mboy.service.mapper;

import com.senior.fsw.mboy.domain.*;
import com.senior.fsw.mboy.service.dto.EmpresaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring", uses = {CidadeMapper.class})
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {

    @Mapping(source = "cidade.id", target = "cidadeId")
    EmpresaDTO toDto(Empresa empresa);

    @Mapping(source = "cidadeId", target = "cidade")
    Empresa toEntity(EmpresaDTO empresaDTO);

    default Empresa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }
}
