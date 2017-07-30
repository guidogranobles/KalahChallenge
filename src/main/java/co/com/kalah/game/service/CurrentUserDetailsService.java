package co.com.kalah.game.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.com.kalah.game.model.CurrentUser;
import co.com.kalah.game.model.User;

@Service("currentUserDetailsService")
public class CurrentUserDetailsService implements UserDetailsService {
	private final UserService userService;

	@Autowired
	public CurrentUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Transactional
	@Override
	public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(String.format("User with email=%s was not found", username));

		CurrentUser currentUser = new CurrentUser(user);
		
		return currentUser;
	}
}