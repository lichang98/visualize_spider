/**
 * 
 */
package lc.alg.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import lc.alg.entity.SpiderConfigInfo;
import lc.alg.entity.SpiderRunInfo;

/**
 * @author 李畅
 *	用于处理spider 配置、监测相关界面的ajax 数据请求
 */
@RestController
public class SpiderPageRestController {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 获取当前正在运行的爬虫名称
	 * @param model
	 * @return
	 */
	@RequestMapping("/spider_supervise/get_curr_spiders")
	public String getCurrSpiders(Model model) {
		List<String> spiderNameList = new ArrayList<>();
		List<SpiderConfigInfo> spiderInfos = mongoTemplate.findAll(SpiderConfigInfo.class);
		System.out.println("in get current spiders: 查询所有的爬虫数据：" + spiderInfos);
		Iterator<SpiderConfigInfo> iter = spiderInfos.iterator();
		while(iter.hasNext()) {
			spiderNameList.add(iter.next().getTaskName());
		}
		System.out.println("in get current spiders: 所有爬虫的任务名：" + spiderNameList);
		return new Gson().toJson(spiderNameList);
	}
	
	@RequestMapping("/spider_supervise/get_spider_runinfo")
	public String getRunInfo(Model model, @RequestParam String taskName) {
		Gson gson = new Gson();
		taskName = gson.fromJson(taskName, String.class);
		taskName = taskName.split(":")[1].trim();
		System.out.println("in getruninfo: 当前查询的任务名称为：" + taskName);
		Query query = new Query(Criteria.where("taskName").is(taskName));
		SpiderRunInfo spiderRunInfo = mongoTemplate.findOne(query, SpiderRunInfo.class);
		return new Gson().toJson(spiderRunInfo);
	}
}
