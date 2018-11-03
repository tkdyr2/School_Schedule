package cookandroid.com.schoolschedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class SearchClasses {
    // TODO 科目のデータ構造体（３次元配列　科目データ×分部屋×科目数）JSON -> SQLite
//    String majors, kindOfClass, grade, classNsme;
//    int[] reqFlag = {0,0,0}; // reqFlag[0] = Mon, reqFlag[1] = Fri, reqFlag[2] = PM
    private int numClasses = 0;
    final int N = 90;
    private String[] title = new String[N];
    private String[] serial = new String[N];
    private String[] room = new String[N];
    private String[] professor = new String[N];
    private String[] category = new String[N];
    private String[] major = new String[N];
    private String[] grade = new String[N];
    private String[] point = new String[N];
    private String[] when = new String[N];
    private String[] where = new String[N];
    private String[] limit = new String[N];
//    private String title, serial, room, professor, category, major, grade, point, when, where, limit;

    public void parseJSON(String jsonString) {
        try {
            JSONArray jsons = new JSONArray(jsonString);
            for (int i = 0; i < jsons.length(); i++) {
                JSONObject jsonRslt = jsons.getJSONObject(i);
                this.title[i] = jsonRslt.getString("title");
                this.serial[i] = jsonRslt.getString("serial");
                this.room[i] = jsonRslt.getString("room");
                this.professor[i] = jsonRslt.getString("professor");
                this.category[i] = jsonRslt.getString("category");
                this.major[i] = jsonRslt.getString("major");
                this.grade [i]= jsonRslt.getString("grade");
                this.point[i] = jsonRslt.getString("point");
                this.when[i] = jsonRslt.getString("when");
                this.where[i] = jsonRslt.getString("where");
                this.limit[i] = jsonRslt.getString("limit");
                this.numClasses++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle(int i){return this.title[i];}
    public String getSerial(int i){return this.serial[i];}
    public String getRoom(int i){return this.room[i];}
    public String getProfessor(int i){return this.professor[i];}
    public String getCategory(int i){return this.category[i];}
    public String getMajor(int i){return this.major[i];}
    public String getGrade(int i){return this.grade[i];}
    public String getPoint(int i){return this.point[i];}
    public String getWhen(int i){return this.when[i];}
    public String getWhere(int i){return this.where[i];}
    public String getLimit(int i){return this.limit[i];}
    public int getNumClasses(){return this.numClasses;}


    public ArrayList searchTheClass(String majors, String kindOfClass, String grade, String classNsme, int[] reqFlag){












        ArrayList<String> result = new ArrayList<>();
        return result;
    }



}
