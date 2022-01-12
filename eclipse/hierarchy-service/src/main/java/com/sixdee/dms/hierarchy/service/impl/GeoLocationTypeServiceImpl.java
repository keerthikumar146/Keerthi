package com.sixdee.dms.hierarchy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sixdee.dms.hierarchy.domain.GeoLocationSubType;
import com.sixdee.dms.hierarchy.domain.GeoLocationType;
import com.sixdee.dms.hierarchy.repository.GeoLocationSubTypeRepo;
import com.sixdee.dms.hierarchy.repository.GeoLocationTypeRepo;

/**
 * @author balu.s
 *
 */

@Service
public class GeoLocationTypeServiceImpl {

	@Autowired
	GeoLocationSubTypeRepo subTypeRepo;
	
	@Autowired
	GeoLocationTypeRepo locTypeRepo;
	
	public List<GeoLocationSubType> getAllSubTypes() {
		return subTypeRepo.findAll();
	}
	
	public List<GeoLocationType> getAllLocationTypes() {
		return locTypeRepo.findAll();
	}
	
}
