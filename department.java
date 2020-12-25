package com.midterm;

public class department {

	private int DEPT_ID;
	private String  NAME;
	public int getDEPT_ID() {
		return DEPT_ID;
	}
	public void setDEPT_ID(int dEPT_ID) {
		DEPT_ID = dEPT_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	@Override
	public String toString()
	{
		return "department [ DEPT_ID=" + DEPT_ID + ",NAME=" +NAME+"]";
	}
}
