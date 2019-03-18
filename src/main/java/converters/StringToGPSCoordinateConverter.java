
package converters;

import java.net.URLDecoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import datatype.GPSCoordinate;

@Component
@Transactional
public class StringToGPSCoordinateConverter implements Converter<String, GPSCoordinate> {

	@Override
	public GPSCoordinate convert(final String text) {
		GPSCoordinate result;
		String parts[];

		if (text == null)
			result = null;
		else
			try {
				parts = text.split("\\|");
				result = new GPSCoordinate();
				result.setLatitude(Double.valueOf(URLDecoder.decode(parts[0], "UTF-8")));
				result.setLatitude(Double.valueOf(URLDecoder.decode(parts[1], "UTF-8")));
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}

}
