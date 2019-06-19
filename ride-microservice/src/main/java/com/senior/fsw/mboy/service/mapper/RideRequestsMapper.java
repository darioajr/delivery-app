package com.senior.fsw.mboy.service.mapper;

import com.senior.fsw.mboy.domain.*;
import com.senior.fsw.mboy.service.dto.RideRequestsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RideRequests} and its DTO {@link RideRequestsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RideRequestsMapper extends EntityMapper<RideRequestsDTO, RideRequests> {



    default RideRequests fromId(Long id) {
        if (id == null) {
            return null;
        }
        RideRequests rideRequests = new RideRequests();
        rideRequests.setId(id);
        return rideRequests;
    }
}
