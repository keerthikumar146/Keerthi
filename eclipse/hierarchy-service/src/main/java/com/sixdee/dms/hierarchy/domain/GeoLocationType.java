package com.sixdee.dms.hierarchy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * @author balu.s
 *
 */
@Generated
@Entity
@Table(name = "SD_GEO_LOCATION_TYPES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocationType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOCATION_TYPE_ID")
	private int locTypeId;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "PARENT_ID")
	private int parentId;

	@Column(name = "IS_LEAF")
	private boolean isLeaf;

	public int getLocTypeId() {
		return locTypeId;
	}

	public void setLocTypeId(int locTypeId) {
		this.locTypeId = locTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
}
