package co.com.kalah.game.dto;

import java.util.List;

public class MessageClient {

	private String type;
	private List<String> availableUsers;
	private String userInvitation;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getAvailableUsers() {
		return availableUsers;
	}

	public void setAvailableUsers(List<String> availableUsers) {
		this.availableUsers = availableUsers;
	}

	public String getUserInvitation() {
		return userInvitation;
	}

	public void setUserInvitation(String userInvitation) {
		this.userInvitation = userInvitation;
	}

	
}
