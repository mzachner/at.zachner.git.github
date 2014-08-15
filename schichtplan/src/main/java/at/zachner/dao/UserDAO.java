package at.zachner.dao;

import java.util.Collection;
import java.util.List;

import at.zachner.domain.User;

public interface UserDAO {

	public void add(User user);
	public void update(User user);
	public void delete(User user);
	public User findByUserName(String userName);
	public List<User> getAllUsers();
	
}
