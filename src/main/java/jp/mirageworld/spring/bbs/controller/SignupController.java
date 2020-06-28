package jp.mirageworld.spring.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import jp.mirageworld.spring.bbs.entity.Users;
import jp.mirageworld.spring.bbs.form.UserForm;
import jp.mirageworld.spring.bbs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * アカウント作成.
 */
@Slf4j
@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	UserService service;

	/**
	 * GET /signup.
	 * 
	 * @param form {@link UserForm}
	 * 
	 * @return PAGE.
	 */
	@GetMapping
	public String get(@ModelAttribute("form") UserForm form) {

		log.debug("初期状態 : {}", form);
		return "signup";
	}

	/**
	 * POST /signup (status=200).
	 * 
	 * @param form {@link UserForm}
	 * 
	 * @return PAGE.
	 */
	@ResponseBody
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Users> post(@Validated @RequestBody UserForm form) {

		log.debug("登録対象 : {}", form);
		return service.insert(form);
	}

	/**
	 * POST /signup (status=400).
	 * 
	 * @param exception {@link WebExchangeBindException}
	 * 
	 * @return list as {@link ObjectError}
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WebExchangeBindException.class)
	public Flux<ObjectError> exceptionHandler(WebExchangeBindException exception) {

		log.debug("エラー件数 {}", exception.getErrorCount());

		return Flux.fromIterable(exception.getAllErrors());
	}
}