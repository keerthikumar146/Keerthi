package com.test.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.app.domain.LocationMaster;
import com.test.app.repo.LocationMasterRepo;

/**
 * @author balu.s
 *
 */

@Service
public class GisServiceImpl {
	
	@Autowired
	LocationMasterRepo locMasterRepo;

	public LocationMaster getLocdetails(String locId) {
		Optional<LocationMaster> existing = locMasterRepo.findByLocId(locId);
		existing.orElseThrow(() -> new RuntimeException("Location does not exist"));
		LocationMaster locationMaster = new LocationMaster();
		locationMaster.setLocId(existing.get().getLocId());
		locationMaster.setLocName(existing.get().getLocName());
		locationMaster.setParentLocId(existing.get().getParentLocId());
		locationMaster.setSubTypeId(existing.get().getSubTypeId());
		return locationMaster;
	}

	
}
