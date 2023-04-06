import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Christofides {
    private Edge[] mst;
    private List<Node> oddDegreeNodes;

    public Christofides(Edge[] mst) {
        this.mst = mst;
    }

    public void findOddDegreeNodes() {
        this.oddDegreeNodes = new ArrayList<>();
        //Build a hashmap of nodes with count of edges
        Map<Node, Integer> nodeEdgeCount = new HashMap<>();

        for(int i = 0; i < mst.length; i++) {
            Node from = mst[i].from;
            Node to = mst[i].to;

            if(nodeEdgeCount.containsKey(from)) {
                nodeEdgeCount.put(from, nodeEdgeCount.get(from) + 1);
            } else {
                nodeEdgeCount.put(from, 1);
            }

            if(nodeEdgeCount.containsKey(to)) {
                nodeEdgeCount.put(to, nodeEdgeCount.get(to) + 1);
            } else {
                nodeEdgeCount.put(to, 1);
            }
        }

        //get odd degree nodes
        for(Map.Entry<Node, Integer> entry : nodeEdgeCount.entrySet()) {
            if(entry.getValue() % 2 != 0) {
                this.oddDegreeNodes.add(entry.getKey());
            }
        }
    }

    public void findPerfectMatching() {
        //Get a set of edges that connect odd degree nodes with minimum weight
        
    }

    public void printOddDegreeNodes() {
        for(int i = 0; i < oddDegreeNodes.size(); i++) {
            System.out.println(oddDegreeNodes.get(i).id);
        }
        System.out.println("Number of odd degree nodes: " + this.oddDegreeNodes.size());
    }

    public static void main(String[] args) throws IOException {
        List<Node> nodes = CSVLoader.loadNodesFromCleanData("data/crimeSample.csv");
        //build MST
        PrimsMST mstSolver = new PrimsMST(nodes);
        mstSolver.solve();
        Edge[] mst = mstSolver.getMst();

        Christofides TSPSolver = new Christofides(mst);
        TSPSolver.findOddDegreeNodes();
        TSPSolver.printOddDegreeNodes();
    }
}
