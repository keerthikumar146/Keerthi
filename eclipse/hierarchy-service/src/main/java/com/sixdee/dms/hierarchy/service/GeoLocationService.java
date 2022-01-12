package com.sixdee.dms.hierarchy.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sixdee.dms.hierarchy.domain.GeoLocationMaster;
import com.sixdee.dms.hierarchy.dto.GeoLocationDTO;
import com.sixdee.dms.utils.common.PagedResponse;

/**
 * @author balu.s
 *
 */

public interface GeoLocationService {

	public String createGeoLocation(GeoLocationDTO userDto);

	public PagedResponse<GeoLocationDTO> getAllLocations(Pageable pageable, Specification<GeoLocationMaster> spec);

	public String updateGeoLocation(GeoLocationDTO gis);

	public GeoLocationDTO getLocationDetails(Long locationId);

	public GeoLocationDTO getChildLocationDetails(Long locationId);

	public String changeStatus(GeoLocationDTO gis);

}
