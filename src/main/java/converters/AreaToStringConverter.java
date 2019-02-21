
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Area;

@Component
@Transactional
public class AreaToStringConverter implements Converter<Area, String> {

	@Override
	public String convert(final Area configuration) {
		String result;

		if (configuration == null)
			result = null;
		else
			result = String.valueOf(configuration.getId());

		return result;
	}

}
