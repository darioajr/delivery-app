package com.senior.fsw.mboy.repository;

import com.senior.fsw.mboy.domain.RideConfirmations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RideConfirmations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RideConfirmationsRepository extends JpaRepository<RideConfirmations, Long> {

}
