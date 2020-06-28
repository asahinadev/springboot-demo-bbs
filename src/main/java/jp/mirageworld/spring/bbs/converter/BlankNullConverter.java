package jp.mirageworld.spring.bbs.converter;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * JSON 文字列の ブランクをヌルに変換する.
 */
public class BlankNullConverter extends StdConverter<String, String> {

	@Override
	public String convert(String value) {
		return StringUtils.isEmpty(value) ? null : value;
	}

}
