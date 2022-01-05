package com.test.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.app.domain.LocationMaster;

/**
 * @author balu.s
 *
 */

public interface LocationMasterRepo extends JpaRepository<LocationMaster, String>{

	Optional<LocationMaster> findByLocId(String locId);

}
