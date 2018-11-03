package cookandroid.com.schoolschedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class SearchClasses {
    // TODO 科目のデータ構造体（３次元配列　科目データ×分部屋×科目数）JSON -> SQLite
    // String majors, kindOfClass, grade, classNsme;
    // int[] reqFlag = {0,0,0}; // reqFlag[0] = Mon, reqFlag[1] = Fri, reqFlag[2] = PM

    private String title, serial, room, professor, category, major, grade, point, when, where, limit;

    public void parseJSON(ArrayList<String> hoge) {
        try {
            JSONArray jsons = new JSONArray(hoge);
            for (int i = 0; i < jsons.length(); i++) {
                JSONObject jsonRslt = jsons.getJSONObject(i);
                this.title = jsonRslt.getString("title");
                this.serial = jsonRslt.getString("serial");
                this.room = jsonRslt.getString("room");
                this.professor = jsonRslt.getString("professor");
                this.category = jsonRslt.getString("category");
                this.major = jsonRslt.getString("major");
                this.grade = jsonRslt.getString("grade");
                this.point = jsonRslt.getString("point");
                this.when = jsonRslt.getString("when");
                this.where = jsonRslt.getString("where");
                this.limit = jsonRslt.getString("limit");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getGrade(){
        return this.grade;
    }



    public ArrayList searchTheClass(String majors, String kindOfClass, String grade, String classNsme, int[] reqFlag){












        ArrayList<String> result = new ArrayList<>();
        return result;
    }



}
