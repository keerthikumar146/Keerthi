package com.test.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.app.domain.LocationMaster;
import com.test.app.service.GisServiceImpl;

/**
 * @author balu.s
 *
 */


@RequestMapping(value = "GIS")
@RestController
public class GisController {

	@Autowired
	GisServiceImpl gisServiceImpl;
	
	@GetMapping(value = "/getLocDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LocationMaster> getLocationDetails(@RequestParam(value = "locId") String locId) {

		LocationMaster response = gisServiceImpl.getLocdetails(locId);

		return new ResponseEntity<LocationMaster>(response, HttpStatus.OK);
	}
	
}
