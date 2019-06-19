package com.senior.fsw.mboy.repository;

import com.senior.fsw.mboy.domain.WhitelistedDrivers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WhitelistedDrivers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WhitelistedDriversRepository extends JpaRepository<WhitelistedDrivers, Long> {

}
