package com.senior.fsw.mboy.service.mapper;

import com.senior.fsw.mboy.domain.*;
import com.senior.fsw.mboy.service.dto.GPSProcessorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GPSProcessor} and its DTO {@link GPSProcessorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GPSProcessorMapper extends EntityMapper<GPSProcessorDTO, GPSProcessor> {



    default GPSProcessor fromId(Long id) {
        if (id == null) {
            return null;
        }
        GPSProcessor gPSProcessor = new GPSProcessor();
        gPSProcessor.setId(id);
        return gPSProcessor;
    }
}
