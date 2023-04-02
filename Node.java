import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    public final String id;
    public final double lat;
    public final double lng;
    public boolean visited;
    public ArrayList<Edge> edges;

    public Node(String id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.edges = new ArrayList<>();
    }
}