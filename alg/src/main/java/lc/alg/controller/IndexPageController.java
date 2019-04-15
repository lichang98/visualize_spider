/**
 * 
 */
package lc.alg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lc.alg.entity.User;


/**
 * @author 李畅
 *
 */
@Controller
public class IndexPageController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(value="/")
	public String directIndexPage(Model model) {
		model.addAttribute("user",new User());
		return "index";
	}
	
	/***
	 * 
	 * @param User
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login")
	public String login2MainPage(@ModelAttribute User user,Model model) {
		System.out.println("获得表单数据：" + user);
		Criteria criteria = new Criteria();
		criteria.and("name").is(user.getName());
		criteria.and("passwd").is(user.getPasswd());
		Query query = new Query(criteria);
		if(mongoTemplate.findOne(query, User.class) == null) {
			System.out.println("用户信息数据库验证失败!!");
			model.addAttribute("response","fail");
			return "index";
		}else {
			System.out.println("用户信息数据库验证成功!!");
			return "main";
		}
	}
//	/**
//	 * 用户点击登录后的请求地址
//	 */
//	@RequestMapping(value="/login")
//	public String loginControl(@RequestBody String reqData,@ModelAttribute User user,Model model) {
//		System.out.println("controller 接收到数据：" + reqData);
//		return "index";
//	}
}

