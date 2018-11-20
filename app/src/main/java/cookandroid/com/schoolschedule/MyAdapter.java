package cookandroid.com.schoolschedule;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    // context는 필수 항목
    private Context context = null;
    // ArrayList안에 ClassData를 지정
    private ArrayList<ClassData> data = null;
    private int resource = 0;

    // 이건 Constructor이고  Activity내에서 Adapter를 생성할 때 호출됨
    public MyAdapter(Context context, ArrayList<ClassData> data, int resource){
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

        ClassData data = (ClassData) getItem(position); // 지정된 위치의 데이터를 취득

        // 재이용 가능한 뷰가 없으면 생성함
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        // ListView의 레이아웃의 각 항목에 값을 할당하는 처리
        ((TextView) convertView.findViewById(R.id.title)).setText(data.getTitle());
        ((TextView) convertView.findViewById(R.id.serial)).setText(data.getSerial());
        ((TextView) convertView.findViewById(R.id.room)).setText(data.getRoom());
        ((TextView) convertView.findViewById(R.id.professor)).setText(data.getProfessor());
        ((TextView) convertView.findViewById(R.id.major)).setText(data.getMajor());
        ((TextView) convertView.findViewById(R.id.grade)).setText(data.getGrade());
        ((TextView) convertView.findViewById(R.id.point)).setText(data.getPoint());
        ((TextView) convertView.findViewById(R.id.when)).setText(data.getWhen());
        ((TextView) convertView.findViewById(R.id.where)).setText(data.getWhere());
        ((TextView) convertView.findViewById(R.id.limit)).setText(data.getLimit());


        return convertView;
    }
}

