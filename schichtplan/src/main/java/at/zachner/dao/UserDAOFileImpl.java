package at.zachner.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.zachner.domain.User;
import at.zachner.domain.UserRoleEnum;

public class UserDAOFileImpl implements UserDAO {

	@Override
	public void add(User user) {
		Map<String, User> userMap = readAllUser();
		if (!userMap.containsKey(user.getUserName())) {
			userMap.put(user.getUserName(), user);
		}
		writeAllUser(userMap);
		
	}

	@Override
	public void update(User user) {
		Map<String, User> userMap = readAllUser();
		userMap.put(user.getUserName(), user);
		writeAllUser(userMap);
		
	}

	@Override
	public void delete(User user) {
		Map<String, User> userMap = readAllUser();
		if (userMap.containsKey(user.getUserName())) {
			userMap.remove(user.getUserName());
		}
		writeAllUser(userMap);
		
	}

	@Override
	public User findByUserName(String userName) {
		List<User> userList = getAllUsers();
		for (User user : userList) {
			if (user.getUserName().equals(userName)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {		
		Map<String, User> userMap = readAllUser();
		List<User> userList = new ArrayList<>();
		if (userMap.size() > 0) {
			for (User user : userMap.values()) {
				userList.add(user);
			}	
		}
		return userList;
	}

	private Map<String, User> readAllUser() {
		Map<String,User> userMap = new HashMap<>();
		
		try (FileInputStream fIS = new FileInputStream("userList.dat");
				ObjectInputStream oIS = new ObjectInputStream(fIS)) {
			
			userMap = (Map<String, User>) oIS.readObject();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userMap;
	}
	
	private void writeAllUser(Map<String,User> userMap) {
		try (FileOutputStream fOS = new FileOutputStream("userList.dat");
				ObjectOutputStream oOS = new ObjectOutputStream(fOS)) {
			
			oOS.writeObject(userMap);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
