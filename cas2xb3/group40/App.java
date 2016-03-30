package cas2xb3.group40;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    private final String TITLE = "reRoute";
    private final Color BACKGROUND_COLOR = Color.DARKSEAGREEN;
    private double mouseX, mouseY;

    private void initUI(Stage stage) {

        Parser p = new Parser();
        Network net = new Network(p.numLines());
        //Camera cam = new Camera(122.30, 47.60, 122.43, 47.76);
        Camera cam = new Camera(122.30, 47.60, 122.33, 47.64);

        // build network
        for (int i = 0; i < p.numLines(); i++) {
            String[] data = p.readLine();
            Intersection a = new Intersection(data[8], data[9],
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
        for (Intersection i: net.iterator()) {
            Intersection[] closest = net.findClosest2(i);
            double dist12 = net.distTo(i,closest[0]);
            double dist13 = net.distTo(i,closest[1]);
            double dist23 = net.distTo(closest[0],closest[1]);

            System.out.println("^^^ " + dist12 + "   " + dist13 + "      " + dist23);
            if (dist13 < dist23) {
                System.out.println(88);
                i.addAdjacent(closest[0]);
                i.addAdjacent(closest[1]);
            } else if (dist12 > dist23) {
                if (dist12 < dist13) {
                    System.out.println(98);
                    i.addAdjacent(closest[0]);
                } else{
                    System.out.println(98);
                    i.addAdjacent(closest[1]);
                }
            }
        }

        Pane root = new Pane();
        root.getChildren().addAll(cam.filterVisible(net));

        Scene scene = new Scene(root, 500, 500, BACKGROUND_COLOR);

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getSceneX();
                mouseY = mouseEvent.getSceneY();
            }
        });

        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double mousedx = mouseEvent.getSceneX() - mouseX;
                double mousedy = mouseEvent.getSceneY() - mouseY;
                cam.pan(mousedx, mousedy);
                root.getChildren().clear();
                root.getChildren().addAll(cam.filterVisible(net));
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.EQUALS) {
                    cam.zoom(true);
                } else if (keyEvent.getCode() == KeyCode.MINUS) {
                    cam.zoom(false);
                }
                root.getChildren().clear();
                root.getChildren().addAll(cam.filterVisible(net));
            }
        });

        stage.setTitle(TITLE);
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