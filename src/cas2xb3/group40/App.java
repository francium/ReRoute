package cas2xb3.group40;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class App extends Application {

    private final String TITLE = "reRoute";
    private final Color MAP_BACKGROUND_COLOR = Color.PAPAYAWHIP;
    private final Color LOAD_BACKGROUND_COLOR = Color.WHITE;
    private double mouseX, mouseY;
    private boolean loaded = false;

    private void initUI(Stage stage) {
        stage.setTitle(TITLE);

        StackPane helpPane = new StackPane();
        Rectangle rectangle = new Rectangle(500, 50, LOAD_BACKGROUND_COLOR);
        Text helpText = new Text("S\t\t\tto perform a search\n" +
                                 "H\t\t\tto toggle help");
        helpPane.getChildren().add(rectangle);
        helpPane.getChildren().add(helpText);

        BorderPane topPane = new BorderPane();
        Scene topScene = new Scene(topPane, 500, 500, LOAD_BACKGROUND_COLOR);

        StackPane load = new StackPane();

        Image imgLogo = new Image("file:data/logo.png");
        ImageView imgViewLogo = new ImageView(imgLogo);
        load.getChildren().add(imgViewLogo);

        Text statusLabel = new Text();
        load.getChildren().add(statusLabel);
        StackPane.setAlignment(statusLabel, Pos.BOTTOM_CENTER);

        topPane.setCenter(load);

        Pane root = new Pane();
        stage.setScene(topScene);

        stage.show();

        Parser p = new Parser(0);
        Network net = new Network(p.numLines());
        Camera cam = new Camera(122.30, 47.60, 122.33, 47.64, stage.getWidth(), stage.getHeight());
            // different aspect ratio
            //Camera cam = new Camera(122.30, 47.60, 122.43, 47.76);

        startLoadingThread(net, p, root, cam, topPane, topScene, helpPane, statusLabel);

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
        topScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.H) {
                    if (loaded)
                        if (topPane.getBottom() == helpPane) {
                            topPane.setBottom(null);
                        } else
                            topPane.setBottom(helpPane);

                } else if (keyEvent.getCode() == KeyCode.S) {
                    getUserInputThread(net, cam, root, topPane);

                } else if (keyEvent.getCode() == KeyCode.EQUALS) {
                    cam.zoom(true);
                    root.getChildren().clear();
                    root.getChildren().addAll(cam.filterVisible(net, null));

                } else if (keyEvent.getCode() == KeyCode.MINUS) {
                    cam.zoom(false);
                    root.getChildren().clear();
                    root.getChildren().addAll(cam.filterVisible(net, null));

                }
            }
        });

        // windows resize handler
        topScene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                if (loaded) {
                    cam.setResX(newSceneWidth.intValue());
                    root.getChildren().clear();
                    root.getChildren().addAll(cam.filterVisible(net, null));
                }
            }
        });

        // window resize handler
        topScene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                if (loaded)
                    cam.setResY(newSceneHeight.intValue());
                    root.getChildren().clear();
                    root.getChildren().addAll(cam.filterVisible(net, null));
            }
        });
    }

    private void startLoadingThread(Network net, Parser p, Pane root, Camera cam, BorderPane topPane, Scene topScene, Pane helpPane, Text statusLabel) {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Network.buildNetwork(net, p, statusLabel);

                        return null;
                    }
                };
            }
        };
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                statusLabel.setText("Sorting data");
                net.sort();

                // add shapes to root pane
                root.getChildren().clear();
                root.getChildren().addAll(cam.filterVisible(net, null));

                // parser obj no longer required
                p.destructor();

                //stage.setScene(mapScene);
                topPane.setCenter(root);
                topPane.setBottom(helpPane);
                topScene.setFill(MAP_BACKGROUND_COLOR);
                loaded = true;
            }
        });
        service.start();
    }

    private void getUserInputThread(Network net, Camera cam, Pane root, BorderPane topPane) {
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
                topPane.setBottom(null);

                Road prev = path[0].get(0);

                // print intersections
                for (int i=1; i<path[0].size(); i++) {
                    // work in progress
                    if (prev.oneI() == path[0].get(i).oneI())
                        System.out.println(path[0].get(i).getIntsec1());
                    else if (prev.oneI() == path[0].get(i).otherI())
                        System.out.println(path[0].get(i).getIntsec2());
                    else if (prev.otherI() == path[0].get(i).oneI())
                        System.out.println(path[0].get(i).getIntsec1());
                    else if (prev.otherI() == path[0].get(i).otherI())
                        System.out.println(path[0].get(i).getIntsec2());

                    prev = path[0].get(i);
                }

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

    @Override
    public void start(Stage stage) {

        initUI(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}