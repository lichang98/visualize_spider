/**
 * 
 */
package lc.alg.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import lc.alg.entity.NewsDocs;

/**
 * @author 李畅
 *	新闻分析数据控制
 */
@RestController
public class AnalysisRestController {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * 	获取食品安全事件的事件分布数据
	 * @return
	 */
	@RequestMapping("/analysis_news/time_distribute")
	public String getTimeDistribute() {
		System.out.println("获取事件分布数据控制.......");
		List<NewsDocs> newsList = mongoTemplate.findAll(NewsDocs.class);
		List<String> timeList=new ArrayList<>();
		//提取新闻时间的年份与月
		for(int i=0;i<newsList.size();++i) {
			Matcher match = Pattern.compile("(\\d{4})(\\D*)(\\d{1,2})").matcher(newsList.get(i).getReleaseTime());
			if(match.find()) {
				timeList.add(match.group(1)+"-"+match.group(3));
			}
		}
		Collections.sort(timeList,new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		Map<String,Integer> timeFreq = new HashMap<>();
		for(int i=0;i<timeList.size();++i) {
			if(timeFreq.containsKey(timeList.get(i))) {
				Integer freq = timeFreq.get(timeList.get(i));
				freq = new Integer(freq.intValue()+1);
				timeFreq.put(timeList.get(i), freq);
			}else {
				timeFreq.put(timeList.get(i), new Integer(1));
			}
		}
		List backList = new ArrayList<>();
		for(int i=0;i<timeList.size();++i) {
			backList.add(timeList.get(i));
			backList.add(timeFreq.get(timeList.get(i)));
		}
		Gson gson = new Gson();
		return gson.toJson(backList);
	}
}
