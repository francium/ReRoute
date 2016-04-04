package cas2xb3.group40;

import java.io.Serializable;

public class Road implements Serializable {
	private int i1, i2;
    private Intersection intsec1, intsec2;
	private double wt;

	Road(Intersection intsec1, Intersection intsec2, int i1, int i2, double wt){
        this.intsec1 = intsec1;
        this.intsec2 = intsec2;
		this.i1 = i1;
		this.i2 = i2;
		this.wt = wt;
	}

	public int oneI(){
		return this.i1;
	}

	public int otherI(){
		return this.i2;
	}

	public double weight(){
		return this.wt;
	}

    public String toString() {
        return intsec1 + " -> " + intsec2;
    }
}
