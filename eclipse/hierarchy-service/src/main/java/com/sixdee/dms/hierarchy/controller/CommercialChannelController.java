package com.sixdee.dms.hierarchy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sixdee.dms.hierarchy.dto.CommercialChannelDTO;
import com.sixdee.dms.hierarchy.service.impl.CommercialChannelService;

/**
 * @author balu.s
 *
 */


@RestController
@RequestMapping("/channel")
public class CommercialChannelController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommercialChannelController.class);
	
	@Autowired
	CommercialChannelService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findAllCommercialChannels() {

		if (LOGGER.isInfoEnabled())
			LOGGER.info("Get Channels");

		List<CommercialChannelDTO> channels = service.getCommercialChannels();
		
		if (channels == null) {
			if (LOGGER.isInfoEnabled())
				LOGGER.error("Get Channels | Channels Not Found");
		} else {
			if (LOGGER.isInfoEnabled())
				LOGGER.info("Get Channels | Channels Found");
		}
		return new ResponseEntity<Object>(channels, HttpStatus.OK);
	}

}
