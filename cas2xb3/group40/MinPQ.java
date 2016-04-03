package cas2xb3.group40;


/**
 * 
 * @author BJ
 * MinPQ class to represent a minimum priority queue
 * for the implementation of Dijkstra's SP algorithm.
 * Implementation based closely off of the Princeton.edu cite
 * (the textbook website). Certain components of the code
 * were omitted since it is only required for Dijkstra.
 * (Full URL in a3_answers.txt)
 *  
 * @param <Key>: Generic type of key for the priority queue
 */
public class MinPQ<Key extends Comparable<Key>> {
	private int maxN; //Holds the maximum number of elements on the PQ
	private int N; //Holds the current number of elements on the PQ
	private int[] pq; //Holds a binary heap based off of the 1-index
	private int[] qp; //Inverse of pq
	private Key[] keys; //Each key[i] holds the priority of i
	
	/**
	 * Constructor for a minimum PQ.
	 * Constructs an empty min. PQ for use.
	 * 
	 * @param maxN: Maximum number of elements to be on the PQ
	 */
	public MinPQ(int maxN){
		this.maxN = maxN;
		keys = (Key[]) new Comparable[maxN+1];
		pq = new int[maxN+1];
		qp = new int[maxN+1];
		for(int i = 0; i <= maxN; i++){ //Initializes all elements of qp to -1
			qp[i] = -1;
		}
	}
	
	/**
	 * Method to check if the PQ is empty.
	 * 
	 * @return: Boolean value, [true] if PQ is empty, [false] otherwise
	 */
	public boolean isEmpty(){
		return N == 0;
	}
	 
	/**
	 * Method to check if the PQ contains a certain index.
	 * Checks based off of the unchanged initial values of qp.
	 * 
	 * @param i: Index to check for in PQ
	 * @return: Boolean value, [true] if PQ contains i, [false] otherwise
	 */
	public boolean contains(int i){
		 return qp[i] != -1;
	}

	/**
	 * Method to insert a new element into the PQ.
	 * PQ adjusts to maintain min. PQ invariant.
	 * 
	 * @param i: Index to be inserted into
	 * @param key: Key value to insert
	 */
	public void insert(int i, Key key){
		N++;
		qp[i] = N;
		pq[N] = i;
		keys[i] = key;
		swim(N);
	}

	/**
	 * Method to delete the minimum of the PQ.
	 * 
	 * @return: The index of the minimum element of the PQ
	 */
	public int delMin(){
		int min = pq[1];
		exch(1,N--);
		sink(1);
		qp[min] = -1;
		keys[min] = null;
		pq[N+1] = -1;
		return min;
	}

	/**
	 * Method to change the key value at index i,
	 * to another key value.
	 * 
	 * @param i: Index to change
	 * @param key: Key to change index to
	 */
	public void changeKey(int i, Key key){
		keys[i] = key;
		swim(qp[i]);
		sink(qp[i]);
	}

	/**
	 * Helper method to maintain PQ invariants.
	 * 
	 * @param k: Element to swim up the PQ
	 */
	private void swim(int k){
		while(k > 1 && greater(k/2,k)){
			exch(k,k/2);
			k = k/2;
		}
	}

	/**
	 * Helper method to maintain PQ invariants.
	 * 
	 * @param k: Element to sink down the PQ
	 */
	private void sink(int k){
		while(2*k <= N){
			int j = 2*k;
			if(j < N && greater(j,j+1)) j++;
			if(!greater(k,j)) break;
			exch(k,j);
			k = j;
		}
	}

	/**
	 * Method to check if element i is greater than element j.
	 * Utilizes the compareTo from the Comparable interface.
	 * 
	 * @param i: Element to check if greater
	 * @param j: Element to check if less than i
	 * @return: Boolean value, [true] if i > j, [false] otherwise
	 */
	private boolean greater(int i, int j){
		return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
	}
	    
	/**
	 * Method to exchange the placement of element i with element j.
	 * 
	 * @param i: First element involved in the exchange
	 * @param j: Other element involved in the exchange
	 */
	private void exch(int i, int j){
		int swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}
}
