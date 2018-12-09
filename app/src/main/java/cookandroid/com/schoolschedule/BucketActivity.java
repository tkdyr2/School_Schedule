package cookandroid.com.schoolschedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class BucketActivity extends AppCompatActivity {

    ArrayList<String> tmpArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket);

        // 로컬 파일 읽기 (장바구니 리스트)
        String dataFileName = "bucketData.txt";
        FileIO fileIO = new FileIO(this);
        tmpArr = fileIO.loadClassDataFromFile(dataFileName);


        // 전 화면 (과목탐색)에서 보내온 데이터 취득
//        Intent thisIntent = getIntent();
//        tmpArr = thisIntent.getStringArrayListExtra("selectedClassInfo");
         /* 10개의 정보
                     this.title[i], this.category[i],this.room[i], this.professor[i],
                     this.major[i], this.grade[i],this.point[i],
                     this.when[i], this.where[i], this.limit[i]
         */

        final ArrayList<ClassData> listData = new ArrayList<>();
        for(int i =0; i<tmpArr.size(); i++){
            String[] tmp2 = tmpArr.get(i).split("_",0);
            ClassData data = new ClassData(tmp2);
            listData.add(data);
        }

        // CustomAdapter를 생성 (R.layout.listview_layout : 자기가 만든 리스트뷰 레이아웃)
        final MyAdapter customAdapter = new MyAdapter(this, listData, R.layout.listview_layout);

        // ListView에 표시항목을 설정
        NonScrollListView myClassListView = findViewById(R.id.nonScrollListView2);
        myClassListView.setAdapter(customAdapter);

    }


    // 시뮬레이션 버튼 동작
    public void onSimuButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_simu:
                EditText edit = findViewById(R.id.userInput);
                String userPointLimit = edit.getText().toString();
                if(!userPointLimit.equals("")){
                    Intent scheduleResultIntent = new Intent(BucketActivity.this, MyScheduleActivity.class);
                    scheduleResultIntent.putExtra("bucketData", tmpArr);
                    scheduleResultIntent.putExtra("pointLimit", userPointLimit);
                    startActivity(scheduleResultIntent);
                }
                else{
                    Toast.makeText(this, R.string.pleaseInputMessage,Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    // 장마구니 과목 지우기 버튼 동작
    public void onCleanListButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:

//                //region 장바구니 내용 지우기 전에 확인용 dialog 표시
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("확인");
//                builder.setMessage("모든 과목을 지우시겠습니까?"); //R.string.bucketCleanMessage)
                builder.setPositiveButton("모든 과목 지우기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            // ボタンをクリックしたときの動作
                                String dataFileName = "bucketData.txt";
                                FileIO fileIO = new FileIO(BucketActivity.this);
                                fileIO.resetClassDataFile(dataFileName);
                                reload();
                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Do nothing
                            }
                        });
                builder.show();
//                //endregion

                break;
        }
    }





    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}