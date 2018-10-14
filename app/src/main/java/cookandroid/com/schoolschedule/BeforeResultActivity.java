package cookandroid.com.schoolschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class BeforeResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_result);


        //Todo: ~~~~~이전의 결과를 받아오기~~~~~
        ArrayList<String> beforeResultData = new ArrayList<>();
       // beforeResultData = null;

        // 시뮬레이션 결과가 없을 때
        if(beforeResultData.isEmpty()){
            Toast toast = Toast.makeText(BeforeResultActivity.this, "이전의 결과가 없습니다.", Toast.LENGTH_LONG);
            toast.show();

            Intent backToMenuIntent = new Intent(BeforeResultActivity.this, MenuActivity.class);
            startActivity(backToMenuIntent);
        }
        else{

        }
    }
}
