package co.com.kalah.game.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gameboard")
public class GameBoard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idGame;
	
	@Column(name = "idboard")
	private String idBoard;
	
	@Column(name = "iduser")
	private Integer iduser;
	
	private Integer pit1;
	private Integer pit2;
	private Integer pit3;
	private Integer pit4;
	private Integer pit5;
	private Integer pit6;
	
	private Integer kalah;
	
	private String status;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="iduser", insertable = false, updatable = false)
	private User player;


	public Integer getIduser() {
		return iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	public Integer getIdGame() {
		return idGame;
	}

	public void setIdGame(Integer idGame) {
		this.idGame = idGame;
	}

	public Integer getPit1() {
		return pit1;
	}

	public void setPit1(Integer pit1) {
		this.pit1 = pit1;
	}

	public Integer getPit2() {
		return pit2;
	}

	public void setPit2(Integer pit2) {
		this.pit2 = pit2;
	}

	public Integer getPit3() {
		return pit3;
	}

	public void setPit3(Integer pit3) {
		this.pit3 = pit3;
	}

	public Integer getPit4() {
		return pit4;
	}

	public void setPit4(Integer pit4) {
		this.pit4 = pit4;
	}

	public Integer getPit5() {
		return pit5;
	}

	public void setPit5(Integer pit5) {
		this.pit5 = pit5;
	}

	public Integer getPit6() {
		return pit6;
	}

	public void setPit6(Integer pit6) {
		this.pit6 = pit6;
	}

	public Integer getKalah() {
		return kalah;
	}

	public void setKalah(Integer kalah) {
		this.kalah = kalah;
	}

	public String getIdBoard() {
		return idBoard;
	}

	public void setIdBoard(String idBoard) {
		this.idBoard = idBoard;
	}

	
	public void initPits(){
		pit1 = pit2 = pit3 = pit4 = pit5 = pit6 = 6;
		kalah = 0;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}