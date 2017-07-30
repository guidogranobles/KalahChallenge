package co.com.kalah.game.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.kalah.game.model.GameBoard;

@Repository("gameBoardRepository")
public interface GameBoardRepository extends JpaRepository<GameBoard, Long> {
	 List<GameBoard>  findByIdBoard(String idboard);
	 GameBoard findByIduser(int iduser);	 
	 @Query(value="SELECT g FROM GameBoard g where g.idBoard= :idHashBoard and g.iduser= :idUser")
	 GameBoard findByIdboardAndIduser(@Param(value = "idHashBoard") String idHashBoard, @Param(value = "idUser") int idUser);
}
