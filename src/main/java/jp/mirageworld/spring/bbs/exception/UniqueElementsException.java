package jp.mirageworld.spring.bbs.exception;

import lombok.Getter;

@SuppressWarnings("serial")
public class UniqueElementsException extends RuntimeException {

	@Getter
	String field;

	/**
	 * コンストラクタ.
	 * 
	 * @param field   {@link String}
	 * @param message {@link String}
	 * @param cause   {@link Throwable}
	 */
	public UniqueElementsException(
			String field,
			String message,
			Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param field   {@link String}
	 * @param message {@link String}
	 */
	public UniqueElementsException(String field, String message) {
		this(field, message, null);
	}

}
