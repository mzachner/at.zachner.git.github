package at.zachner.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UserRoleList {
	
	private Collection<String> userRoleList = new ArrayList<>();

	public UserRoleList() {
		for (UserRoleEnum uRE : UserRoleEnum.values()) {
			userRoleList.add(uRE.toString());
		}
	}

	public Collection<String> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(Collection<String> userRoleList) {
		this.userRoleList = userRoleList;
	}
}
