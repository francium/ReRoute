package cas2xb3.group40;

import java.util.ArrayList;

public class Intersection {
	private int[] coords;
	private String streets;
	private int ID;
	
	Intersection(String str, int x, int y, int ID){
		coords = new int[2];
		this.streets = str;
		this.coords[0] = x;
		this.coords[1] = y;
		this.ID = ID;
	}
	
	public int[] getCoords(){
		return this.coords;
	}
	
	public String getStreet(){
		return this.streets;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public String toString(){
		return "[" + getStreet() + "]";
	}
	
}
