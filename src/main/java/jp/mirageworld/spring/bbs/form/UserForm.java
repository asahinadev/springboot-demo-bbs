package jp.mirageworld.spring.bbs.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jp.mirageworld.spring.bbs.converter.BlankNullConverter;
import jp.mirageworld.spring.bbs.validator.Password;
import jp.mirageworld.spring.bbs.validator.Username;
import jp.mirageworld.spring.bbs.validator.group.Create;
import jp.mirageworld.spring.bbs.validator.group.Update;
import lombok.Data;

@Data
@JsonInclude(content = JsonInclude.Include.NON_EMPTY)
public class UserForm {

	@NotEmpty(groups = {
			Create.class,
			Update.class
	})
	@Username(groups = {
			Create.class,
			Update.class
	})
	@JsonDeserialize(converter = BlankNullConverter.class)
	String username;

	@NotEmpty(groups = {
			Create.class,
			Update.class
	})
	@Email(groups = {
			Create.class,
			Update.class
	})
	@JsonDeserialize(converter = BlankNullConverter.class)
	String email;

	@NotEmpty(groups = {
			Create.class
	})
	@Password(groups = {
			Create.class,
			Update.class
	})
	@JsonDeserialize(converter = BlankNullConverter.class)
	String password;

}
