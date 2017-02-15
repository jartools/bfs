package com.bfs.v4;

public class TransformGate {
	public int ID;
	public int fromMapID;
	public int toMapID;
	
	public TransformGate(int iD, int fromMapID, int toMapID) {
		super();
		ID = iD;
		this.fromMapID = fromMapID;
		this.toMapID = toMapID;
	}
	

	public TransformGate(int iD, int fromMapID) {
		this(iD, fromMapID, 0);
	}

	@Override
	public String toString() {
		return "TransformGate [ID=" + ID + ", fromMapID=" + fromMapID
				+ ", toMapID=" + toMapID + "]";
	}
	
}
