import java.io.IOException;
import java.util.List;

public class HaversineDistance {
    private static final double R = 6371; // radius of the earth in km

    static double haversine(Node n1, Node n2)
    {
        double lat1 = n1.lat;
        double lat2 = n2.lat;
        double lon1 = n1.lng;
        double lon2 = n2.lng;

        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formula
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);

        //currently the return type is miles (depends on the value of rad)
        double rad = 3958.756;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }
}
