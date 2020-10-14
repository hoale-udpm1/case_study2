package module;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.media.AudioClip;

public class Student {private SimpleStringProperty name;
    private SimpleStringProperty mssv;
    private SimpleStringProperty date;
    private SimpleStringProperty sex;
    private SimpleStringProperty className;
    private SimpleStringProperty cpa;

    public Student(String mssv, String name, String date, String sex,
                   String className, String cpa) {
        this.name = new SimpleStringProperty(name);
        this.mssv = new SimpleStringProperty(mssv);
        this.date = new SimpleStringProperty(date);
        this.sex = new SimpleStringProperty(sex);
        this.className = new SimpleStringProperty(className);
        this.cpa = new SimpleStringProperty(cpa);
    }


    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getMssv() {
        return mssv.get();
    }

    public void setMssv(String mssv) {
        this.mssv.set(mssv);
    }

    public String getDate() {
        String tmp = date.get();
        if (tmp.startsWith("/", 2))
            return tmp;
        else
            return tmp.substring(8) + "/" + tmp.substring(5, 7) + "/"
                    + tmp.substring(0, 4);
    }

    public String toDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getSex() {
        if (sex.get().equals("1") || sex.get().equals("nữ"))
            return "nữ";
        else
            return "nam";
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public String getCpa() {
        return cpa.get();
    }

    public void setCpa(String cpa) {
        this.cpa.set(cpa);
    }

}