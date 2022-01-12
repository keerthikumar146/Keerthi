package com.sixdee.dms.hierarchy.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sixdee.dms.hierarchy.domain.GeoLocationMaster;
import com.sixdee.dms.hierarchy.domain.GeoLocationType;
import com.sixdee.dms.hierarchy.dto.GeoLocationDTO;
import com.sixdee.dms.hierarchy.repository.GeoLocationMasterRepo;
import com.sixdee.dms.hierarchy.repository.GeoLocationTypeRepo;
import com.sixdee.dms.hierarchy.service.GeoLocationService;
import com.sixdee.dms.hierarchy.utils.ApplicationConstants;
import com.sixdee.dms.hierarchy.utils.ServiceConstants;
import com.sixdee.dms.hierarchy.utils.StatusCodes;
import com.sixdee.dms.hierarchy.validators.FieldValidators;
import com.sixdee.dms.utils.common.PagedResponse;
import com.sixdee.dms.utils.exceptions.CommonException;
import com.sixdee.dms.utils.service.MessageUtil;

/**
 * @author balu.s
 *
 */

@Service
@Transactional
public class GeoLocationServiceImpl implements GeoLocationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeoLocationServiceImpl.class);

	@Autowired
	FieldValidators validators;

	@Autowired
	GeoLocationMasterRepo locMasterRepo;

	@Autowired
	GeoLocationTypeRepo locationTypeRepo;

	@Override
	public String createGeoLocation(GeoLocationDTO gis) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Creating geo location : {}", gis);
		}

		validators.isValidLocationName(gis.getLocName(), gis.getLocTypeId());
		gis.setLocName(gis.getLocName().toUpperCase());

		if (gis.getParentLocId() == null)
			throw new CommonException(MessageUtil.get("dms.gis.parentLocationId.notNull"),
					StatusCodes.CUSTOM_FIELD_VALIDATION);

		locMasterRepo.findById(gis.getParentLocId())
				.orElseThrow(() -> new CommonException(MessageUtil.get("dms.gis.parentLocationId.notExist"),
						StatusCodes.CUSTOM_FIELD_VALIDATION));

		Optional<GeoLocationMaster> existing = locMasterRepo.findByLocationNameAndParentLocationId(gis.getLocName(),
				gis.getParentLocId());

		if (existing.isPresent())
			throw new CommonException(
					MessageUtil.get("dms.gis.locationName.alreadyExist",
							new Object[] { existing.get().getParentLocDetails().getLocationName(),
									existing.get().getParentLocDetails().getLocationType().getName() }),
					StatusCodes.CUSTOM_FIELD_VALIDATION);

		if (gis.getLocTypeId() == 0)
			throw new CommonException(MessageUtil.get("dms.gis.locationTypeId.notNull"),
					StatusCodes.CUSTOM_FIELD_VALIDATION);

		Optional<GeoLocationType> existingLocType = locationTypeRepo.findById(gis.getLocTypeId());

		existingLocType.orElseThrow(() -> new CommonException(MessageUtil.get("dms.gis.locationTypeId.notExist"),
				StatusCodes.CUSTOM_FIELD_VALIDATION));

		GeoLocationMaster locationMaster = new GeoLocationMaster();
		locationMaster.setLocationName(gis.getLocName());
		locationMaster.setParentLocationId(gis.getParentLocId());
		locationMaster.setLocationTypeId(gis.getLocTypeId());
		locationMaster.setStatus(ServiceConstants.GEO_LOC_STATUS_ACTIVE);
		locationMaster.setLocationSubTypeId(gis.getSubTypeId());
		locMasterRepo.save(locationMaster);
		locMasterRepo.updateHasChild(gis.getParentLocId());

		return MessageUtil.get("dms.gis.location.crete.success",
				new Object[] { existingLocType.get().getName(), gis.getLocName() });

	}

	@Override
	public PagedResponse<GeoLocationDTO> getAllLocations(Pageable pageable, Specification<GeoLocationMaster> spec) {

		Page<GeoLocationMaster> locations = locMasterRepo.findAll(spec, pageable);

		if (locations.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), locations.getNumber(), locations.getSize(),
					locations.getTotalElements(), locations.getTotalPages(), locations.isLast());
		}

		List<GeoLocationDTO> locationDto = locations.stream().map(loc -> {
			GeoLocationDTO gisBean = new GeoLocationDTO();
			gisBean.setLocId(loc.getLocationId());
			gisBean.setLocName(loc.getLocationName());
			gisBean.setLocTypeId(loc.getLocationTypeId());
			if (loc.getLocationType()!=null)
				gisBean.setLocType(loc.getLocationType().getName());
			gisBean.setParentLocId(loc.getParentLocationId());
			gisBean.setParentLocationName(
					loc.getParentLocDetails() != null ? loc.getParentLocDetails().getLocationName() : null);
			gisBean.setParentLocationType(
					loc.getParentLocDetails() != null ? loc.getParentLocDetails().getLocationType().getName() : null);
			gisBean.setLocSubType(loc.getLocationSubType() != null ? loc.getLocationSubType().getName() : null);
			gisBean.setStatus(loc.getStatus());
			gisBean.setHasChild(loc.isHasChild());
			gisBean.setCreatedBy(loc.getCreatedBy());
			gisBean.setCreatedDate(loc.getCreatedDate());
			gisBean.setUpdatedBy(loc.getUpdatedBy());
			gisBean.setUpdatedDate(loc.getUpdatedDate());
			return gisBean;
		}).collect(Collectors.toList());

		return new PagedResponse<>(locationDto, locations.getNumber(), locations.getSize(),
				locations.getTotalElements(), locations.getTotalPages(), locations.isLast());

	}

	@Override
	public String updateGeoLocation(GeoLocationDTO gis) {

		if (gis.getLocId() == null)
			throw new CommonException(MessageUtil.get("dms.gis.locationId.notNull"),
					StatusCodes.CUSTOM_FIELD_VALIDATION);

		validators.isValidLocationName(gis.getLocName(), gis.getLocTypeId());
		gis.setLocName(gis.getLocName().toUpperCase());

		if (gis.getLocTypeId() == 0)
			throw new CommonException(MessageUtil.get("dms.gis.locationTypeId.notNull"),
					StatusCodes.CUSTOM_FIELD_VALIDATION);

		if (gis.getLocTypeId() == 3) {
			if (gis.getSubTypeId() == 0)
				throw new CommonException(MessageUtil.get("dms.gis.subTypeId.notNull"),
						StatusCodes.CUSTOM_FIELD_VALIDATION);
		}

		if (gis.getParentLocId() == null)
			throw new CommonException(MessageUtil.get("dms.gis.parentLocationId.notNull"),
					StatusCodes.CUSTOM_FIELD_VALIDATION);

		Optional<GeoLocationMaster> existing = locMasterRepo.findById(gis.getLocId());
		existing.orElseThrow(() -> new CommonException(MessageUtil.get("dms.gis.locationId.notExist"),
				StatusCodes.NOT_FOUND_ERROR_CODE));

		Optional<GeoLocationMaster> existingLoc = locMasterRepo.findByLocationNameAndParentLocationId(gis.getLocName(),
				gis.getParentLocId());
		if (existingLoc.isPresent()) {
			if (!existingLoc.get().getLocationId().equals(gis.getLocId())) {
				throw new CommonException(
						MessageUtil.get("dms.gis.locationName.alreadyExist",
								new Object[] { existing.get().getParentLocDetails().getLocationName(),
										existing.get().getParentLocDetails().getLocationType().getName() }),
						StatusCodes.CUSTOM_FIELD_VALIDATION);
			}
		}

		GeoLocationMaster locationMaster = existing.get();
		locationMaster.setLocationId(gis.getLocId());
		locationMaster.setLocationName(gis.getLocName());
		locationMaster.setParentLocationId(gis.getParentLocId());
		locationMaster.setLocationTypeId(gis.getLocTypeId());
		locationMaster.setLocationSubTypeId(gis.getSubTypeId());
		locMasterRepo.save(locationMaster);

		return MessageUtil.get("dms.gis.location.updated.success");
	}

	@Override
	public GeoLocationDTO getLocationDetails(Long locationId) {

		Optional<GeoLocationMaster> existing = locMasterRepo.findById(locationId);
		existing.orElseThrow(() -> new CommonException(
				MessageUtil.get("dms.gis.locationId.notExist"), StatusCodes.NOT_FOUND_ERROR_CODE));

		GeoLocationDTO gisBean = new GeoLocationDTO();
		gisBean.setLocId(existing.get().getLocationId());
		gisBean.setLocName(existing.get().getLocationName());
		gisBean.setLocName(gisBean.getLocName().toUpperCase());
		gisBean.setLocTypeId(existing.get().getLocationTypeId());
		gisBean.setLocType(existing.get().getLocationType().getName());
		gisBean.setParentLocId(existing.get().getParentLocationId());
		gisBean.setLocSubType(
				existing.get().getLocationSubType() != null ? existing.get().getLocationSubType().getName() : null);
		gisBean.setParentLocationName(
				existing.get().getParentLocDetails() != null ? existing.get().getParentLocDetails().getLocationName()
						: null);
		gisBean.setParentLocationType(existing.get().getParentLocDetails() != null
				? existing.get().getParentLocDetails().getLocationType().getName()
				: null);
		gisBean.setStatus(existing.get().getStatus());
		gisBean.setCreatedBy(existing.get().getCreatedBy());
		gisBean.setCreatedDate(existing.get().getCreatedDate());
		gisBean.setUpdatedBy(existing.get().getUpdatedBy());
		gisBean.setUpdatedDate(existing.get().getUpdatedDate());
		gisBean.setHasChild(existing.get().isHasChild());
		return gisBean;

	}

	@Override
	public GeoLocationDTO getChildLocationDetails(Long locationId) {

		List<GeoLocationMaster> existing = locMasterRepo
				.findByParentLocationIdOrLocationIdAndStatusOrderByLocationNameAsc(locationId,
						ApplicationConstants.STATUS_ACTIVE_ID);

		List<GeoLocationDTO> locationDto = existing.stream().map(loc -> {
			GeoLocationDTO gisBean = new GeoLocationDTO();
			gisBean.setLocId(loc.getLocationId());
			gisBean.setLocName(loc.getLocationName());
			gisBean.setLocTypeId(loc.getLocationTypeId());
			gisBean.setLocType(loc.getLocationType()!=null?loc.getLocationType().getName():"");
			gisBean.setParentLocId(loc.getParentLocationId());
			if (loc.getParentLocDetails() != null) {
				gisBean.setParentLocationName(loc.getParentLocDetails().getLocationName());
				gisBean.setParentLocationType(loc.getParentLocDetails().getLocationType() != null
						? loc.getParentLocDetails().getLocationType().getName()
						: null);
			}
			gisBean.setLocSubType(loc.getLocationSubType() != null ? loc.getLocationSubType().getName() : null);
			gisBean.setStatus(loc.getStatus());
			gisBean.setHasChild(loc.isHasChild());
			gisBean.setCreatedBy(loc.getCreatedBy());
			gisBean.setCreatedDate(loc.getCreatedDate());
			gisBean.setUpdatedBy(loc.getUpdatedBy());
			gisBean.setUpdatedDate(loc.getUpdatedDate());
			return gisBean;
		}).collect(Collectors.toList());

		GeoLocationDTO response = listToTree(locationDto, locationId);

		return response;
	}

	public GeoLocationDTO listToTree(List<GeoLocationDTO> nodes, Long parentNode) {
		Map<Long, GeoLocationDTO> nodeMap = new HashMap<>();

		for (GeoLocationDTO current : nodes) {
			nodeMap.put(current.getLocId(), current);
		}

		for (GeoLocationDTO current : nodes) {
			Long parentId = current.getParentLocId();
			if (parentId != null) {
				GeoLocationDTO parent = nodeMap.get(parentId);
				if (parent != null) {
					if (parentNode != null && parentNode.equals(current.getLocId())) {
						continue;
					}
					
					if (LOGGER.isDebugEnabled())
						LOGGER.debug("Current {}", current.getLocId());
					current.setParent(parent);
					parent.addChild(current);
					nodeMap.put(parentId, parent);
					nodeMap.put(current.getLocId(), current);
				}
			}
		}

		GeoLocationDTO root = null;
		for (GeoLocationDTO node : nodeMap.values()) {
			if (parentNode != null) {
				if (node.getLocId().equals(parentNode)) {
					root = node;
					if (LOGGER.isDebugEnabled())
						LOGGER.debug("Root found, Name {} count {}", root.getLocName(),
								root.getChildLocations().size());
					break;
				}
			} else {
				if (node.getParentLocationType() == null) {
					root = node;
					if (LOGGER.isDebugEnabled())
						LOGGER.debug("Parent null, Root found {}", root.getLocName());
					break;
				}
			}
		}
		return root;
	}

	@Override
	public String changeStatus(GeoLocationDTO gis) {

		Optional<GeoLocationMaster> existing = locMasterRepo.findById(gis.getLocId());
		existing.orElseThrow(() -> new CommonException(MessageUtil.get("dms.visit.gis.locationId.notExist"),
				StatusCodes.NOT_FOUND_ERROR_CODE));

		int existingStatus = existing.get().getStatus();
		if (existingStatus == gis.getStatus()) {
			throw new CommonException(MessageUtil.get("dms.gis.status.unchanged"), StatusCodes.CUSTOM_FIELD_VALIDATION);
		}

		if (gis.getStatus() == ApplicationConstants.STATUS_INACTIVE_ID) {

			if (LOGGER.isDebugEnabled())
				LOGGER.info("Deactivating Geo Location with id : " + gis.getLocId());

			if (LOGGER.isDebugEnabled())
				LOGGER.debug("1. check if any child location exists for " + gis.getLocId());

			List<GeoLocationMaster> childrenList = locMasterRepo.findByParentLocationIdAndStatus(gis.getLocId(),
					ApplicationConstants.STATUS_ACTIVE_ID);
			if (childrenList != null && childrenList.size() > 0) {
				throw new CommonException(MessageUtil.get("dms.gis.deactivate.childLocation.exist"),
						StatusCodes.CUSTOM_FIELD_VALIDATION);
			}

			if (LOGGER.isDebugEnabled())
				LOGGER.debug(" 2. check if its linked with any sale location for " + gis.getLocId());
			
			//TODO Check link with sales location
			
//			List<SalesGeoLocationMaster> salesGeoLocationMasterList = salesGeoLocRepo.findByGeoLocIdVal(gis.getLocId());
//			if (salesGeoLocationMasterList != null && salesGeoLocationMasterList.size() > 0) {
//				throw new CommonException(ErrorConstants.CUSTOM_FIELD_VALIDATION,
//						MessageUtil.get("dms.visit.gis.deactivate.salesLocation.exist"));
//			}

			if (LOGGER.isDebugEnabled())
				LOGGER.debug(
						"3. check in channel partner master if village id/sub district id/ district id is present for "
								+ gis.getLocId());
			
			//TODO Check channel partner 
			
//			int channelPartnerMasterCount = 0;
//			channelPartnerMasterCount = channelPartnerRepository.findByLocationIdInAll(gis.getLocId());
//			if (channelPartnerMasterCount > 0) {
//				throw new CommonException(ErrorConstants.CUSTOM_FIELD_VALIDATION,
//						MessageUtil.get("dms.visit.gis.deactivate.channelPartner.exist"));
//			}
		}

		GeoLocationMaster existingLocationMaster = existing.get();
		existingLocationMaster.setStatus(gis.getStatus());
		locMasterRepo.saveAndFlush(existingLocationMaster);
		return MessageUtil.get("dms.gis.location.status.change.success",
				new Object[] { existing.get().getParentLocDetails().getLocationName() });

	}

}
