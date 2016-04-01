package cas2xb3.group40;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    private final String TITLE = "reRoute";
    private final Color MAP_BACKGROUND_COLOR = Color.DARKSEAGREEN;
    private final Color LOAD_BACKGROUND_COLOR = Color.WHITE;
    private double mouseX, mouseY;

    private void initUI(Stage stage) {
        stage.setTitle(TITLE);

        Pane load = new StackPane();
        Scene loadScene = new Scene(load, 500, 500, LOAD_BACKGROUND_COLOR);
        Image imgLogo = new Image("file:data/logo.png");
        ImageView imgViewLogo = new ImageView(imgLogo);
        load.getChildren().add(imgViewLogo);

        Pane root = new Pane();
        Scene mapScene = new Scene(root, 500, 500, MAP_BACKGROUND_COLOR);
        stage.setScene(loadScene);

        stage.show();

        Parser p = new Parser();
        Network net = new Network(p.numLines());
        Camera cam = new Camera(122.30, 47.60, 122.33, 47.64, stage.getWidth(), stage.getHeight());
            // different aspect ratio
            //Camera cam = new Camera(122.30, 47.60, 122.43, 47.76);

/*        new Thread() {
            public void run() {
                // build network
                Network.buildNetwork(net, p);

                // add shapes to root pane
                root.getChildren().addAll(cam.filterVisible(net));

                // parser obj no longer required
                p.destructor();

                stage.setScene(mapScene);
            }
        }.start();*/

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
                root.getChildren().addAll(cam.filterVisible(net));

                // parser obj no longer required
                p.destructor();

                stage.setScene(mapScene);
            }
        });
        service.start();

        // mouse click handler
        mapScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getSceneX();
                mouseY = mouseEvent.getSceneY();
            }
        });

        // pan handler
        mapScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double mousedx = mouseEvent.getSceneX() - mouseX;
                double mousedy = mouseEvent.getSceneY() - mouseY;
                cam.pan(mousedx, mousedy);
                root.getChildren().clear();
                root.getChildren().addAll(cam.filterVisible(net));
            }
        });

        // zoom handler
        mapScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
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

        // windows resize handler
        mapScene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                cam.setResX(newSceneWidth.intValue());
                root.getChildren().addAll(cam.filterVisible(net));
            }
        });

        // window resize handler
        mapScene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                cam.setResY(newSceneHeight.intValue());
            }
        });
    }

    @Override
    public void start(Stage stage) {

        initUI(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}