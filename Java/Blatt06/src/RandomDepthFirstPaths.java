import java.util.*;

public class RandomDepthFirstPaths {
  	private boolean[] marked;    // marked[v] = is there an s-v path?
    private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public RandomDepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
    }
	    
	public void randomDFS(Graph G) {
		randomDFS(G,s);
	}
	    

    private void randomDFS(Graph G, int v) {


        marked[v] = true;
        Collections.shuffle(G.adj(v));// wir shuffeln immer genau vorm  wählen des nächsten Elements.
        for (int w : G.adj(v)) {       // copy paste von non random - post pre order und distTo + shuffle

            if (!marked[w]) {

                edgeTo[w] = v;
                randomDFS(G, w);

            }
        }
    }
    
    public void randomNonrecursiveDFS(Graph G) {

        marked = new boolean[G.V()];
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();


        Stack<Integer> stack = new Stack<Integer>();
        marked[s] = true;
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked[w]) {
                    Collections.shuffle(G.adj(w));      // wir shuffeln genau wie bei recursive genau vor dem machen der Kante
                    edgeTo[w] = v;
                    marked[w] = true;
                    stack.push(w);
                }
            }
            else stack.pop();
        }
    }

    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a path between the source vertex {@code s} and vertex {@code v}, or
     * {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a path between the source vertex
     *         {@code s} and vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * 
     * This method is different compared to the original one.
     */
    public List<Integer> pathTo(int v) {

        if (!hasPathTo(v)) return null;
        List<Integer> path = new LinkedList<>();
        for (int w = v; w != s; w = edgeTo[w]){ path.add(w); }
        path.add(s);
        Collections.reverse(path);   // wir gehen von hinten nach vorne, reserve damit beim start angefangen wird

        return path;
    }
    
    public int [] edge() {
    	return edgeTo;
    }  

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

}

