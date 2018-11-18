package cookandroid.com.schoolschedule;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyScheduleActivity extends AppCompatActivity {

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
        int[][] classFlog = new int[5][10];


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

        String[] hoge = new String[searchClasses.getNumClasses()]; //과목의 내용 하나를 통채로 다 보여주는 hoge배열
        for(int i =0; i < searchClasses.getNumClasses(); i++){
            hoge[i] = searchClasses.getTitle(i) + "_" +searchClasses.getProfessor(i) ;
        }




         m0.setText(hoge[0],TextView.BufferType.NORMAL);
        m1.setText(hoge[0],TextView.BufferType.NORMAL);
        m2.setText(hoge[0],TextView.BufferType.NORMAL);
        m3.setText(hoge[0],TextView.BufferType.NORMAL);
        m4.setText(hoge[0],TextView.BufferType.NORMAL);
        m5.setText(hoge[0],TextView.BufferType.NORMAL);
        m6.setText(hoge[0],TextView.BufferType.NORMAL);
        m7.setText(hoge[0],TextView.BufferType.NORMAL);
        m8.setText(hoge[0],TextView.BufferType.NORMAL);
        m9.setText(hoge[0],TextView.BufferType.NORMAL);
















    }
}
