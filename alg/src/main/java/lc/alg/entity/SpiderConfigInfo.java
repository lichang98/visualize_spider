/**
 * 
 */
package lc.alg.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author �
 *	����������Ϣ��
 */
@Document(collection="spider_config")
public class SpiderConfigInfo {
	
	private String targetUrl; // �������е���ַ
	private String taskName;	// ��ǰ���������
	private List<Map<String,String>> attributeParser; // ��ȡ�������Լ���Ӧ�Ľ�������
	private String curStatus;	//���浱ǰ��״̬, running, finish,pending
	public SpiderConfigInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SpiderConfigInfo(String targetUrl, String taskName, List<Map<String, String>> attributeParser,
			String curStatus) {
		super();
		this.targetUrl = targetUrl;
		this.taskName = taskName;
		this.attributeParser = attributeParser;
		this.curStatus = curStatus;
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
	public String getCurStatus() {
		return curStatus;
	}
	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}
	@Override
	public String toString() {
		return "SpiderConfigInfo [targetUrl=" + targetUrl + ", taskName=" + taskName + ", attributeParser="
				+ attributeParser + ", curStatus=" + curStatus + "]";
	}
	
}