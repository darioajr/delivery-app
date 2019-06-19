package com.senior.fsw.mboy.service.mapper;

import com.senior.fsw.mboy.domain.*;
import com.senior.fsw.mboy.service.dto.RideConfirmationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RideConfirmations} and its DTO {@link RideConfirmationsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RideConfirmationsMapper extends EntityMapper<RideConfirmationsDTO, RideConfirmations> {



    default RideConfirmations fromId(Long id) {
        if (id == null) {
            return null;
        }
        RideConfirmations rideConfirmations = new RideConfirmations();
        rideConfirmations.setId(id);
        return rideConfirmations;
    }
}
