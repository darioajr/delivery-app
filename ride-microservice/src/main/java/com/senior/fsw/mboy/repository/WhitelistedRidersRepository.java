package com.senior.fsw.mboy.repository;

import com.senior.fsw.mboy.domain.WhitelistedRiders;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WhitelistedRiders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WhitelistedRidersRepository extends JpaRepository<WhitelistedRiders, Long> {

}
