package com.sixdee.dms.hierarchy.validators;

import org.springframework.stereotype.Component;

import com.sixdee.dms.hierarchy.utils.ServiceConstants;
import com.sixdee.dms.hierarchy.utils.StatusCodes;
import com.sixdee.dms.utils.exceptions.CommonException;
import com.sixdee.dms.utils.service.MessageUtil;

/**
 * @author balu.s
 *
 */

@Component
public class FieldValidators {

	public boolean isValidLocationName(String value, int locationType) {
		int errorType = 0;
		String onlySpecialCharacters = "[" + "/.(), " + "]+";
		String startwithSpecialCharacters = "^\\w[a-zA-Z0-9()/., ]*$";

		if (locationType == ServiceConstants.GEO_LOC_TYPE_POSTCODE) {
			if (value == null || value.trim().equals("")) {
				errorType = 9;
			} else if (value.length() != 5) {
				errorType = 8;
			} else if (!value.matches("^[0-9]*$")) {
				errorType = 8;
			} else {
				return true;
			}
		} else {
			if (value == null || value.trim().equals("")) {
				errorType = 9;
			} else if (value.length() < 2 || value.length() > 50) {
				errorType = 7;
			} else if (!value.matches("^[a-zA-Z0-9()/., ]*$")) {
				errorType = 1;
			}

			else if (value.matches("^[0-9]*$")) {
				errorType = 3;
			} else if (value.matches("^\\s+.*")) {
				errorType = 4;
			} else if (value.matches(onlySpecialCharacters)) {
				errorType = 5;
			} else if (!value.matches(startwithSpecialCharacters)) {
				errorType = 6;
			} else {
				return true;
			}
		}

		throw new CommonException(StatusCodes.CUSTOM_FIELD_VALIDATION, getMessage(errorType));

	}

	public String getMessage(int errorType) {

		switch (errorType) {
		case 1:
			return MessageUtil.get("dms.gis.locationName.invalidName");
		case 2:
			return MessageUtil.get("dms.gis.locationName.consecutive.spChars");
		case 3:
			return MessageUtil.get("dms.gis.locationName.onlyNums");
		case 4:
			return MessageUtil.get("dms.gis.locationName.beginWithSpace");
		case 5:
			return MessageUtil.get("dms.gis.locationName.onlySpChars");
		case 6:
			return MessageUtil.get("dms.gis.locationName.beginWithSpChars");
		case 7:
			return MessageUtil.get("dms.gis.locationName.sizeCheck");
		case 8:
			return MessageUtil.get("dms.gis.locationName.postCode.sizeCheck");
		case 9:
			return MessageUtil.get("dms.gis.locationName.notNull");
		default:
			return null;
		}

	}

}
