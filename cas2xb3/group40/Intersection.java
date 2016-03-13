package cas2xb3.group40;

import java.util.ArrayList;

public class Intersection {

    private static int intersectionNum = 0;

	private double x, y;
	private String streets;
	private int id;

	public Intersection(String s, double x, double y){
		this.streets = s;
        this.x = x;
        this.y = y;
		this.id = intersectionNum++;
	}

	public double getX() {
        return x;
	}

	public double getY() {
        return y;
    }

	public String getStreets() {
		return this.streets;
	}

	public int getId() {
		return this.id;
	}

	public String toString() {
		return "[" + streets + "]";
	}

}
