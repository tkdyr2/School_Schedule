package cookandroid.com.schoolschedule;

import android.app.Activity;
import android.app.backup.BackupHelper;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ResearchClassActivity extends AppCompatActivity {

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


    // 조회 버튼 동작
    public void onResearchButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_research:

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
                ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items.getItems_array());

                // ListView에게 ArrayAdapter를 성정한다
                NonScrollListView classListView = (NonScrollListView) findViewById(R.id.nonScrollListView1);
                classListView.setAdapter(adapter);
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



