package com.sixdee.dms.hierarchy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author balu.s
 *
 */

@Generated
@Entity
@Table(name = "SD_COMMERCIAL_CHANNEL_MASTER")
@AllArgsConstructor
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class CommercialChannelMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CHANNEL_NAME")
	private String channelName;
	
	@Column(name = "IS_SALES_LOC_ALLOWED")
	private boolean isSalesLocAllowed;
	
}
