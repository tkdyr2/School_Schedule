package cookandroid.com.schoolschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    //region 배경 Full Screen을 위해 status bar, navigation bar을 표시 안함
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            Log.d("debug","The system bars are visible");
                        } else {
                            Log.d("debug","The system bars are NOT visible");
                        }
                    }
                });
    }

    //endregion

    //region 각 버튼을 터지하면 화면 이동
     // 시뮬레이션 버튼 클릭
    public void onSimuButtonClick(View view){
        switch (view.getId()) {
            case R.id.btn_simulation:
                Intent researchIntent = new Intent(MenuActivity.this, ResearchClassActivity.class);
                startActivity(researchIntent);
                break;
        }
    }
     // 나의 시간표 버튼 클릭
     public void onResultButtonClick(View view){
         switch (view.getId()) {
             case R.id.btn_result:
                 Intent myResultIntent = new Intent(MenuActivity.this, MyScheduleActivity.class);
                 startActivity(myResultIntent);
                 break;
         }
     }
    //endregion

}
