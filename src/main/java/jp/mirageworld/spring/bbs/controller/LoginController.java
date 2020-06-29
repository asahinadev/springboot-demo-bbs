package jp.mirageworld.spring.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.mirageworld.spring.bbs.form.LoginForm;

/**
 * ログイン画面.
 */
@Controller()
@RequestMapping("/login")
public class LoginController {

	@ModelAttribute("title")
	public String getTitle() {
		return "LOGIN";
	}

	/**
	 * GET /login.
	 * 
	 * @param form  {@link LoginForm}
	 * @param model {@link Model}
	 * 
	 * @return PAGE.
	 */
	@GetMapping("")
	public String login(
			@ModelAttribute("form") LoginForm form,
			Model model) {
		return "login";
	}

	/**
	 * GET /login?regist.
	 * 
	 * @param form  {@link LoginForm}
	 * @param model {@link Model}
	 * 
	 * @return PAGE.
	 */
	@GetMapping(params = "regist")
	public String loginByRegist(
			@ModelAttribute("form") LoginForm form,
			Model model) {

		model.addAttribute("message", "登録が正常に完了しました。");
		return "login";
	}

	/**
	 * GET /login?error.
	 * 
	 * @param form  {@link LoginForm}
	 * @param model {@link Model}
	 * 
	 * @return PAGE.
	 */
	@GetMapping(params = "error")
	public String loginByError(
			@ModelAttribute("form") LoginForm form,
			Model model) {

		model.addAttribute("message", "ログインに失敗しました。");
		return "login";
	}
}
