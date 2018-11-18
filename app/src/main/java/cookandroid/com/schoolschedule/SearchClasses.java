package cookandroid.com.schoolschedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SearchClasses {

    private int numClasses = 0;
    private final int N = 90;
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



    // JSON파일 변환 & setter
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

    // 과목 검색 메서드
    public ArrayList<String[]> searchTheClass(String target_title, String target_category, String target_grade, int time_first, int time_last, int[] reqFlag){
        ArrayList<String[]> result = new ArrayList<>();

        int[] flag = new int[this.numClasses]; // 초기화로 모둔 요소에는 0이 들어감
        int judgeNum = 0;


        // 이수구분으로 조건 조회
        if(!target_category.equals("이수구분")){ // 이수구분이 선택 되어있을 때
            judgeNum++;
            for(int i = 0; i < this.numClasses; i++){
                if(this.category[i].equals(target_category)){
                    // 일치하는 이수 구분의 index를 +1 함
                    flag[i] += 1;
                }
            }
        }

        // 학년으로 조건 조회
        if(!target_grade.equals("학년")){// 학년이 선택 되어있을 때
            judgeNum++;
            for(int i = 0; i < this.numClasses; i++){
                if(this.grade[i].contains(target_grade) || this.grade[i].equals("컴퓨터공학부(공통)")){
                    // 일치하는 학년의 index를 +1 함
                    flag[i] += 1;
                }
            }
        }

        // 강좌명으로 조건 조회
        if(!target_grade.equals("")){// 학년이 선택 되어있을 때
            judgeNum++;
            for(int i = 0; i < this.numClasses; i++){
                if(this.title[i].contains(target_title)){
                    // 유저가 지정한 입력값을 포함하는 과목 index를 +1 함
                    flag[i] += 1;
                }
            }
        }

        // 교시로 조건 조회
        if(time_first == -1 && time_last == -1){} // 시작 교시과 끝 교시 둘 다 선택 되어있지 않음
        else{
            if(time_first == -1) time_first = 0; // 시작교시만 선택 되어있지 않으면 시작교시를 0으로 설정
            if(time_last == -1) time_last = 13;  // 끝교시만 선택 되어있지 않으면 끝교시를 13으로 설정
            for(int i = 0; i < this.numClasses; i++){
                int[] fflag = new int[14]; //0 - 13
                // json의 정보를 분리하는 작업
                String[] tmp1 = this.when[i].split(" ",0); // 월-1,2 수-7,8 -> "월-1,2"과 "수-7,8"로 분리
                for(int j = 0; j < tmp1.length; j++){
                    String[] tmp2 = tmp1[j].split("-",0);  // 월-1,2 -> "월"과 "1,2"로 분리
                    String[] tmp3 = tmp2[1].split(",",0);  // 1,2 -> "1"과 "2"로 분리
                    for(int n = 0; n < tmp3.length; n++){
                        fflag[Integer.parseInt(tmp3[n])] = 1;
                    }
                }

                // fflag에 1이 유저가 지정한 범위에 속해 있는지 검사
                for (int m = time_first; m < time_last; m++){
                    if(fflag[m] == 1) {
                        flag[i] = -1;
                        break;
                    }
                }
            }
        }

        // 공강으로 조건 조회
        if (reqFlag[0] == 1 && reqFlag[1] == 1) { // 월, 금 공강 희망
            for(int i = 0; i < this.numClasses; i++){
                if(this.when[i].contains("월") || this.when[i].contains("금")){
                    // 조건의 안맞는(월요일,금요일에 수업하는 과목) index를 -1로 함
                    flag[i] = -1;
                }
            }
        } else if (reqFlag[0] == 1) { // 월 공강 희망
            for(int i = 0; i < this.numClasses; i++) {
                if (this.when[i].contains("월")) {
                    flag[i] = -1;
                }
            }
        } else if (reqFlag[1] == 1) { // 금 공강 희망
            for(int i = 0; i < this.numClasses; i++) {
                if (this.when[i].contains("금")) {
                    flag[i] = -1;
                }
            }
        }

        for(int i = 0; i < this.numClasses; i++){
            if(flag[i] >= judgeNum) {
                String[] tmpo = { // 10개의 정보
                        "과목명:" + this.title[i], "번호:" + this.serial[i],
                        this.room[i] + "반", "교수:" + this.professor[i] , this.major[i], this.grade[i],
                        this.point[i] + "학점", this.when[i] + "교시", this.where[i], "인원:" + this.limit[i]
                };
                result.add(tmpo);
            }
        }

        return result;
    }

    //region getter
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
    //endregion



}
