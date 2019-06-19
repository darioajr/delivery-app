package com.senior.fsw.mboy.repository;

import com.senior.fsw.mboy.domain.Motoboy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Motoboy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotoboyRepository extends JpaRepository<Motoboy, Long> {

}
