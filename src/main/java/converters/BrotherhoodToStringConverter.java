
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Brotherhood;

@Component
@Transactional
public class BrotherhoodToStringConverter implements Converter<Brotherhood, String> {

	@Override
	public String convert(final Brotherhood brother) {
		String result;

		if (brother == null)
			result = null;
		else
			result = brother.getTitle() + " (" + String.valueOf(brother.getId()) + ")";

		return result;
	}

}
