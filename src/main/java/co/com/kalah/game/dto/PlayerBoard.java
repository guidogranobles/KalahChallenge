package co.com.kalah.game.dto;

public class PlayerBoard{
	
	private Integer pit1;
	private Integer pit2;
	private Integer pit3;
	private Integer pit4;
	private Integer pit5;
	private Integer pit6;
	
	private Integer kalah;
	
	public PlayerBoard(){
		pit1 = pit2 = pit3 = pit4 = pit5 = pit6 = 6;
		kalah = 0;
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
	
}