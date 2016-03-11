package cas2xb3.group40.drawPrototype;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class App extends Application {

    private final int RADIUS = 2;

    private void initUI(Stage stage) {

        float[][] verts = {{100, 100}, {200, 300}, {400, 350}, {350, 250}, {250, 250}};
        int[][] adj = {{1, 3, 4}, {0, 2}, {1, 4}, {0}, {0, 2}};
        Graph g = new Graph(verts, adj);

        Shape[] points = new Shape[g.getNumV()];
        Shape[] lines = new Shape[g.getNumV()];
        Vertex[] verticies = g.getVerticies();

        for (int v=0; v<g.getNumV(); v++) {
            Vertex vert = verticies[v];
            points[v] = new Circle(vert.getX(), vert.getY(), RADIUS);
            points[v].setFill(Color.BLACK);
            int[] vertAdj = vert.getAdj();
            // implement edge drawing
        }

        Pane root = new Pane();
        root.getChildren().addAll(points);

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