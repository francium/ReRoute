package cas2xb3.group40;

import java.util.ArrayList;

/**
 * 
 * @author BJ
 * Dijkstra class to run Dijkstra's SP algorithm for use
 * in determining the shortest path from start city to 
 * destination city based on the fuel pricing.
 * The implementation closely follows the implementation 
 * given in the Algorithms 4e. textbook (p. 655) with minor changes 
 * to provide the appropriate output and to accept a undirected graph.
 */
public class Dijkstra {
	private Road[] edgeTo; //Holds the edges involved in the minimum path 
	private double[] distTo; //Holds the weights (distance) involved in the min. path
	private MinPQ<Double> pq; //Utilizes a MinPQ for the vertices

	/**
	 * Method computes a shortest paths tree from the start vertex to every other
	 * vertex in the edge weighted graph G.
	 * 
	 * @param G: Edge-weighted graph to be analyzed
	 * @param s: Start vertex for computation
	 */
	public Dijkstra(Network G, int s){
		edgeTo = new Road[G.V()]; //Initializes arrays based on total number of vertices
		distTo = new double[G.V()];
		pq = new MinPQ<Double>(G.V());

		for(int v = 0; v < G.V(); v++){ //Initializes all distTo[] elements except the start vertex to pos. infinity
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		distTo[s] = 0.0; //Sets distTo[] start to 0.0

		pq.insert(s,0.0); //Inserts start vertex into the PQ
		while(!pq.isEmpty()){
			relax(G, pq.delMin()); //Relaxes each vertex in G and adds it to the PQ
		}
	}

	/**
	 * Relaxes edge v and updates PQ if changed.
	 * 
	 * @param G: Edge weighted graph (undirected)
	 * @param v: Vertex to be relaxed
	 */
	private void relax(Network G, int v){
		for(Road r : G.get(v).adjR()){
			int w = r.otherI();
			if(distTo[w] > distTo[v] + r.weight()){ //Relaxes vertices in order of distance from start vertex
				distTo[w] = distTo[v] + r.weight();
				edgeTo[w] = r;
				if(pq.contains(w)){ //Changes PQ appropriately depending on current condition of PQ
					pq.changeKey(w, distTo[w]);
				}else{
					pq.insert(w, distTo[w]);
				}
			}
		}
	}

	/**
	 * Method to return the length (distance) of the shortest path 
	 * from start vertex to destination vertex.
	 * 
	 * @param v: Destination vertex
	 * @return: Length (distance) of shortest path from start to destination vertex
	 */
	public double distTo(int v){
		return distTo[v];
	}

	/**
	 * Method to return a boolean value corresponding to 
	 * if there is a path from the start vertex to the destination vertex.
	 * 
	 * @param v: Destination vertex
	 * @return: Boolean value, [true] if a path from start to destination, [false] otherwise
	 */
	public boolean hasPathTo(int v){
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	/**
	 * Method to compute (and return) the shortest path from 
	 * start vertex to the destination vertex (with use of the 2 above 
	 * methods).
	 * 
	 * @param v: Destination vertex
	 * @return: Shortest path (in array list form) from start vertex to dest. vertex
	 */
	public ArrayList<Road> pathTo(int v){
		if(!hasPathTo(v)) return null;
		ArrayList<Road> path = new ArrayList<Road>();
		for(Road r = edgeTo[v]; r != null; r = edgeTo[r.oneI()]){
			path.add(r);
		}
		ArrayList<Road> reverse = new ArrayList<Road>(); //Reverses the order of the edges stored in path for correct output
		for(int i = path.size()-1; i >= 0; i--){
			reverse.add(path.get(i));
		}
		return reverse;
	}
}
