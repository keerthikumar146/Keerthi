package com.sixdee.dms.test.hierarchy.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sixdee.dms.hierarchy.domain.GeoLocationMaster;
import com.sixdee.dms.hierarchy.domain.GeoLocationSubType;
import com.sixdee.dms.hierarchy.domain.GeoLocationType;
import com.sixdee.dms.hierarchy.dto.DanamasLocationInfo;
import com.sixdee.dms.hierarchy.repository.GeoLocationMasterRepo;
import com.sixdee.dms.hierarchy.service.impl.DanamasLocationServiceImpl;
import com.sixdee.dms.hierarchy.utils.ApplicationConstants;
import com.sixdee.dms.hierarchy.utils.StatusCodes;
import com.sixdee.dms.utils.exceptions.CommonException;

/**
 * @author balu.s
 *
 */

@DisplayName("GeoLocationServiceTest")
public class DanamasLocationServiceTest {

	@Mock
	GeoLocationMasterRepo repo;
	
	@InjectMocks
	DanamasLocationServiceImpl service;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void getDanamasLocationInfo_locationNotFound() {
		GeoLocationMaster country = new GeoLocationMaster();
		country.setLocationId(1L);
		country.setLocationName("Indonasia");
		country.setLocationTypeId(0);
		country.setStatus(ApplicationConstants.STATUS_ACTIVE_ID);
		
		when(repo.findByLocationName("Indonasia")).thenReturn(Optional.of(country));
		CommonException thrown = assertThrows(CommonException.class, () -> service.getDanamasLocationInfo("subDistrict"),
				StatusCodes.NOT_FOUND_ERROR_CODE);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.NOT_FOUND_ERROR_CODE));
	}
	
	@Test
	public void getDanamasLocationInfo_parentLocationNotFound() {
		GeoLocationMaster country = new GeoLocationMaster();
		country.setLocationId(1L);
		country.setLocationName("Indonasia");
		country.setLocationTypeId(0);
		country.setStatus(ApplicationConstants.STATUS_ACTIVE_ID);
		
		GeoLocationMaster province = new GeoLocationMaster();
		province.setLocationId(2L);
		province.setLocationName("Province");
		province.setLocationTypeId(0);
		province.setStatus(ApplicationConstants.STATUS_ACTIVE_ID);
		province.setParentLocationId(3L);
		
		when(repo.findByLocationName("Province")).thenReturn(Optional.of(province));
		when(repo.findByLocationId(1L)).thenReturn(Optional.of(country));
		CommonException thrown = assertThrows(CommonException.class, () -> service.getDanamasLocationInfo("Province"),
				StatusCodes.NOT_FOUND_ERROR_CODE);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.NOT_FOUND_ERROR_CODE));
	}
	
	
	@Test
	public void getDanamasLocationInfo() {
		
		GeoLocationMaster country = new GeoLocationMaster();
		country.setLocationId(1L);
		country.setLocationName("Indonasia");
		country.setLocationTypeId(0);
		country.setStatus(ApplicationConstants.STATUS_ACTIVE_ID);
		
		GeoLocationMaster province = new GeoLocationMaster();
		province.setLocationId(2L);
		province.setLocationName("Province");
		province.setLocationTypeId(1);
		province.setStatus(ApplicationConstants.STATUS_ACTIVE_ID);
		province.setParentLocationId(1L);
		province.setParentLocDetails(country);
		
		GeoLocationMaster district = new GeoLocationMaster();
		district.setLocationId(3L);
		district.setLocationName("District");
		district.setLocationTypeId(1);
		district.setStatus(ApplicationConstants.STATUS_ACTIVE_ID);
		district.setParentLocationId(2L);
		district.setParentLocDetails(province);
		GeoLocationSubType kabupaten = new GeoLocationSubType(1, "KABUPATEN");
		GeoLocationType type = new GeoLocationType(1, "District", 0, true);
		district.setLocationTypeId(1);
		district.setLocationType(type);
		district.setLocationSubTypeId(1);
		district.setLocationSubType(kabupaten);
		
		GeoLocationMaster subdistrict = new GeoLocationMaster();
		subdistrict.setLocationId(4L);
		subdistrict.setLocationName("SubDistrict");
		subdistrict.setLocationTypeId(1);
		subdistrict.setStatus(ApplicationConstants.STATUS_ACTIVE_ID);
		subdistrict.setParentLocationId(3L);
		subdistrict.setParentLocDetails(district);
		
		when(repo.findByLocationName("SubDistrict")).thenReturn(Optional.of(subdistrict));
		when(repo.findByLocationId(3L)).thenReturn(Optional.of(district));
		when(repo.findByLocationId(2L)).thenReturn(Optional.of(province));
		
		DanamasLocationInfo response = service.getDanamasLocationInfo("SubDistrict");
		assertTrue(response.getProvince().equalsIgnoreCase(province.getLocationName()));
	}
	
}
