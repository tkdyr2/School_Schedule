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
        Spinner major_spinner = (Spinner) findViewById(R.id.spi_major);
        ArrayAdapter<CharSequence> major_adapter = ArrayAdapter.createFromResource(this,
                R.array.spi_major_array, android.R.layout.simple_spinner_item);
        major_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        major_spinner.setAdapter(major_adapter);
        //endregion

        //region 과목 종류 선택 창 설정
        Spinner class_spinner = (Spinner) findViewById(R.id.spi_kindOfClass);
        ArrayAdapter<CharSequence> class_adapter = ArrayAdapter.createFromResource(this,
                R.array.spi_kindOfClass_array, android.R.layout.simple_spinner_item);
        class_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        class_spinner.setAdapter(class_adapter);
        //endregion

        //region 학년 선택 창 설정
        Spinner grade_spinner = (Spinner) findViewById(R.id.spi_grade);
        ArrayAdapter<CharSequence> grade_adapter = ArrayAdapter.createFromResource(this,
                R.array.spi_grade_array, android.R.layout.simple_spinner_item);
        grade_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade_spinner.setAdapter(grade_adapter);
        //endregion

        //region 희망 교시 범위 창 설정
        Spinner first_spinner = (Spinner) findViewById(R.id.spinner_first);
        ArrayAdapter<CharSequence> time_adapter = ArrayAdapter.createFromResource(this,
                R.array.spi_time_array,android.R.layout.simple_spinner_item);
        time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        first_spinner.setAdapter(time_adapter);

        Spinner last_spinner = (Spinner)findViewById(R.id.spinner_last);
        last_spinner.setAdapter(time_adapter);
        //endregion
    }

    // 조회 버튼 동작
    public void onResearchButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_research:

                //region 가목조회 및 ListView 표시

                // 유정 입력 값 병수
                String target_category, target_grade;
                int time_first, time_last;
                int[] reqFlag = {0,0}; // reqFlag[0] = 월공강, reqFlag[1] = 금공강

                //region 유저입력 값 취득
                // Spinner Object를 취득
                Spinner kind_spinner = (Spinner) findViewById(R.id.spi_kindOfClass); //이수구분
                Spinner grade_spinner = (Spinner) findViewById(R.id.spi_grade); //학년
                Spinner first_spinner = (Spinner) findViewById(R.id.spinner_first);
                Spinner last_spinner = (Spinner) findViewById(R.id.spinner_last);

                // 선택되어있는 아이템 취득
                target_category = kind_spinner.getSelectedItem().toString();
                target_grade = grade_spinner.getSelectedItem().toString();
                time_first = first_spinner.getSelectedItemPosition();
                time_last = last_spinner.getSelectedItemPosition();

                // 지정된 교시가 유효한지 검사
                if(time_first != 0 && time_last != 0 && time_first >= time_last) {
                    Toast.makeText(this, "제외할 교시가 유효하지 않습니다.", Toast.LENGTH_LONG).show();
                    first_spinner.setSelection(0);
                    last_spinner.setSelection(0);
                    break;
                }

                time_first -= 1;
                time_last -= 1;

                // CheckBox Object를 취득
                CheckBox condition_m = (CheckBox)findViewById(R.id.cB_Mon); // 월공강
                CheckBox condition_f = (CheckBox)findViewById(R.id.cB_Fri); // 금공강
                if(condition_m.isChecked()){
                    reqFlag[0] = 1;
                }
                if(condition_f.isChecked()){
                    reqFlag[1] = 1;
                }
                // endregion

                // 과목 검색 메서드 호출
                ArrayList<String> tmpArray = searchClasses.searchTheClass(target_category, target_grade, time_first, time_last, reqFlag);
                ArrayList<ClassData> listData = new ArrayList<>();
                // ListViewに表示する項目を生成
                for(int i = 0; i < tmpArray.size(); i++) {
                    String[] resultArray = tmpArray.get(i).split(",",0);
                    ClassData data = new ClassData( resultArray[0],  resultArray[1],  resultArray[2],  resultArray[3],  resultArray[4],  resultArray[5],  resultArray[6],  resultArray[7],  resultArray[8],  resultArray[9], resultArray[10]);
                    listData.add(data);
                }

                // CustomAdapterを生成 (R.layout.listview_layout : (自作)リストビューのレイアウト)
                MyAdapter customAdapter = new MyAdapter(this, listData, R.layout.listview_layout);

                // ListViewを取得
                NonScrollListView myClassListView = (NonScrollListView) findViewById(R.id.nonScrollListView1);
                myClassListView.setAdapter(customAdapter);



                //--------------------------------------------------------------------------------------------------------------------
                //ListView에 표시하는 리스트 항목을 ArrayList로 준비한다.
//                Item items = new Item("조회 과목");
//                for(int i = 0; i < tmpArray.size(); i++){
//                    items.add_Items_array(tmpArray.get(i));
//                }
//
//                // List항목과 ListView를 대응 시키는 ArrayAdapter를 준비
//                final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items.getItems_array());
//
//                // ListView에게 ArrayAdapter를 성정한다
//                NonScrollListView classListView = (NonScrollListView) findViewById(R.id.nonScrollListView1);
//                classListView.setAdapter(adapter);
                //endregion

                final ArrayList<String> bucketArray = new ArrayList<String>();
                //region ListView의 아이템을 길게 눌렀을때 아이템 배경색 바꾸기
                myClassListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;
                        //TODO: 아이템 배경색 바꾸기
                        return false;
                    }
                });
                //endregion

                //region 숨긴 장바구니 버튼 표시
                Button btn_bucket = (Button)this.findViewById(R.id.btn_bucket);
                if (btn_bucket.getVisibility() == View.INVISIBLE) {
                    btn_bucket.setVisibility(View.VISIBLE);
                }
                //endregion

        }
    }


    // 장바구니 버튼 동작
    public void onBucketButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bucket:
                Intent bucketIntent = new Intent(ResearchClassActivity.this, BucketActivity.class);
                startActivity(bucketIntent);
                break;
        }
    }

}


