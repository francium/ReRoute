package cas2xb3.group40;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class App extends Application {

    private final String TITLE = "reRoute";
    private final Color MAP_BACKGROUND_COLOR = Color.PAPAYAWHIP;
    private final Color LOAD_BACKGROUND_COLOR = Color.WHITE;
    private double mouseX, mouseY;

    private void initUI(Stage stage) {
        stage.setTitle(TITLE);

        BorderPane topPane = new BorderPane();
        Scene topScene = new Scene(topPane, 500, 500, LOAD_BACKGROUND_COLOR);

        StackPane load = new StackPane();
        //Scene loadScene = new Scene(load, 500, 500, LOAD_BACKGROUND_COLOR);

        Image imgLogo = new Image("file:data/logo.png");
        ImageView imgViewLogo = new ImageView(imgLogo);
        load.getChildren().add(imgViewLogo);

        topPane.setCenter(load);

        Pane root = new Pane();
        //Scene mapScene = new Scene(root, 500, 500, MAP_BACKGROUND_COLOR);
        //stage.setScene(loadScene);
        stage.setScene(topScene);

        stage.show();

        Parser p = new Parser();
        Network net = new Network(p.numLines());
        Camera cam = new Camera(122.30, 47.60, 122.33, 47.64, stage.getWidth(), stage.getHeight());
            // different aspect ratio
            //Camera cam = new Camera(122.30, 47.60, 122.43, 47.76);

        startLoadingThread(net, p, root, cam, topPane, topScene);

        // mouse click handler
        //mapScene.setOnMousePressed(new EventHandler<MouseEvent>() {
        topScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getSceneX();
                mouseY = mouseEvent.getSceneY();
            }
        });

        // pan handler
        //mapScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
        topScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double mousedx = mouseEvent.getSceneX() - mouseX;
                double mousedy = mouseEvent.getSceneY() - mouseY;
                cam.pan(mousedx, mousedy);
                root.getChildren().clear();
                root.getChildren().addAll(cam.filterVisible(net, null));
            }
        });

        // zoom handler
        //mapScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        topScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A) {
                    getUserInputThread(net, cam, root);
                    return;
                }
                if (keyEvent.getCode() == KeyCode.EQUALS) {
                    cam.zoom(true);
                } else if (keyEvent.getCode() == KeyCode.MINUS) {
                    cam.zoom(false);
                }
                root.getChildren().clear();
                root.getChildren().addAll(cam.filterVisible(net, null));
            }
        });

        /*
        // windows resize handler
        //mapScene.widthProperty().addListener(new ChangeListener<Number>() {
        topScene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                cam.setResX(newSceneWidth.intValue());
                root.getChildren().addAll(cam.filterVisible(net, null));
            }
        });

        // window resize handler
        //mapScene.heightProperty().addListener(new ChangeListener<Number>() {
        topScene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                cam.setResY(newSceneHeight.intValue());
            }
        });
        */
    }

    private void getUserInputThread(Network net, Camera cam, Pane root) {
        ArrayList<Road>[] path = new ArrayList[1];

        //Dijkstra[] djk = {null};
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //djk[0] = StreetSearch.getUserInput(net, cam);

                        return null;
                    }
                };
            }
        };
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                System.out.println("here******************");
                //Road[] path = (Road[])djk[0].pathTo(fromTo[1].getId()).toArray();
                //Road[] path = (Road[])djk[0].pathTo(10078).toArray();
                //root.getChildren().clear();
                //root.getChildren().addAll(cam.filterVisible(net, path));
                //System.out.println(djk[0] == null);
                //System.out.println(djk[0].pathTo(55) == null);


                //djk[0] = StreetSearch.getUserInput(net, cam);
                //System.out.println("obj == null ? " + (djk[0] == null));

                path[0] = StreetSearch.getUserInput(net, cam);
                root.getChildren().clear();
                root.getChildren().addAll(cam.filterVisible(net, path[0]));

                /* // Working block
                int rand = (int)(10000*Math.random());
                Dijkstra d = new Dijkstra(net, rand);
                if (d.hasPathTo(rand)) {
                    ArrayList<Road> path = d.pathTo(5550);
                    cam.panTo(net, net.get(rand));
                    root.getChildren().clear();
                    root.getChildren().addAll(cam.filterVisible(net, path));
                } // Working block */
            }
        });
        service.start();
    }

    private void startLoadingThread(Network net, Parser p, Pane root, Camera cam, BorderPane topPane, Scene topScene) {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        // build network
                        Network.buildNetwork(net, p);

                        return null;
                    }
                };
            }
        };
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                // add shapes to root pane
                root.getChildren().clear();
                root.getChildren().addAll(cam.filterVisible(net, null));

                // parser obj no longer required
                p.destructor();

                //stage.setScene(mapScene);
                topPane.setCenter(root);
                topScene.setFill(MAP_BACKGROUND_COLOR);
            }
        });
        service.start();
    }

    private double distance(Shape s, double x, double y) {
        return distance(s.getLayoutX(), s.getLayoutY(), x, y);
    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((Math.pow((x2-x1),2))+(Math.pow((y2-y1),2)));
    }

    @Override
    public void start(Stage stage) {

        initUI(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}