package com.sixdee.dms.hierarchy.dto;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.PastOrPresent;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

/**
 * @author balu.s
 *
 */

@Generated
@Getter
@Setter
public class AbstractAuditingDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String createdBy;

	@PastOrPresent
	private Instant createdDate;

	private String updatedBy;

	@PastOrPresent
	private Instant updatedDate;
	
}
