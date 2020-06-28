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

@Slf4j
@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	protected UserService service;

	/**
	 * create ページ用 (GET).
	 * 
	 * @param request リクエスト情報.
	 * @param form    入力フォーム.
	 * @return 画面表示用ワード（テンプレート、リダイレクト）.
	 */
	@GetMapping
	public String create(@ModelAttribute("form") UserForm form) {

		log.debug("form{} : {}", form);
		return "signup";
	}

	/**
	 * create ページ用 (POST).
	 * 
	 * @param request リクエスト情報.
	 * @param form    入力フォーム.
	 * @param result  バリデーション結果
	 * @return 画面表示用ワード（テンプレート、リダイレクト）.
	 */
	@ResponseBody
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Users> createPost(@Validated @RequestBody UserForm form) {

		log.debug("form{} : {}", form);
		return service.insert(form);
	}

	/**
	 * 入力エラー.
	 * 
	 * @param exception {@link WebExchangeBindException}
	 * @return エラー情報.
	 */
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WebExchangeBindException.class)
	public Flux<ObjectError> exceptionHandler(WebExchangeBindException exception) {

		log.debug("エラー件数：{}", exception.getErrorCount());

		return Flux.fromIterable(exception.getAllErrors());
	}
}