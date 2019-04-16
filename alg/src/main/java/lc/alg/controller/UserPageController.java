/**
 * 
 */
package lc.alg.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
public class UserPageController {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@RequestMapping("/user/info_post")
	public String userInfoChange(@ModelAttribute User user,@ModelAttribute UserInfoForm userInfoForm,Model model) {
		System.out.println("控制层接收到user: " + user);
		//对表单数据进行检验
		userInfoForm = new UserInfoForm(true,true,true,true,true,true,true); // 用于向前端传递验证信息
		if(user.getPasswd().length() < 6) {
			userInfoForm.setPasswdValid(false);
		}else {
			String passwd = new String(user.getPasswd());
			passwd = passwd.toLowerCase();
			char[] passwdArr = passwd.toCharArray();
			for(int i=0;i<passwd.length();i++) {
				if((passwdArr[i]  >= '0' && passwdArr[i] <= '9') ||
						passwdArr[i] >= 'a' && passwdArr[i] <= 'z') {
					continue;
				}else {
					userInfoForm.setPasswdValid(false);
				}
			}
		}
		if(!user.getPasswd().equals(user.getPasswdConfirm()))
			userInfoForm.setPasswdSame(false);
		String pattern="^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.com";
		if(!Pattern.matches(pattern, user.getEmail())) {
			userInfoForm.setEmailValid(false);
		}
		pattern="\\d{8,}";
		if(!Pattern.matches(pattern, user.getTelephone())) {
			userInfoForm.setTelephoneValid(false);
		}
		pattern="\\d+";
		if(!Pattern.matches(pattern, user.getQq())) {
			userInfoForm.setQqValid(false);
		}
		if(user.getDescription().trim().length()==0) {
			userInfoForm.setDescriptionValid(false);
		}
		System.out.println("控制层反馈消息：" + userInfoForm);
		model.addAttribute("response",userInfoForm);
		// 将修改后的用户信息更新到mongodb 数据库中
		if(userInfoForm.isNameValid() && userInfoForm.isPasswdValid()
				&& userInfoForm.isPasswdSame() && userInfoForm.isEmailValid()
				&& userInfoForm.isTelephoneValid() && userInfoForm.isQqValid()
				&& userInfoForm.isDescriptionValid()) {
			Query query = new Query(Criteria.where("name").is(user.getName()));
			Update update = new Update().set("passwd", user.getPasswd())
					.set("email", user.getEmail()).set("telephone", user.getTelephone())
					.set("qq", user.getQq()).set("description", user.getDescription());
			mongoTemplate.updateFirst(query, update, User.class);
			model.addAttribute("userform_check","yes");
		}
		return "user";
	}
}
