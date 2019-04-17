/**
 * 
 */
package lc.alg.entity;

import java.util.List;
import java.util.Map;

/**
 * @author 李畅
 *	爬虫配置信息类
 */
public class SpiderConfigInfo {
	
	private String targetUrl; // 爬虫运行的网址
	private String taskName;	// 当前任务的名称
	private Map<String,List<String>> attributeParser; // 爬取的属性以及对应的解析规则
	public SpiderConfigInfo(String targetUrl, String taskName, Map<String, List<String>> attributeParser) {
		super();
		this.targetUrl = targetUrl;
		this.taskName = taskName;
		this.attributeParser = attributeParser;
	}
	public SpiderConfigInfo() {
		super();
		// TODO Auto-generated constructor stub
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
	public Map<String, List<String>> getAttributeParser() {
		return attributeParser;
	}
	public void setAttributeParser(Map<String, List<String>> attributeParser) {
		this.attributeParser = attributeParser;
	}
	@Override
	public String toString() {
		return "SpiderConfigInfo [targetUrl=" + targetUrl + ", taskName=" + taskName + ", attributeParser="
				+ attributeParser + "]";
	}
	
	
}
