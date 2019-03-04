
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.FloatEntityRepository;
import domain.FloatEntity;

@Component
@Transactional
public class StringToFloatEntityConverter implements Converter<String, FloatEntity> {

	@Autowired
	FloatEntityRepository	floatEntityRepository;


	@Override
	public FloatEntity convert(final String text) {
		FloatEntity result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.floatEntityRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
