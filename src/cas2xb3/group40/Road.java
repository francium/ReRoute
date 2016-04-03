package cas2xb3.group40;

public class Road {
	private int i1, i2;
	private double wt;

	Road(int i1, int i2, double wt){
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

	public String toString(){
		return i1 + " -> " + i2 + " (" + wt + ")";
	}
}
