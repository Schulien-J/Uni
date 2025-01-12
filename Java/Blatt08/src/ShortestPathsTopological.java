import java.util.Stack;

public class ShortestPathsTopological {
    private int[] parent;
    private int s;
    private double[] dist;

    public ShortestPathsTopological(WeightedDigraph G, int s) {
        //todo
        this.s = s;
        parent = new int[G.V()];
        dist = new double[G.V()];


        for (int v = 0; v < G.V(); v++) {
            dist[v] = Double.POSITIVE_INFINITY;
            parent[v] = -1;
        }
        parent[s] = 0;
        dist[s] = 0.0;


        TopologicalWD topo = new TopologicalWD(G);
        topo.dfs(s);
        Stack<Integer> order = topo.order();
        System.out.println(order +"");


        while (!order.isEmpty()) {
            int v = order.pop();
            System.out.println(v+"");
            for (DirectedEdge e : G.incident(v)) {
                relax(e);
            }
        }
    }

    public void relax(DirectedEdge e) {
        //todo
        int v = e.from();
        int w = e.to();
        if (dist[v] != Double.POSITIVE_INFINITY && dist[w] > dist[v] + e.weight()) {
            dist[w] = dist[v] + e.weight();
            parent[w] = v;
        }
    }

    public boolean hasPathTo(int v) {
        return parent[v] >= 0;
    }

    public Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int w = v; w != s; w = parent[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }

}

