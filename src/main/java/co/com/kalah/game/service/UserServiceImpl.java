package co.com.kalah.game.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import co.com.kalah.game.model.Role;
import co.com.kalah.game.model.User;
import co.com.kalah.game.repository.RoleRepository;
import co.com.kalah.game.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public  List<String> findByState(int state) {
		List<User> lUsers = userRepository.findByState(state);
		List<String> lUserNames = new ArrayList<String>();
	
		for(User user: lUsers){
			lUserNames.add(user.getUsername());
		}
		return lUserNames;
	} 

	@Override
	public void saveUser(User user){
		userRepository.save(user);
	}

	@Override
	public void saveNewUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("PLAYER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        user.setLastimeConnected(new Timestamp(System.currentTimeMillis()));
		userRepository.save(user);
	}

	@Override
	public User findUserById(Integer id) {
		return userRepository.findUserById(id);
	}

	
}
