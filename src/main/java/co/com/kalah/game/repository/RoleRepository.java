package co.com.kalah.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.kalah.game.model.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByRole(String role);

}
