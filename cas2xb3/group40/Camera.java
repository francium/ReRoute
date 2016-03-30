package cas2xb3.group40;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Arrays;

public class Camera {

    private final int RADIUS = 2;
    private final int PAN_FACTOR = 5000;
    private final double SCROLL_FACTOR = 0.5;
    private final Color INTERSECTION_COLOR = Color.KHAKI;

    //private final double MIN_X =
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    // data
    // min
    // x 122.22787754099994
    // y 47.48558708100006
    // max
    // x 122.43071639499999
    // y 47.734142562000045
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
        //if (minX )
    }

    public void zoom(boolean in) {
        double dx = maxX - minX;
        double dy = maxY - minY;
        double ndx = dx * SCROLL_FACTOR / 2;
        double ndy = dy * SCROLL_FACTOR / 2;
        if (in) {
            minX += ndx;
            minY += ndy;
            maxX -= ndx;
            maxY -= ndy;
        } else {
            minX -= ndx;
            minY -= ndy;
            maxX += ndx;
            maxY += ndy;
        }
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
                Circle circ = new Circle(xy[0], xy[1], RADIUS, INTERSECTION_COLOR);
                intsecs[c++] = circ;
            }
        }
        return Arrays.copyOfRange(intsecs, 0, c);
    }

    public String toString() {
        return String.format("(%f, %f), (%f, %f)", minX, minY, maxX, maxY);
    }

}
