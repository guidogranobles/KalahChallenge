package co.com.kalah.game.model;

import java.util.Set;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return user.getId();
    }

    public Set<Role> getRoles() {
        return user.getRoles();
    }

}