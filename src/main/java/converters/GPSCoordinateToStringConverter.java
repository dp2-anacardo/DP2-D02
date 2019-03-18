
package converters;

import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import datatype.GPSCoordinate;

@Component
@Transactional
public class GPSCoordinateToStringConverter implements Converter<GPSCoordinate, String> {

	@Override
	public String convert(final GPSCoordinate g) {
		String result;
		StringBuilder builder;

		if (g == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(Double.toString(g.getLatitude()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Double.toString(g.getLongitude()), "UTF-8"));
				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}
}
