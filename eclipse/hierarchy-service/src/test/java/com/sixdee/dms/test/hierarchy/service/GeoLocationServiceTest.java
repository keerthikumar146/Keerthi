package com.sixdee.dms.test.hierarchy.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sixdee.dms.hierarchy.domain.GeoLocationMaster;
import com.sixdee.dms.hierarchy.domain.GeoLocationSubType;
import com.sixdee.dms.hierarchy.domain.GeoLocationType;
import com.sixdee.dms.hierarchy.dto.GeoLocationDTO;
import com.sixdee.dms.hierarchy.repository.GeoLocationMasterRepo;
import com.sixdee.dms.hierarchy.repository.GeoLocationTypeRepo;
import com.sixdee.dms.hierarchy.service.impl.GeoLocationServiceImpl;
import com.sixdee.dms.hierarchy.utils.ApplicationConstants;
import com.sixdee.dms.hierarchy.utils.StatusCodes;
import com.sixdee.dms.hierarchy.validators.FieldValidators;
import com.sixdee.dms.utils.common.PagedResponse;
import com.sixdee.dms.utils.exceptions.CommonException;

/**
 * @author balu.s
 *
 */

@DisplayName("GeoLocationServiceTest")
public class GeoLocationServiceTest {

	@InjectMocks
	GeoLocationServiceImpl service;

	@Spy
	FieldValidators validators = new FieldValidators();

	@Mock
	GeoLocationMasterRepo repo;

	@Mock
	GeoLocationTypeRepo locationTypeRepo;

	List<GeoLocationMaster> locationList = new ArrayList<>();

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		GeoLocationMaster parent = new GeoLocationMaster();
		parent.setLocationId(1L);
		parent.setLocationName("Indonasia");
		parent.setLocationTypeId(0);
		parent.setStatus(ApplicationConstants.STATUS_ACTIVE_ID);
		GeoLocationSubType subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		parent.setLocationSubTypeId(1);
		parent.setLocationSubType(subType);
		GeoLocationType type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Province");
		parent.setLocationType(type);
		GeoLocationMaster parentLoc = new GeoLocationMaster();
		parentLoc.setLocationId(1L);
		parentLoc.setLocationName("Parent");
		parentLoc.setLocationTypeId(0);
		parentLoc.setLocationType(new GeoLocationType());
		parentLoc.getLocationType().setName("Country");
		parent.setParentLocDetails(parentLoc);
		locationList.add(parent);

		when(repo.findById(1L)).thenReturn(Optional.of(parent));
		when(locationTypeRepo.findById(1)).thenReturn(Optional.of(new GeoLocationType(1, "Province", 0, false)));
		when(repo.save(Mockito.any(GeoLocationMaster.class))).thenAnswer(i -> i.getArguments()[0]);
	}

	@Test
	public void createGeoLocationTest() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocName("Test Loc");
		request.setLocTypeId(1);
		request.setParentLocId(1L);
		service.createGeoLocation(request);
	}

	@Test
	public void createGeoLocation_nullParent() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocName("Test Loc");
		request.setLocTypeId(1);
		CommonException thrown = assertThrows(CommonException.class, () -> service.createGeoLocation(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void createGeoLocation_invalidParentTest() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocName("Test Loc");
		request.setLocTypeId(1);
		request.setParentLocId(10l);
		CommonException thrown = assertThrows(CommonException.class, () -> service.createGeoLocation(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void createGeoLocation_nameExistsTest() {

		GeoLocationMaster existing = new GeoLocationMaster();
		existing.setLocationId(1L);
		existing.setLocationName("Test Loc");
		existing.setLocationTypeId(0);

		GeoLocationMaster parent = new GeoLocationMaster();
		parent.setLocationId(1L);
		parent.setLocationName("Indonasia");
		parent.setLocationTypeId(0);
		parent.setLocationType(new GeoLocationType());
		parent.getLocationType().setName("Country");

		existing.setParentLocDetails(parent);

		when(repo.findByLocationNameAndParentLocationId(any(String.class), any(Long.class)))
				.thenReturn(Optional.of(existing));
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocName("Indonasia");
		request.setLocTypeId(1);
		request.setParentLocId(1l);
		CommonException thrown = assertThrows(CommonException.class, () -> service.createGeoLocation(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void createGeoLocation_emptyLocType() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocName("Test Loc");
		request.setLocTypeId(0);
		request.setParentLocId(1l);
		CommonException thrown = assertThrows(CommonException.class, () -> service.createGeoLocation(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void createGeoLocation_invalidLocType() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocName("Test Loc");
		request.setLocTypeId(10);
		request.setParentLocId(1l);
		CommonException thrown = assertThrows(CommonException.class, () -> service.createGeoLocation(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void getAllLocationsTest() {

		GeoLocationMaster existing = new GeoLocationMaster();
		existing.setLocationId(2L);
		existing.setLocationName("Test Loc");
		existing.setLocationTypeId(1);
		existing.setParentLocationId(1L);
		GeoLocationMaster parent = new GeoLocationMaster();
		existing.setLocationId(1L);
		existing.setLocationName("Indonasia");
		existing.setLocationTypeId(0);
		GeoLocationSubType subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		existing.setLocationSubTypeId(1);
		existing.setLocationSubType(subType);
		GeoLocationType type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Province");
		existing.setLocationType(type);
		type = new GeoLocationType();
		type.setLocTypeId(0);
		type.setName("Country");
		parent.setLocationType(type);
		existing.setParentLocDetails(parent);
		locationList.add(existing);

		when(repo.findAll(any(Specification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<GeoLocationMaster>(locationList));

		Specification<GeoLocationMaster> spec = Specification.where(null);
		PagedResponse<GeoLocationDTO> response = service.getAllLocations(Pageable.unpaged(), spec);
		assertTrue(response.getTotalElements() == locationList.size());

	}

	@Test
	@SuppressWarnings("unchecked")
	public void getAllLocationsTest_emptyLocationType() {

		GeoLocationMaster existing = new GeoLocationMaster();
		existing.setLocationId(1L);
		existing.setLocationName("Test Loc");
		existing.setLocationTypeId(1);
		locationList.add(existing);

		when(repo.findAll(any(Specification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<GeoLocationMaster>(locationList));

		Specification<GeoLocationMaster> spec = Specification.where(null);
		PagedResponse<GeoLocationDTO> response = service.getAllLocations(Pageable.unpaged(), spec);
		assertTrue(response.getTotalElements() == locationList.size());

	}

	@Test
	@SuppressWarnings("unchecked")
	public void getAllLocationsTest_EmptyResult() {

		when(repo.findAll(any(Specification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<GeoLocationMaster>(new ArrayList<GeoLocationMaster>()));

		Specification<GeoLocationMaster> spec = Specification.where(null);
		PagedResponse<GeoLocationDTO> response = service.getAllLocations(Pageable.unpaged(), spec);
		assertTrue(response.getTotalElements() == 0);

	}

	@Test
	public void updateGeoLocationTest() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(1l);
		request.setLocName("Test Loc");
		request.setLocTypeId(1);
		request.setParentLocId(1L);
		service.updateGeoLocation(request);
	}

	@Test
	public void updateGeoLocationTest_emptyLocId() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(null);
		request.setLocName("Test Loc");
		request.setLocTypeId(1);
		request.setParentLocId(1L);

		CommonException thrown = assertThrows(CommonException.class, () -> service.updateGeoLocation(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void updateGeoLocationTest_invalidLocTypeId() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(1l);
		request.setLocName("Test Loc");
		request.setLocTypeId(0);
		request.setParentLocId(1L);

		CommonException thrown = assertThrows(CommonException.class, () -> service.updateGeoLocation(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void updateGeoLocationTest_invalidLocSubTypeId() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(1l);
		request.setLocName("Test Loc");
		request.setLocTypeId(3);
		request.setSubTypeId(0);
		request.setParentLocId(1L);

		CommonException thrown = assertThrows(CommonException.class, () -> service.updateGeoLocation(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void updateGeoLocationTest_nullParentId() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(1L);
		request.setLocName("Test Loc");
		request.setLocTypeId(1);
		request.setParentLocId(null);

		CommonException thrown = assertThrows(CommonException.class, () -> service.updateGeoLocation(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void updateGeoLocationTest_locIdNotFound() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(10L);
		request.setLocName("Test Loc");
		request.setLocTypeId(1);
		request.setParentLocId(1L);

		CommonException thrown = assertThrows(CommonException.class, () -> service.updateGeoLocation(request),
				StatusCodes.NOT_FOUND_ERROR_CODE);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.NOT_FOUND_ERROR_CODE));
	}

	@Test
	public void updateGeoLocationTest_locationNameExists() {

		GeoLocationMaster existing = new GeoLocationMaster();
		existing.setLocationId(2L);
		existing.setLocationName("Test Loc");
		existing.setLocationTypeId(0);

		GeoLocationMaster parent = new GeoLocationMaster();
		parent.setLocationId(1L);
		parent.setLocationName("Indonasia");
		parent.setLocationTypeId(0);
		parent.setLocationType(new GeoLocationType());
		parent.getLocationType().setName("Country");

		existing.setParentLocDetails(parent);

		when(repo.findByLocationNameAndParentLocationId(any(String.class), any(Long.class)))
				.thenReturn(Optional.of(existing));
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(1L);
		request.setLocName("Indonasia");
		request.setLocTypeId(1);
		request.setParentLocId(1l);
		CommonException thrown = assertThrows(CommonException.class, () -> service.updateGeoLocation(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void getLocationDetailsTest() {
		GeoLocationDTO response = service.getLocationDetails(1L);
		assertTrue(response.getLocId() == 1L);
	}

	@Test
	public void getLocationDetailsTest_locIdNotFound() {
		CommonException thrown = assertThrows(CommonException.class, () -> service.getLocationDetails(10L),
				StatusCodes.NOT_FOUND_ERROR_CODE);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.NOT_FOUND_ERROR_CODE));
	}

	@Test
	public void getLocationDetailsTest_emptyParentLocationDetails() {
		GeoLocationMaster parent = new GeoLocationMaster();
		parent.setLocationId(2L);
		parent.setLocationName("Jakarta");
		parent.setLocationTypeId(0);
		GeoLocationSubType subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		parent.setLocationSubTypeId(1);
		parent.setLocationSubType(subType);
		GeoLocationType type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Province");
		parent.setLocationType(type);
		when(repo.findById(2L)).thenReturn(Optional.of(parent));
		GeoLocationDTO response = service.getLocationDetails(2L);
		assertTrue(response.getLocId() == 2L);
	}

	@Test
	public void getLocationDetailsTest_emptySubType() {
		GeoLocationMaster location = new GeoLocationMaster();
		location.setLocationId(2L);
		location.setLocationName("Jakarta");
		location.setLocationTypeId(1);
		location.setLocationSubTypeId(1);
		GeoLocationType type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Province");
		location.setLocationType(type);

		GeoLocationMaster parent = new GeoLocationMaster();
		parent.setLocationId(1L);
		parent.setLocationName("Indonasia");
		parent.setLocationTypeId(0);
		parent.setLocationSubTypeId(1);
		type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Country");
		parent.setLocationType(type);

		location.setParentLocationId(1l);
		location.setParentLocDetails(parent);
		when(repo.findById(1L)).thenReturn(Optional.of(parent));
		GeoLocationDTO response = service.getLocationDetails(1L);
		assertTrue(response.getLocId() == 1L);
	}

	@Test
	public void getChildLocationDetailsTest() {
		GeoLocationMaster location = new GeoLocationMaster();
		location.setLocationId(2L);
		location.setLocationName("Jakarta");
		location.setLocationTypeId(1);
		location.setLocationSubTypeId(1);
		GeoLocationSubType subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		location.setLocationSubType(subType);
		GeoLocationType type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Province");
		location.setLocationType(type);

		GeoLocationMaster parent = new GeoLocationMaster();
		parent.setLocationId(1L);
		parent.setLocationName("Indonasia");
		parent.setLocationTypeId(0);
		parent.setLocationSubTypeId(1);
		subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		parent.setLocationSubType(subType);
		type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Country");
		parent.setLocationType(type);

		location.setParentLocationId(1l);
		location.setParentLocDetails(parent);

		List<GeoLocationMaster> list = new ArrayList<>();
		list.add(parent);
		list.add(location);
		when(repo.findByParentLocationIdOrLocationIdAndStatusOrderByLocationNameAsc(1L,
				ApplicationConstants.STATUS_ACTIVE_ID)).thenReturn(list);

		GeoLocationDTO response = service.getChildLocationDetails(1L);
		assertTrue(response.getChildLocations().size() == 1);

	}

	@Test
	public void getChildLocationDetails_nullParent() {
		GeoLocationMaster location = new GeoLocationMaster();
		location.setLocationId(2L);
		location.setLocationName("Jakarta");
		location.setLocationTypeId(1);
		location.setLocationSubTypeId(1);
		GeoLocationSubType subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		location.setLocationSubType(subType);
		GeoLocationType type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Province");
		location.setLocationType(type);

		GeoLocationMaster parent = new GeoLocationMaster();
		parent.setLocationId(1L);
		parent.setLocationName("Indonasia");
		parent.setLocationTypeId(0);
		parent.setLocationSubTypeId(1);
		subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		parent.setLocationSubType(subType);
		type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Country");
		parent.setLocationType(type);

		location.setParentLocationId(1l);
//		location.setParentLocDetails(parent);

		List<GeoLocationMaster> list = new ArrayList<>();
		list.add(parent);
		list.add(location);
		when(repo.findByParentLocationIdOrLocationIdAndStatusOrderByLocationNameAsc(1L,
				ApplicationConstants.STATUS_ACTIVE_ID)).thenReturn(list);

		GeoLocationDTO response = service.getChildLocationDetails(1L);
		assertTrue(response.getChildLocations().size() == 1);

	}

	@Test
	public void getChildLocationDetails_singleNode() {
		GeoLocationMaster parent = new GeoLocationMaster();
		parent.setLocationId(1L);
		parent.setLocationName("Indonasia");
		parent.setLocationTypeId(0);
		parent.setLocationSubTypeId(1);
		GeoLocationSubType subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		parent.setLocationSubType(subType);
		GeoLocationType type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Country");
		parent.setLocationType(type);
		parent.setParentLocationId(1L);

		List<GeoLocationMaster> list = new ArrayList<>();
		list.add(parent);
		when(repo.findByParentLocationIdOrLocationIdAndStatusOrderByLocationNameAsc(1L,
				ApplicationConstants.STATUS_ACTIVE_ID)).thenReturn(list);

		GeoLocationDTO response = service.getChildLocationDetails(1L);
		assertTrue(response.getChildLocations().size() == 0);

	}

	@Test
	public void getChildLocationDetails_nullParentNode() {
		GeoLocationMaster parent = new GeoLocationMaster();
		parent.setLocationId(1L);
		parent.setLocationName("Indonasia");
		parent.setLocationTypeId(0);
		parent.setLocationSubTypeId(1);
		GeoLocationSubType subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		parent.setLocationSubType(subType);
		GeoLocationType type = new GeoLocationType();
		type.setLocTypeId(1);
		type.setName("Country");
		parent.setLocationType(type);
		parent.setParentLocationId(null);
		List<GeoLocationMaster> list = new ArrayList<>();
		list.add(parent);
		when(repo.findByParentLocationIdOrLocationIdAndStatusOrderByLocationNameAsc(1L,
				ApplicationConstants.STATUS_ACTIVE_ID)).thenReturn(list);

		GeoLocationDTO response = service.getChildLocationDetails(1L);
		assertTrue(response.getChildLocations().size() == 0);

	}

	@Test
	public void getChildLocationDetailsTest_emptyParentLocType() {
		GeoLocationMaster location = new GeoLocationMaster();
		location.setLocationId(2L);
		location.setLocationName("Jakarta");
		location.setLocationSubTypeId(1);
		GeoLocationSubType subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		location.setLocationSubType(subType);

		GeoLocationMaster parent = new GeoLocationMaster();
		parent.setLocationId(1L);
		parent.setLocationName("Indonasia");
		parent.setLocationTypeId(0);
		subType = new GeoLocationSubType();
		subType.setId(1);
		subType.setName("SubType");
		parent.setLocationSubType(subType);

		location.setParentLocationId(1l);
		location.setParentLocDetails(parent);

		List<GeoLocationMaster> list = new ArrayList<>();
		list.add(parent);
		list.add(location);
		when(repo.findByParentLocationIdOrLocationIdAndStatusOrderByLocationNameAsc(1L,
				ApplicationConstants.STATUS_ACTIVE_ID)).thenReturn(list);

		GeoLocationDTO response = service.getChildLocationDetails(1L);
		assertTrue(response.getChildLocations().size() == 1);
	}

	@Test
	public void changeStatusTest_invalidLocId() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(10l);
		request.setLocName("Test Loc");
		request.setLocTypeId(1);
		request.setParentLocId(1L);

		CommonException thrown = assertThrows(CommonException.class, () -> service.changeStatus(request),
				StatusCodes.NOT_FOUND_ERROR_CODE);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.NOT_FOUND_ERROR_CODE));
	}

	@Test
	public void changeStatusTest_sameStatus() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(1L);
		request.setLocName("Indonasia");
		request.setLocTypeId(1);
		request.setParentLocId(1L);
		request.setStatus(ApplicationConstants.STATUS_ACTIVE_ID);

		CommonException thrown = assertThrows(CommonException.class, () -> service.changeStatus(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void changeStatusTest_downlineExists() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(1L);
		request.setLocName("Indonasia");
		request.setLocTypeId(1);
		request.setParentLocId(1L);
		request.setStatus(ApplicationConstants.STATUS_INACTIVE_ID);

		GeoLocationMaster existing = new GeoLocationMaster();
		existing.setLocationId(2L);
		existing.setLocationName("Test Loc");
		existing.setLocationTypeId(1);
		existing.setParentLocationId(1L);

		List<GeoLocationMaster> list = new ArrayList<>();
		list.add(existing);
		when(repo.findByParentLocationIdAndStatus(1L, ApplicationConstants.STATUS_ACTIVE_ID)).thenReturn(list);

		CommonException thrown = assertThrows(CommonException.class, () -> service.changeStatus(request),
				StatusCodes.CUSTOM_FIELD_VALIDATION);
		assertTrue(thrown.getStatusCode().equals(StatusCodes.CUSTOM_FIELD_VALIDATION));
	}

	@Test
	public void changeStatusTest_inactive() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(1L);
		request.setLocName("Indonasia");
		request.setLocTypeId(1);
		request.setParentLocId(1L);
		request.setStatus(ApplicationConstants.STATUS_INACTIVE_ID);

		List<GeoLocationMaster> list = new ArrayList<>();
		when(repo.findByParentLocationIdAndStatus(1L, ApplicationConstants.STATUS_INACTIVE_ID)).thenReturn(list);
		service.changeStatus(request);
	}
	
	@Test
	public void listToTreeTest() {
		GeoLocationDTO request = new GeoLocationDTO();
		request.setLocId(1L);
		request.setLocName("Indonasia");
		request.setLocTypeId(1);
		request.setParentLocId(null);
		request.setStatus(ApplicationConstants.STATUS_ACTIVE_ID);
		List<GeoLocationDTO> dtoList = new ArrayList<>();
		dtoList.add(request);
		GeoLocationDTO result = service.listToTree(dtoList, null);
		assertTrue(result.getChildLocations().size() == 0);

	}

}
