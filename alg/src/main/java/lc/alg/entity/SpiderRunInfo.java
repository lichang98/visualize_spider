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
	private String taskName;	//爬虫的任务名称
	private List<Map<String,String>> memInfo;	// 内存占用随时间的变换情况
	private List<Map<String,String>> getCountInfo;	//获取的新闻数量随时间的变化情况
	private String runlog;	//爬虫运行日志
	public SpiderRunInfo(String taskName, List<Map<String, String>> memInfo, List<Map<String, String>> getCountInfo,
			String runlog) {
		super();
		this.taskName = taskName;
		this.memInfo = memInfo;
		this.getCountInfo = getCountInfo;
		this.runlog = runlog;
	}
	public SpiderRunInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public List<Map<String, String>> getMemInfo() {
		return memInfo;
	}
	public void setMemInfo(List<Map<String, String>> memInfo) {
		this.memInfo = memInfo;
	}
	public List<Map<String, String>> getGetCountInfo() {
		return getCountInfo;
	}
	public void setGetCountInfo(List<Map<String, String>> getCountInfo) {
		this.getCountInfo = getCountInfo;
	}
	public String getRunlog() {
		return runlog;
	}
	public void setRunlog(String runlog) {
		this.runlog = runlog;
	}
	@Override
	public String toString() {
		return "SpiderRunInfo [taskName=" + taskName + ", memInfo=" + memInfo + ", getCountInfo=" + getCountInfo
				+ ", runlog=" + runlog + "]";
	}
}
