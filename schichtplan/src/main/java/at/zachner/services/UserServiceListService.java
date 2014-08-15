package at.zachner.services;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import at.zachner.dao.UserDAO;
import at.zachner.dao.UserDAOFileImpl;
import at.zachner.domain.User;

@ManagedBean
@ViewScoped
public class UserServiceListService {
	
	private UserDAO userDAO = new UserDAOFileImpl();
	
	public List<UserService> getUserServiceList() {
		List<User> userList = userDAO.getAllUsers();
		List<UserService> userServiceList = new ArrayList<UserService>();
		for (User user : userList) {
			UserService userService = new UserService();
			userService.setUser(user);
			userServiceList.add(userService);
		}
		return userServiceList;
	}

}
