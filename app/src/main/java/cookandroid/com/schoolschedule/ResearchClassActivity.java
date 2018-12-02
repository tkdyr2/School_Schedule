package cookandroid.com.schoolschedule;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ResearchClassActivity extends AppCompatActivity {
    SearchClasses searchClasses = new SearchClasses();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_class);

        //region JSON파일 읽기
        AssetManager assetManager = getResources().getAssets(); // asset Folder안에 있는 파일을 취득하기 위한 인스탄스
        InputStream is = null; // text파일을 읽기 위한 인스탄스
        BufferedReader bf = null; // 한글자씩이 아니라 한줄씩 읽기 위한 인스탄스
        try {
            // JSON파일 읽기
            is = assetManager.open("subject_data.json"); // asset 내에 있는 JSON파일 취득
            bf = new BufferedReader(new InputStreamReader(is)); //JSON파일 읽기
            String jsonString = "";
            String str = bf.readLine();
            while(str != null){
                jsonString += str;
                str = bf.readLine();
            }
            is.close();
            bf.close();
            searchClasses.parseJSON(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // endregion

        //region 전공 선택 창 설정
        Spinner major_spinner = findViewById(R.id.spi_major);
        ArrayAdapter<CharSequence> major_adapter = ArrayAdapter.createFromResource(this,
                R.array.spi_major_array, android.R.layout.simple_spinner_item);
        major_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        major_spinner.setAdapter(major_adapter);
        //endregion

        //region 과목 종류 선택 창 설정
        Spinner class_spinner = findViewById(R.id.spi_kindOfClass);
        ArrayAdapter<CharSequence> class_adapter = ArrayAdapter.createFromResource(this,
                R.array.spi_kindOfClass_array, android.R.layout.simple_spinner_item);
        class_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        class_spinner.setAdapter(class_adapter);
        //endregion

        //region 학년 선택 창 설정
        Spinner grade_spinner = findViewById(R.id.spi_grade);
        ArrayAdapter<CharSequence> grade_adapter = ArrayAdapter.createFromResource(this,
                R.array.spi_grade_array, android.R.layout.simple_spinner_item);
        grade_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade_spinner.setAdapter(grade_adapter);
        //endregion

        //region 희망 교시 범위 창 설정
        Spinner first_spinner = findViewById(R.id.spinner_first);
        ArrayAdapter<CharSequence> time_adapter = ArrayAdapter.createFromResource(this,
                R.array.spi_time_array,android.R.layout.simple_spinner_item);
        time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        first_spinner.setAdapter(time_adapter);

        Spinner last_spinner = findViewById(R.id.spinner_last);
        last_spinner.setAdapter(time_adapter);
        //endregion
    }

    ArrayList<String[]> selectedClassInfoArray = new ArrayList<>();

    // 조회 버튼 동작
    public void onResearchButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_research:

                //region 가목조회 및 ListView 표시

                // 유정 입력 값 병수
                String target_category, target_grade, target_title;
                int time_first, time_last;
                int[] reqFlag = {0,0}; // reqFlag[0] = 월공강, reqFlag[1] = 금공강

                //region 유저입력 값 취득
                // 강좌명 입력값 취득
                EditText text_title = findViewById(R.id.className);
                target_title = text_title.getText().toString();

                // Spinner Object를 취득
                Spinner kind_spinner = findViewById(R.id.spi_kindOfClass); //이수구분
                Spinner grade_spinner = findViewById(R.id.spi_grade); //학년
                Spinner first_spinner = findViewById(R.id.spinner_first); //시작 교시
                Spinner last_spinner = findViewById(R.id.spinner_last); //끝 교시

                // 선택되어있는 아이템 취득
                target_category = kind_spinner.getSelectedItem().toString(); // 문자열을 그대로 취득
                target_grade = grade_spinner.getSelectedItem().toString(); // 문자열을 그대로 취득
                time_first = first_spinner.getSelectedItemPosition() - 1; // 위치를 int로 취득
                time_last = last_spinner.getSelectedItemPosition() - 1;  // 위치를 int로 취득

                // 지정된 교시가 유효한지 검사
                if(time_first != -1 && time_last != -1 && time_first >= time_last) {
                    Toast.makeText(this, "제외할 교시가 유효하지 않습니다.", Toast.LENGTH_LONG).show();
                    first_spinner.setSelection(0);
                    last_spinner.setSelection(0);
                    break;
                }

                // CheckBox Object를 취득
                CheckBox condition_m = findViewById(R.id.cB_Mon); // 월공강
                CheckBox condition_f = findViewById(R.id.cB_Fri); // 금공강
                if(condition_m.isChecked()) reqFlag[0] = 1;
                if(condition_f.isChecked()) reqFlag[1] = 1;
                // endregion

                // 과목 검색 메서드 호출
                ArrayList<String[]> tmpArr = searchClasses.searchTheClass(target_title, target_category, target_grade, time_first, time_last, reqFlag);
                final ArrayList<String[]> tmpArr2 = searchClasses.searchTheClass(target_title, target_category, target_grade, time_first, time_last, reqFlag);
                final ArrayList<ClassData> listData = new ArrayList<>();
                // ListView에 표시하는 항목을 생성
                for(int i = 0; i < tmpArr.size(); i++) {
                    String[] processing = tmpArr.get(i);
                    processing[0] = "과목명:" + processing[0];
                    processing[1] = "구분:" + processing[1];
                    processing[2] = processing[2] + "반";
                    processing[3] = "교수:" + processing[3];
                    processing[6] = processing[6] + "학점";
                    processing[7] = processing[7] + "교시";
                    processing[9] = "인원:" + processing[9];

                    ClassData data = new ClassData(processing);
                    listData.add(data);
                }

                // CustomAdapter를 생성 (R.layout.listview_layout : 자기가 만든 리스트뷰 레이아웃)
                final MyAdapter customAdapter = new MyAdapter(this, listData, R.layout.listview_layout);

                // ListView를 취득
                NonScrollListView myClassListView = findViewById(R.id.nonScrollListView1);
                myClassListView.setAdapter(customAdapter);



                // 아이템 선택 동작
                myClassListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        selectedClassInfoArray.add(tmpArr2.get(pos));
                        new QuickToastTask(getApplicationContext(), R.string.toastMessageSelected).execute();

                        return false;
                    }
                });


                //region 숨긴 장바구니 버튼 표시
                Button btn_bucket = this.findViewById(R.id.btn_bucket);
                if (btn_bucket.getVisibility() == View.INVISIBLE) {
                    btn_bucket.setVisibility(View.VISIBLE);
                }
                //endregion

        }
    }



    public ArrayList<String> getStringArray(){
        ArrayList<String> tmpString2 = new ArrayList<>();
        for(int i=0; i < selectedClassInfoArray.size(); i++){
            String[] tmpString1 = selectedClassInfoArray.get(i);
            tmpString2.add(tmpString1[0] + "_" + tmpString1[1]+ "_" + tmpString1[2] + "_" + tmpString1[3] + "_" +
                    tmpString1[4]+ "_" + tmpString1[5]+ "_" + tmpString1[6]+ "_" +
                    tmpString1[7]+ "_" + tmpString1[8]+ "_" + tmpString1[9]);
        }
        /* tmpString2에 들어가 있는 10개의 정보
                     this.title[i], this.category[i],this.room[i], this.professor[i],
                     this.major[i], this.grade[i],this.point[i],
                     this.when[i], this.where[i], this.limit[i]
         */

        return tmpString2;
    }

    // 장바구니 버튼 동작
    public void onBucketButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bucket:
                Intent bucketIntent = new Intent(ResearchClassActivity.this, BucketActivity.class);
                bucketIntent.putExtra("selectedClassInfo", getStringArray());
                startActivity(bucketIntent);
                break;
        }
    }

}


