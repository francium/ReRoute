package cas2xb3.group40;

import java.util.ArrayList;

public class Intersection {

    private static int intersectionNum = 0;

	private int[] coords;
	private String streets;
	private int id;

	Intersection(String str, int x, int y){
		coords = new int[2];
		this.streets = str;
		this.coords[0] = x;
		this.coords[1] = y;
		this.id = intersectionNum++;
	}

	public int[] getCoords(){
		return this.coords;
	}

	public String getStreet(){
		return this.streets;
	}

	public int getID(){
		return this.id;
	}

	public String toString(){
		return "[" + getStreet() + "]";
	}

}
