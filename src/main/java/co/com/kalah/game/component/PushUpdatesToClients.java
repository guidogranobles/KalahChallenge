package co.com.kalah.game.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.com.kalah.game.service.UserService;

@Component
public class PushUpdatesToClients {

	@Autowired
    private SimpMessagingTemplate template;
	
	@Autowired
	private UserService userService;
	
    @Scheduled(fixedDelay=30000)
    public void publishUpdates(){
    	List<String> users = userService.findByState(1);
    	Map<String, Object> msg = new HashMap<String,Object>();
    	msg.put("TYPE", "AVAILABLE_PLAYERS");
	    msg.put("PAYLOAD", users);
        template.convertAndSend("/queue/playerUpdates", msg);
    }
}
