/**
 * 
 */
package lc.alg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import lc.alg.entity.NewsDocs;

/**
 * @author 李畅
 *
 */
@RestController
public class NewsPageRestController {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * 获取新闻数据
	 * @return
	 */
	@RequestMapping("/news_view/get_news")
	public String getNews() {
		System.out.println("新闻数据获取控制");
		List<NewsDocs> newsDataList = mongoTemplate.findAll(NewsDocs.class);	
		System.out.println("获取新闻条数:"+newsDataList.size());
		Gson gson = new Gson();
		return gson.toJson(newsDataList);
	}
	
	@RequestMapping("/news_view/del_news")
	public String delNewsByUrl(@RequestParam String url) {
		System.out.println("新闻删除控制, 接收到url="+url);
		//从数据库中删除该条新闻
		mongoTemplate.findAndRemove(new Query(Criteria.where("url").is(url)), NewsDocs.class);
		return new Gson().toJson("");
	}
}
