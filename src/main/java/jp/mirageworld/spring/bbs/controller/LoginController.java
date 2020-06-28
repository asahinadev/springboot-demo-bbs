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
	 * login �y�[�W�p�i���펞�j.
	 * 
	 * @param request ���N�G�X�g���
	 * @param form    ���̓t�H�[��
	 * @return ��ʕ\���p���[�h�i�e���v���[�g�A���_�C���N�g�j.
	 */
	@GetMapping("")
	public String login(
			@ModelAttribute("form") LoginForm form,
			Model model) {
		return "login";
	}

	/**
	 * login �y�[�W�p�i�o�^�����j.
	 * 
	 * @param request ���N�G�X�g���
	 * @param form    ���̓t�H�[��
	 * @return ��ʕ\���p���[�h�i�e���v���[�g�A���_�C���N�g�j.
	 */
	@GetMapping(params = "regist")
	public String login(
			@ModelAttribute("form") LoginForm form,
			@RequestParam(name = "regist", required = false) String regist,
			Model model) {
		model.addAttribute("message", "�A�J�E���g�̓o�^���������܂����B");
		return "login";
	}

	/**
	 * login �y�[�W�p�i�ُ펞�j.
	 * 
	 * @param request   ���N�G�X�g���
	 * @param form      ���̓t�H�[��
	 * @param exception �F�؃G���[
	 * @return ��ʕ\���p���[�h�i�e���v���[�g�A���_�C���N�g�j.
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
			model.addAttribute("message", "�A�J�E���g�܂��̓p�X���[�h����v���܂���ł���");
		} else {
			// WebFlux �̏ꍇ���̔���͂ǂ�����̂��H
			if (exception instanceof DisabledException) {
				model.addAttribute("message", "�A�J�E���g�������ł�");
			} else if (exception instanceof LockedException) {
				model.addAttribute("message", "�A�J�E���g���������ł�");
			} else if (exception instanceof AccountExpiredException) {
				model.addAttribute("message", "�A�J�E���g�̗L���������؂�Ă��܂�");
			} else if (exception instanceof CredentialsExpiredException) {
				model.addAttribute("message", "�p�X���[�h�̗L���������؂�Ă��܂�");
			} else if (exception instanceof BadCredentialsException) {
				model.addAttribute("message", "�A�J�E���g�܂��̓p�X���[�h����v���܂���ł���");
			} else if (exception instanceof UsernameNotFoundException) {
				model.addAttribute("message", "�A�J�E���g�܂��̓p�X���[�h����v���܂���ł���");
			} else {
				model.addAttribute("message", exception.getMessage());
			}
		}
		return "login";
	}
}
