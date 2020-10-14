package module;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;

import java.io.IOException;

public class ScreenManage {
    private Scene scene;
    private Point2D screen;
    private Point2D prediousLocation;

    public void DraggScreen(Stage primary, AnchorPane bane) {
        bane.setOnMouseDragged((MouseEvent e) -> {
            if (screen != null && prediousLocation != null) {
                primary.setX(prediousLocation.getX() + e.getScreenX()
                        - screen.getX());
                primary.setY(prediousLocation.getY() + e.getScreenY()
                        - screen.getY());
            }
        });
        bane.setOnMousePressed((MouseEvent e) -> {
            screen = new Point2D(e.getScreenX(), e.getScreenY());
        });
        bane.setOnMouseReleased((MouseEvent e) -> {
            prediousLocation = new Point2D(primary.getX(), primary.getY());
        });
    }

    public void SplapScreen(Stage primary, Scene scene) {
        scene.setFill(Color.TRANSPARENT);
        Timeline t = new Timeline(new KeyFrame(Duration.ZERO,
                (ActionEvent e) -> {
                    primary.close();
                    primary.setScene(scene);
                    primary.show();
                }, new KeyValue(scene.getRoot().rotateProperty(), 180)),
                new KeyFrame(new Duration(3000), new KeyValue(scene.getRoot()
                        .rotateProperty(), 0)));
        t.play();

    }

    public void SplapScreen(Stage primary, String FXML, int start, int end) {
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource(
                    "/view/" + FXML)));
            scene.setFill(Color.TRANSPARENT);
            Timeline t = new Timeline(new KeyFrame(new Duration(start), (
                    ActionEvent e) -> {
                primary.close();
                primary.setScene(scene);
                primary.show();
            }, new KeyValue(scene.getRoot().translateXProperty(), 20)),
                    new KeyFrame(new Duration(end), new KeyValue(scene
                            .getRoot().translateXProperty(), 0)));
            t.play();
        } catch (IOException e1) {
        }
    }

    public void iconExit(ImageView iv) {
        iv.setOnMouseClicked((MouseEvent e) -> {
            System.exit(0);
        });
        iv.setOnMouseEntered((MouseEvent e) -> {
            iv.setImage(new Image(getClass().getResource(
                    "/image/exitIconHove.png").toString()));
        });
        iv.setOnMouseExited((MouseEvent e) -> {
            iv.setImage(new Image(getClass().getResource("/image/exitIcon.png")
                    .toString()));
        });
    }

    public void iconMinimum(ImageView iv) {
        iv.setOnMouseClicked((MouseEvent e) -> {
            Main.primaryStage.setIconified(true);
        });
        iv.setOnMouseEntered((MouseEvent e) -> {
            iv.setImage(new Image(getClass().getResource(
                    "/image/minIconHove.png").toString()));
        });
        iv.setOnMouseExited((MouseEvent e) -> {
            iv.setImage(new Image(getClass().getResource("/image/minIcon.png")
                    .toString()));
        });
    }

    public void iconPass(ImageView iv, TextField tf, PasswordField pwf) {

        iv.setOnMousePressed((MouseEvent e) -> {
            tf.setText(pwf.getText());
            tf.setStyle("-fx-background-color: WHITE; -fx-font-weight: bold;");
            pwf.setVisible(false);
            tf.setDisable(false);
            tf.setVisible(true);
            iv.setImage(new Image(getClass().getResource(
                    "/image/appearClick.png").toString()));
        });

        iv.setOnMouseReleased((MouseEvent e) -> {
            pwf.setVisible(true);
            pwf.setDisable(false);
            tf.setDisable(true);
            tf.setVisible(false);
            // tf.setStyle("-fx-font-size: 12;");
            iv.setImage(new Image(getClass().getResource("/image/appear.png")
                    .toString()));
        });

        pwf.setOnKeyTyped((KeyEvent e) -> {
            if (pwf.getText().length() != 0) {
                iv.setVisible(true);
            } else {
                iv.setVisible(false);
            }
        });
    }

    public void iconClean(ImageView iv, TextField tf) {
        iv.setOnMouseEntered((MouseEvent e) -> {
            iv.setImage(new Image(getClass().getResource(
                    "/image/exitIconHove.png").toString()));
        });
        iv.setOnMouseExited((MouseEvent e) -> {
            iv.setImage(new Image(getClass().getResource("/image/exitIcon.png")
                    .toString()));
        });
        iv.setOnMouseClicked((MouseEvent) -> {
            tf.clear();
            iv.setVisible(false);
        });
        tf.setOnKeyTyped((KeyEvent e) -> {
            if (tf.getText().length() != 0) {
                iv.setVisible(true);
            } else {
                iv.setVisible(false);
            }
        });
    }

}