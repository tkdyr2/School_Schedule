package cookandroid.com.schoolschedule;

import java.util.Random;

public class ClassData {
    private long id;
    private String title;
    private String serial;
    private String room;
    private String professor;
    private String category;
    private String major;
    private String grade;
    private String point;
    private String when;
    private String where;
    private String limit;

    // constructor
    public ClassData(String[] classInfo){
        this.id = (new Random()).nextLong();
        this.title = classInfo[0];
        this.serial = classInfo[1];
        this.room = classInfo[2];
        this.professor = classInfo[3];
        this.major = classInfo[4];
        this.grade = classInfo[5];
        this.point = classInfo[6];
        this.when = classInfo[7];
        this.where = classInfo[8];
        this.limit = classInfo[9];
    }


    // getter
    public long getId(){ return id; }
    public String getTitle() { return title; }
    public String getSerial() { return serial; }
    public String getRoom() { return room; }
    public String getProfessor() { return professor; }
    public String getCategory() { return category; }
    public String getMajor() { return major; }
    public String getGrade() { return grade; }
    public String getPoint() { return point; }
    public String getWhen() { return when; }
    public String getWhere() { return where; }
    public String getLimit() { return limit; }

    // setter
    public void setTitle(String title) { this.title = title; }
    public void setSerial(String serial) { this.serial = serial; }
    public void setRoom(String room) { this.room = room; }
    public void setProfessor(String professor) { this.serial = professor; }
    public void setCategory(String category) { this.category = category; }
    public void setMajor(String major) { this.major = major; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setPoint(String point) { this.point = point; }
    public void setWhen(String when) { this.when = when; }
    public void setWhere(String where) { this.where = where; }
    public void setLimit(String limit) { this.limit = limit; }
}
