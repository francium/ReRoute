package cas2xb3.group40;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Arrays;

public class Camera {

    private final int RADIUS = 2;

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

    public void pan(int dX, int dY) {
        minX += dX;
        minY += dY;
        maxX += dX;
        maxY += dY;
    }

    /*
    public Shape[] filterVisible(Intersection[] in) {
        int c = 0;
        // assume 1/4 of objects are visible
        Intersection[] out = new Intersection[in.length/4];
        for (int i=0; i<in.length; i++) {
            Intersection intsec = in[i];
            if (intsec.getX() > minX && intsec.getX() < maxX)
                if (intsec.getY() > minY &&  intsec.getY() < maxY)
                    out[c++] = intsec;
        }
        return Arrays.copyOfRange(out, 0, c);
    }
    */

    private double[] normalizeCoords(double i, double j) {
        int RESX = 500;
        int RESY = 500;
        double dx = maxX - minX;
        double dy = maxY - minY;
        i -= minX;
        j -= minY;
        i /= dx;
        j /= dy;
        return new double[] {RESX * i, RESY * j};
    }

    public Shape[] filterVisible(Network net) {
        Shape[] intsecs = new Shape[net.V()/4]; // assume only 1/4
        int c = 0;
        for (int v=0; v<net.V(); v++) {
            Intersection i = net.getIntersection(v);
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

}
