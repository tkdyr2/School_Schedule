package cookandroid.com.schoolschedule;

import java.util.Random;

public class ClassData {
    private long _id;
    private String _title;
    private String _serial;
    private String _room;
    private String _professor;
    private String _category;
    private String _major;
    private String _grade;
    private String _point;
    private String _when;
    private String _where;
    private String _limit;

    // constructor
    public ClassData(String[] classInfo){
        this._id = (new Random()).nextLong();
        this._title = classInfo[0];
        this._serial = classInfo[1];
        this._room = classInfo[2];
        this._professor = classInfo[3];
        this._major = classInfo[4];
        this._grade = classInfo[5];
        this._point = classInfo[6];
        this._when = classInfo[7];
        this._where = classInfo[8];
        this._limit = classInfo[9];
    }


    // getter
    public long getId(){ return _id; }
    public String getTitle() { return _title; }
    public String getSerial() { return _serial; }
    public String getRoom() { return _room; }
    public String getProfessor() { return _professor; }
    public String getCategory() { return _category; }
    public String getMajor() { return _major; }
    public String getGrade() { return _grade; }
    public String getPoint() { return _point; }
    public String getWhen() { return _when; }
    public String getWhere() { return _where; }
    public String getLimit() { return _limit; }

    // setter
    public void setTitle(String title) { this._title = title; }
    public void setSerial(String serial) { this._serial = serial; }
    public void setRoom(String room) { this._room = room; }
    public void setProfessor(String professor) { this._serial = professor; }
    public void setCategory(String category) { this._category = category; }
    public void setMajor(String major) { this._major = major; }
    public void setGrade(String grade) { this._grade = grade; }
    public void setPoint(String point) { this._point = point; }
    public void setWhen(String when) { this._when = when; }
    public void setWhere(String where) { this._where = where; }
    public void setLimit(String limit) { this._limit = limit; }
}
