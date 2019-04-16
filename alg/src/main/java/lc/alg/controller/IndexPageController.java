/**
 * 
 */
package lc.alg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lc.alg.entity.User;
import lc.alg.entity.UserInfoForm;


/**
 * @author 李畅
 *
 */
@Controller
public class IndexPageController {
	
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
			model.addAttribute("response",new UserInfoForm(true,true,true,true,true,true,true));
			System.out.println("model : " + model.toString());
			return "user";
		}
	}
	
	/***
	 * 转向用户信息管理界面
	 */
	@RequestMapping(value="/user")
	public String userInfoControl(@ModelAttribute User user,@ModelAttribute UserInfoForm userInfoForm,Model model) {
		System.out.println("转向用户信息管理界面....");
		return "user";
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

