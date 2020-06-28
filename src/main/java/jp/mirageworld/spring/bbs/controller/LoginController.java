package jp.mirageworld.spring.bbs.controller;

import static org.springframework.security.web.WebAttributes.AUTHENTICATION_EXCEPTION;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jp.mirageworld.spring.bbs.form.LoginForm;

@Controller()
@RequestMapping("/login")
public class LoginController {

	/**
	 * login ページ用（正常時）.
	 * 
	 * @param request リクエスト情報
	 * @param form    入力フォーム
	 * @return 画面表示用ワード（テンプレート、リダイレクト）.
	 */
	@GetMapping("")
	public String login(
			@ModelAttribute("form") LoginForm form,
			Model model) {
		return "login";
	}

	/**
	 * login ページ用（登録完了）.
	 * 
	 * @param request リクエスト情報
	 * @param form    入力フォーム
	 * @return 画面表示用ワード（テンプレート、リダイレクト）.
	 */
	@GetMapping(params = "regist")
	public String login(
			@ModelAttribute("form") LoginForm form,
			@RequestParam(name = "regist", required = false) String regist,
			Model model) {
		model.addAttribute("message", "アカウントの登録が完了しました。");
		return "login";
	}

	/**
	 * login ページ用（異常時）.
	 * 
	 * @param request   リクエスト情報
	 * @param form      入力フォーム
	 * @param exception 認証エラー
	 * @return 画面表示用ワード（テンプレート、リダイレクト）.
	 */
	@GetMapping(params = "error")
	public String login(
			@ModelAttribute("form") LoginForm form,
			@SessionAttribute(
					name = AUTHENTICATION_EXCEPTION,
					required = false //
			) Exception exception,
			@RequestParam(name = "error", required = false) String error,
			Model model) {

		if (exception == null) {
			model.addAttribute("message", "アカウントまたはパスワードが一致しませんでした");
		} else {
			// WebFlux の場合この判定はどうするのか？
			if (exception instanceof DisabledException) {
				model.addAttribute("message", "アカウントが無効です");
			} else if (exception instanceof LockedException) {
				model.addAttribute("message", "アカウントが凍結中です");
			} else if (exception instanceof AccountExpiredException) {
				model.addAttribute("message", "アカウントの有効期限が切れています");
			} else if (exception instanceof CredentialsExpiredException) {
				model.addAttribute("message", "パスワードの有効期限が切れています");
			} else if (exception instanceof BadCredentialsException) {
				model.addAttribute("message", "アカウントまたはパスワードが一致しませんでした");
			} else if (exception instanceof UsernameNotFoundException) {
				model.addAttribute("message", "アカウントまたはパスワードが一致しませんでした");
			} else {
				model.addAttribute("message", exception.getMessage());
			}
		}
		return "login";
	}
}
