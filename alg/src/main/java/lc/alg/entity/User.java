/**
 * 
 */
package lc.alg.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 李畅
 * 该类映射至mongodb 中的users集合
 */
@Document(collection="users")
public class User{
	
	private String name;
	private String passwd;
	public User(String name, String passwd) {
		super();
		this.name = name;
		this.passwd = passwd;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
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
	@Override
	public String toString() {
		return "User [name=" + name + ", passwd=" + passwd + "]";
	}
	
	
}
