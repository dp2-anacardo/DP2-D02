
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Parade;

@Component
@Transactional
public class ParadeToStringConverter implements Converter<Parade, String> {

	@Override
	public String convert(final Parade configuration) {
		String result;

		if (configuration == null)
			result = null;
		else
			result = String.valueOf(configuration.getId());

		return result;
	}
}
