package jp.mirageworld.spring.bbs.converter;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * Json •ÏŠ·i"" => null)
 */
public class BlankNullConverter extends StdConverter<String, String> {

	@Override
	public String convert(String value) {
		return StringUtils.isEmpty(value) ? null : value;
	}

}
