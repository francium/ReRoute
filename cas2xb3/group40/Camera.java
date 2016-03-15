package cas2xb3.group40;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Arrays;

public class Camera {

    private final int RADIUS = 1;
    private final int PAN_FACTOR = 5000;

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public Camera(double minX, double minY, double maxX, double maxY) {
        if (minX > maxX || minY > maxY) throw new IllegalArgumentException();
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void pan(double dX, double dY) {
        minX += dX/PAN_FACTOR;
        minY += dY/PAN_FACTOR;
        maxX += dX/PAN_FACTOR;
        maxY += dY/PAN_FACTOR;
    }

    private double[] normalizeCoords(double i, double j) {
        int RESX = 500;
        int RESY = 500;
        double dx = maxX - minX;
        double dy = maxY - minY;
        i -= minX;
        j -= minY;
        i /= dx;
        j /= dy;
        // flip x,y coordinates to make it right side up
        return new double[] {RESX - RESX * i, RESY - RESY * j};
    }

    public Shape[] filterVisible(Network net) {
        Shape[] intsecs = new Shape[net.V()];
        int c = 0;
        for (int v=0; v<net.V(); v++) {
            Intersection i = net.get(v);
            if (i.getX() > minX && i.getX() < maxX && i.getY() > minY && i.getY() < maxY) {
                double[] xy = normalizeCoords(i.getX(), i.getY());
                intsecs[c++] = new Circle(xy[0], xy[1], RADIUS, Color.RED);
            }
        }
        return Arrays.copyOfRange(intsecs, 0, c);
    }

    // TODO
    /* public void zoom(float z) {
        int dx = ((minX - maxX) / 2);
        int dx = ((minY - maxY) / 2);
    } */

    public String toString() {
        return String.format("(%f, %f), (%f, %f)", minX, minY, maxX, maxY);
    }

}
