package cookandroid.com.schoolschedule;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyScheduleAdapter extends BaseAdapter {

    // context는 필수 항목
    private Context context = null;
    // ArrayList안에 TableData를 지정
    private ArrayList<TableData> data = null;
    private int resource = 0;

    // 이건 Constructor이고  Activity내에서 Adapter를 생성할 때 호출됨
    public MyScheduleAdapter(Context context, ArrayList<TableData> data, int resource){
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    // 데이터 개수를 return하는 메서드. ※필수
    public int getCount() {
        return data.size();
    }

    // 지정된 순번에 있는 항목을 return하는 메서드 ※필수
    public Object getItem(int position) {
        return data.get(position);
    }

    // 지정된 순번에 있는 항목의 식별id를 return 하는 메서드 ※필수
    public long getItemId(int position) {
        return data.get(position).getId();
    }


    // 리스트 항목을 표시하기 위한 메서드. Adapter 작성 시 제일 중요한 부분. 실제로 유저가 호출하는 건 아니고 리스트를 생성하기 위해 자동호출됨. ※필수
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) context;

        TableData data = (TableData) getItem(position); // 지정된 위치의 데이터를 취득
        String[][] tmpData = data.getTheTable();

        // 재이용 가능한 뷰가 없으면 생성함
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        String sumClasses = Integer.toString(data.getSumClasses());
        String sumPoints = Integer.toString(data.getSumPoints());

        //region ListView의 레이아웃의 각 항목에 값을 할당하는 처리
        ((TextView) convertView.findViewById(R.id.textViewCountClasses)).setText(sumClasses);
        ((TextView) convertView.findViewById(R.id.textViewSumPoints)).setText(sumPoints);

        ((TextView) convertView.findViewById(R.id.monday0)).setText(tmpData[0][0]);
        ((TextView) convertView.findViewById(R.id.tuesday0)).setText(tmpData[1][0]);
        ((TextView) convertView.findViewById(R.id.monday0)).setText(tmpData[0][0]);
        ((TextView) convertView.findViewById(R.id.tuesday0)).setText(tmpData[1][0]);
        ((TextView) convertView.findViewById(R.id.wndnesday0)).setText(tmpData[2][0]);
        ((TextView) convertView.findViewById(R.id.thursday0)).setText(tmpData[3][0]);
        ((TextView) convertView.findViewById(R.id.friday0)).setText(tmpData[4][0]);

        ((TextView) convertView.findViewById(R.id.monday1)).setText(tmpData[0][1]);
        ((TextView) convertView.findViewById(R.id.tuesday1)).setText(tmpData[1][1]);
        ((TextView) convertView.findViewById(R.id.wndnesday1)).setText(tmpData[2][1]);
        ((TextView) convertView.findViewById(R.id.thursday1)).setText(tmpData[3][1]);
        ((TextView) convertView.findViewById(R.id.friday1)).setText(tmpData[4][1]);

        ((TextView) convertView.findViewById(R.id.monday2)).setText(tmpData[0][2]);
        ((TextView) convertView.findViewById(R.id.tuesday2)).setText(tmpData[1][2]);
        ((TextView) convertView.findViewById(R.id.wndnesday2)).setText(tmpData[2][2]);
        ((TextView) convertView.findViewById(R.id.thursday2)).setText(tmpData[3][2]);
        ((TextView) convertView.findViewById(R.id.friday2)).setText(tmpData[4][2]);

        ((TextView) convertView.findViewById(R.id.monday3)).setText(tmpData[0][3]);
        ((TextView) convertView.findViewById(R.id.tuesday3)).setText(tmpData[1][3]);
        ((TextView) convertView.findViewById(R.id.wndnesday3)).setText(tmpData[2][3]);
        ((TextView) convertView.findViewById(R.id.thursday3)).setText(tmpData[3][3]);
        ((TextView) convertView.findViewById(R.id.friday3)).setText(tmpData[4][3]);

        ((TextView) convertView.findViewById(R.id.monday4)).setText(tmpData[0][4]);
        ((TextView) convertView.findViewById(R.id.tuesday4)).setText(tmpData[1][4]);
        ((TextView) convertView.findViewById(R.id.wndnesday4)).setText(tmpData[2][4]);
        ((TextView) convertView.findViewById(R.id.thursday4)).setText(tmpData[3][4]);
        ((TextView) convertView.findViewById(R.id.friday4)).setText(tmpData[4][4]);

        ((TextView) convertView.findViewById(R.id.monday5)).setText(tmpData[0][5]);
        ((TextView) convertView.findViewById(R.id.tuesday5)).setText(tmpData[1][5]);
        ((TextView) convertView.findViewById(R.id.wndnesday5)).setText(tmpData[2][5]);
        ((TextView) convertView.findViewById(R.id.thursday5)).setText(tmpData[3][5]);
        ((TextView) convertView.findViewById(R.id.friday5)).setText(tmpData[4][5]);

        ((TextView) convertView.findViewById(R.id.monday6)).setText(tmpData[0][6]);
        ((TextView) convertView.findViewById(R.id.tuesday6)).setText(tmpData[1][6]);
        ((TextView) convertView.findViewById(R.id.wndnesday6)).setText(tmpData[2][6]);
        ((TextView) convertView.findViewById(R.id.thursday6)).setText(tmpData[3][6]);
        ((TextView) convertView.findViewById(R.id.friday6)).setText(tmpData[4][6]);

        ((TextView) convertView.findViewById(R.id.monday7)).setText(tmpData[0][7]);
        ((TextView) convertView.findViewById(R.id.tuesday7)).setText(tmpData[1][7]);
        ((TextView) convertView.findViewById(R.id.wndnesday7)).setText(tmpData[2][7]);
        ((TextView) convertView.findViewById(R.id.thursday7)).setText(tmpData[3][7]);
        ((TextView) convertView.findViewById(R.id.friday7)).setText(tmpData[4][7]);

        ((TextView) convertView.findViewById(R.id.monday8)).setText(tmpData[0][8]);
        ((TextView) convertView.findViewById(R.id.tuesday8)).setText(tmpData[1][8]);
        ((TextView) convertView.findViewById(R.id.wndnesday8)).setText(tmpData[2][8]);
        ((TextView) convertView.findViewById(R.id.thursday8)).setText(tmpData[3][8]);
        ((TextView) convertView.findViewById(R.id.friday8)).setText(tmpData[4][8]);

        ((TextView) convertView.findViewById(R.id.monday9)).setText(tmpData[0][9]);
        ((TextView) convertView.findViewById(R.id.tuesday9)).setText(tmpData[1][9]);
        ((TextView) convertView.findViewById(R.id.wndnesday9)).setText(tmpData[2][9]);
        ((TextView) convertView.findViewById(R.id.thursday9)).setText(tmpData[3][9]);
        ((TextView) convertView.findViewById(R.id.friday9)).setText(tmpData[4][9]);

        ((TextView) convertView.findViewById(R.id.monday10)).setText(tmpData[0][10]);
        ((TextView) convertView.findViewById(R.id.tuesday10)).setText(tmpData[1][10]);
        ((TextView) convertView.findViewById(R.id.wndnesday10)).setText(tmpData[2][10]);
        ((TextView) convertView.findViewById(R.id.thursday10)).setText(tmpData[3][10]);
        ((TextView) convertView.findViewById(R.id.friday10)).setText(tmpData[4][10]);

        ((TextView) convertView.findViewById(R.id.monday11)).setText(tmpData[0][11]);
        ((TextView) convertView.findViewById(R.id.tuesday11)).setText(tmpData[1][11]);
        ((TextView) convertView.findViewById(R.id.wndnesday11)).setText(tmpData[2][11]);
        ((TextView) convertView.findViewById(R.id.thursday11)).setText(tmpData[3][11]);
        ((TextView) convertView.findViewById(R.id.friday11)).setText(tmpData[4][11]);

        ((TextView) convertView.findViewById(R.id.monday12)).setText(tmpData[0][12]);
        ((TextView) convertView.findViewById(R.id.tuesday12)).setText(tmpData[1][12]);
        ((TextView) convertView.findViewById(R.id.wndnesday12)).setText(tmpData[2][12]);
        ((TextView) convertView.findViewById(R.id.thursday12)).setText(tmpData[3][12]);
        ((TextView) convertView.findViewById(R.id.friday12)).setText(tmpData[4][12]);

        ((TextView) convertView.findViewById(R.id.monday13)).setText(tmpData[0][13]);
        ((TextView) convertView.findViewById(R.id.tuesday13)).setText(tmpData[1][13]);
        ((TextView) convertView.findViewById(R.id.wndnesday13)).setText(tmpData[2][13]);
        ((TextView) convertView.findViewById(R.id.thursday13)).setText(tmpData[3][13]);
        ((TextView) convertView.findViewById(R.id.friday13)).setText(tmpData[4][13]);
        return convertView;
        //endregion
    }
}
