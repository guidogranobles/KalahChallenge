package co.com.kalah.game.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import co.com.kalah.game.model.User;
import co.com.kalah.game.service.UserService;

@Component
public class SessionTimeoutListener implements ApplicationListener<SessionDestroyedEvent> {

	@Autowired
	private UserService userService;

	@Autowired
    private SimpMessagingTemplate template;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
        for (SecurityContext securityContext : event.getSecurityContexts())
        {
            Authentication auth = securityContext.getAuthentication();
            
            User curUser = userService.findUserByUsername(auth.getName());
    		curUser.setState(0);
    		userService.saveUser(curUser);
    		
    		Map<String, Object> msg = new HashMap<String,Object>();
    	    msg.put("TYPE","SESSION_TIMEOUT");
    	    msg.put("PAYLOAD", "");
    		template.convertAndSendToUser(curUser.getUsername(), "/queue/playerUpdates", msg);
    		System.out.println("SESSION_TIMEOUT sent it");
        }
    }

}