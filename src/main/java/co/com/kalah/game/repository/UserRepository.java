package co.com.kalah.game.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.com.kalah.game.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
	 User findByUsername(String username);
	 User findUserById(Integer id);
	 @Query(value="select * from User where state=?1  order by lastimeconnected desc limit 10", nativeQuery = true)
	 List<User> findByState(int state);
}
