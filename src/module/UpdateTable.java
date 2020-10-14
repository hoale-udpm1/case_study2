package module;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateTable {
    private static final Connection conn = Connect.getConnect();
    private static PreparedStatement psm;
    private static ResultSet rs;
    private static ObservableList<Student> list;
    private static TableColumn<Student, String> tbcName;
    private static TableColumn<Student, String> tbcMssv;
    private static TableColumn<Student, String> tbcBirth;
    private static TableColumn<Student, String> tbcSex;
    private static TableColumn<Student, String> tbcClass;
    private static TableColumn<Student, String> tbcCpa;

    /**
     * check input SQL true or false and check data exist
     *
     * @param sql sentence SQL
     * @return <b>true</b> if input true else input false return <b>false</b>
     */
    public static Boolean checkResult(String sql) {
        try {
            psm = conn.prepareStatement(sql);
            rs = psm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        } finally {
            close();
        }
        return false;
    }

    /**
     * load table from database
     *
     * @param sql sentence SQL
     * @param table table load database
     * @return count Student follow instructor
     */
    @SuppressWarnings("unchecked")
    public static int loadTable(String sql, TableView<Student> table) {

        table.getColumns().clear();

        tbcMssv = new TableColumn<>("Mã số sinh viên");
        tbcName = new TableColumn<>("Họ tên sinh viên");
        tbcBirth = new TableColumn<>("Ngày sinh");
        tbcSex = new TableColumn<>("Giới tính");
        tbcClass = new TableColumn<>("Tên lớp");
        tbcCpa = new TableColumn<>("CPA");

        tbcMssv.setCellValueFactory(new PropertyValueFactory<>("mssv"));
        tbcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbcBirth.setCellValueFactory(new PropertyValueFactory<>("date"));
        tbcSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        tbcClass.setCellValueFactory(new PropertyValueFactory<>("className"));
        tbcCpa.setCellValueFactory(new PropertyValueFactory<>("cpa"));

        tbcMssv.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcName.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcBirth.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcSex.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcClass.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcCpa.setCellFactory(TextFieldTableCell.<Student>forTableColumn());

        tbcMssv.setOnEditCommit((CellEditEvent<Student, String> e) -> {
            ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).setMssv(e
                    .getNewValue());
            updateTable("UPDATE Data SET mssv = '"
                    + e.getNewValue()
                    + "' WHERE mssv = '"
                    + ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).getMssv()
                    + "'");
        });
        tbcName.setOnEditCommit((CellEditEvent<Student, String> e) -> {
            ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).setName(e
                    .getNewValue());
            updateTable("UPDATE Data SET name = N'"
                    + e.getNewValue()
                    + "' WHERE mssv = '"
                    + ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).getMssv()
                    + "'");
        });
        tbcBirth.setOnEditCommit((CellEditEvent<Student, String> e) -> {
            ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).setDate(e
                    .getNewValue());
            String tmp = e.getNewValue();
            updateTable("UPDATE Data SET birth = '"
                    + (tmp.substring(6) + "-" + tmp.substring(3, 5) + "-" + tmp
                    .substring(0, 2))
                    + "' WHERE mssv = '"
                    + ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).getMssv()
                    + "'");
        });
        tbcSex.setOnEditCommit((CellEditEvent<Student, String> e) -> {
            ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).setSex(e.getNewValue());
            updateTable("UPDATE Data SET sex = '"
                    + e.getNewValue().equals("nữ")
                    + "' WHERE mssv = '"
                    + ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).getMssv()
                    + "'");
        });
        tbcClass.setOnEditCommit((CellEditEvent<Student, String> e) -> {
            ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).setClassName(e
                    .getNewValue());
            updateTable("UPDATE Data SET [class] = N'"
                    + e.getNewValue()
                    + "' WHERE mssv = '"
                    + ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).getMssv()
                    + "'");
        });
        tbcCpa.setOnEditCommit((CellEditEvent<Student, String> e) -> {
            ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).setCpa(e.getNewValue());
            updateTable("UPDATE Data SET cpa = '"
                    + e.getNewValue()
                    + "' WHERE mssv = '"
                    + ((Student) e.getTableView().getItems()
                    .get(e.getTablePosition().getRow())).getMssv()
                    + "'");
        });

        tbcMssv.setPrefWidth(120);
        tbcName.setPrefWidth(230);
        tbcBirth.setPrefWidth(120);
        tbcSex.setPrefWidth(75);
        tbcClass.setPrefWidth(190);
        tbcCpa.setPrefWidth(40);

        table.getColumns().addAll(tbcMssv, tbcName, tbcBirth, tbcSex, tbcClass,
                tbcCpa);

        int countSV = 0;
        try {
            psm = conn.prepareStatement(sql);
            rs = psm.executeQuery();
            ArrayList<Student> arr = new ArrayList<>();
            while (rs.next()) {
                ++countSV;
                arr.add(new Student(rs.getString(1), rs.getString(2), rs
                        .getString(3), rs.getString(4), rs.getString(5), rs
                        .getString(6)));
            }
            list = FXCollections.observableArrayList(arr);
            table.setItems(list);
        } catch (SQLException e) {
        } finally {
            close();
        }
        return countSV;
    }

    /**
     * load data from database
     *
     * @param sql sentence SQL
     * @param listView listView load name Teacher
     */
    public static void loadListView(String sql, ListView<String> listView) {
        try {
            psm = conn.prepareCall(sql);
            rs = psm.executeQuery();
            ArrayList<String> arr = new ArrayList<>();
            while (rs.next()) {
                arr.add(rs.getString(1));
            }
            listView.setItems(FXCollections.observableArrayList(arr));
        } catch (SQLException ex) {
        } finally {
            close();
        }
    }

    /**
     *
     * load Information Teacher
     *
     * @param sql sentence SQL
     * @param name name information
     * @param age age information
     * @param addr address information
     * @param phone phone information
     * @param mail mail information
     * @param subject subject information
     */
    public static void loadInfor(String sql, TextField name, TextField age, TextField addr, TextField phone, TextField mail, TextField subject) {
        try {
            psm = conn.prepareCall(sql);
            rs = psm.executeQuery();
            rs.next();
            name.setText(rs.getString(1));
            age.setText(rs.getString(2));
            addr.setText(rs.getString(3));
            phone.setText(rs.getString(4));
            mail.setText(rs.getString(5));
            subject.setText(rs.getString(6));
        } catch (SQLException ex) {
        }
    }

    /**
     *
     * load Information Teacher
     *
     * @param sql sentence SQL
     * @param name name information
     * @param age age information
     * @param addr address information
     * @param phone phone information
     * @param mail mail information
     * @param subject subject information
     */
    public static void loadInfor(String sql, Text name, Text age, Text addr, Text phone, Text mail, Text subject) {
        try{
            psm = conn.prepareCall(sql);
            rs = psm.executeQuery();
            rs.next();
            name.setText(rs.getString(1));
            age.setText(rs.getString(2));
            addr.setText(rs.getString(3));
            phone.setText(rs.getString(4));
            mail.setText(rs.getString(5));
            subject.setText(rs.getString(6));
        } catch (SQLException ex) {
        }
    }

    /**
     * update database
     *
     * @param sql : sentence code SQL
     */
    public static void updateTable(String sql) {
        try {
            psm = conn.prepareStatement(sql);
            rs = psm.executeQuery();
        } catch (SQLException e) {
            System.out.println("error");
        } finally {
            close();
        }
    }

    /**
     * close PreparedStatement add ResultSet on event
     */
    private static void close() {
        try {
            psm.close();
            rs.close();
        } catch (SQLException e) {
        }
    }

}
