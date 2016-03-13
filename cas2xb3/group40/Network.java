package cas2xb3.group40;

public class Network {

    private Intersection[] intsecs;
    private int nV;
    private int nE;

    public Network(int cap){
        intsecs = new Intersection[cap];
    }

    public void addIntersection(Intersection i) {
        intsecs[i.getId()] = i;
        nV++;
    }

    public Intersection getIntersection(int i) {
        return intsecs[i];
    }

    /*
    public void addEdge(Intersection v, Intersection w){
        //Add an edge from v to w (symmetrically).
    	checkAdd(v);
    	checkAdd(w);
    	this.adj.get(v.getId()).add(w);
    	if (v != w){
    		this.adj.get(w.getID()).add(v);
    	}
    	this.nE++;
    }

    public Iterable<Intersection> adj(int id){
        //Returns all adjacent vertices of v.
        return (Iterable<Intersection>) this.adj.get(id).clone();
    }
    */

    public int V(){
        //Returns the number of vertices in the graph.
        return nV;
    }

    public int E(){
        //Returns the number of edges in the graph.
        return nE;
    }

    /*
    private Intersection getIntersection(String streets){
    	for (Intersection w: intsecs){
    		if (w.getId() == id) return w;
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
    */

}
