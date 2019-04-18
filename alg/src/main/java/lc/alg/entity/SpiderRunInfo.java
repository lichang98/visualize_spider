package lc.alg.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 爬虫性能监测类
 * @author 李畅
 *
 */
@Document(collection="spiderrun_info")
public class SpiderRunInfo {
	private String type; // 内存： mem, 每小时抓取的新闻数量：count
	private List<Map<String,String>> info; // <时间，内存使用> 或<时间， 每小时抓取的新闻数量>
	public SpiderRunInfo(String type, List<Map<String, String>> info) {
		super();
		this.type = type;
		this.info = info;
	}
	public SpiderRunInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Map<String, String>> getInfo() {
		return info;
	}
	public void setInfo(List<Map<String, String>> info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return "SpiderRunInfo [type=" + type + ", info=" + info + "]";
	}
	
	
	
}
