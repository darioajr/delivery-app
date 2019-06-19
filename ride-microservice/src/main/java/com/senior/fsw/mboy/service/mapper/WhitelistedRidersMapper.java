package com.senior.fsw.mboy.service.mapper;

import com.senior.fsw.mboy.domain.*;
import com.senior.fsw.mboy.service.dto.WhitelistedRidersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WhitelistedRiders} and its DTO {@link WhitelistedRidersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WhitelistedRidersMapper extends EntityMapper<WhitelistedRidersDTO, WhitelistedRiders> {



    default WhitelistedRiders fromId(Long id) {
        if (id == null) {
            return null;
        }
        WhitelistedRiders whitelistedRiders = new WhitelistedRiders();
        whitelistedRiders.setId(id);
        return whitelistedRiders;
    }
}
