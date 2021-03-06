package cookandroid.com.schoolschedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class RegisteredScheduleActivity extends AppCompatActivity {
    public int classTime = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_schedule);
        FrameLayout emptyFrame = findViewById(R.id.frame_empty);
        FrameLayout contentsFrame = findViewById(R.id.frame_contents);

        // 로컬 파일 읽기 (장바구니 리스트)
        String dataFileName = "registeredData.txt";
        FileIO fileIO = new FileIO(this);
        ArrayList<String> tmpArr = fileIO.loadClassDataFromFile(dataFileName);

        dataFileName = "assumptionData.txt";
        ArrayList<String> tmpAssu = fileIO.loadClassDataFromFile(dataFileName);


        // frame 전환
        if(tmpArr.isEmpty()){     // 로컬 파일이 비어있을 때 (등록된 시간표가 없을 때)
            contentsFrame.setVisibility(View.INVISIBLE);
            emptyFrame.setVisibility(View.VISIBLE);
        }
        else{                     // 로컬 파일이 내용이 있을 때 (등록된 시간표가 있을 때)
            emptyFrame.setVisibility(View.INVISIBLE);
            contentsFrame.setVisibility(View.VISIBLE);

            ArrayList<int[]> assu = new ArrayList<>();

            if(tmpAssu != null) {
                for (int i = 0; i < tmpAssu.size(); i++) {
                    String hoge = tmpAssu.get(i);
                    String[] hoge2 = hoge.split(",", 0);
                    int[] hoge3 = {parseInt(hoge2[0]), parseInt(hoge2[1])};
                    assu.add(hoge3);
                }
            }

            ArrayList<String[][]> tableList = new ArrayList<>();
            String[][] table = new String[5][classTime];

            for (String theClass: tmpArr) {
                String[] box = theClass.split("\t",0);
                for(int x = 0; x < 5; x++){
                    for(int y = 0; y < classTime; y++){
                        try{table[x][y] = box[x + 5 * y];}
                        catch (Exception e) {System.out.println(e+"----------------------------------------------------------------------------");}

                        if(table[x][y].equals("empty")) table[x][y] = null;
                    }
                }
                tableList.add(table);
            }


            final ArrayList<TableData> tableData = new ArrayList<>();
            final MyScheduleAdapter customScheduleAdapter;
            NonScrollListView myClassListView;

            if(tableList.size()!=tmpAssu.size()){
                Toast.makeText(this,"発火", Toast.LENGTH_LONG).show();
            }
            else{
                //region ListView로 시간표 표시

                for(int i = 0; i < tableList.size(); i++) {
                    TableData data = new TableData(tableList.get(i), assu.get(i));
                    tableData.add(data);
                }

                // customScheduleAdapter 생성 (R.layout.schedule_layout : 자기가 만든 리스트뷰 레이아웃)
                customScheduleAdapter = new MyScheduleAdapter(this,tableData,R.layout.schedule_layout);

                // ListView를 취득
                myClassListView = findViewById(R.id.nonScrollListView4);
                myClassListView.setAdapter(customScheduleAdapter);
                //endregion
            }

        }
    }

    public void onDeleteButtonClick(View view) {
        switch (view.getId()) {
            case R.id.delete_btn:
                //region 장바구니 내용 지우기 전에 확인용 dialog 표시
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("확인");
                builder.setMessage("모든 과목을 지우시겠습니까?"); //R.string.bucketCleanMessage)
                builder.setPositiveButton("모든 시간표 지우기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        FileIO fileIO = new FileIO(RegisteredScheduleActivity.this);
                        String dataFileName = "registeredData.txt";
                        fileIO.resetClassDataFile(dataFileName);
                        dataFileName = "assumptionData.txt";
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
                //endregion

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
