package cookandroid.com.schoolschedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SearchClasses {
    // TODO 科目のデータ構造体（３次元配列　科目データ×分部屋×科目数）JSON -> SQLite
    // String majors, kindOfClass, grade, classNsme;
    // int[] reqFlag = {0,0,0}; // reqFlag[0] = Mon, reqFlag[1] = Fri, reqFlag[2] = PM


    public void perseJSON() {
        try {
            JSONArray jsons = new JSONArray();
            for (int i = 0; i < jsons.length(); i++) {
                JSONObject jsonRslt = jsons.getJSONObject(i);
                String title = jsonRslt.getString("title");
                String serial = jsonRslt.getString("serial");
                String room = jsonRslt.getString("room");
                String professer = jsonRslt.getString("professer");
                String category = jsonRslt.getString("category");
                String major = jsonRslt.getString("major");
                String grade = jsonRslt.getString("grade");
                String point = jsonRslt.getString("point");
                String when = jsonRslt.getString("when");
                String where = jsonRslt.getString("where");
                String limit = jsonRslt.getString("limit");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public ArrayList searchTheClass(String majors, String kindOfClass, String grade, String classNsme, int[] reqFlag){












        ArrayList<String> result = new ArrayList<>();
        return result;
    }



}
