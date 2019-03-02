
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.FloatEntity;

@Component
@Transactional
public class FloatEntityToStringConverter implements Converter<FloatEntity, String> {

	@Override
	public String convert(final FloatEntity f) {
		String result;

		if (f == null)
			result = null;
		else
			result = String.valueOf(f.getId());

		return result;
	}

}
