package co.com.kalah.game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.kalah.game.model.GameBoard;
import co.com.kalah.game.repository.GameBoardRepository;

@Service("gameBoardService")
public class GameBoardServiceImpl implements GameBoardService {
	
	@Autowired
	private GameBoardRepository gameBoardRepository;

	@Override
	public List<GameBoard>  findByIdboard(String idboard) {
		return gameBoardRepository.findByIdBoard(idboard);
	}

	@Override
	public GameBoard findByIduser(int iduser) {
		return gameBoardRepository.findByIduser(iduser);
	}

	@Override
	public GameBoard findByIdboardAndIduser(String idBoard, int idUser) {
		return gameBoardRepository.findByIdboardAndIduser(idBoard, idUser);
	}

	@Override
	public void save(GameBoard gameBoard) {
		gameBoardRepository.save(gameBoard);
		
	}

}
