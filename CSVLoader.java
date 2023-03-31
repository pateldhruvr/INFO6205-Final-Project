import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader {
    public static List<Node> loadNodesFromCSV(String filepath) throws IOException {
        List<Node> nodes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line = reader.readLine(); // read the header line
        int id = 0;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if(!parts[6].equals("No Location")) {
                double lat = Double.parseDouble(parts[5]);
                double lng = Double.parseDouble(parts[4]);
                id++;
                nodes.add(new Node(id, lat, lng));
              //  if(nodes.size() > 4) break;
            }
        }
        reader.close();
        return nodes;
    }

    public static void printNodes(List<Node> nodes) {
        for(int i = 0; i < nodes.size(); i++) {
            //print the distance between all the nodes
            for(int j = 0; j < nodes.size(); j++) {
                if(i != j) {
                    System.out.print("From : " + nodes.get(i).id + " To: " + nodes.get(j).id);
                    System.out.println(" " + HaversineDistance.haversine(nodes.get(i), nodes.get(j)));
                }
            }
        }
    }
}
