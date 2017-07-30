package co.com.kalah.game.service;

import java.util.List;

import co.com.kalah.game.model.GameBoard;

public interface GameBoardService {

	List<GameBoard>  findByIdboard(String idboard);
	GameBoard findByIduser(int iduser);	 
	 GameBoard findByIdboardAndIduser(String idBoard, int idUser);
	 void save(GameBoard gameBoard);
}
