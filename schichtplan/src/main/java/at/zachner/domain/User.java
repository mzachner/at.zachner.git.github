package at.zachner.domain;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private byte[] password;
	private String userRole;
	private Integer tageVorher;
	private Integer tageNachher;
	private String schichtModel;
	
	public User(String userName, String userRole) {
		this.userName = userName;
		this.userRole = userRole;
		this.tageVorher = 6;
		this.tageNachher = 30;
		this.schichtModel = "B";
	}

	public User(String userName, String userRole,
			Integer tageVorher, Integer tageNachher, String schichtModel) {
		this(userName, userRole);
		this.tageVorher = tageVorher;
		this.tageNachher = tageNachher;
		this.schichtModel = schichtModel;
	}
	
	public User() {

	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public byte[] getPassword() {
		return password;
	}
	
	public void setPassword(byte[] password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Integer getTageVorher() {
		return tageVorher;
	}

	public void setTageVorher(Integer tageVorher) {
		this.tageVorher = tageVorher;
	}

	public Integer getTageNachher() {
		return tageNachher;
	}

	public void setTageNachher(Integer tageNachher) {
		this.tageNachher = tageNachher;
	}

	public String getSchichtModel() {
		return schichtModel;
	}

	public void setSchichtModel(String schichtModel) {
		this.schichtModel = schichtModel;
	}
	
}
