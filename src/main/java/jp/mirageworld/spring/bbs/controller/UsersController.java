package jp.mirageworld.spring.bbs.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.util.UriComponentsBuilder;

import jp.mirageworld.spring.bbs.entity.Roles;
import jp.mirageworld.spring.bbs.entity.Users;
import jp.mirageworld.spring.bbs.exception.UniqueElementsException;
import jp.mirageworld.spring.bbs.form.UserForm;
import jp.mirageworld.spring.bbs.service.UsersService;
import jp.mirageworld.spring.bbs.validator.group.Create;
import jp.mirageworld.spring.bbs.validator.group.Update;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * アカウント管理.
 */
@Slf4j
@Controller
@RequestMapping("/users")
public class UsersController {

	@ModelAttribute("title")
	public String getTitle() {
		return "USERS";
	}

	@Autowired
	UsersService usersService;

	@ModelAttribute("roles")
	public Roles[] roles() {
		return Roles.values();
	}

	/**
	 * GET /users.
	 * 
	 * @param model {@link Model}
	 * @param page  {@link Pageable} 仮
	 * 
	 * @return PAGE.
	 */
	@GetMapping
	public String index(Model model) {

		model.addAttribute("users", usersService.findAll());
		return "users/index";

	}

	/**
	 * GET /users/add.
	 * 
	 * @param form {@link UserForm
	 * 
	 * @return PAGE.
	 */
	@GetMapping("add")
	public String addGet(
			Model model,
			@ModelAttribute("form") UserForm form) {

		model.addAttribute("redirect", UriComponentsBuilder.fromPath("/users").build().toUriString());
		model.addAttribute("method", "post");
		model.addAttribute("action", UriComponentsBuilder.fromPath("/users/add").build().toUriString());
		model.addAttribute("button", "登録");
		model.addAttribute("passwordRequired", true);
		model.addAttribute("confime", "この内容で登録します。よろしいですか？");

		return "users/form";
	}

	/**
	 * POST /users/add.
	 * 
	 * @param form {@link UserForm
	 * @return {@link Users}
	 */
	@PostMapping("add")
	@ResponseBody
	public Mono<Users> addPost(
			@Validated(Create.class) @ModelAttribute("form") UserForm form) {

		return usersService.insert(form);
	}

	/**
	 * GET /users/{id}.
	 * 
	 * @param id   {@link Users#getId()}
	 * @param form {@link UserForm}
	 * @return PAGE.
	 */
	@GetMapping("{id}")
	public String modifyGet(
			Model model,
			@PathVariable String id,
			@ModelAttribute("form") UserForm form) {

		model.addAttribute("redirect", UriComponentsBuilder.fromPath("/users").build().toUriString());
		model.addAttribute("method", "post");
		model.addAttribute("action", UriComponentsBuilder.fromPath("/users/{id}").build(id).toString());
		model.addAttribute("button", "更新");
		model.addAttribute("passwordRequired", false);
		model.addAttribute("confime", "この内容で更新します。よろしいですか？");

		Users user = usersService.findById(id).block();
		BeanUtils.copyProperties(user, form, "password");

		return "users/form";
	}

	/**
	 * GET /users/{id}?create.
	 * 
	 * @param model {@link Model}
	 * @param id    {@link Users#getId()}
	 * @param form  {@link UserForm}
	 * @return PAGE.
	 */
	@GetMapping(name = "{id}", params = "delete")
	public String modifyGetByDelete(
			Model model,
			@PathVariable String id,
			@ModelAttribute("form") UserForm form) {

		model.addAttribute("redirect", UriComponentsBuilder.fromPath("/users").build().toUriString());
		model.addAttribute("method", "delete");
		model.addAttribute("action", UriComponentsBuilder.fromPath("/users/{id}").build(id).toString());
		model.addAttribute("button", "削除");
		model.addAttribute("passwordRequired", false);
		model.addAttribute("confime", "現在表示中のデータを削除します。よろしいですか？");

		Users user = usersService.findById(id).block();
		BeanUtils.copyProperties(user, form, "password");

		return "users/form";
	}

	/**
	 * GET /users/{id}?create.
	 * 
	 * @param model {@link Model}
	 * @param id    {@link Users#getId()}
	 * @param form  {@link UserForm}
	 * @return PAGE.
	 */
	@GetMapping(name = "{id}", params = "create")
	public String modifyGetByCreate(
			Model model,
			@PathVariable String id,
			@ModelAttribute("form") UserForm form) {

		model.addAttribute("message", "登録が正常に完了しました。");
		return modifyGet(model, id, form);
	}

	/**
	 * GET /users/{id}?modify.
	 * 
	 * @param model {@link Model}
	 * @param id    {@link Users#getId()}
	 * @param form  {@link UserForm}
	 * @return PAGE.
	 */
	@GetMapping(name = "{id}", params = "modify")
	public String modifyGetModify(
			Model model,
			@PathVariable String id,
			@ModelAttribute("form") UserForm form) {

		model.addAttribute("message", "更新が正常に完了しました。");
		return modifyGet(model, id, form);
	}

	/**
	 * POST /users/{id}.
	 * 
	 * @param id   {@link Users#getId()}
	 * @param form {@link UserForm}
	 * @return {@link Users}
	 */
	@PostMapping("{id}")
	@ResponseBody
	public Mono<Users> modifyPost(
			@PathVariable String id,
			@Validated(Update.class) @RequestBody UserForm form) {

		Users user = usersService.findById(id).block();
		return usersService.update(user, form);
	}

	/**
	 * DELETE /users/{id}.
	 * 
	 * @param id {@link Users#getId()}
	 * @return {@link Users}
	 */
	@DeleteMapping("{id}")
	public Mono<Users> delete(@PathVariable String id) {

		Users user = usersService.findById(id).block();
		return usersService.delete(user);
	}

	/**
	 * POST /users/** (status=400).
	 * 
	 * @param exception {@link WebExchangeBindException}
	 * 
	 * @return list as {@link ObjectError}
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WebExchangeBindException.class)
	public Flux<ObjectError> exceptionHandler(WebExchangeBindException exception) {

		log.debug("エラー件数 {}", exception.getErrorCount());
		if (log.isDebugEnabled()) {
			exception.getAllErrors().forEach(e -> {
				log.debug("{}:{}", e.getObjectName(), e.getDefaultMessage());
			});
		}

		return Flux.fromIterable(exception.getAllErrors());
	}

	/**
	 * POST /users/** (status=400).
	 * 
	 * @param exception {@link WebExchangeBindException}
	 * 
	 * @return list as {@link ObjectError}
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UniqueElementsException.class)
	public Flux<ObjectError> exceptionHandler(UniqueElementsException exception) {

		log.debug("エラー {}", exception.getMessage());

		return Mono.<ObjectError>create((e) -> {
			e.success(new ObjectError(exception.getField(), exception.getMessage()));
		}).flux();
	}

	/**
	 * POST /users/** (status=400).
	 * 
	 * @param exception {@link WebExchangeBindException}
	 * 
	 * @return list as {@link ObjectError}
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	public Flux<ObjectError> exceptionHandler(IncorrectResultSizeDataAccessException exception) {

		log.debug("エラー {}", exception.getMessage());

		return Mono.<ObjectError>create((e) -> {
			e.success(new ObjectError("form", exception.getMessage()));
		}).flux();
	}

}