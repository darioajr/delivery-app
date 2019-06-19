package com.senior.fsw.mboy.service.mapper;

import com.senior.fsw.mboy.domain.*;
import com.senior.fsw.mboy.service.dto.WhitelistedDriversDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WhitelistedDrivers} and its DTO {@link WhitelistedDriversDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WhitelistedDriversMapper extends EntityMapper<WhitelistedDriversDTO, WhitelistedDrivers> {



    default WhitelistedDrivers fromId(Long id) {
        if (id == null) {
            return null;
        }
        WhitelistedDrivers whitelistedDrivers = new WhitelistedDrivers();
        whitelistedDrivers.setId(id);
        return whitelistedDrivers;
    }
}
