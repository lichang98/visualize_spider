/**
 * 
 */
package lc.alg.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	
	/**
	 * 将数据库中的新闻导出到文件夹下
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/news_view/dump_files")
	public String dumpNews() throws IOException {
	//	Path path = Paths.get(System.getProperty("user.dir"),"src","main","resources","static","text");
		List<NewsDocs> newsList = mongoTemplate.findAll(NewsDocs.class);
		System.out.println("文件导出控制，新闻数目:"+newsList.size());
		for(int i=0;i<newsList.size();++i) {
			File file = new File(Paths.get(System.getProperty("user.dir"),"src","main","resources","static","text",i+".txt").toString());
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(newsList.get(i).getContent()); //只写入新闻内容
			writer.flush();
			writer.close();
		}
		System.out.println("文件导出控制，新闻文本导出完毕.....");
		//向页面返回结果
		return new Gson().toJson("文件保存路径:"+Paths.get(System.getProperty("user.dir"),"src","main","resources","static","text").toString());
	}
}
