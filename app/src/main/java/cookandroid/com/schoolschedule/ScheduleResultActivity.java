package cookandroid.com.schoolschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ScheduleResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_result);

        // BucketActivity(장바구니화면)에서 넘긴 값 받기
        ArrayList<String> resultData = new ArrayList<>();
        Intent intent = getIntent();
        resultData = intent.getStringArrayListExtra("resultSchedule");

        //region TableLayout 설정
        // TableLayout 취득
        ViewGroup table1 = (ViewGroup)findViewById(R.id.timeTable1);
        ViewGroup table2 = (ViewGroup)findViewById(R.id.timeTable2);
        ViewGroup table3 = (ViewGroup)findViewById(R.id.timeTable3);
        ViewGroup table4 = (ViewGroup)findViewById(R.id.timeTable4);
        ViewGroup table5 = (ViewGroup)findViewById(R.id.timeTable5);
        ViewGroup table6 = (ViewGroup)findViewById(R.id.timeTable6);
        ViewGroup table7 = (ViewGroup)findViewById(R.id.timeTable7);

        int classTime = 14;
        for (int i=0; i<classTime; i++) {
            // 행 추가
            getLayoutInflater().inflate(R.layout.row_layout, table1);
            getLayoutInflater().inflate(R.layout.row_layout, table2);
            getLayoutInflater().inflate(R.layout.row_layout, table3);
            getLayoutInflater().inflate(R.layout.row_layout, table4);
            getLayoutInflater().inflate(R.layout.row_layout, table5);
            getLayoutInflater().inflate(R.layout.row_layout, table6);
            getLayoutInflater().inflate(R.layout.row_layout, table7);
            // 문자 설정
            TableRow tr1 = (TableRow)table1.getChildAt(i);
            TableRow tr2 = (TableRow)table2.getChildAt(i);
            TableRow tr3 = (TableRow)table3.getChildAt(i);
            TableRow tr4 = (TableRow)table4.getChildAt(i);
            TableRow tr5 = (TableRow)table5.getChildAt(i);
            TableRow tr6 = (TableRow)table6.getChildAt(i);
            TableRow tr7 = (TableRow)table7.getChildAt(i);
            String str = String.format(Locale.getDefault(), "%s", "dummy"); //resultData.get(i));
            ((TextView)(tr1.getChildAt(0))).setText(str);
            ((TextView)(tr2.getChildAt(0))).setText(str);
            ((TextView)(tr3.getChildAt(0))).setText(str);
            ((TextView)(tr4.getChildAt(0))).setText(str);
            ((TextView)(tr5.getChildAt(0))).setText(str);
            ((TextView)(tr6.getChildAt(0))).setText(str);
            ((TextView)(tr7.getChildAt(0))).setText(str);
        }

        //endregion


    }
}
