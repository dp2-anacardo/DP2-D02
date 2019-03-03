
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Transactional
public class StringToIntConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(final String text) {
		Integer result;
		try {
			if (StringUtils.isEmpty(text))
				result = 0;
			else
				result = Integer.parseInt(text);

		} catch (final Throwable oops) {
			result = 0;
		}
		return result;
	}

}
