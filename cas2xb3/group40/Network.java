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

    public Intersection get(int i) {
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
    */

    public Intersection[] iterator(){
        return intsecs.clone();
    }

    public int V(){
        //Returns the number of vertices in the graph.
        return nV;
    }

    public int E(){
        //Returns the number of edges in the graph.
        return nE;
    }

    /*
    private Intersection get(String streets){
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

    /*
    public Iterator<Network> iterator() {
        return new ElementIterator();
    }

    class ElementIterator implements Iterator {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < V();
        }

        @Override
        public Intersection next() {
            return get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
    */

    public Intersection[] findClosest(String street, Intersection intsec) {
        Intersection first = null;
        double firstDist = Double.POSITIVE_INFINITY;
        Intersection second = null;

        for (int i=1; i<intsecs.length; i++) {
            double dist = distTo(intsec, intsecs[i]);
            boolean isSame = (intsec == intsecs[i]);
            boolean isStreet = intsecs[i].getStreets()[0].equals(street) || intsecs[i].getStreets()[1].equals(street);

            if (dist < firstDist && !isSame && isStreet && dist < 0.005) {
                second = first;
                first = intsecs[i];
                firstDist = dist;
            }
        }

        if (first == null) return new Intersection[] {};
        if (second == null) return new Intersection[] {first};
        return new Intersection[] {first, second};
    }

    public static double distTo(Intersection i, Intersection j) {
        double x1 = i.getX();
        double y1 = i.getY();
        double x2 = j.getX();
        double y2 = j.getY();
        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
    }

}
