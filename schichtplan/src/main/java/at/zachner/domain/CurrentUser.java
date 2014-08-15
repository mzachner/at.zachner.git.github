package at.zachner.domain;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CurrentUser {
	private User user;
//	private Boolean advancedView;
	private Boolean showControls;

	public CurrentUser() {
//		advancedView = false;
		showControls = false;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public Boolean getAdvancedView() {
//		return advancedView;
//	}
//
//	public void setAdvancedView(Boolean advancedView) {
//		this.advancedView = advancedView;
//	}

	public Boolean getShowControls() {
		return showControls;
	}

	public void setShowControls(Boolean showControls) {
		this.showControls = showControls;
	}
	
	
}
