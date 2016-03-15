package cas2xb3.group40;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class App extends Application {

    private double iX, iY;

    private void initUI(Stage stage) {

        Parser p = new Parser();
        Network net = new Network(p.numLines());
        Camera cam = new Camera(122.30, 47.60, 122.43, 47.76);

        // build network
        for (int i = 0; i < p.numLines(); i++) {
            String[] data = p.readLine();
            Intersection a = new Intersection(data[8] + " and " + data[9],
                                              Math.abs(Double.parseDouble(data[12]) ),
                                              Math.abs(Double.parseDouble(data[11]) ) );
            net.addIntersection(a);
        }

        // parser obj no longer required
        p = null;

        // for each intersection in network
        // find all intersections on road A
        // find closest 2 intersections
        // if d(1,3) < d(2,3)
        // add both intersections to adjacency
        // if d(1,2) > d(2,3)
        // add only closer node
        // repeat for road B

        /*
        for (int v=0; v<net.V(); v++) {
            Intersection i = net.getIntersection(v);
            if (i.getX() > 121.15 && i.getX() < 122.30 && i.getY() > 47 && i.getY() < 47.50) {
                points[c] = new Circle((i.getX()-122)*10000-2500, (i.getY()-47)*10000-5000, RADIUS);
                points[c++].setFill(Color.BLACK);
                System.out.println(c-1 + " " + points[c-1]);
            }
        }
        */

        Pane root = new Pane();
        root.getChildren().addAll(cam.filterVisible(net));

        Scene scene = new Scene(root, 500, 500, Color.BLACK);

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                iX = mouseEvent.getSceneX();
                iY = mouseEvent.getSceneY();
            }
        });

        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double dx = mouseEvent.getSceneX() - iX;
                double dy = mouseEvent.getSceneY() - iY;
                cam.pan(dx, dy);
                root.getChildren().clear();
                root.getChildren().addAll(cam.filterVisible(net));
            }
        });

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