package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.Main;
import module.ScreenManage;
import module.UpdateTable;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailController implements Initializable {
    protected static Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setIconClean();
    }

    private void setIconClean() {
        ScreenManage screenManage = new ScreenManage();
        screenManage.iconClean(ivName, tfName);
        screenManage.iconClean(ivAge, tfAge);
        screenManage.iconClean(ivAddr, tfAddr);
        screenManage.iconClean(ivPhoneNumber, tfPhoneNumber);
        screenManage.iconClean(ivMail, tfMail);
        screenManage.iconClean(ivSubject, tfSubject);
    }

    @FXML
    public void btOK() {
        try {
            if (tfName.getText().length() == 0
                    || tfAddr.getText().length() == 0
                    || tfAge.getText().length() == 0
                    || tfMail.getText().length() == 0
                    || tfPhoneNumber.getText().length() == 0
                    || tfSubject.getText().length() == 0) {
                tfName.requestFocus();
            } else if (1 > Integer.parseInt(tfAge.getText())) {
                tfAge.requestFocus();
            } else {
                String sql = "INSERT INTO Instructor(Name, Age, Addr, Sdt, Mail, Subject)"
                        + "VALUES('" + tfName.getText().trim() + "',"
                        + tfAge.getText().trim() + ",'"
                        + tfAddr.getText().trim() + "','"
                        + tfPhoneNumber.getText().trim() + "','"
                        + tfMail.getText().trim() + "','"
                        + tfSubject.getText().trim() + "')";
                UpdateTable.updateTable(sql);
                btCancel();
            }
        } catch (NumberFormatException e) {
            tfAge.requestFocus();
        }
    }

    @FXML
    public void btCancel() {
        stage.close();
        Main.primaryStage.setOpacity(1.0);
    }

    @FXML
    private void btOKReslease() {
        btOK.setStyle("-fx-background-color: blue; -fx-border-color: white; -fx-text-fill: white;");
    }

    @FXML
    private void btCancelReslease() {
        btCancel.setStyle("-fx-background-color: blue; -fx-border-color: white; -fx-text-fill: white;");
    }

    @FXML
    private void btOKPress() {
        btOK.setStyle("-fx-font-color: black; -fx-border-color: black; -fx-text-fill: black;");
    }

    @FXML
    private void btCancelPress() {
        btCancel.setStyle("-fx-font-color: black; -fx-border-color: black; -fx-text-fill: black;");
    }

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField tfAddr;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfMail;
    @FXML
    private TextField tfSubject;
    @FXML
    private ImageView ivName;
    @FXML
    private ImageView ivAge;
    @FXML
    private ImageView ivAddr;
    @FXML
    private ImageView ivPhoneNumber;
    @FXML
    private ImageView ivMail;
    @FXML
    private ImageView ivSubject;

    @FXML
    private Button btOK;
    @FXML
    private Button btCancel;

}
