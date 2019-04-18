/**
 * 
 */
package lc.alg.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 李畅
 *	爬虫配置信息类
 */
@Document(collection="spider_config")
public class SpiderConfigInfo {
	
	private String targetUrl; // 爬虫运行的网址
	private String taskName;	// 当前任务的名称
	private List<Map<String,String>> attributeParser; // 爬取的属性以及对应的解析规则
	public SpiderConfigInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SpiderConfigInfo(String targetUrl, String taskName, List<Map<String, String>> attributeParser) {
		super();
		this.targetUrl = targetUrl;
		this.taskName = taskName;
		this.attributeParser = attributeParser;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public List<Map<String, String>> getAttributeParser() {
		return attributeParser;
	}
	public void setAttributeParser(List<Map<String, String>> attributeParser) {
		this.attributeParser = attributeParser;
	}
	@Override
	public String toString() {
		return "SpiderConfigInfo [targetUrl=" + targetUrl + ", taskName=" + taskName + ", attributeParser="
				+ attributeParser + "]";
	}
	
	
}
