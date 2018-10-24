package cookandroid.com.schoolschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MyResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_result);

        //Todo: ~~~~~결과를 받아오기~~~~~
        ArrayList<String> myResultData = new ArrayList<>();
        // beforeResultData = null;

        // 시뮬레이션 결과가 없을 때
        if(myResultData.isEmpty()){
            Toast toast = Toast.makeText(MyResultActivity.this, "등록된 시간표가 없습니다.", Toast.LENGTH_LONG);
            toast.show();

            Intent backToMenuIntent = new Intent(MyResultActivity.this, MenuActivity.class);
            startActivity(backToMenuIntent);
        }
        else{

        }

    }
}
