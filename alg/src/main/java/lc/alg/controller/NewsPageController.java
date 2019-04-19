/**
 * 
 */
package lc.alg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ¿Ó≥©
 *
 */
@Controller
public class NewsPageController {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@RequestMapping("/news_view")
	public String viewNewsControl(Model model,@RequestParam String user_name) {
		model.addAttribute("user_name",user_name);
		return "news_view";
	}
}
