package com.betthief;

public class Coeficent {

	
	public Coeficent(double mPlayerOneWin, double mX, double mPlayerTwoWin) {
		super();
		this.mPlayerOneWin = mPlayerOneWin;
		this.mX = mX;
		this.mPlayerTwoWin = mPlayerTwoWin;
	}

	private double mPlayerOneWin;
	private double mX;
	private double mPlayerTwoWin;

	
	public double getPlayerOneWin() {
		return mPlayerOneWin;
	}

	public double getX() {
		return mX;
	}

	public double getPlayerTwoWin() {
		return mPlayerTwoWin;
	}

	
}
