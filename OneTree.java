import java.io.IOException;
import java.util.List;

import static java.lang.Math.max;

public class OneTree {
    private Edge[] oneTree;
    private long oneTreeCost;
    private MinIndexedDHeap<Edge> ipq;
    private static long maxOneTreeCost = 0;

    public OneTree(int n) {
        int degree = (int) Math.ceil(Math.log(n) / Math.log(2));
        this.ipq = new MinIndexedDHeap<>(max(2, degree), n);
        this.oneTreeCost = 0;
        this.oneTree = null;
    }

    public void buildOneTree(Edge[] mst, Node excludedNode, long mstCost, List<Node> nodes) {
        this.oneTreeCost = mstCost;
        for(int i = 0; i < nodes.size(); i++) {
            if(!excludedNode.id.equals(nodes.get(i))) {
                ipq.insert(i, new Edge(excludedNode, nodes.get(i)));
            }
        }
        mst[mst.length - 2] = ipq.pollMinValue();
        mst[mst.length - 1] = ipq.pollMinValue();

        this.oneTree = mst;
        this.oneTreeCost += mst[mst.length - 2].distance + mst[mst.length - 1].distance;

        //update the maxOneTreeCost
        if(this.oneTreeCost > maxOneTreeCost) {
            maxOneTreeCost = this.oneTreeCost;
        }
    }

    public void printOneTree() {
        for(int i = 0; i < oneTree.length; i++) {
            System.out.println(oneTree[i].to + " " + oneTree[i].from + " " + oneTree[i].distance);
        }
    }

    public long getOnetreeCost() {
        return this.oneTreeCost;
    }

    public static void main(String args[]) throws IOException {

        List<Node> nodes = CSVLoader.loadNodesFromCleanData("data/crimeSample.csv");

        for(int i = 0; i < nodes.size(); i++) {
            //exclue first node
            Node excludedNode = nodes.get(i);
            nodes.remove(i);

            //build MST
            PrimsMST mstSolver = new PrimsMST(nodes);
            mstSolver.solve();
            Edge[] mst = mstSolver.getMst();

            //build one tree
            OneTree oneTreeSolver = new OneTree(nodes.size());
            oneTreeSolver.buildOneTree(mst, excludedNode, mstSolver.getMstCost(), nodes);

            //One tree cost
            System.out.println("One Tree Cost: " + oneTreeSolver.getOnetreeCost());
        }
        System.out.println("Lower bound using one-tree: " + OneTree.maxOneTreeCost);
    }
}
