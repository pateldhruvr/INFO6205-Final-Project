import java.io.IOException;
import java.util.List;

import static java.lang.Math.max;

public class PrimsMST {
    //inputs
    private int n;
    private List<Node> nodes;

    //Internal
    private boolean solved;
    private boolean mstExists;
    private boolean[] visited;
    private MinIndexedDHeap<Edge> ipq;

    //Outputs
    private long minCostSum;
    private Edge[] mstEdges;

    // Returns the edges used in finding the minimum spanning tree,
    // or returns null if no MST exists
    public Edge[] getMst(){
        solve();
        return mstExists ? mstEdges : null;
    }

    public Long getMstCost() {
        solve();
        return mstExists ? minCostSum : null;
    }

    private void relaxEdgesAtNode(int currentNodeIndex) {
        visited[currentNodeIndex] = true;

        //@TODO no need to loop through nodes already visited, remove them beforehand
        for(int i = 0; i < nodes.size(); i++) {
            int destNodeIndex = i;

            //skip if already visited
            if(visited[destNodeIndex]) continue;

            Edge edge = new Edge(nodes.get(currentNodeIndex), nodes.get(i));
            if(ipq.contains(destNodeIndex)) {
                // Try and improve the cheapest edge at destNodeIndex with the current edge in the IPQ.
                ipq.decrease(destNodeIndex, edge);
            } else {
                //insert edge for the first time
                ipq.insert(destNodeIndex, edge);
            }
        }
    }

    // Computes the minimum spanning tree and minimum spanning tree cost.
    private void solve() {
        if(solved) return;
        solved = true;

        n = nodes.size();
        int m = n - 1, edgeCount = 0;
        visited = new boolean[n];
        mstEdges = new Edge[m];

        // The degree of the d-ary heap supporting the IPQ can greatly impact performance, especially
        // on dense graphs. The base 2 logarithm of n is a decent value based on my quick experiments
        // (even better than E/V in many cases).
        int degree = (int) Math.ceil(Math.log(n) / Math.log(2));
        ipq = new MinIndexedDHeap<>(max(2, degree), n);

        // Add initial set of edges to the priority queue starting at node 0.
        relaxEdgesAtNode(0);

        while(!ipq.isEmpty() && edgeCount != m) {
            int destNodeIndex = ipq.peekMinKeyIndex();
            Edge edge = ipq.pollMinValue();

            mstEdges[edgeCount++] = edge;
            minCostSum += edge.distance;

            relaxEdgesAtNode(destNodeIndex);
        }

        // Verify MST spans entire graph.
        mstExists = (edgeCount == m);
    }

    private void printMST(Edge[] edges) {
        for(int i = 0; i < edges.length; i++) {
            System.out.println(edges[i].from.id + " " + edges[i].to.id + " " + edges[i].distance);
        }
    }

    public static void main(String[] args) throws IOException {
        PrimsMST mstSolver = new PrimsMST();
        mstSolver.nodes = CSVLoader.loadNodesFromCSV("data/2023-01-metropolitan-street.csv");
        System.out.println("MST for nodes: " + mstSolver.nodes.size());
       mstSolver.printMST(mstSolver.getMst());
    }
}

class Edge implements Comparable<Edge>{
    public Node from;
    public Node to;
    public double distance;

    public Edge(Node n1, Node n2) {
        this.from = n1;
        this.to = n2;
        this.distance = HaversineDistance.haversine(n1, n2);
    }

    @Override
    public int compareTo(Edge e) {
        if(this.distance < e.distance) {
            return -1;
        } else if(this.distance > e.distance) {
            return 1;
        } else {
            return 0;
        }
    }
}