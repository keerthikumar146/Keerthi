package com.sixdee.dms.hierarchy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sixdee.dms.hierarchy.domain.GeoLocationMaster;
import com.sixdee.dms.hierarchy.dto.GeoLocationDTO;
import com.sixdee.dms.hierarchy.service.GeoLocationService;
import com.sixdee.dms.hierarchy.service.impl.DanamasLocationServiceImpl;
import com.sixdee.dms.hierarchy.service.impl.GeoLocationTypeServiceImpl;
import com.sixdee.dms.hierarchy.utils.ApplicationConstants;
import com.sixdee.dms.hierarchy.utils.FeatureConstants;
import com.sixdee.dms.hierarchy.validators.ValidatorGroups.Create;
import com.sixdee.dms.hierarchy.validators.ValidatorGroups.Update;
import com.sixdee.dms.utils.common.CommonResponse;
import com.sixdee.dms.utils.common.PagedResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

/**
 * @author balu.s
 *
 */
@RequestMapping(value = "GIS")
@RestController
@Api(tags = { "Geo Location Services" })
public class GeoLocationController {

	@Autowired
	GeoLocationService geoLocService;

	@Autowired
	DanamasLocationServiceImpl danamasLocService;

	@Autowired
	GeoLocationTypeServiceImpl geoLocTypeService;

	@ApiOperation(value = "Create geo location", httpMethod = "POST", response = CommonResponse.class, code = 200)
	@PreAuthorize("hasAuthority(\"" + FeatureConstants.CREATE_GEO_TERRITORY + "\")")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> create(@Validated(Create.class) @RequestBody final GeoLocationDTO gis) {
		String response = geoLocService.createGeoLocation(gis);
		return new ResponseEntity<Object>(new CommonResponse(response), HttpStatus.OK);
	}

	@ApiOperation(value = "Get all geo locations", httpMethod = "GET", response = PagedResponse.class, code = 200)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getGeoLocationDetails(
			@RequestParam(value = "locationTypeId", required = false) Integer locTypeId,
			@RequestParam(value = "parentId", required = false) String parentId,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "size", defaultValue = ApplicationConstants.PAGE_DEFAULT_SIZE, required = false) int size,
			@RequestParam(value = "sort", defaultValue = "locationName", required = false) String sort,
			@RequestParam(value = "order", defaultValue = "asc", required = false) String order,
			@RequestParam(value = "status", defaultValue = "1", required = false) int status,
			@Conjunction({ @Or({ @Spec(path = "locationTypeId", params = "locTypeId", spec = Equal.class) }),
					@Or({ @Spec(path = "parentLocationId", params = "parentId", spec = Equal.class) }),
					@Or({ @Spec(path = "status", params = "status", spec = Equal.class) }),
					@Or({ @Spec(path = "locationName", params = "keyword", spec = LikeIgnoreCase.class) })

			}) Specification<GeoLocationMaster> spec) {

		Pageable pageable = (size != 0
				? PageRequest.of(page - 1, size, order.trim().equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
						sort)
				: Pageable.unpaged());

		PagedResponse<GeoLocationDTO> response = geoLocService.getAllLocations(pageable, spec);

		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@ApiOperation(value = "Get location details from id", httpMethod = "GET", response = GeoLocationDTO.class, code = 200)
	@GetMapping(value = "/{locationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeoLocationDTO> getLocationDetails(@PathVariable("locationId") Long locationId) {

		GeoLocationDTO response = geoLocService.getLocationDetails(locationId);

		return new ResponseEntity<GeoLocationDTO>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Update geo location", httpMethod = "POST", response = CommonResponse.class, code = 200)
	@PreAuthorize("hasAuthority(\"" + FeatureConstants.EDIT_GEO_TERRITORY + "\")")
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(@Validated(Update.class) @RequestBody final GeoLocationDTO gis) {
		String response = geoLocService.updateGeoLocation(gis);
		return new ResponseEntity<Object>(new CommonResponse(response), HttpStatus.OK);
	}

	@ApiOperation(value = "Get child location details", httpMethod = "GET", response = GeoLocationDTO.class, code = 200)
	@GetMapping(value = "/getChildLocations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getChildLocations(@RequestParam(value = "locId", required = true) long locationId) {

		GeoLocationDTO response = geoLocService.getChildLocationDetails(locationId);

		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getDistrictAndProvinceNames")
	public ResponseEntity<Object> getDistrictAndProvinceNames(@RequestParam(value = "subdistrict") String subdistrict) {

		return new ResponseEntity<Object>(danamasLocService.getDanamasLocationInfo(subdistrict), HttpStatus.OK);
	}

	@GetMapping(value = "/getAllLocationSubType")
	public ResponseEntity<Object> getAllLocationSubType() {
		CommonResponse response = new CommonResponse(geoLocTypeService.getAllSubTypes(), "Success");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getAllLocationType")
	public ResponseEntity<Object> getAllLocationType() {
		CommonResponse response = new CommonResponse(geoLocTypeService.getAllLocationTypes(), "Success");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@PutMapping(value = "changeStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority(\"" + FeatureConstants.EDIT_GEO_TERRITORY + "\")")
	public ResponseEntity<Object> changeStatus(@RequestBody final GeoLocationDTO gis) {

		String response = geoLocService.changeStatus(gis);

		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

}
