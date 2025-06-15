package Main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LocationManager {
    private static final List<Point> availablePoints = new ArrayList<>();

    private static final Set<Point> assignedPoints = new HashSet<>();

    // create grid 10xrad5
    static{
        int spacingX = 80, spacingY = 60;

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <=5; j++) {
                availablePoints.add(new Point(i*spacingX, j*spacingY));
            }
        }

        Collections.shuffle(availablePoints); //random location set
    }

    public static Point assignLoaction(){
        for (Point point : availablePoints) {
            if (!assignedPoints.contains(point)) {
                assignedPoints.add(point);
                return point;
            }
        }

        // if no available points
        return new Point(new Random().nextInt(700), new Random().nextInt(400));
    }

    public static void resetLocations(){
        assignedPoints.clear();
    }

    
}
