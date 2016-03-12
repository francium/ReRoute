package cas2xb3.group40;

public class Camera {

    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public Camera(int minX, int minY, int maxX, int maxY) {
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

    // TODO
    /* public void zoom(float z) {
        int dx = ((minX - maxX) / 2);
        int dx = ((minY - maxY) / 2);
    } */

}
