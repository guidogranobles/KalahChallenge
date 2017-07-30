package co.com.kalah.game.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import co.com.kalah.game.model.User;
import co.com.kalah.game.service.UserService;

/**
 * ****************************************************************.
 * 
 * @autor: Guido Granobles
 * @fecha: 27/07/2017 @descripci�n: This class manage all the login and registration operations.
 * @copyright: Copyright � 2017 GG.
 *             ****************************************************************
 */

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = { "/game/dashboard" }, method = RequestMethod.GET)
	public ModelAndView loginIn() {
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findUserByUsername(auth.getName());
		user.setState(1);
		userService.saveUser(user);

		modelAndView.addObject("userName", user.getUsername()); 
		modelAndView.setViewName("game/game-dashboard");
		
		return modelAndView;
	}


	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		
		if (userExists == null) {
			userExists = userService.findUserByUsername(user.getUsername());
		}
		
		if(userExists != null){
			bindingResult.rejectValue("password", "error.user",
					"There is already a user registered with the email or username provided");

		}
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("login");
		} else {
			user.setState(2);
			userService.saveNewUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("login");

		}
		return modelAndView;
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName",
				 user.getName());
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

}
