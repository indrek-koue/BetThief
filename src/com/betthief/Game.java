package com.betthief;

public class Game {

	private String mLeague;
	private String mPlayerOne;
	private String mPlayerTwo;
	private Coeficent mCoeficent;
	private String mTime;

	public Game(String league, String playerOne, String playerTwo, String time) {
		mLeague = league;
		mPlayerOne = playerOne;
		mPlayerTwo = playerTwo;
		setmTime(time);

	}

	@Override
	public String toString() {

		return String.format("[%s] %s VS %s @ %s %.2f : %.2f : %.2f", mTime,
				mPlayerOne, mPlayerTwo, mLeague, mCoeficent.getPlayerOneWin(),
				mCoeficent.getX(), mCoeficent.getPlayerTwoWin());
	}

	public String getLeague() {
		return mLeague;
	}

	public String getPlayerOne() {
		return mPlayerOne;
	}

	public String getPlayerTwo() {
		return mPlayerTwo;
	}

	public Coeficent getCoeficent() {
		return mCoeficent;
	}

	public void setCoeficent(Coeficent coeficent) {
		this.mCoeficent = coeficent;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

}
