package com.senior.fsw.mboy.service.mapper;

import com.senior.fsw.mboy.domain.*;
import com.senior.fsw.mboy.service.dto.MotoboyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Motoboy} and its DTO {@link MotoboyDTO}.
 */
@Mapper(componentModel = "spring", uses = {CidadeMapper.class})
public interface MotoboyMapper extends EntityMapper<MotoboyDTO, Motoboy> {

    @Mapping(source = "cidade.id", target = "cidadeId")
    MotoboyDTO toDto(Motoboy motoboy);

    @Mapping(source = "cidadeId", target = "cidade")
    Motoboy toEntity(MotoboyDTO motoboyDTO);

    default Motoboy fromId(Long id) {
        if (id == null) {
            return null;
        }
        Motoboy motoboy = new Motoboy();
        motoboy.setId(id);
        return motoboy;
    }
}
