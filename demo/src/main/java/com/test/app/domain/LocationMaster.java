package com.test.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author balu.s
 *
 */

@Entity
@Table(name = "SD_GEO_LOCATION_MASTER")
public class LocationMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LOCATION_ID")
	private String locId;

	@Column(name = "LOCATION_NAME", nullable = false)
	private String locName;

	@Column(name = "PARENT_LOCATION_ID")
	private String parentLocId;

	@Column(name = "LOCATION_TYPE_ID", insertable = false, updatable = false)
	private Integer locTypeId;

	@Column(name = "SUB_TYPE_ID")
	private Integer subTypeId;

	@Column(name = "STATUS")
	private int status;

	public String getLocId() {
		return locId;
	}

	public void setLocId(String locId) {
		this.locId = locId;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getParentLocId() {
		return parentLocId;
	}

	public void setParentLocId(String parentLocId) {
		this.parentLocId = parentLocId;
	}

	public Integer getLocTypeId() {
		return locTypeId;
	}

	public void setLocTypeId(Integer locTypeId) {
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

}