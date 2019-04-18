/**
 * 
 */
package lc.alg.entity;

/**
 * @author ¿Ó≥©
 *
 */
public class SpiderConfigForm {

	private SpiderConfigInfo spiderConfigInfo;
	private String userName;
	public SpiderConfigForm(SpiderConfigInfo spiderConfigInfo, String userName) {
		super();
		this.spiderConfigInfo = spiderConfigInfo;
		this.userName = userName;
	}
	public SpiderConfigForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SpiderConfigInfo getSpiderConfigInfo() {
		return spiderConfigInfo;
	}
	public void setSpiderConfigInfo(SpiderConfigInfo spiderConfigInfo) {
		this.spiderConfigInfo = spiderConfigInfo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "SpiderConfigForm [spiderConfigInfo=" + spiderConfigInfo + ", userName=" + userName + "]";
	}
	
	
}
