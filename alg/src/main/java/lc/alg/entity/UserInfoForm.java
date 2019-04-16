/**
 * 
 */
package lc.alg.entity;

/**
 * @author ¿Ó≥©
 *
 */
public class UserInfoForm {
	private boolean nameValid;
	private boolean passwdValid;
	private boolean passwdSame;
	private boolean emailValid;
	private boolean telephoneValid;
	private boolean qqValid;
	private boolean descriptionValid;
	public UserInfoForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfoForm(boolean nameValid, boolean passwdValid, boolean passwdSame, boolean emailValid,
			boolean telephoneValid, boolean qqValid, boolean descriptionValid) {
		super();
		this.nameValid = nameValid;
		this.passwdValid = passwdValid;
		this.passwdSame = passwdSame;
		this.emailValid = emailValid;
		this.telephoneValid = telephoneValid;
		this.qqValid = qqValid;
		this.descriptionValid = descriptionValid;
	}
	public boolean isNameValid() {
		return nameValid;
	}
	public void setNameValid(boolean nameValid) {
		this.nameValid = nameValid;
	}
	public boolean isPasswdValid() {
		return passwdValid;
	}
	public void setPasswdValid(boolean passwdValid) {
		this.passwdValid = passwdValid;
	}
	public boolean isPasswdSame() {
		return passwdSame;
	}
	public void setPasswdSame(boolean passwdSame) {
		this.passwdSame = passwdSame;
	}
	public boolean isEmailValid() {
		return emailValid;
	}
	public void setEmailValid(boolean emailValid) {
		this.emailValid = emailValid;
	}
	public boolean isTelephoneValid() {
		return telephoneValid;
	}
	public void setTelephoneValid(boolean telephoneValid) {
		this.telephoneValid = telephoneValid;
	}
	public boolean isQqValid() {
		return qqValid;
	}
	public void setQqValid(boolean qqValid) {
		this.qqValid = qqValid;
	}
	public boolean isDescriptionValid() {
		return descriptionValid;
	}
	public void setDescriptionValid(boolean descriptionValid) {
		this.descriptionValid = descriptionValid;
	}
	@Override
	public String toString() {
		return "UserInfoForm [nameValid=" + nameValid + ", passwdValid=" + passwdValid + ", passwdSame=" + passwdSame
				+ ", emailValid=" + emailValid + ", telephoneValid=" + telephoneValid + ", qqValid=" + qqValid
				+ ", descriptionValid=" + descriptionValid + "]";
	}
	
	
	
}
