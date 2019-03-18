
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Chapter;

@Component
@Transactional
public class ChapterToStringConverter implements Converter<Chapter, String> {

	@Override
	public String convert(final Chapter c) {
		String result;

		if (c == null)
			result = null;
		else
			result = String.valueOf(c.getId());

		return result;
	}

}
