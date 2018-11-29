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
   // Intent thisIntent = getIntent();
   // ArrayList<String> becketDatas = thisIntent.getStringArrayListExtra("becketDatas");

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
    int mo0,mo1,mo2,mo3,mo4,mo5,mo6,mo7,mo8,mo9,
        tu0,tu1,tu2,tu3,tu4,tu5,tu6,tu7,tu8,tu9,
        we0,we1,we2,we3,we4,we5,we6,we7,we8,we9,
        th0,th1,th2,th3,th4,th5,th6,th7,th8,th9,
        fr0,fr1,fr2,fr3,fr4,fr5,fr6,fr7,fr8,fr9 =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        TextView m0 = (TextView) findViewById(R.id.monday0);
        TextView m1 = (TextView) findViewById(R.id.monday1);
        TextView m2 = (TextView) findViewById(R.id.monday2);
        TextView m3 = (TextView) findViewById(R.id.monday3);
        TextView m4 = (TextView) findViewById(R.id.monday4);
        TextView m5 = (TextView) findViewById(R.id.monday5);
        TextView m6 = (TextView) findViewById(R.id.monday6);
        TextView m7 = (TextView) findViewById(R.id.monday7);
        TextView m8 = (TextView) findViewById(R.id.monday8);
        TextView m9 = (TextView) findViewById(R.id.monday9);
        TextView t0 = (TextView) findViewById(R.id.tuesday0);
        TextView t1 = (TextView) findViewById(R.id.tuesday1);
        TextView t2 = (TextView) findViewById(R.id.tuesday2);
        TextView t3 = (TextView) findViewById(R.id.tuesday3);
        TextView t4 = (TextView) findViewById(R.id.tuesday4);
        TextView t5 = (TextView) findViewById(R.id.tuesday5);
        TextView t6 = (TextView) findViewById(R.id.tuesday6);
        TextView t7 = (TextView) findViewById(R.id.tuesday7);
        TextView t8 = (TextView) findViewById(R.id.tuesday8);
        TextView t9 = (TextView) findViewById(R.id.tuesday9);
        TextView w0 = (TextView) findViewById(R.id.wndnesday0);
        TextView w1 = (TextView) findViewById(R.id.wndnesday1);
        TextView w2 = (TextView) findViewById(R.id.wndnesday2);
        TextView w3 = (TextView) findViewById(R.id.wndnesday3);
        TextView w4 = (TextView) findViewById(R.id.wndnesday4);
        TextView w5 = (TextView) findViewById(R.id.wndnesday5);
        TextView w6 = (TextView) findViewById(R.id.wndnesday6);
        TextView w7 = (TextView) findViewById(R.id.wndnesday7);
        TextView w8 = (TextView) findViewById(R.id.wndnesday8);
        TextView w9 = (TextView) findViewById(R.id.wndnesday9);
        TextView th0 = (TextView) findViewById(R.id.thursday0);
        TextView th1 = (TextView) findViewById(R.id.thursday1);
        TextView th2 = (TextView) findViewById(R.id.thursday2);
        TextView th3 = (TextView) findViewById(R.id.thursday3);
        TextView th4 = (TextView) findViewById(R.id.thursday4);
        TextView th5 = (TextView) findViewById(R.id.thursday5);
        TextView th6 = (TextView) findViewById(R.id.thursday6);
        TextView th7 = (TextView) findViewById(R.id.thursday7);
        TextView th8 = (TextView) findViewById(R.id.thursday8);
        TextView th9 = (TextView) findViewById(R.id.thursday9);
        TextView f0 = (TextView) findViewById(R.id.friday0);
        TextView f1 = (TextView) findViewById(R.id.friday1);
        TextView f2 = (TextView) findViewById(R.id.friday2);
        TextView f3 = (TextView) findViewById(R.id.friday3);
        TextView f4 = (TextView) findViewById(R.id.friday4);
        TextView f5 = (TextView) findViewById(R.id.friday5);
        TextView f6 = (TextView) findViewById(R.id.friday6);
        TextView f7 = (TextView) findViewById(R.id.friday7);
        TextView f8 = (TextView) findViewById(R.id.friday8);
        TextView f9 = (TextView) findViewById(R.id.friday9);


        String[] classlist = new String[15];
        int[][] Flog = new int[11][5];

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
            while (str != null) {
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


        for (int i = 0; i < searchClasses.getNumClasses(); i++) {
            hoge[i] = searchClasses.getTitle(i) + "_" + searchClasses.getProfessor(i);
        }


//setText를 사용하여 나의시간표xml화면에 표시함.
        //m0.setText(hoge[0],TextView.BufferType.NORMAL);


        String[] exampleClass = new String[6];
        String[] mondayWhen = new String[searchClasses.getNumClasses()];
        String[] mondayTitle = new String[searchClasses.getNumClasses()];

        for (int i = 0; i < searchClasses.getNumClasses(); i++) {
            mondayWhen[i] = searchClasses.getWhen(i);
            mondayTitle[i] = searchClasses.getTitle(i) + "  " + searchClasses.getRoom(i);

        }





        for(int i =0; i < searchClasses.getNumClasses(); i++)
        {


            if(   searchClasses.getWhen(i).equals("월-4,5"))
            {
                if (Flog[4][0]==0 && Flog[5][0]==0){
                Flog[4][0] =1;
                Flog[5][0] =1;
                m4.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                m5.setText(mondayTitle[i],TextView.BufferType.NORMAL);

                }
                else if(Flog[4][0]==1 || Flog[5][0]==1)
                {



                }


            }
            else if( searchClasses.getWhen(i).equals("월-7,8,9"))
            {
                if (Flog[7][0]==0 && Flog[8][0]==0 && Flog[9][0]==0) {
                    Flog[7][0]=1;
                    Flog[8][0]=1;
                    Flog[9][0]=1;
                    m7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    m8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    m9.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[7][0]==1 || Flog[8][0]==1 || Flog[9][0]==1){


                }


            }
            else if(searchClasses.getWhen(i).equals("월-1,2,3"))
            {
                if(Flog[1][0]==0 && Flog[1][0]==0 && Flog[3][0]==0){
                   Flog[1][0] =1;
                   Flog[2][0] =1;
                   Flog[3][0] =1;
                   m1.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                   m2.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                   m3.setText(mondayTitle[i],TextView.BufferType.NORMAL);
                  }
                 else if (Flog[1][0]==1 || Flog[2][0]==1 || Flog[3][0]==1){



                }

            }
            else if(searchClasses.getWhen(i).equals("월-6,7,8"))
            {
                if (Flog[6][0]==0 && Flog[7][0]==0 && Flog[8][0]==0) {
                    Flog[6][0] = 1;
                    Flog[7][0] = 1;
                    Flog[8][0] = 1;
                    m6.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    m7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    m8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[6][0]==1 || Flog[7][0]==1 || Flog[8][0]==1 ) {



                }


            }
            else if(searchClasses.getWhen(i).equals("월-4,5 수-6"))
            {
                if (Flog[4][0]==0 && Flog[5][0]==0 && Flog[6][2]==0) {
                    Flog[4][0]=1;
                    Flog[5][0]=1;
                    Flog[6][2]=1;
                    m4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    m5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w6.setText(mondayTitle[i], TextView.BufferType.NORMAL);

                }

                else if (Flog[4][0]==1 || Flog[5][0]==1 || Flog[6][2]==1)
                {

                }


            }
            else if(searchClasses.getWhen(i).equals("월-6 수-7,8"))
            {
                if (Flog[6][0]==0 && Flog[7][2]==0 && Flog[8][2]==0) {
                    Flog[6][0]=1;
                    Flog[7][2]=1;
                    Flog[8][2]=1;
                    m6.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w8.setText(mondayTitle[i], TextView.BufferType.NORMAL);

                }

                else if (Flog[6][0]==1 || Flog[7][2]==1 || Flog[8][2]==1){



                }
            }
            else if(searchClasses.getWhen(i).equals("월-1,2 수-3"))
            {
                if (Flog[1][0]==0 && Flog[2][0]==0 && Flog[3][2]==0 ) {
                    Flog[1][0]=1;
                    Flog[2][0]=1;
                    Flog[3][2]=1;
                    m1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    m2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w3.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if(Flog[1][0]==1 || Flog[2][0]==1 || Flog[3][2]==1){



                }

            }
            else if(searchClasses.getWhen(i).equals("월-7,8 수-9"))
            {
                if (Flog[7][0]==0 && Flog[8][0]==0 && Flog[9][2]==0) {
                    Flog[7][0] = 1;
                    Flog[8][0] = 1;
                    Flog[9][2] = 1;
                    m7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    m8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w9.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                 else if (Flog[7][0]==1 || Flog[8][0]==1 || Flog[9][2]==1){


                }

            }
            else if(searchClasses.getWhen(i).equals("월-3 수-1,2"))
            {
                if (Flog[3][0]==0 && Flog[1][2]==0 && Flog[2][2]==0) {
                    Flog[3][0] = 1;
                    Flog[1][2] = 1;
                    Flog[2][2] = 1;
                    m3.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }

                else if (Flog[3][0]==1 || Flog[1][2]==1 || Flog[2][2]==1){



                }

            }
            else if(searchClasses.getWhen(i).equals("월-1,2 수-7,8"))
            {
                if (Flog[1][0]==0 && Flog[2][0]==0 && Flog[7][2]==0 && Flog[8][2]==0 ) {
                    Flog[1][0] = 1;
                    Flog[2][0] = 1;
                    Flog[7][2] = 1;
                    Flog[8][2] = 1;
                    m1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    m2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if(Flog[1][0]==1 || Flog[2][0]==1 || Flog[7][2]==0 && Flog[8][2]==0){


                }




            }
            else if(searchClasses.getWhen(i).equals("화-7,8"))
            {
                if (Flog[7][1]==0 && Flog[8][1]==0) {
                    Flog[7][1] = 1;
                    Flog[8][1] = 1;
                    t7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[7][1]==1 || Flog[8][1]==1){


                }

            }
            else if(searchClasses.getWhen(i).equals("화-7,8,9"))
            {
                if(Flog[7][1]==0 && Flog[8][1]==0 && Flog[9][1]==0) {
                    Flog[7][1]=1;
                    Flog[9][1]=1;
                    t7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t9.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                 else if (Flog[7][1]==1 || Flog[8][1]==1 || Flog[9][1]==1){


                }

            }
            else if(searchClasses.getWhen(i).equals("화-1,2,3"))
            {
                if (Flog[1][1]==0 && Flog[2][1]==0 && Flog[3][1]==0) {
                    Flog[1][1] = 1;
                    Flog[2][1] = 1;
                    Flog[3][1] = 1;
                    t1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t3.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[1][1]==1 || Flog[2][1]==1 || Flog[3][1]==1 ){


                }

            }
            else if(searchClasses.getWhen(i).equals("화-1,2 목-1,2"))
            {

                if (Flog[1][1]==0 && Flog[2][1]==0 && Flog[1][3]==0 && Flog[2][3]==0) {
                    Flog[1][1] = 1;
                    Flog[2][1] = 1;
                    Flog[1][3] = 1;
                    Flog[2][3] = 1;
                    t1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[1][1]==1 || Flog[2][1]==1 || Flog[1][3]==1 || Flog[2][3]==1){


                }
            }




            else if(searchClasses.getWhen(i).equals("화-4,5 목-7,8"))
            {
                if (Flog[4][1]==0 && Flog[5][1]==0 && Flog[7][3]==0 && Flog[8][3]==0) {
                    Flog[4][1] = 1;
                    Flog[5][1] = 1;
                    Flog[7][3] = 1;
                    Flog[8][3] = 1;
                    t4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[4][1]==1 || Flog[5][1]==1 || Flog[7][3]==1 || Flog[8][3]==1){


                }


            }


            else if(searchClasses.getWhen(i).equals("화-4,5 금-1,2")) {


                if (Flog[4][1] == 0 && Flog[5][1] == 0 && Flog[1][4] == 0 && Flog[2][4] == 1) {
                    Flog[4][1] = 1;
                    Flog[5][1] = 1;
                    Flog[4][1] = 1;
                    Flog[2][4] = 1;
                    t4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    f1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    f2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }

                else if (Flog[4][1]==1 || Flog[5][1]==1 || Flog[4][1]==1 || Flog[2][4]==1)
                {


                }
            }
            else if(searchClasses.getWhen(i).equals("화-4,5 목-4,5"))
            {

                if (Flog[4][1]==0 && Flog[5][1]==0 && Flog[4][3]==0 && Flog[5][3]==0) {
                    Flog[4][1] = 1;
                    Flog[5][1] = 1;
                    Flog[4][3] = 1;
                    Flog[5][3] = 1;
                    t4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[5][1]==1 || Flog[5][1]==1 || Flog[4][3]==1 || Flog[5][3]==1){


                }
            }



            else if(searchClasses.getWhen(i).equals("화-1,2 목-3"))
            {
                if (Flog[1][1]==0 && Flog[2][1]==0 && Flog[3][3]==0 ) {
                    Flog[1][1] = 1;
                    Flog[2][1] = 1;
                    Flog[3][3] = 1;
                    t1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th3.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[1][1]==1 || Flog[2][1]==1 || Flog[3][3]==1){



                }
            }


            else if(searchClasses.getWhen(i).equals("화-4,5 목-6"))
            {

                if (Flog[4][1]==0 && Flog[5][1]==0 && Flog[6][3]==0) {
                    Flog[4][1] = 1;
                    Flog[5][1] = 1;
                    Flog[6][3] = 1;
                    t4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th6.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }

                else if (Flog[4][1]==1 || Flog[5][1]==1 || Flog[6][3]==1){


                }
            }

            else if(searchClasses.getWhen(i).equals("화-6 금-1,2"))
            {
                if (Flog[6][1]==0 && Flog[1][4]==0 && Flog[2][4]==0) {
                    Flog[6][1] = 1;
                    Flog[1][4] = 1;
                    Flog[2][4] = 1;
                    t6.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    f1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    f2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[6][1]==1 || Flog[1][4]==1 || Flog[2][4]==1){


                }
            }
            else if(searchClasses.getWhen(i).equals("화-7,8 목-7"))
            {

                if (Flog[7][1]==0 && Flog[8][1]==0 && Flog[7][3]==0) {
                    Flog[7][1] = 1;
                    Flog[8][1] = 1;
                    Flog[7][3] = 1;
                    t7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    t8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[7][1]==1 && Flog[8][1]==1 && Flog[7][3]==1){


                }
            }

            else if(searchClasses.getWhen(i).equals("화-3 목-1,2")) {
                if (Flog[3][1] == 0 && Flog[1][3] == 0 && Flog[3][3] == 0) {
                    Flog[3][1] = 1;
                    Flog[1][3] = 1;
                    Flog[3][3] = 1;
                    t3.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[3][1]==1 && Flog[1][3]==1 && Flog[3][3]==1 ){


                }

            }
            else if(searchClasses.getWhen(i).equals("화-9 목-8,9"))
            {
                if (Flog[9][1] == 0 && Flog[8][3] == 0 && Flog[9][3] == 0) {
                    Flog[9][1] = 1;
                    Flog[8][3] = 1;
                    Flog[9][3] = 1;
                    t9.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th9.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[9][1]==1 || Flog[8][3]==1 || Flog[9][3]==1){

                }
            }
            else if(searchClasses.getWhen(i).equals("화-6 목-4,5"))
            {
                if (Flog[6][1] == 0 && Flog[4][3] == 0 && Flog[5][3] == 0) {
                    Flog[6][1] = 1;
                    Flog[4][3] = 1;
                    Flog[5][3] = 1;
                    t6.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[6][1]==1 || Flog[4][3]==1 || Flog[5][3]==1 )
                {

                }
            }
            else if(searchClasses.getWhen(i).equals("수-7,8,9"))
            {
                if (Flog[7][2] == 0 && Flog[8][2] == 0 && Flog[9][2] == 0) {
                    Flog[7][2] = 1;
                    Flog[8][2] = 1;
                    Flog[9][2] = 1;
                    w7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w9.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[7][2]==1 || Flog[8][2]==1 || Flog[9][2]==1){



                }
            }
            else if(searchClasses.getWhen(i).equals("수-1,2,3"))
            {
                if (Flog[1][2] == 0 && Flog[2][2] == 0 && Flog[3][2] == 0) {
                    Flog[1][2] = 1;
                    Flog[2][2] = 1;
                    Flog[3][2] = 1;
                    w1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    w3.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[1][2]==1 || Flog[2][2]==1 || Flog[3][2]==1)
                {


                }
            }

            else if(searchClasses.getWhen(i).equals("수-5"))
            {
                if (Flog[5][2] == 0 ) {
                    Flog[5][2] = 1;
                    w5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[5][2]==1){


                }
            }

            else if(searchClasses.getWhen(i).equals("수-4"))
            {
                if (Flog[4][2] == 0 ) {
                    Flog[4][2] = 1;
                    w4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[4][2]==1){


                }
            }

            else if(searchClasses.getWhen(i).equals("목-4,5")) {
                if (Flog[4][2] == 0 && Flog[5][2] == 0) {
                    Flog[4][2] = 1;
                    Flog[5][2] = 1;

                    th4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[4][2]==1 || Flog[5][2]==1){


                }
            }
            else if(searchClasses.getWhen(i).equals("목-4,5,6"))
            {
                if (Flog[4][3] == 0 && Flog[5][3] == 0 && Flog[6][3] == 0) {
                    Flog[4][3] = 1;
                    Flog[5][3] = 1;
                    Flog[6][3] = 1;


                    th4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th6.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[4][3]==1 || Flog[5][3]==1 || Flog[6][3]==1){

                }
            }
            else if(searchClasses.getWhen(i).equals("목-7,8,9"))
            {
                if (Flog[7][3] == 0 && Flog[8][3] == 0 && Flog[9][3] == 0) {
                    Flog[7][3] = 1;
                    Flog[8][3] = 1;
                    Flog[9][3] = 1;
                    th7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th8.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    th9.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[7][3]==1 || Flog[8][3]==1 || Flog[9][3]==1)
                {


                }
            }
            else if(searchClasses.getWhen(i).equals("금-1,2,3"))
            {
                if (Flog[1][4] == 0 && Flog[2][4] == 0 && Flog[3][4] == 0) {
                    Flog[1][4] = 1;
                    Flog[2][4] = 1;
                    Flog[3][4] = 1;

                    f1.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    f2.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    f3.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[1][4]==1 || Flog[2][4]==1 || Flog[3][4]==1){


                }
            }
            else if(searchClasses.getWhen(i).equals("금-4,5,6"))
            {
                if (Flog[4][4] == 0 && Flog[5][4] == 0 && Flog[6][4] == 0) {
                    Flog[4][4] = 1;
                    Flog[5][4] = 1;
                    Flog[6][4] = 1;

                    f4.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    f5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    f6.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[4][4]==1 || Flog[5][4]==1 || Flog[6][4]==1)
                {

                }
            }
            else if(searchClasses.getWhen(i).equals("금-5,6,7"))
            {
                if (Flog[5][4] == 0 && Flog[6][4] == 0 && Flog[7][4] == 0) {
                    Flog[5][4] = 1;
                    Flog[6][4] = 1;
                    Flog[7][4] = 1;
                    f5.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    f6.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                    f7.setText(mondayTitle[i], TextView.BufferType.NORMAL);
                }
                else if (Flog[5][4]==1 || Flog[6][4]==1 || Flog[7][4]==1 ){

                }
            }





        } //마지막for문















    }
}
