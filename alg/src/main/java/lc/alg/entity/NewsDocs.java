/**
 * 
 */
package lc.alg.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 李畅
 *	该类对应mongodb 数据库中新闻文本的相关信息
 */
@Document(collection="news_docs")
public class NewsDocs {

	private String title;
	private String url;
	private String releaseTime;
	private String content;
	public NewsDocs() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NewsDocs(String title, String url, String releaseTime, String content) {
		super();
		this.title = title;
		this.url = url;
		this.releaseTime = releaseTime;
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "NewsDocs [title=" + title + ", url=" + url + ", releaseTime=" + releaseTime + ", content=" + content
				+ "]";
	}
	
	
}
