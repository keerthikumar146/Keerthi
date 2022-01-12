package com.sixdee.dms.test.hierarchy.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sixdee.dms.hierarchy.domain.GeoLocationSubType;
import com.sixdee.dms.hierarchy.domain.GeoLocationType;
import com.sixdee.dms.hierarchy.repository.GeoLocationSubTypeRepo;
import com.sixdee.dms.hierarchy.repository.GeoLocationTypeRepo;
import com.sixdee.dms.hierarchy.service.impl.GeoLocationTypeServiceImpl;

/**
 * @author balu.s
 *
 */

@DisplayName("GeoLocationServiceTypeTest")
public class GeoLocationTypeServiceTest {

	@InjectMocks
	GeoLocationTypeServiceImpl service;

	@Mock
	GeoLocationSubTypeRepo subTypeRepo;

	@Mock
	GeoLocationTypeRepo locTypeRepo;
	
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		GeoLocationSubType kabupaten = new GeoLocationSubType(1, "KABUPATEN");
		GeoLocationSubType kota = new GeoLocationSubType(2, "KOTA");
		List<GeoLocationSubType> subTypeList = new ArrayList<>();
		subTypeList.add(kabupaten);
		subTypeList.add(kota);
		
		GeoLocationType country = new GeoLocationType(0, "Country", 0, false);
		GeoLocationType province = new GeoLocationType(1, "Province", 0, true);
		List<GeoLocationType> typeList = new ArrayList<>();
		typeList.add(province);
		typeList.add(country);
		
		when(locTypeRepo.findAll()).thenReturn(typeList);
		when(subTypeRepo.findAll()).thenReturn(subTypeList);
		
	}
	
	@Test
	public void getAllSubTypesTest() {
		List<GeoLocationSubType> subTypes = service.getAllSubTypes();
		assertTrue(subTypes.size() == 2);
	}
	
	@Test
	public void getAllTypesTest() {
		List<GeoLocationType> types = service.getAllLocationTypes();
		assertTrue(types.size() == 2);	
	}

}
