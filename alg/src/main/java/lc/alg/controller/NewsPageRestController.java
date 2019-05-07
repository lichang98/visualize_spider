/**
 * 
 */
package lc.alg.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Gson gson = new Gson();
	
	/**
	 * 获取新闻数据
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping("/news_view/get_news")
	public String getNews() throws JsonProcessingException {
		System.out.println("新闻数据获取控制");
		List<NewsDocs> newsDataList = mongoTemplate.findAll(NewsDocs.class);	
		System.out.println("获取新闻条数:"+newsDataList.size());
		//清空列表中每个新闻的内容,减少数据传输
		for(int i=0;i<newsDataList.size();++i) {
			newsDataList.get(i).setContent(null);
			newsDataList.get(i).setContent("");
		}
		System.out.println("新闻数据发送中....");
//		Gson gson = new Gson();
//		return gson.toJson(newsDataList);
		return objectMapper.writeValueAsString(newsDataList);
	}
	
	/**
	 * 根据网址获取对应的新闻正文内容
	 * @param urlString
	 * @return
	 */
	@RequestMapping("/news_view/get_news_content")
	public String getNewsContent(@RequestParam String urlString) {
		System.out.println("新闻正文内容获取控制.........");
		System.out.println("当前请求的网址为："+urlString);
		Query query = new Query(Criteria.where("url").is(urlString));
		NewsDocs newsDocs = null;
		if ((newsDocs = mongoTemplate.findOne(query, NewsDocs.class)) != null) {
			return gson.toJson(newsDocs.getContent());
		} else {
			return gson.toJson("");
		}
	}
	
	@RequestMapping("/news_view/del_news")
	public String delNewsByUrl(@RequestParam String url) {
		System.out.println("新闻删除控制, 接收到url="+url);
		//从数据库中删除该条新闻
		mongoTemplate.findAndRemove(new Query(Criteria.where("url").is(url)), NewsDocs.class);
		return gson.toJson("");
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
	
	@RequestMapping("/news_view/download")
	public String downloadNews(HttpServletRequest servletRequest,HttpServletResponse response) throws IOException {
		System.out.println("新闻文件下载处理.........");
		response.setCharacterEncoding(servletRequest.getCharacterEncoding());
		response.setContentType("application/octet-stream");
		FileInputStream fileInputStream=null;
		File file=new File("G:\\file_test.txt");
		fileInputStream=new FileInputStream(file);
		response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
		response.addHeader("Set-Cookie", "fileDownload=true;path=/");
		IOUtils.copy(fileInputStream, response.getOutputStream());
		response.flushBuffer();
		fileInputStream.close();
		return "";
	}
}
