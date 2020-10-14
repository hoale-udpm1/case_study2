package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import module.ScreenManage;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenLogo implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScreenManage scrManage = new ScreenManage();
        scrManage.iconExit(ivExit);
        scrManage.iconMinimum(ivMin);
        TimeLine();
    }

    @FXML
    private Text tx;
    @FXML
    private Label label;
    @FXML
    private ImageView ivExit;
    @FXML
    private ImageView ivMin;

    @FXML
    public void TimeLine() {
        Timeline t = new Timeline(

                new KeyFrame(Duration.ZERO, new KeyValue(tx.opacityProperty(), 0),
                        new KeyValue(label.opacityProperty(), 0), new KeyValue(
                        label.translateXProperty(), -50)),

                new KeyFrame(Duration.seconds(1),
                        new KeyValue(tx.opacityProperty(), 0), new KeyValue(
                        label.translateXProperty(), -50)),

                new KeyFrame(Duration.seconds(2), new KeyValue(label.opacityProperty(),
                        0.3), new KeyValue(label.translateXProperty(), 0)),

                new KeyFrame(Duration.seconds(2.8), new KeyValue(
                        label.opacityProperty(), 1), new KeyValue(tx.opacityProperty(),
                        1), new KeyValue(label.translateXProperty(), 0)));
        t.play();
    }

}
