package cas2xb3.group40;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
	private ArrayList<ArrayList<Intersection>> adj;
	private ArrayList<Intersection> all;
    private int nV = 0;
    private int nE = 0;

    public Graph(){
        this.adj = new ArrayList<ArrayList<Intersection>>();
    }

    public Graph(int nV){
        this.adj = new ArrayList<ArrayList<Intersection>>();
        this.all = new ArrayList<Intersection>();
        for (int i = 0; i < nV; ++i) {
            this.adj.add(new ArrayList<Intersection>());
            this.nV ++;
        }
    }

    public void addEdge(Intersection v, Intersection w){
        //Add an edge from v to w (symmetrically).
    	checkAdd(v);
    	checkAdd(w);
    	this.adj.get(v.getID()).add(w);
    	if (v != w){
    		this.adj.get(w.getID()).add(v);
    	}
    	this.nE++;
    }

    public Iterable<Intersection> adj(int id){
        //Returns all adjacent vertices of v.
        return (Iterable<Intersection>) this.adj.get(id).clone();
    }

    public int V(){
        //Returns the number of vertices in the graph.
        return nV;
    }

    public int E(){
        //Returns the number of edges in the graph.
        return nE;
    }
    
    private Intersection find(int id){
    	for (Intersection w: this.all){
    		if (w.getID() == id) return w;
    	}
    	return null;
    }
    
    private void checkAdd(Intersection v){
    	for(Intersection w : this.all){
    		if (v.getID() == w.getID()) return;
    	}
    	all.add(v);
    }

    public String toString(){
        String s = this.V() + " intersections, " + this.E() + " edges\n";
        String t = "Intersections include: ";
        for (int i = 0; i < all.size(); i++){
        	t += all.get(i) + " ";
        }
        t += "\n";
        s += t;
        for (int v = 0; v < this.V(); ++v){
            s += find(v) + ": ";
            for (Intersection w : this.adj(v))
                s += w + " ";
            s += "\n";
        }
        return s;
    }
	
    public static void main(String[] args){
    	Graph s = new Graph(3);
    	Intersection i1 = new Intersection("ABC", 1, 2, 0);
    	Intersection i2 = new Intersection("DEF", 1, 3, 1);
    	Intersection i3 = new Intersection("GHI", 4, 5, 2);
    	s.addEdge(i1, i2);
    	s.addEdge(i2, i3);
    	System.out.println(s);
    }
	
}
