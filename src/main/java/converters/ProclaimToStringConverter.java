
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Proclaim;

@Component
@Transactional
public class ProclaimToStringConverter implements Converter<Proclaim, String> {

	@Override
	public String convert(final Proclaim p) {
		String result;

		if (p == null)
			result = null;
		else
			result = String.valueOf(p.getId());

		return result;
	}

}
