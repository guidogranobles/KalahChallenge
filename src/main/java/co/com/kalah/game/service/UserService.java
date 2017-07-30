package co.com.kalah.game.service;

import java.util.List;

import co.com.kalah.game.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public User findUserByUsername(String username);				
	public void saveNewUser(User user);
	public void saveUser(User user);
	public List<String> findByState(int state);
	public User findUserById(Integer id);
}
