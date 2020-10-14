package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import module.ScreenManage;
import module.UpdateTable;

public class StudentConstraintInstructor extends ScreenManage implements Initializable {
    protected static Stage stage;
    private Point2D screen;
    private Point2D prediousLocation;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        iconExit(ivExit);
        UpdateTable.loadListView("SELECT Instructor.name FROM Instructor, Follow "
                + "WHERE Instructor.Accounts = Follow.Accounts AND mssv = '"
                + MainUIController.mssv + "'", listInstructor);
    }

    @Override
    public void iconExit(ImageView iv) {
        iv.setOnMouseClicked((MouseEvent e) -> {
            stage.close();
            Main.primaryStage.setOpacity(1.0);
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

    @FXML
    public void listViewClick() {
        if (listInstructor.getSelectionModel().getSelectedIndex() < 0) {
            return;
        }
        String sql = "SELECT * FROM Instructor WHERE name = N'" + listInstructor.getSelectionModel().getSelectedItem() + "'";
        UpdateTable.loadInfor(sql, tfName, tfAge, tfAddr, tfPhoneNumber, tfEmail, tfSubject);
    }

    @FXML
    public void DraggScr(MouseEvent e) {
        if (prediousLocation != null && screen != null) {
            stage.setX(prediousLocation.getX() - screen.getX() + e.getScreenX());
            stage.setY(prediousLocation.getY() - screen.getY() + e.getScreenY());
        }
    }

    @FXML
    public void PreScr(MouseEvent e) {
        screen = new Point2D(e.getScreenX(), e.getScreenY());
    }

    @FXML
    public void ResScr() {
        prediousLocation = new Point2D(stage.getX(), stage.getY());
    }

    @FXML
    private ImageView ivExit;
    @FXML
    private AnchorPane bane;
    @FXML
    private ListView<String> listInstructor;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField tfAddr;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfSubject;

}
