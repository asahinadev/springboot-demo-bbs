package jp.mirageworld.spring.bbs.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jp.mirageworld.spring.bbs.converter.BlankNullConverter;
import jp.mirageworld.spring.bbs.validator.Password;
import jp.mirageworld.spring.bbs.validator.Username;
import lombok.Data;

@Data
@JsonInclude(content = JsonInclude.Include.NON_EMPTY)
public class UserForm {

	@NotEmpty
	@Username
	@JsonDeserialize(converter = BlankNullConverter.class)
	String username;

	@NotEmpty
	@Email
	@JsonDeserialize(converter = BlankNullConverter.class)
	String email;

	@NotEmpty
	@Password
	@JsonDeserialize(converter = BlankNullConverter.class)
	String password;

}
