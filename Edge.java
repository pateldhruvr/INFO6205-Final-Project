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