package com.senior.fsw.mboy.repository;

import com.senior.fsw.mboy.domain.CompletedRides;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompletedRides entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompletedRidesRepository extends JpaRepository<CompletedRides, Long> {

}
