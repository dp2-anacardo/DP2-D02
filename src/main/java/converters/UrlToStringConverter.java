
package converters;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import datatype.Url;

@Component
@Transactional
public class UrlToStringConverter implements Converter<Url, String> {

	@Override
	public String convert(final Url url) {
		String result;
		StringBuilder builder;

		if (url == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(url.getLink(), StandardCharsets.UTF_8.name()));
				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}
}
