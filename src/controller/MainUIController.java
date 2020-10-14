package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.Main;
import module.ScreenManage;
import module.Student;
import module.UpdateTable;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

import static controller.LoginController.passWork;
import static controller.LoginController.userName;

public class MainUIController implements Initializable {
    protected static String mssv;
    private final ScreenManage scrManage = new ScreenManage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setIcon();
        InforAcc();
        ivEnableMenu(false);
        disImage(ivInsert, true);
        animationTabManageAcc();

    }

    /**
     * create icon and Drag Screen of MainUI
     */
    private void setIcon() {
        scrManage.iconExit(ivExit);
        scrManage.iconMinimum(ivMin);
        scrManage.iconClean(ivMssv, tfMssv);
        scrManage.iconClean(ivName, tfName);
        scrManage.iconClean(ivClass, tfClass);
        scrManage.iconClean(ivCpa, tfCpa);
        scrManage.iconClean(ivNewAcc, tfNewAcc);
        scrManage.iconPass(ivPwfOld, tfPwfOld, pwfOld);
        scrManage.iconPass(ivPwfNew, tfPwfNew, pwfNew);
        scrManage.iconPass(ivPwfAgainNew, tfPwfAgainNew, pwfAgainNew);
        scrManage.iconPass(ivNewPass, tfNewPass, pwfNewPass);
        scrManage.iconPass(ivNewAgainPass, tfNewAgainPass, pwfNewAgainPass);
        scrManage.DraggScreen(Main.primaryStage, bane);
    }

    /**
     * insert Student in table
     */
    @FXML
    public void insertTable() {
        if (tfMssv.getText().length() == 0 || tfName.getText().length() == 0
                || dpBirth.getValue() == null
                || tfClass.getText().length() == 0
                || toggle.getSelectedToggle() == null
                || tfCpa.getText().length() == 0) {

        } else if (UpdateTable.checkResult("SELECT * FROM Data WHERE mssv = '" + tfMssv.getText() + "'")) {
            System.out.println("Da co trong CSDL");
        } else {
            String sql = "INSERT INTO Data VALUES('" + tfMssv.getText().trim()
                    + "', N'" + tfName.getText().trim() + "','"
                    + dpBirth.getValue() + "','" + rbF.isSelected() + "',N'"
                    + tfClass.getText().trim() + "', " + tfCpa.getText() + ")";
            UpdateTable.updateTable(sql);

            sql = "INSERT INTO Follow VALUES('" + userName + "','" + tfMssv.getText() + "')";
            UpdateTable.updateTable(sql);
            txCountSV.setText(String.valueOf(UpdateTable.loadTable("SELECT Data.* FROM Data, Follow "
                    + "WHERE Data.mssv = Follow.mssv AND Follow.Accounts = '" + userName + "'", tbStudent)));
            resetTexfField();
        }
    }

    @FXML
    public void tableClick(MouseEvent e) {
        if (MouseButton.PRIMARY == e.getButton()) {
            ivEnableMenu(true);
            disImage(ivInsert, false);
            if (tbStudent.getSelectionModel().getSelectedIndex() < 0) {
                return;
            }
            mssv = tbStudent.getSelectionModel().getSelectedItem().getMssv();
            tfMssv.setText(mssv);
            tfName.setText(tbStudent.getSelectionModel().getSelectedItem()
                    .getName());
            tfClass.setText(tbStudent.getSelectionModel().getSelectedItem()
                    .getClassName());
            tfCpa.setText(tbStudent.getSelectionModel().getSelectedItem()
                    .getCpa());
            String time = tbStudent.getSelectionModel().getSelectedItem()
                    .getDate();
            dpBirth.setValue(LocalDate.of(Integer.parseInt(time.substring(6)),
                    Integer.parseInt(time.substring(3, 5)),
                    Integer.parseInt(time.substring(0, 2))));
            if (tbStudent.getSelectionModel().getSelectedItem().getSex()
                    .equals("nữ")) {
                toggle.selectToggle(rbF);
            } else {
                toggle.selectToggle(rbM);
            }
        } else {
            StudentConstraintInstructor.stage = new Stage(StageStyle.TRANSPARENT);
            StudentConstraintInstructor.stage.initModality(Modality.APPLICATION_MODAL);
            Main.primaryStage.setOpacity(0.5);
            new ScreenManage().SplapScreen(StudentConstraintInstructor.stage,
                    "StudentConstraintInstructor.fxml", 0, 700);
        }
    }

    @FXML
    public void changeTable() {
        String sql = "UPDATE Data SET mssv = '" + tfMssv.getText().trim()
                + "', name = N'" + tfName.getText().trim() + "', birth = '"
                + dpBirth.getValue() + "', sex = '" + rbF.isSelected()
                + "', [class] = N'" + tfClass.getText().trim() + "', cpa = "
                + tfCpa.getText().trim() + " WHERE mssv = " + mssv + "";
        UpdateTable.updateTable(sql);
        // Neu la thao tac them Student vao User
        if (UpdateTable.checkResult("SELECT * FROM Follow WHERE Accounts = '"
                + userName + "' AND mssv = '" + tfMssv.getText() + "'")) {
            sql = "UPDATE Follow SET mssv = '" + tfMssv.getText().trim() + "' WHERE mssv = " + mssv + "";
            UpdateTable.updateTable(sql);
        } // Con khong la thao tac chinh sua
        else {
            UpdateTable.updateTable("INSERT INTO Follow VALUES('" + userName + "', '" + tfMssv.getText() + "')");
        }
        txCountSV.setText(String.valueOf(UpdateTable.loadTable("SELECT Data.* FROM Data, Follow "
                + "WHERE Data.mssv = Follow.mssv AND Follow.Accounts = '" + userName + "'", tbStudent)));
        resetTexfField();
    }

    @FXML
    public void deleteTable() {
        String sql = "DELETE Follow WHERE mssv = '" + mssv + "' AND Accounts = '" + userName + "'";
        UpdateTable.updateTable(sql);
        sql = "SELECT * FROM Follow WHERE mssv = '" + mssv + "'";
        if (!UpdateTable.checkResult(sql)) {
            sql = "DELETE Data WHERE mssv = '" + mssv + "'";
            UpdateTable.updateTable(sql);
        }
        txCountSV.setText(String.valueOf(UpdateTable.loadTable("SELECT Data.* FROM Data, Follow "
                + "WHERE Data.mssv = Follow.mssv AND Follow.Accounts = '" + userName + "'", tbStudent)));
        resetTexfField();
    }

    /**
     * reset all textField in tab Manage
     */
    @FXML
    public void resetTexfField() {
        tfMssv.clear();
        tfName.clear();
        tfClass.clear();
        tfCpa.clear();
        dpBirth.setValue(null);
        toggle.selectToggle(null);
        ivEnableMenu(false);
        ivMssv.setVisible(false);
        ivName.setVisible(false);
        ivCpa.setVisible(false);
        ivClass.setVisible(false);

        disImage(ivReset, true);
        disImage(ivInsert, true);
    }

    /**
     * tabSearch animation
     */
    @FXML
    public void tabSearch() {
        cbSearch.setItems(FXCollections.observableArrayList("Mã số sinh viên",
                "Họ tên sinh viên", "Ngày sinh", "Giới tính", "Lớp", "Điểm"));
        cbSearch.setOpacity(0);
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,
                new KeyValue(cbSearch.opacityProperty(), 0), new KeyValue(
                cbSearch.rotateProperty(), 0), new KeyValue(
                labelLogo.scaleXProperty(), 0.3), new KeyValue(
                labelLogo.scaleYProperty(), 0.3)), new KeyFrame(
                new Duration(1000), new KeyValue(cbSearch.opacityProperty(),
                0.1), new KeyValue(cbSearch.rotateProperty(), 360),
                new KeyValue(labelLogo.scaleXProperty(), 1.4), new KeyValue(
                labelLogo.scaleYProperty(), 1.4)), new KeyFrame(
                new Duration(2000), new KeyValue(cbSearch.opacityProperty(),
                1.0)));
        timeline.play();
    }

    /**
     * comboBox animation
     */
    @FXML
    public void comboBoxChoose() {
        tfSearch.setDisable(false);
        btSearch.setDisable(false);
        tfSearch.setOpacity(1.0);
        btSearch.setOpacity(1.0);
        tfSearch.clear();
    }

    /**
     * search student in data
     */
    @FXML
    public void search() {
        StringBuffer sql = new StringBuffer("SELECT * FROM Data WHERE ");
        switch (cbSearch.getSelectionModel().getSelectedItem()) {
            case "Mã số sinh viên":
                sql.append("mssv LIKE'%").append(tfSearch.getText().trim())
                        .append("%'");
                break;
            case "Họ tên sinh viên":
                sql.append("name LIKE N'%").append(tfSearch.getText().trim())
                        .append("%'");
                break;
            case "Ngày sinh":
                sql.append("DAY(birth) = ").append(tfSearch.getText().trim())
                        .append(" OR " + "MONTH(birth) = ")
                        .append(tfSearch.getText().trim())
                        .append(" OR YEAR(birth) = ")
                        .append(tfSearch.getText().trim()).append("");
                break;
            case "Giới tính":
                String str = tfSearch.getText().trim();
                switch (str) {
                    case "nữ":
                    case "girl":
                    case "gái":
                    case "women":
                    case "woman":
                    case "1":
                        sql.append("sex = 'true'");
                        break;
                    case "nam":
                    case "boy":
                    case "trai":
                    case "men":
                    case "man":
                    case "0":
                        sql.append("sex = 'false'");
                        break;
                    default:
                        sql.append("sex = 'null'");
                        break;
                }
                break;
            case "Lớp":
                sql.append("[class] LIKE N'%").append(tfSearch.getText().trim())
                        .append("%'");
                break;
            case "Điểm":
                sql.append("cpa = ").append(tfSearch.getText().trim()).append("");
                break;
            default:
                sql = new StringBuffer("null");
                break;
        }
        UpdateTable.loadTable(sql.toString(), tbStudent);

    }

    /**
     * change password
     */
    @FXML
    public void btChangePass() {
        if (pwfOld.getText().length() == 0 || pwfNew.getText().length() == 0
                || pwfAgainNew.getText().length() == 0) {
            pwfOld.requestFocus();
        } else if (!pwfOld.getText().equals(passWork)) {
            pwfOld.requestFocus();
        } else if (!pwfNew.getText().equals(pwfAgainNew.getText())) {
            pwfNew.requestFocus();
        } else {
            String sql = "UPDATE Instructor SET Password = '" + pwfNew.getText()
                    + "' WHERE Accounts = '" + userName + "'";
            UpdateTable.updateTable(sql);
        }
    }

    /**
     * reset textField in tab Change Password
     */
    @FXML
    public void btResetChangePass() {
        pwfOld.clear();
        ivPwfOld.setVisible(false);
        pwfNew.clear();
        ivPwfNew.setVisible(false);
        pwfAgainNew.clear();
        ivPwfAgainNew.setVisible(false);
    }

    /**
     * create new Account
     *
     */
    @FXML
    public void btNewAcc() {
        if (tfNewAcc.getText().length() == 0
                || pwfNewPass.getText().length() == 0
                || pwfNewAgainPass.getText().length() == 0) {
            tfNewAcc.requestFocus();
        } else if (!pwfNewPass.getText().equals(pwfNewAgainPass.getText())) {
            pwfNewPass.requestFocus();
        } else {
            String sql = "SELECT * FROM Instructor WHERE Accounts = '"
                    + tfNewAcc.getText().trim() + "'";
            if (UpdateTable.checkResult(sql)) {
                tfNewAcc.requestFocus();
            } else {
                String sql1 = "INSERT INTO Instructor(Accounts, Password)"
                        + " VALUES('"
                        + tfNewAcc.getText().trim() + "', '"
                        + pwfNewPass.getText().trim() + "')";
                UpdateTable.updateTable(sql1);
            }
        }
    }

    @FXML
    public void detailNewAcc() {
        DetailController.stage = new Stage(StageStyle.TRANSPARENT);
        DetailController.stage.initModality(Modality.APPLICATION_MODAL);
        Main.primaryStage.setOpacity(0.5);
        new ScreenManage().SplapScreen(DetailController.stage,
                "Detail.fxml", 0, 700);
    }

    /**
     * reset textField in tab create new Account
     *
     */
    @FXML
    public void btResetNewAcc() {
        tfNewAcc.clear();
        ivNewAcc.setVisible(false);
        pwfNewPass.clear();
        ivNewPass.setVisible(false);
        pwfNewAgainPass.clear();
        ivNewAgainPass.setVisible(false);
    }

    /**
     * login imageView in tab Manage
     *
     * @param check <b>true</b> if disable imageView or <false> if enable
     * imageView
     */
    private void ivEnableMenu(boolean check) {
        disImage(ivInsert, check);
        disImage(ivChange, check);
        disImage(ivDelete, check);
        disImage(ivReset, check);
    }

    /**
     * hide imageView
     *
     * @param iv imageView
     * @param check login imageView
     */
    private void disImage(ImageView iv, boolean check) {
        iv.setDisable(!check);
        if (check) {
            iv.setOpacity(1.0);
        } else {
            iv.setOpacity(0.2);
        }
    }

    /**
     * tab Account Information
     */
    private void InforAcc() {
        Calendar time = Calendar.getInstance();
        txDay.setText(String.valueOf(time.get(Calendar.DATE)));
        txMonth.setText(String.valueOf(time.get(Calendar.MONTH) + 1));
        txYear.setText(String.valueOf(time.get(Calendar.YEAR)));
        txAcc_Name.setText(userName);
        String sql = "SELECT * FROM Instructor WHERE Accounts = '" + userName + "'";
        UpdateTable.loadInfor(sql, txAccName, txAccAge, txAccAddr, txAccPhoneNumber, txAccMail, txAccSubject);
        txCountSV.setText(String.valueOf(UpdateTable.loadTable("SELECT Data.* FROM Data, Follow "
                + "WHERE Data.mssv = Follow.mssv AND Follow.Accounts = '" + userName + "'", tbStudent)));
    }

    /**
     * tab Account Manage animation
     */
    private void animationTabManageAcc() {
        manageAcc.expandedPaneProperty().addListener(
                (ChangeListener<TitledPane>) (ov, oldVal, newVal) -> {
                    btResetChangePass();
                    btResetNewAcc();
                    if (manageAcc.getExpandedPane() == tpChangePass) {
                        Timeline timeline = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(
                                        gridPane1.opacityProperty(), 0),
                                        new KeyValue(gridPane1
                                                .translateXProperty(), -30),
                                        new KeyValue(gridPane1
                                                .translateYProperty(), 0),
                                        new KeyValue(labelLogo1
                                                .rotateProperty(), 360),
                                        new KeyValue(labelLogo1
                                                .scaleXProperty(), 0.001),
                                        new KeyValue(labelLogo1
                                                .scaleYProperty(), 0.001)),
                                new KeyFrame(new Duration(500), new KeyValue(
                                        gridPane1.translateXProperty(), 50),
                                        new KeyValue(gridPane1
                                                .opacityProperty(), 0.3),
                                        new KeyValue(labelLogo1
                                                .rotateProperty(), 0)),
                                new KeyFrame(new Duration(1300), new KeyValue(
                                        gridPane1.opacityProperty(), 1.0),
                                        new KeyValue(gridPane1
                                                .translateXProperty(), 0),
                                        new KeyValue(gridPane1
                                                .translateYProperty(), 30),
                                        new KeyValue(labelLogo1
                                                .scaleXProperty(), 1.5),
                                        new KeyValue(labelLogo1
                                                .scaleYProperty(), 1.5)));
                        timeline.play();
                    } else if (manageAcc.getExpandedPane() == tpCreateNewAcc) {
                        Timeline timeline = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(
                                        labelLogo2.rotateProperty(), 0),
                                        new KeyValue(labelLogo3
                                                .rotateProperty(), 0),
                                        new KeyValue(labelLogo4
                                                .rotateProperty(), 0),
                                        new KeyValue(tfNewAcc
                                                .translateXProperty(), 60),
                                        new KeyValue(pwfNewPass
                                                .translateXProperty(), 60),
                                        new KeyValue(pwfNewAgainPass
                                                .translateXProperty(), 60)),
                                new KeyFrame(new Duration(900), new KeyValue(
                                        labelLogo2.rotateProperty(), 360),
                                        new KeyValue(labelLogo3
                                                .rotateProperty(), 360),
                                        new KeyValue(labelLogo4
                                                .rotateProperty(), 360)),
                                new KeyFrame(new Duration(1400), new KeyValue(
                                        tfNewAcc.translateXProperty(), 0),
                                        new KeyValue(pwfNewAgainPass
                                                .translateXProperty(), 0),
                                        new KeyValue(pwfNewPass
                                                .translateXProperty(), 0)));
                        timeline.play();
                    }
                });
    }

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabSearch;
    @FXML
    private Tab tabMnageAcc;
    @FXML
    private Tab tabManage;
    @FXML
    private Tab tabAbout;

    @FXML
    private ToggleGroup toggle;
    @FXML
    private TableView<Student> tbStudent;

    @FXML
    private ImageView ivInsert;
    @FXML
    private ImageView ivChange;
    @FXML
    private ImageView ivDelete;
    @FXML
    private ImageView ivReset;

    @FXML
    private TextField tfMssv;
    @FXML
    private ImageView ivMssv;
    @FXML
    private TextField tfName;
    @FXML
    private ImageView ivName;
    @FXML
    private TextField tfClass;
    @FXML
    private ImageView ivClass;
    @FXML
    private DatePicker dpBirth;
    @FXML
    private TextField tfCpa;
    @FXML
    private ImageView ivCpa;
    @FXML
    private RadioButton rbF;
    @FXML
    private RadioButton rbM;
    @FXML
    private ComboBox<String> cbSearch;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btSearch;
    @FXML
    private Label labelLogo;
    @FXML
    private Text labelLogo1;

    @FXML
    private Label labelLogo2;
    @FXML
    private Label labelLogo3;
    @FXML
    private Label labelLogo4;

    @FXML
    private Accordion manageAcc;
    @FXML
    private TitledPane tpChangePass;
    @FXML
    private TitledPane tpCreateNewAcc;
    @FXML
    private TitledPane tpInfoAcc;

    @FXML
    private PasswordField pwfOld;
    @FXML
    private TextField tfPwfOld;
    @FXML
    private ImageView ivPwfOld;
    @FXML
    private PasswordField pwfNew;
    @FXML
    private TextField tfPwfNew;
    @FXML
    private ImageView ivPwfNew;
    @FXML
    private PasswordField pwfAgainNew;
    @FXML
    private TextField tfPwfAgainNew;
    @FXML
    private ImageView ivPwfAgainNew;

    @FXML
    private TextField tfNewAcc;
    @FXML
    private ImageView ivNewAcc;
    @FXML
    private PasswordField pwfNewPass;
    @FXML
    private TextField tfNewPass;
    @FXML
    private ImageView ivNewPass;

    @FXML
    private PasswordField pwfNewAgainPass;
    @FXML
    private TextField tfNewAgainPass;
    @FXML
    private ImageView ivNewAgainPass;

    @FXML
    private Text txAcc_Name;
    @FXML
    private Text txAccName;
    @FXML
    private Text txAccAge;
    @FXML
    private Text txAccAddr;
    @FXML
    private Text txAccPhoneNumber;
    @FXML
    private Text txAccMail;
    @FXML
    private Text txAccSubject;
    @FXML
    private Text txCountSV;
    @FXML
    private Text txDay;
    @FXML
    private Text txMonth;
    @FXML
    private Text txYear;

    @FXML
    private GridPane gridPane1;
    @FXML
    private ImageView ivExit;
    @FXML
    private ImageView ivMin;
    @FXML
    private AnchorPane bane;
}