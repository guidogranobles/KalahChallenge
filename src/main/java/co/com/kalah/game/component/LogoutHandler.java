package co.com.kalah.game.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import co.com.kalah.game.model.User;
import co.com.kalah.game.service.UserService;

@Component
public class LogoutHandler implements LogoutSuccessHandler{
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		
		User curUser = userService.findUserByUsername(auth.getName());
		curUser.setState(0);
		userService.saveUser(curUser);
		
		response.setStatus(HttpServletResponse.SC_OK);		
		response.sendRedirect("/login");
	}
	
}