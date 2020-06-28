package jp.mirageworld.spring.bbs.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Length;

/**
 * パスワード用
 */
@Documented
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(Password.List.class)
//��{�ݒ�
@Pattern(regexp = "^[\\p{ASCII}]+$")
@Length(min = 8, max = 32)
public @interface Password {

	@OverridesAttribute(constraint = Length.class, name = "min")
	int min() default 8;

	@OverridesAttribute(constraint = Length.class, name = "max")
	int max() default 32;

	@OverridesAttribute(constraint = Length.class, name = "message")
	String message() default "{org.hibernate.validator.constraints.Length.message}";

	@OverridesAttribute(constraint = Pattern.class, name = "regexp")
	String regexp() default "^[\\p{ASCII}]+$";

	@OverridesAttribute(constraint = Pattern.class, name = "flags")
	Flag[] flags() default {};

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * Defines several {@code @Length} annotations on the same element.
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		Password[] value();
	}
}
