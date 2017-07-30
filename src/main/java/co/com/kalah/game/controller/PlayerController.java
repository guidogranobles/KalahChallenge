package co.com.kalah.game.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.kalah.game.dto.MessageClient;
import co.com.kalah.game.service.UserService;

/**
 * ****************************************************************.
 * 
 * @autor: Guido Granobles
 * @fecha: 28/07/2017 @descripci�n: This class manage all the player updates.
 * @copyright: Copyright � 2017 GG.
 *             ****************************************************************
 */

@Controller
public class PlayerController {

	@Autowired
	private UserService userService;
	
	@Autowired
    private SimpMessagingTemplate template;

	@RequestMapping(value = { "/availablePlayers" }, method = RequestMethod.GET)
	public @ResponseBody List<String> getAvailablePlayer() {
		return userService.findByState(1);
	}

	@MessageMapping("/availablePlayersWS")
	@SendTo("/queue/playerUpdates")
	public  Map<String, Object>  getAvailablePlayerWS() throws Exception {
		List<String> users = userService.findByState(1);
    	Map<String, Object> msg = new HashMap<String,Object>();
    	msg.put("TYPE", "AVAILABLE_PLAYERS");
	    msg.put("PAYLOAD", users);	
		return msg;
	}

	@MessageMapping("/invitationToPlay")
	@SendTo("/queue/playerUpdates")
	public void sendInvitationToPlay(String userDest, Principal principal) throws Exception {
		Map<String, Object> msg = new HashMap<String,Object>();
	    msg.put("TYPE","INVITATION");
	    msg.put("PAYLOAD", principal.getName());
		template.convertAndSendToUser(userDest, "/queue/playerUpdates", msg);
		
	}
	
	@MessageMapping("/resInvitationToPlay")
	@SendTo("/queue/playerUpdates")
	public void resInvitationToPlay(String userResponse) throws Exception {
		String[] response = userResponse.split("-");
		Map<String, Object> msg = new HashMap<String,Object>();
	    msg.put("TYPE","INVITATION_RESPONSE");
	    msg.put("PAYLOAD", response[1]);
		template.convertAndSendToUser(response[0], "/queue/playerUpdates", msg);
		
	}

}
