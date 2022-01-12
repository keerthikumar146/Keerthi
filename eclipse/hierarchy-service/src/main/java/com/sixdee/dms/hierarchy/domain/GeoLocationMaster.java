package com.sixdee.dms.hierarchy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sixdee.dms.utils.constants.CommonConstants;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * @author balu.s
 *
 */
@Generated
@Entity
@Table(name = "SD_GEO_LOCATION_MASTER")
@AllArgsConstructor
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GeoLocationMaster extends AbstractAuditingEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LOCATION_ID")
	@GenericGenerator(name = "uuid_gen", strategy = CommonConstants.UUID)
	@GeneratedValue(generator = "uuid_gen")
	private Long locationId;

	@Column(name = "LOCATION_NAME")
	private String locationName;

	@Column(name = "PARENT_LOCATION_ID")
	private Long parentLocationId;

	@Column(name = "LOCATION_TYPE_ID")
	private int locationTypeId;

	@Column(name = "SUB_TYPE_ID", nullable = true)
	private Integer locationSubTypeId;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "HAS_CHILD")
	private boolean hasChild;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JsonIgnore
	@JoinColumn(name = "PARENT_LOCATION_ID", referencedColumnName = "LOCATION_ID", insertable = false, updatable = false, nullable = true)
	private GeoLocationMaster parentLocDetails;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LOCATION_TYPE_ID", referencedColumnName = "LOCATION_TYPE_ID", insertable = false, updatable = false)
	private GeoLocationType locationType;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "SUB_TYPE_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = true)
	private GeoLocationSubType locationSubType;

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Long getParentLocationId() {
		return parentLocationId;
	}

	public void setParentLocationId(Long parentLocationId) {
		this.parentLocationId = parentLocationId;
	}

	public int getLocationTypeId() {
		return locationTypeId;
	}

	public void setLocationTypeId(int locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	public Integer getLocationSubTypeId() {
		return locationSubTypeId;
	}

	public void setLocationSubTypeId(Integer locationSubTypeId) {
		this.locationSubTypeId = locationSubTypeId;
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

	public GeoLocationMaster getParentLocDetails() {
		return parentLocDetails;
	}

	public void setParentLocDetails(GeoLocationMaster parentLocDetails) {
		this.parentLocDetails = parentLocDetails;
	}

	public GeoLocationType getLocationType() {
		return locationType;
	}

	public void setLocationType(GeoLocationType locationType) {
		this.locationType = locationType;
	}

	public GeoLocationSubType getLocationSubType() {
		return locationSubType;
	}

	public void setLocationSubType(GeoLocationSubType locationSubType) {
		this.locationSubType = locationSubType;
	}

}
