package cas2xb3.group40;

import cas2xb3.group40.Intersection;
import cas2xb3.group40.Network;
import cas2xb3.group40.Parser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.Arrays;

public class App extends Application {

    private final int RADIUS = 2;

    private void initUI(Stage stage) {

        Parser p = new Parser();
        Network net = new Network(p.numLines());

        for (int i = 0; i < p.numLines(); i++) {
            String[] data = p.readLine();
            Intersection a = new Intersection(data[8] + " and " + data[9],
                                              Math.abs(Double.parseDouble(data[12]) ),
                                              Math.abs(Double.parseDouble(data[11]) ) );
            net.addIntersection(a);
        }

        p = null;

        // for each intersection in network
        // find all intersections on road A
        // find closest 2 intersections
        // if d(1,3) < d(2,3)
        // add both intersections to adjacency
        // if d(1,2) > d(2,3)
        // add only closer node
        // repeat for road B


        Shape[] points = new Shape[net.V()];
        int c = 0;

        for (int v=0; v<net.V(); v++) {
            Intersection i = net.getIntersection(v);
            if (i.getX() > 121.15 && i.getX() < 122.30 && i.getY() > 47 && i.getY() < 47.50) {
                points[c] = new Circle((i.getX()-122)*10000-2500, (i.getY()-47)*10000-5000, RADIUS);
                points[c++].setFill(Color.BLACK);
                System.out.println(c-1 + " " + points[c-1]);
            }
        }

        Pane root = new Pane();
        System.out.println("Noo " + c);
        //if (points[0] == null) System.exit(99);
        Shape[] selPoints = Arrays.copyOfRange(points, 0, c);
        root.getChildren().addAll(selPoints);

        Scene scene = new Scene(root, 250, 220, Color.WHITESMOKE);

        stage.setTitle("Absolute layout");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) {

        initUI(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}