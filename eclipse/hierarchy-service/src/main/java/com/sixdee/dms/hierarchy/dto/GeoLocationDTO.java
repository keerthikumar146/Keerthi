package com.sixdee.dms.hierarchy.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sixdee.dms.hierarchy.utils.ApplicationConstants;
import com.sixdee.dms.hierarchy.validators.ValidatorGroups.Create;
import com.sixdee.dms.hierarchy.validators.ValidatorGroups.Delete;
import com.sixdee.dms.hierarchy.validators.ValidatorGroups.Update;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * @author balu.s
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Generated
public class GeoLocationDTO extends AbstractAuditingDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "{dms.gis.locationId.notNull}", groups = { Update.class, Delete.class })
	private Long locId;
	
	@NotEmpty(message = "{dms.gis.locationName.notNull}", groups = { Create.class , Update.class})
	private String locName;

	@NotEmpty(message = "{dms.gis.parentLocationId.notNull}", groups = { Create.class })
	private Long parentLocId;
	
	@NotNull(message = "{dms.gis.locationTypeId.notNull}", groups = { Create.class })
	private int locTypeId;

	private Integer subTypeId;
	
	private int status;
	
	private String locType;
	
	private String locSubType;
	
	private boolean hasChild;
	
	private String statusName;
	
	private String parentLocationName;
	
	private String parentLocationType;
	
	private List<GeoLocationDTO> childLocations = new ArrayList<>();
	
	@JsonIgnore
	private GeoLocationDTO parent;
	
	public String getLocType() {
		return locType;
	}

	public void setLocType(String locType) {
		this.locType = locType;
	}

	public String getLocSubType() {
		return locSubType;
	}

	public void setLocSubType(String locSubType) {
		this.locSubType = locSubType;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public Long getParentLocId() {
		return parentLocId;
	}

	public void setParentLocId(Long parentLocId) {
		this.parentLocId = parentLocId;
	}

	public int getLocTypeId() {
		return locTypeId;
	}

	public void setLocTypeId(int locTypeId) {
		this.locTypeId = locTypeId;
	}

	public Integer getSubTypeId() {
		return subTypeId;
	}

	public void setSubTypeId(Integer subTypeId) {
		this.subTypeId = subTypeId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public String getStatusName() {
		if (this.status == ApplicationConstants.STATUS_ACTIVE_ID) {
			return ApplicationConstants.STATUS_ACTIVE_NAME;
		} else if (this.status == ApplicationConstants.STATUS_INACTIVE_ID) {
			return ApplicationConstants.STATUS_INACTIVE_NAME;
		} else
			return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getParentLocationName() {
		return parentLocationName;
	}

	public void setParentLocationName(String parentLocationName) {
		this.parentLocationName = parentLocationName;
	}

	public String getParentLocationType() {
		return parentLocationType;
	}

	public void setParentLocationType(String parentLocationType) {
		this.parentLocationType = parentLocationType;
	}

	public List<GeoLocationDTO> getChildLocations() {
		return childLocations;
	}

	public void setChildLocations(List<GeoLocationDTO> childLocations) {
		this.childLocations = childLocations;
	}

	public GeoLocationDTO getParent() {
		return parent;
	}

	public void setParent(GeoLocationDTO parent) {
		this.parent = parent;
	}
	
	
	public void addChild(GeoLocationDTO child) {
		if (!this.childLocations.contains(child) && child != null)
			this.childLocations.add(child);
	}

	public GeoLocationDTO(
			@NotEmpty(message = "{dms.gis.locationId.notNull}", groups = { Update.class, Delete.class }) Long locId,
			@NotEmpty(message = "{dms.gis.locationName.notNull}", groups = { Create.class,
					Update.class }) String locName,
			@NotEmpty(message = "{dms.gis.parentLocationId.notNull}", groups = Create.class) Long parentLocId,
			@NotNull(message = "{dms.gis.locationTypeId.notNull}", groups = Create.class) int locTypeId,
			Integer subTypeId, int status, boolean hasChild) {
		super();
		this.locId = locId;
		this.locName = locName;
		this.parentLocId = parentLocId;
		this.locTypeId = locTypeId;
		this.subTypeId = subTypeId;
		this.status = status;
		this.hasChild = hasChild;
	}

	@Override
	public String toString() {
		return "GeoLocationDTO [locId=" + locId + ", locName=" + locName + ", parentLocId=" + parentLocId
				+ ", locTypeId=" + locTypeId + ", subTypeId=" + subTypeId + ", status=" + status + ", locType="
				+ locType + ", locSubType=" + locSubType + ", hasChild=" + hasChild + ", statusName=" + statusName
				+ "]";
	}
}
