package cookandroid.com.schoolschedule;

import android.content.Intent;
import android.content.res.AssetManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyScheduleActivity extends AppCompatActivity {

    // 전 화면 (과목탐색)에서 보내온 데이터 취득
    Intent thisIntent = getIntent();
    ArrayList<String> becketDatas = thisIntent.getStringArrayListExtra("becketDatas");

    private int numClasses = 0;
    final int N = 90;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        TextView m0 = (TextView)findViewById(R.id.monday0);
        TextView m1 = (TextView)findViewById(R.id.monday1);
        TextView m2 = (TextView)findViewById(R.id.monday2);
        TextView m3 =(TextView) findViewById(R.id.monday3);
        TextView m4 =(TextView) findViewById(R.id.monday4);
        TextView m5 = (TextView)findViewById(R.id.monday5);
        TextView m6 =(TextView) findViewById(R.id.monday6);
        TextView m7 =(TextView) findViewById(R.id.monday7);
        TextView m8 =(TextView) findViewById(R.id.monday8);
        TextView m9 =(TextView) findViewById(R.id.monday9);
        TextView t0 =(TextView) findViewById(R.id.tuesday0);
        TextView t1 =(TextView) findViewById(R.id.tuesday1);
        TextView t2 =(TextView) findViewById(R.id.tuesday2);
        TextView t3 =(TextView) findViewById(R.id.tuesday3);
        TextView t4 =(TextView) findViewById(R.id.tuesday4);
        TextView t5 = (TextView) findViewById(R.id.tuesday5);
        TextView t6 = (TextView) findViewById(R.id.tuesday6);
        TextView t7 = (TextView) findViewById(R.id.tuesday7);
        TextView t8 =(TextView)  findViewById(R.id.tuesday8);
        TextView t9 =(TextView)  findViewById(R.id.tuesday9);
        TextView w0 =(TextView)  findViewById(R.id.wndnesday0);
        TextView w1 = (TextView) findViewById(R.id.wndnesday1);
        TextView w2 =(TextView)  findViewById(R.id.wndnesday2);
        TextView w3 =(TextView)  findViewById(R.id.wndnesday3);
        TextView w4 =(TextView)  findViewById(R.id.wndnesday4);
        TextView w5 =(TextView)  findViewById(R.id.wndnesday5);
        TextView w6 = (TextView) findViewById(R.id.wndnesday6);
        TextView w7 =(TextView)  findViewById(R.id.wndnesday7);
        TextView w8 =(TextView)  findViewById(R.id.wndnesday8);
        TextView w9 = (TextView) findViewById(R.id.wndnesday9);
        TextView th0 = (TextView)findViewById(R.id.thursday0);
        TextView th1 =(TextView) findViewById(R.id.thursday1);
        TextView th2 = (TextView)findViewById(R.id.thursday2);
        TextView th3 =(TextView) findViewById(R.id.thursday3);
        TextView th4 = (TextView)findViewById(R.id.thursday4);
        TextView th5 =(TextView)findViewById(R.id.thursday5);
        TextView th6 =(TextView) findViewById(R.id.thursday6);
        TextView th7 =(TextView) findViewById(R.id.thursday7);
        TextView th8 =(TextView) findViewById(R.id.thursday8);
        TextView th9 =(TextView) findViewById(R.id.thursday9);
        TextView f0 = (TextView) findViewById(R.id.friday0);
        TextView f1 = (TextView) findViewById(R.id.friday1);
        TextView f2 = (TextView) findViewById(R.id.friday2);
        TextView f3 = (TextView) findViewById(R.id.friday3);
        TextView f4 = (TextView) findViewById(R.id.friday4);
        TextView f5 = (TextView) findViewById(R.id.friday5);
        TextView f6 = (TextView) findViewById(R.id.friday6);
        TextView f7 = (TextView) findViewById(R.id.friday7);
        TextView f8 = (TextView) findViewById(R.id.friday8);
        TextView f9 =(TextView)  findViewById(R.id.friday9);


        String[] classlist = new String[15];
        int[][] mondayFlog = new int[5][10];


        SearchClasses searchClasses = new SearchClasses();
        // JSON파일 읽기
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

        String[] hoge = new String[searchClasses.getNumClasses()]; //과목의 내용을 읽어와서 hoge배열에 넣어줌
        for(int i =0; i < searchClasses.getNumClasses(); i++){
            hoge[i] = searchClasses.getTitle(i) + "_" +searchClasses.getProfessor(i) ;
        }





//setText를 사용하여 나의시간표xml화면에 표시함.
         //m0.setText(hoge[0],TextView.BufferType.NORMAL);



        String[] exampleClass= new String[6];
        String[] mondayWhen = new String[searchClasses.getNumClasses()];
        String[] mondayTitle = new String[searchClasses.getNumClasses()];

        for(int i =0; i < searchClasses.getNumClasses(); i++){
            mondayWhen[i] = searchClasses.getWhen(i);
            mondayTitle[i] =searchClasses.getTitle(i)+"  "+searchClasses.getRoom(i);

        }


           /* for(int i =0; i < searchClasses.getNumClasses(); i++)
            {

                 if(mondayWhen[i].equals("월-4,5"))
                {
                 if(mondayFlog[4][0]==0&&mondayFlog[5][0]==0)
                 {
                     mondayFlog[4][0] = 1;
                     mondayFlog[5][0] = 1;
                     m4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                     m5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                  }
                }*/



        for(int i =0; i < searchClasses.getNumClasses(); i++)
        {
            if(searchClasses.getWhen(i).equals("월-4,5"))
            {
                m4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("월-7,8,9"))
            {
                m7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m9.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("월-1,2,3"))
            {
                m1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m3.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("월-6,7,8"))
            {
                m6.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("월-4,5 수-6"))
            {
                m4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w6.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("월-6 수-7,8"))
            {
                m6.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("월-1,2 수-3"))
            {
                m1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w3.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("월-7,8 수-9"))
            {
                m7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w9.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("월-3 수-1,2"))
            {
                m3.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("월-1,2 수-7,8"))
            {
                m1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-7,8"))
            {
                t7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-7,8,9"))
            {
                t7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t9.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-1,2,3"))
            {
                t1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t3.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-1,2 목-1,2"))
            {
                t1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-4,5 목-7,8"))
            {
                t4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-4,5 금-1,2"))
            {
                t4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                f1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                f2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-4,5 목-4,5"))
            {
                t4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-1,2 목-3"))
            {
                t1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th3.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-4,5 목-6"))
            {
                t4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th6.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-6 금-1,2"))
            {
                t6.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                f1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                f2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-7,8 목-7"))
            {
                t7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                t8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-3 목-1,2"))
            {
                t3.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-9 목-8,9"))
            {
                t9.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th9.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("화-6 목-4,5"))
            {
                t6.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("수-7,8,9"))
            {
                w7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w9.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("수-1,2,3"))
            {
                w1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                w3.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("수-5"))
            {
                w5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("수-4"))
            {
                w4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("목-4,5"))
            {
                th4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("목-4,5,6"))
            {
                th4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th6.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("목-7,8,9"))
            {
                th7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th8.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                th9.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("금-1,2,3"))
            {
                f1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                f2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                f3.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("금-4,5,6"))
            {
                f4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                f5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                f6.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }
            else if(searchClasses.getWhen(i).equals("금-5,6,7"))
            {
                f5.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                f6.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                f7.setText(mondayTitle[i],TextView.BufferType.NORMAL);
            }






        }















    }
}
