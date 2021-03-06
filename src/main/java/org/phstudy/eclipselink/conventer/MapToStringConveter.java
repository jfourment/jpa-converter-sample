package org.phstudy.eclipselink.conventer;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.backtothefront.HstoreHelper;

@Converter(autoApply = true)
public class MapToStringConveter implements
		AttributeConverter<Map<String, String>, Object> {

	@Override
	public String convertToDatabaseColumn(Map<String, String> attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return "";
		}
		return HstoreHelper.toString(attribute);
	}

	@Override
	public Map<String, String> convertToEntityAttribute(Object dbData) {
		// EclipseLink Bug or Feature? Hstore will be converted to HashMap, not
		// String..., so the target type should be Object
		if (dbData instanceof String) {
			String str = (String) dbData;
			if (str.trim().length() == 0) {
				return new HashMap<String, String>();
			}
			return HstoreHelper.toMap((String) dbData);

		} else {
			return (Map<String, String>) dbData;
		}
	}
}
