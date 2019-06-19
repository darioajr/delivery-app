package com.senior.fsw.mboy.repository;

import com.senior.fsw.mboy.domain.RideRequests;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RideRequests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RideRequestsRepository extends JpaRepository<RideRequests, Long> {

}
