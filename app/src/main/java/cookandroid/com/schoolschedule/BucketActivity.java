package cookandroid.com.schoolschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class BucketActivity extends AppCompatActivity {

    ArrayList<String> tmpArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket);

        // 전 화면 (과목탐색)에서 보내온 데이터 취득
        Intent thisIntent = getIntent();
        tmpArr = thisIntent.getStringArrayListExtra("selectedClassInfo");
         /* 10개의 정보
                        this.title[i], this.category[i],
                        this.room[i], this.professor[i], this.major[i], this.grade[i],
                        this.point[i], this.when[i], this.where[i], this.limit[i]
         */

        final ArrayList<ClassData> listData = new ArrayList<>();
        for(int i =0; i<tmpArr.size(); i++){
            String[] tmp2 = tmpArr.get(i).split("_",0);
            ClassData data = new ClassData(tmp2);
            listData.add(data);
        }

        // CustomAdapter를 생성 (R.layout.listview_layout : 자기가 만든 리스트뷰 레이아웃)
        final MyAdapter customAdapter = new MyAdapter(this, listData, R.layout.listview_layout);

        // ListView를 취득
        NonScrollListView myClassListView = findViewById(R.id.nonScrollListView2);
        myClassListView.setAdapter(customAdapter);

    }

    // 시뮬레이션 버튼 동작
    public void onSimuButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_simu:

                Intent scheduleResultIntent = new Intent(BucketActivity.this, MyScheduleActivity.class);
                scheduleResultIntent.putExtra("bucketData", tmpArr);
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