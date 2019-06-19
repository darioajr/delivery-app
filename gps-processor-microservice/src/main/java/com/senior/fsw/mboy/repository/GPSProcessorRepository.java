package com.senior.fsw.mboy.repository;

import com.senior.fsw.mboy.domain.GPSProcessor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GPSProcessor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GPSProcessorRepository extends JpaRepository<GPSProcessor, Long> {

}
