package cookandroid.com.schoolschedule;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ResearchClassActivity extends AppCompatActivity {

    public String category, target_grade, condition_mon, condition_fri, condition_pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_class);

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

    }

    //region  JSON파일 읽기
    public void loadJSONFromRaw(){
        AssetManager assetManager = getResources().getAssets();
        AssetFileDescriptor afd = null;
        InputStream is = null;
        BufferedReader bf = null;
        try {
            // JSON파일 읽기
            is = assetManager.open("subject_data.json");
            bf = new BufferedReader(new InputStreamReader(is));

            ArrayList<String> jsonArray = new ArrayList<>();
            String str = bf.readLine();
            while(str != null){
 //               System.out.println(str);
                str = bf.readLine();
                jsonArray.add(str);
            }
            is.close();
            bf.close();
            System.out.println(is);
            SearchClasses searchClasses = new SearchClasses();
            //searchClasses.parseJSON(jsonArray);
            //Toast.makeText(this,searchClasses.getGrade(),Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }


//        String jsonString = "";//writer.toString();
//        return jsonString;
    }

    //endregion



    // 조회 버튼 동작
    public void onResearchButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_research:

            loadJSONFromRaw();


            //region get Users input data

            // Spinner Object를 취득
            Spinner kind_spinner = (Spinner) findViewById(R.id.spi_kindOfClass); //이수구분
            Spinner grade_spinner = (Spinner) findViewById(R.id.spi_grade); //학년

            // 선택되어있는 아이템의 Index를 취득
                int classKind_idx = kind_spinner.getSelectedItemPosition();
                switch (classKind_idx){
                    case 0:
                        category = ""; // 선택 안함
                        break;
                    case 1:
                        category = "전선"; // 전선 선택
                        break;
                    case 2:
                        category = "전필"; // 전필 선택
                    case 3:
                        category = "영어"; //
                                break;
                    case 4:
                        category = "전문ICT"; //
                        break;
                    case 5:
                        category = ""; //
                        break;
                    case 6:
                        category = ""; //
                        break;
                }
//        <item>사제동행세미나</item>
//        <item>학기</item>
//        <item>균형3</item>


                    // endregion



                //region ListView에 표시하는 리스트 항목을 ArrayList로 준비한다.
                Item items = new Item("dummyData");
                items.add_Items_array("더미 데이터 1");
                items.add_Items_array("더미 데이터 2");
                items.add_Items_array("더미 데이터 3");
                items.add_Items_array("더미 데이터 4");
                items.add_Items_array("더미 데이터 5");
                items.add_Items_array("더미 데이터 6");
                items.add_Items_array("더미 데이터 7");
                items.add_Items_array("더미 데이터 8");
                items.add_Items_array("더미 데이터 9");

                // List항목과 ListView를 대응 시키는 ArrayAdapter를 준비
                final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items.getItems_array());

                // ListView에게 ArrayAdapter를 성정한다
                NonScrollListView classListView = (NonScrollListView) findViewById(R.id.nonScrollListView1);
                classListView.setAdapter(adapter);

                // ListView의 Item를 클릭하면 선택 중인 상태로 하기
                classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long ld) {
                        String selectedClassInfo = (String) parent.getItemAtPosition(pos);
                        //Toast.makeText(ResearchClassActivity.this,selectedClassInfo,Toast.LENGTH_LONG).show();
                    }
                });

                //endregion

                //region 숨긴 장바구니 버튼 표시
                Button btn_bucket = (Button)this.findViewById(R.id.btn_bucket);
                if (btn_bucket.getVisibility() == View.INVISIBLE) {
                    btn_bucket.setVisibility(View.VISIBLE);
                }
                //endregion

                break;
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



