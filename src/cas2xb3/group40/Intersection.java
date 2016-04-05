package cas2xb3.group40;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import java.io.Serializable;
import java.util.ArrayList;

public class Intersection implements Serializable {

    private static int intersectionNum = 0;

	private double x, y;
	private String s1, s2;
	private int id;
    private transient Shape shape;
    private boolean visible;
    private ArrayList<Intersection> adj;
    private ArrayList<Road> adjR;

	public Intersection(String s1, String s2, double x, double y){
		this.s1 = s1;
		this.s2 = s2;
        this.x = x;
        this.y = y;
		this.id = intersectionNum++;
        adj = new ArrayList<>();
        adjR = new ArrayList<>();
        shape = new Circle(0, 0, 0, Color.BLACK);
	}

    public void newCircle() {
        shape = new Circle(0, 0, 0, Color.BLACK);
    }

    public int compareTo(Intersection other, Sortable s) {
        if (s == Sortable.STREET) {
            return (other.s1 + other.s2).compareTo(s1 + s2);

        } else if (s == Sortable.X) {
            if (other.x > x) return -1;
            else if (other.x < x) return 1;
            else return 0;

        } else if (s == Sortable.Y) {
            if (other.y > y) return -1;
            else if (other.y < y) return 1;
            else return 0;
        }

        System.out.println("this statement should never be reached");
        return 0;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Shape getShape() { return this.shape; }

    public void setShape(Shape s) { this.shape = s; }

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
        Road r = new Road(this, i, this.getId(),i.getId(),1.0*10*Math.random());
        adjR.add(r);
    }

    public Road[] adjR(){
    	Road[] adjR = new Road[this.adjR.size()];
    	int i = 0;
    	for (Road r:this.adjR){
    		adjR[i++] = r;
    	}
    	return adjR;
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
