package cas2xb3.group40;

import java.util.ArrayList;

public class Intersection {

    private static int intersectionNum = 0;

	private double x, y;
	private String s1, s2;
	private int id;
    private ArrayList<Intersection> adj;

	public Intersection(String s1, String s2, double x, double y){
		this.s1 = s1;
		this.s2 = s2;
        this.x = x;
        this.y = y;
		this.id = intersectionNum++;
        adj = new ArrayList<>();
	}

	public double getX() {
        return x;
	}

	public double getY() {
        return y;
    }

	public String[] getStreets() {
		return new String[] {s1, s2};
	}

	public int getId() {
		return this.id;
	}

    public void addAdjacent(Intersection i) {
        adj.add(i);
    }

    public Intersection[] getAdjacent() {
        Intersection[] adj = new Intersection[this.adj.size()];
        int i = 0;
        for (Intersection intsec: this.adj) {
            adj[i++] = intsec;
        }
        return adj;
    }

    @Override
	public String toString() {
		return "[" + s1  + " and " + s2 + "]";
	}

    @Override
    public boolean equals(Object other) {
        if (this.id == ((Intersection)other).id) return true;
        return false;
    }

}
