package at.zachner.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import at.zachner.dao.UserDAO;
import at.zachner.dao.UserDAOFileImpl;
import at.zachner.domain.CurrentUser;
import at.zachner.domain.User;
import at.zachner.domain.UserRoleEnum;

@ManagedBean
@ViewScoped
public class UserService {
	
	private User user = new User();	
	private UserDAO userDAO = new UserDAOFileImpl();
	private String password;
	private String passwordRepeat;

	public UserService() {
		List<User> userList = userDAO.getAllUsers();
		if (userList.size() <= 0) {
			User user1 = new User("admin", UserRoleEnum.ADMIN.toString());
			try {
				user1.setPassword(encryptPassword("123"));
				userDAO.add(user1);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			User user2 = new User("user", UserRoleEnum.USER.toString(),50,50,"D"); 
			try {
				user2.setPassword(encryptPassword("123"));
				userDAO.add(user2);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public String logInUser() {
		User foundUser = userDAO.findByUserName(user.getUserName());
		if (foundUser != null) {
			try {
				if (Arrays.equals(foundUser.getPassword(), encryptPassword(getPassword()))) {
					
					ExternalContext eC = 
							FacesContext.getCurrentInstance().getExternalContext();
					Map<String, Object> sMap = eC.getSessionMap();
					CurrentUser cU = (CurrentUser) sMap.get("currentUser");
					cU.setUser(foundUser);
					
					return "/schichtplan.jsf?faces-redirect=true";
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "/logIn.jsf?faces-redirect=true";
	}

	public String logOut() {
		ExternalContext eC = 
				FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sMap = eC.getSessionMap();
		CurrentUser cU = (CurrentUser) sMap.get("currentUser");
		cU.setShowControls(false);
		cU.setUser(null);
		
		return "/logIn.jsf?faces-redirect=true";
	}
	
//	public void advancedViewChanged(ValueChangeEvent e) {
//		ExternalContext eC = 
//				FacesContext.getCurrentInstance().getExternalContext();
//		Map<String, Object> sMap = eC.getSessionMap();
//		CurrentUser cU = (CurrentUser) sMap.get("currentUser");
//		Boolean advancedView = (Boolean) e.getNewValue();
//		if (advancedView != null) {
//			cU.setAdvancedView(advancedView);
//		}
//		FacesContext.getCurrentInstance().renderResponse();
//	}
	
	public String showControls() {
		ExternalContext eC = 
				FacesContext.getCurrentInstance().getExternalContext();
		Map<String,Object> sMap = eC.getSessionMap();
		CurrentUser cU = (CurrentUser) sMap.get("currentUser");
		cU.setShowControls(true);			
		return null;
	}
	
	public String hideControls() {
		ExternalContext eC = 
				FacesContext.getCurrentInstance().getExternalContext();
		Map<String,Object> sMap = eC.getSessionMap();
		CurrentUser cU = (CurrentUser) sMap.get("currentUser");
		cU.setShowControls(false);	
		return null;
	}
	
	
	public String createUser() {
		User foundUser = userDAO.findByUserName(user.getUserName());
		if (foundUser != null) {
			FacesMessage msg = new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					"User already exists!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return null;
		}
		else if (!getPassword()
				.equals(getPasswordRepeat())) {
			FacesMessage msg = new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					"Repeated password does not match with the password!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return null;
		}
		else {
			try {
				user.setPassword(encryptPassword(getPassword()));
				userDAO.add(user);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "/userList.jsf?faces-redirect=true";
	}
	
	private byte[] encryptPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		return md.digest(password.getBytes());
	}

	public String deleteUser() {
		User foundUser = userDAO.findByUserName(user.getUserName());
		if (foundUser == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"User does not exist!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return null;
		}
		else {
			userDAO.delete(foundUser);
		}
		
		return "/userList.jsf?faces-redirect=true";
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserPasswordString() {
		return new String(user.getPassword());
	}
}
