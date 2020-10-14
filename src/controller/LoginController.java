package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.Main;
import module.ScreenManage;
import module.UpdateTable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    protected static String userName;
    protected static String passWork;

    @Override
    public void  initialize(URL location, ResourceBundle resources) {
        ScreenManage screenManage = new ScreenManage();
        screenManage.iconExit(ivExit);
        screenManage.iconMinimum(ivMin);
        screenManage.iconClean(ivUser, tfUser);
        screenManage.iconPass(ivPass, tfPass, pwfPass);
    }

    @FXML
    public void ButtonHander() {
        if ("".equals(tfUser.getText())) {
        } else {
            userName = tfUser.getText();
            passWork = pwfPass.getText();
            String sql = "SELECT * FROM Instructor WHERE Accounts ='" + userName
                    + "' AND Password = '" + passWork + "'";
            if (!UpdateTable.checkResult(sql)) {
                tfUser.requestFocus();
            } else {
                Main.primaryStage.close();
                try {
                    Scene scene = new Scene(FXMLLoader.load(getClass()
                            .getResource("/view/MainUI.fxml")));
                    new ScreenManage()
                            .SplapScreen(Main.primaryStage, scene);
                } catch (IOException e) {
                    System.out.println("error");
                }
            }
        }

    }

    @FXML
    private void passWordEnter(KeyEvent e) {
        if (KeyCode.ENTER == e.getCode()) {
            ButtonHander();
        }
    }

    @FXML
    private void btReslease() {
        btLogin.setStyle("-fx-background-color: #0e298e");
    }

    @FXML
    private void btPress() {
        btLogin.setStyle("-fx-font-color: black; -fx-border-color: black; -fx-text-fill: black;");
    }

    @FXML
    private TextField tfUser;
    @FXML
    private PasswordField pwfPass;
    @FXML
    private TextField tfPass;
    @FXML
    private Button btLogin;
    @FXML
    private ImageView ivExit;
    @FXML
    private ImageView ivMin;
    @FXML
    private ImageView ivUser;
    @FXML
    private ImageView ivPass;


}
