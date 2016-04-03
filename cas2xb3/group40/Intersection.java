package cas2xb3.group40;

import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Intersection {

    private static int intersectionNum = 0;

	private double x, y;
	private String s1, s2;
	private int id;
    private Shape shape;
    private boolean visible;
    private ArrayList<Intersection> adj;
    private ArrayList<Road> adjR;//ADDED

	public Intersection(String s1, String s2, double x, double y){
		this.s1 = s1;
		this.s2 = s2;
        this.x = x;
        this.y = y;
		this.id = intersectionNum++;
        adj = new ArrayList<>();
        adjR = new ArrayList<>();
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
	
	//MODIFIED
    public void addAdjacent(Intersection i) {
        adj.add(i);
        Road r = new Road(this.getId(),i.getId(),1.0);//ADDED
        adjR.add(r);//ADDED
    }
    
    //ADDED
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
