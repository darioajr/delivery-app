package com.senior.fsw.mboy.service.mapper;

import com.senior.fsw.mboy.domain.*;
import com.senior.fsw.mboy.service.dto.CompletedRidesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompletedRides} and its DTO {@link CompletedRidesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompletedRidesMapper extends EntityMapper<CompletedRidesDTO, CompletedRides> {



    default CompletedRides fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompletedRides completedRides = new CompletedRides();
        completedRides.setId(id);
        return completedRides;
    }
}
