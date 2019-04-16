/**
 * 
 */
package lc.alg.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 李畅
 * 该类映射至mongodb 中的users集合
 */
@Document(collection="users")
public class User{
	
	private String name;
	private String passwd;
	private String passwdConfirm;
	private String email;
	private String telephone;
	private String qq;
	private String description;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String name, String passwd, String passwdConfirm, String email, String telephone, String qq,
			String description) {
		super();
		this.name = name;
		this.passwd = passwd;
		this.passwdConfirm = passwdConfirm;
		this.email = email;
		this.telephone = telephone;
		this.qq = qq;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getPasswdConfirm() {
		return passwdConfirm;
	}
	public void setPasswdConfirm(String passwdConfirm) {
		this.passwdConfirm = passwdConfirm;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", passwd=" + passwd + ", passwdConfirm=" + passwdConfirm + ", email=" + email
				+ ", telephone=" + telephone + ", qq=" + qq + ", description=" + description + "]";
	}
	
	
	
}
