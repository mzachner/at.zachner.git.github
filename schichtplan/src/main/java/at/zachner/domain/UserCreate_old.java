package at.zachner.domain;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UserCreate_old extends User{

	private String passwordRepeat;

	public UserCreate_old(String userName, String userRole,
			String passwordRepeat) {
		super(userName, userRole);
		this.passwordRepeat = passwordRepeat;
	}
	
	public UserCreate_old() {
		
	}	

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}
	
}
