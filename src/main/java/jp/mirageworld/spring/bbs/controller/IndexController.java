package jp.mirageworld.spring.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ホームページ.
 */
@Controller()
@RequestMapping("/")
public class IndexController {

	/**
	 * GET /.
	 * 
	 * @return PAGE.
	 */
	@GetMapping
	public String index() {
		return "index";
	}
}
