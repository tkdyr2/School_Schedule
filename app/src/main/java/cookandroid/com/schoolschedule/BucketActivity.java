package cookandroid.com.schoolschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class BucketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket);

        // 전 화면 (과목탐색)에서 보내온 데이터 취득
        Intent thisIntent = getIntent();
        ArrayList<String> tmpArr = new ArrayList<>();
        tmpArr = thisIntent.getStringArrayListExtra("selectedClassInfo");

        //region 과목 리스트
        String[] hoge = {"hoge","hoge","hoge","hoge","hoge"};

        // List항목과 ListView를 대응 시키는 ArrayAdapter를 준비
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hoge);

        // ListView에게 ArrayAdapter를 성정한다
        NonScrollListView selectedClassListView = (NonScrollListView) findViewById(R.id.nonScrollListView2);
        selectedClassListView.setAdapter(adapter);


        //endregion

    }

    // 시뮬레이션 버튼 동작
    public void onSimuButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_simu:
                int numWeek = 5, classTime = 14;
                ArrayList<ArrayList<String>> classList = new ArrayList<>(); // 첫번째 배열 (교시)
                ArrayList<String> week = new ArrayList<String>(); // 두번째 배열 (요일)

                for (int j = 0; j < classTime; j++) {
                    for (int i = 0; i < numWeek; i++) {
                        String temp = "결과 과목 " + (i + 1) + "-" + (j);
                        week.add(temp);
                    }
                    classList.add(week);
                }

                Intent scheduleResultIntent = new Intent(BucketActivity.this, MyScheduleActivity.class);
                scheduleResultIntent.putExtra("resultSchedule", classList);
                startActivity(scheduleResultIntent);
                break;
        }
    }

    // 과목 담기 버튼 동작
    public void onBackButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent backIntent = new Intent(BucketActivity.this, ResearchClassActivity.class);
                startActivity(backIntent);
                break;
        }
    }
}