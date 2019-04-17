/**
 * 
 */
package lc.alg.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lc.alg.entity.User;

/**
 * @author 李畅
 *
 */
@Controller
public class SpiderPageController {

	@RequestMapping("/new_spider")
	public String createNewSpiderController(@ModelAttribute User user,Model model,HttpServletRequest request) {
		model.addAttribute("user_name",request.getParameter("user_name"));
		System.out.println("createnew spider control接收到  model: " + model.toString());
		return "new_spider";
	}
}
