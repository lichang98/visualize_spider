/**
 * 
 */
package lc.alg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lc.alg.entity.User;

/**
 * @author ¿Ó≥©
 *
 */
@Controller
public class IndexPageController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String directIndexPage(Model model) {
		model.addAttribute("user",new User("test name","passwd"));
		return "index";
	}
}
