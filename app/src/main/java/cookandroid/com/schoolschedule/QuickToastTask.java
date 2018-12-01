package cookandroid.com.schoolschedule;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

public class QuickToastTask extends AsyncTask<String, Integer, Integer> {
    private Toast toast;
    private final Context context;
    private final int msgResId;
    private int dispTime;
    private static final int SHORT = 800;

    public QuickToastTask(final Context context, final int msgResId) {
        this.msgResId = msgResId;
        this.context = context;
        this.dispTime = SHORT;
    }

    @Override
    protected void onPreExecute() {
        Toast toast = Toast.makeText(context, msgResId, Toast.LENGTH_SHORT);
        this.toast = toast;
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected Integer doInBackground(final String... params) {
        try {
            //여기서 토스트 표시시간 Sleep시킴
            Thread.sleep(dispTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(final Integer i) {
        //취소하면 토스트 없어짐
        this.toast.cancel();
    }
}
