package com.sixdee.dms.hierarchy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sixdee.dms.hierarchy.domain.CommercialChannelMaster;
import com.sixdee.dms.hierarchy.dto.CommercialChannelDTO;
import com.sixdee.dms.hierarchy.repository.CommercialChannelRepo;

/**
 * @author balu.s
 *
 */

@Service
public class CommercialChannelService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommercialChannelService.class);

	@Autowired
	CommercialChannelRepo repo;

	public List<CommercialChannelDTO> getCommercialChannels() {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get all commercial channels");
		}

		List<CommercialChannelMaster> channels = repo.findAll();

		List<CommercialChannelDTO> channelList = new ArrayList<>();
		channels.stream().forEach(k -> {
			CommercialChannelDTO dto = new CommercialChannelDTO();
			BeanUtils.copyProperties(k, dto);
			channelList.add(dto);
		});

		return channelList;
	}

}
