import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
/**
 * Class that represents a maze with N*N junctions.
 * 
 * @author Vera Röhr
 */
public class Maze{
    private final int N;
    private Graph M;    //Maze
    public int startnode;
        
	public Maze(int N, int startnode) {
		
        if (N < 0) throw new IllegalArgumentException("Number of vertices in a row must be nonnegative");
        this.N = N;
        this.M= new Graph(N*N);
        this.startnode= startnode;
        buildMaze();
	}
	
    public Maze (In in) {
    	this.M = new Graph(in);
    	this.N= (int) Math.sqrt(M.V());
    	this.startnode=0;
    }

	
    /**
     * Adds the undirected edge v-w to the graph M.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {

		M.addEdge(v, w);
    }
    
    /**
     * Returns true if there is an edge between 'v' and 'w'
     * @param v one vertex
     * @param w another vertex
     * @return true or false
     */
    public boolean hasEdge( int v, int w) {

        if (v==w) return true;
        if (M.adj(v).contains(w)) return true;
        return false;

    }
    
    /**
     * Builds a grid as a graph.
     * @return Graph G -- Basic grid on which the Maze is built
     */
    public Graph mazegrid() {

        Graph grid = new Graph(N * N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                int v = i * N + j;   // starts at s
                if (i < N - 1) grid.addEdge(v, v + N);
                if (j < N - 1) grid.addEdge(v, v + 1);

            }
        }
        return grid;
    }
    
    /**
     * Builds a random maze as a graph.
     * The maze is build with a randomized DFS as the Graph M.
     */
    private void buildMaze() {

        Graph template = mazegrid();
        RandomDepthFirstPaths SpanningTree = new RandomDepthFirstPaths(template, 0);
        SpanningTree.randomNonrecursiveDFS(template);   // random spannbaum über das grid template

        int counter = SpanningTree.edge().length-1;

        while (counter >= 0) {

            M.addEdge(counter, SpanningTree.edge()[counter]);
            counter--;
        }

    }

    /**
     * Find a path from node v to w
     * @param v start node
     * @param w end node
     * @return List<Integer> -- a list of nodes on the path from v to w (both included) in the right order.
     */
    public List<Integer> findWay(int v, int w){

        DepthFirstPaths path = new DepthFirstPaths(M, v);
        path.nonrecursiveDFS(M);  // recursive gibt bei kleineren N nach als nonrecursive // stackoverflow


        return path.pathTo(w).reversed();
    }
    
    /**
     * @return Graph M
     */
    public Graph M() {
    	return M;
    }

    public static void main(String[] args) {

        int n = 20;
        int e = n*n-1;

        Maze maze = new Maze(n,0);
        GridGraph Graph = new GridGraph( maze.M, maze.findWay(0,e) );

    }


}

