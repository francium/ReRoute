package cas2xb3.group40.drawPrototype;

/**
 * Graph ADT
 * @author Varun Hooda
 */
public class Graph {

    private final int numV;
    private int numE;
    private final Vertex[] verticies;

    public Graph(float[][] verticies, int[][] adj) {
        numV = verticies.length;
        this.verticies = new Vertex[numV];
        for (int i=0; i<numV; i++) {
            this.verticies[i] = new Vertex(verticies[i], adj[i]);
        }
    }

    public int getNumV() { return numV; }

    public Vertex[] getVerticies() {
        return verticies.clone();
    }

    // TODO implement iterator
    /*
    public Iterator<Vertex> iterator() {
        return
    }
    */

}

class Vertex {

    private final float x;
    private final float y;
    private final int[] adj;

    public Vertex(float[] coords, int[] adj) {
        this.x = coords[0];
        this.y = coords[1];
        this.adj = adj;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public int[] getAdj() { return adj.clone(); }

}