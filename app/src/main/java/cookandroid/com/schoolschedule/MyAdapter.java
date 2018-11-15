package cookandroid.com.schoolschedule;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    // contextはおまじないと思って記述してください（説明が難しいため）
    private Context context = null;
    // ArrayListの中に独自クラスのSearchClassesを指定
    private ArrayList<ClassData> data = null;
    private int resource = 0;

    // コンストラクタ  MainActivityでアダプターを生成する箇所で呼ばれている
    public MyAdapter(Context context, ArrayList<ClassData> data, int resource){
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    /**
     * データの個数を返すメソッド
     * ※このメソッドは必ず記述すること
     */
    public int getCount() {
        return data.size();
    }

    /**
     * 指定された順番にある項目を返すメソッド
     * ※このメソッドは必ず記述すること
     */
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 指定された順番にある項目の識別idを返すメソッド
     * ※このメソッドは必ず記述すること
     */
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    /**
     * リスト項目を表示するためのメソッド
     * 自作アダプターを作成するにあたって一番重要
     * 実際にユーザが呼ぶ箇所ではなく、リストを生成するために自動で呼ばれる
     * ※このメソッドは必ず記述すること
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) context;
        // 指定された位置のデータを取得
        ClassData data = (ClassData) getItem(position);

        // 再利用可能なビューが無かったら生成する
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        /**
         * ここから各項目に値を割り当てる処理
         */
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

