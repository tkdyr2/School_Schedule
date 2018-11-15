package cookandroid.com.schoolschedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

public class MyScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        String[] classlist = new String[15];
        int[][] classFlog = new int[5][10];

        public String loadJSONFromAsset() {
            String json = null;
            try {

                InputStream is = getAssets().open("subject_data.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");

            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;

        }
        JSONObject obj = new JSONObject(json_return_by_the_function);

       findViewById(R.id.monday0);
       findViewById(R.id.monday1);
        findViewById(R.id.monday2);
        findViewById(R.id.monday3);
        findViewById(R.id.monday4);
        findViewById(R.id.monday5);
        findViewById(R.id.monday6);
        findViewById(R.id.monday7);
        findViewById(R.id.monday8);
        findViewById(R.id.monday9);
        findViewById(R.id.tuesday0);
        findViewById(R.id.tuesday1);
        findViewById(R.id.tuesday2);
        findViewById(R.id.tuesday3);
        findViewById(R.id.tuesday4);
        findViewById(R.id.tuesday5);
        findViewById(R.id.tuesday6);
        findViewById(R.id.tuesday7);
        findViewById(R.id.tuesday8);
        findViewById(R.id.tuesday9);
        findViewById(R.id.wndnesday0);
        findViewById(R.id.wndnesday1);
        findViewById(R.id.wndnesday2);
        findViewById(R.id.wndnesday3);
        findViewById(R.id.wndnesday4);
        findViewById(R.id.wndnesday5);
        findViewById(R.id.wndnesday6);
        findViewById(R.id.wndnesday7);
        findViewById(R.id.wndnesday8);
        findViewById(R.id.wndnesday9);
        findViewById(R.id.tuesday0);
        findViewById(R.id.tuesday1);
        findViewById(R.id.tuesday2);
        findViewById(R.id.tuesday3);
        findViewById(R.id.tuesday4);
        findViewById(R.id.tuesday5);
        findViewById(R.id.tuesday6);
        findViewById(R.id.tuesday7);
        findViewById(R.id.tuesday8);
        findViewById(R.id.tuesday9);
        findViewById(R.id.friday0);
        findViewById(R.id.friday1);
        findViewById(R.id.friday2);
        findViewById(R.id.friday3);
        findViewById(R.id.friday4);
        findViewById(R.id.friday5);
        findViewById(R.id.friday6);
        findViewById(R.id.friday7);
        findViewById(R.id.friday8);
        findViewById(R.id.friday9);













    }
}
