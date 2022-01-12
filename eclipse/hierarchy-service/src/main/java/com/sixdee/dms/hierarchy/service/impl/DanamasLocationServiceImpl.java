package com.sixdee.dms.hierarchy.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sixdee.dms.hierarchy.domain.GeoLocationMaster;
import com.sixdee.dms.hierarchy.dto.DanamasLocationInfo;
import com.sixdee.dms.hierarchy.repository.GeoLocationMasterRepo;
import com.sixdee.dms.hierarchy.utils.StatusCodes;
import com.sixdee.dms.utils.exceptions.CommonException;
import com.sixdee.dms.utils.service.MessageUtil;

/**
 * @author balu.s
 *
 */

@Service
public class DanamasLocationServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DanamasLocationServiceImpl.class);
	
	@Autowired
	GeoLocationMasterRepo locMasterRepo;

	public DanamasLocationInfo getDanamasLocationInfo(String subdistrict) {
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Getting location info for {}", subdistrict);
		}

		DanamasLocationInfo danamasLocationInfo = new DanamasLocationInfo();

		Optional<GeoLocationMaster> subDistrictDetails = locMasterRepo.findByLocationName(subdistrict);
		subDistrictDetails.orElseThrow(() -> new CommonException(MessageUtil.get("dms.gis.locationname.notExist"),
						StatusCodes.NOT_FOUND_ERROR_CODE));
		Optional<GeoLocationMaster> districtDetails = locMasterRepo
				.findByLocationId(subDistrictDetails.get().getParentLocationId());
		districtDetails.orElseThrow(() -> new CommonException(MessageUtil.get("dms.gis.locationid.notExist"),
				StatusCodes.NOT_FOUND_ERROR_CODE));

		danamasLocationInfo.setDistrict(districtDetails.get().getLocationName());

		Optional<GeoLocationMaster> province = locMasterRepo
				.findByLocationId(districtDetails.get().getParentLocationId());

		danamasLocationInfo.setProvince(province.get().getLocationName());

		if (districtDetails.get().getLocationSubTypeId() != null && districtDetails.get().getLocationSubTypeId() > 0
				&& districtDetails.get().getLocationSubType() != null) {
			danamasLocationInfo.setSubType(districtDetails.get().getLocationSubType().getName());
		}

		return danamasLocationInfo;
	}

	
}
