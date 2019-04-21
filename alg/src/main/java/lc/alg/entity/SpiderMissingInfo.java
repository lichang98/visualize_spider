/**
 * 
 */
package lc.alg.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Àî³©
 *	ÅÀ³æ×Ö¶ÎÈ±Ê§¼ÇÂ¼
 */
@Document(collection="spider_missing_info")
public class SpiderMissingInfo {

	private String url;
	private boolean title;	//¸Ã×Ö¶Î´æÔÚ: true ,×Ö¶ÎÈ±Ê§: false
	private boolean releaseTime;
	private boolean content;
	public SpiderMissingInfo(String url, boolean title, boolean releaseTime, boolean content) {
		super();
		this.url = url;
		this.title = title;
		this.releaseTime = releaseTime;
		this.content = content;
	}
	public SpiderMissingInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isTitle() {
		return title;
	}
	public void setTitle(boolean title) {
		this.title = title;
	}
	public boolean isReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(boolean releaseTime) {
		this.releaseTime = releaseTime;
	}
	public boolean isContent() {
		return content;
	}
	public void setContent(boolean content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "SpiderMissingInfo [url=" + url + ", title=" + title + ", releaseTime=" + releaseTime + ", content="
				+ content + "]";
	}
	
	
}
