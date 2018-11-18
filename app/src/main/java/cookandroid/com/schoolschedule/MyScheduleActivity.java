package cookandroid.com.schoolschedule;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        TextView m0 =(TextView) findViewById(R.id.monday0);
        TextView m1 =(TextView) findViewById(R.id.monday1);
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

        AssetManager assetManager = getResources().getAssets();

        try{
            //사용하고자 하는 json 파일 open
            AssetManager.AssetInputStream ais = (AssetManager.AssetInputStream)assetManager.open("json/subject_data.json");

            //stream을 리더로 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(ais));

            //StringBuilder 사용
            StringBuilder sb = new StringBuilder();

            //json파일의 내용이 용량이 클경우 Stirng 의 허용점인 4096 byte 를 넘어가면 오류발생
            int bufferSize = 1024 * 1024;

            //char 로 버프 싸이즈 만큼 담기위해 선언
            char readBuf [] = new char[bufferSize];
            int resultSize = 0;

            //파일의 전체 내용 읽어오기
            while((resultSize = br.read(readBuf))  != -1){
                if(resultSize == bufferSize){
                    sb.append(readBuf);
                }else{
                    for(int i = 0; i < resultSize; i++){
                        //StringBuilder 에 append
                        sb.append(readBuf[i]);
                    }
                }
            }
            // 수정 - 새로운 문자열을 만들어서 내부 버퍼의 내용을 복사하고 반환한다.
            String jString = sb.toString();

            //JSONObject 얻어 오기
            JSONObject  jsonObject =  new JSONObject(jString);

            //json value 값 얻기
            String title = jsonObject.getString("title").toString();   //결과값 TEST

            //이미지 사용법에 대해서는 이미 아시리라 믿고 패스 할께요;;

            //JSONArray 사용법
            JSONArray jArr = new JSONArray(jsonObject.getString("buttons"));

            //StringArray에 buttons 의 title 키의 value값을 담겠습니다.

            String btnTitle [] = new String[jArr.length()];

            for(int i = 0; i < jArr.length(); i++){
                btnTitle [i] = jArr.getJSONObject(i).getString("title").toString();
                //출력하여 결과 얻기
                System.out.println("btnTitle[" + i + "]=" + btnTitle[i]);
            }

        }catch(JSONException je){
            Log.e("jsonErr", "json에러입니당~", je);
        }catch(Exception e){
            Log.e("execption", "파일이 없나봐용", e);
        }
    }












}
}
