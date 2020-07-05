package jp.mirageworld.spring.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.util.UriComponentsBuilder;

import jp.mirageworld.spring.bbs.entity.Users;
import jp.mirageworld.spring.bbs.exception.UniqueElementsException;
import jp.mirageworld.spring.bbs.form.UserForm;
import jp.mirageworld.spring.bbs.service.UsersService;
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

	@ModelAttribute("title")
	public String getTitle() {
		return "SIGNUP";
	}

	@Autowired
	UsersService usersService;

	/**
	 * GET /signup.
	 * 
	 * @param form {@link UserForm}
	 * 
	 * @return PAGE.
	 */
	@GetMapping
	public String get(Model model, @ModelAttribute("form") UserForm form) {

		log.debug("初期状態 : {}", form);

		model.addAttribute("redirect", UriComponentsBuilder.fromPath("/login").build().toUriString());
		model.addAttribute("method", "post");
		model.addAttribute("action", UriComponentsBuilder.fromPath("/signup").build().toUriString());
		model.addAttribute("button", "登録");
		model.addAttribute("passwordRequired", true);
		model.addAttribute("confime", "この内容で登録します。よろしいですか？");

		return "signup";
	}

	/**
	 * POST /signup (status=200).
	 * 
	 * @param form {@link UserForm}
	 * 
	 * @return {@link Users}.
	 */
	@ResponseBody
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Users> post(@Validated @RequestBody UserForm form) {

		log.debug("登録対象 : {}", form);
		return usersService.insert(form);
	}

	/**
	 * POST /signup (status=400).
	 * 
	 * @param exception {@link WebExchangeBindException}
	 * 
	 * @return list as {@link ObjectError}
	 */
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WebExchangeBindException.class)
	public Flux<ObjectError> exceptionHandler(WebExchangeBindException exception) {

		log.debug("エラー件数 {}", exception.getErrorCount());

		return Flux.fromIterable(exception.getAllErrors());
	}

	/**
	 * POST /users/** (status=400).
	 * 
	 * @param exception {@link WebExchangeBindException}
	 * 
	 * @return list as {@link ObjectError}
	 */
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UniqueElementsException.class)
	public Flux<ObjectError> exceptionHandler(UniqueElementsException exception) {

		log.debug("エラー {}", exception.getMessage());
		return Flux.just(new ObjectError(exception.getField(), exception.getMessage()));
	}

	/**
	 * POST /users/** (status=400).
	 * 
	 * @param exception {@link WebExchangeBindException}
	 * 
	 * @return list as {@link ObjectError}
	 */
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	public Flux<ObjectError> exceptionHandler(IncorrectResultSizeDataAccessException exception) {

		log.debug("エラー {}", exception.getMessage());
		return Flux.just(new ObjectError("form", exception.getMessage()));
	}

}