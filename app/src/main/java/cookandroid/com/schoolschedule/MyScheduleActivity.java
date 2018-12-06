package cookandroid.com.schoolschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Integer.parseInt;


public class MyScheduleActivity extends AppCompatActivity {
    public int classTime = 14;
    public int pointLimit;
    public final ArrayList<String[][]> registeredClassInfoArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        Intent thisintent = getIntent();
        ArrayList<String> tmp = thisintent.getStringArrayListExtra("bucketData");
        pointLimit = parseInt(thisintent.getStringExtra("pointLimit"));

          /* 10개의 정보
                        this.title[i], this.category[i],
                        this.room[i], this.professor[i], this.major[i], this.grade[i],
                        this.point[i], this.when[i], this.where[i], this.limit[i]
         */

        ArrayList<String[][]> simuSamples = new ArrayList<>();

        // 경우 (sample)를 생성
        int sampleNum = 10;
        for(int n = 0; n < sampleNum; n++){
            tmp = sort(tmp);                  // ArrayList의 과목 순서를 결정
            String[][] table = setTable(tmp); // table에 과목 정보 넣기
            simuSamples.add(table);           // table를 ArrayList에 추가
        }

        simuSamples = simuSamplesSort(simuSamples); // table의 우선순위를 맺음 (중간 공백 시간, 학점으로)


        //TODO: 총 학점 표시
        int tableNum = 3;
        int[] point = new int[simuSamples.size()];
        for(int g=0; g < simuSamples.size(); g++){
            point[g] = 0;
        }

        //시간표 중북 검사 및 제거
        boolean[] notSameFlag = new boolean[simuSamples.size()]; // 초기값 false
        for(int v = 0; v < simuSamples.size() - 1; v++){
            for(int b = v + 1; b < simuSamples.size(); b++){
                String[][] tableA = simuSamples.get(v);
                String[][] tableB = simuSamples.get(b);
                for(int x = 0; x < 5; x++){
                    for(int y = 0; y < classTime; y++){
                        if(tableA[x][y] != tableB[x][y]) { // 하나라도 불일치하면 플래그를 true로하고 유효 시간표임을 표시. null 에러 회피를 위해 ! .equals이 아니라 != 를 씀
                            notSameFlag[b] = true;
                        }
                    }
                }
            }
        }

        // 표시 할 시간표 정리
        final ArrayList<String[][]> dispTableList = new ArrayList<>();
        // 표시를 위해 정리
        for(int i = 0; i < simuSamples.size(); i++){
            String[][] dispTable = simuSamples.get(i);
            for(int x = 0; x < 5; x++){
                for(int y = 0; y < classTime; y++){
                    if(dispTable[x][y]!=null) dispTable[x][y] = dispTable[x][y].replace("_", " ");
                }
            }
            if(notSameFlag[i]) dispTableList.add(dispTable);
        }

        // 시간표의 총 과목 수, 총 학점 수
        int[] Assumption = {0,0};
        //region ListView로 시간표 표시
        final ArrayList<TableData> tableData = new ArrayList<>();
        for(int i = 0; i < dispTableList.size(); i++) {
            TableData data = new TableData(dispTableList.get(i), Assumption);
            tableData.add(data);
        }

        // customScheduleAdapter 생성 (R.layout.schedule_layout : 자기가 만든 리스트뷰 레이아웃)
        final MyScheduleAdapter customScheduleAdapter = new MyScheduleAdapter(this,tableData,R.layout.schedule_layout);

        // ListView를 취득
        NonScrollListView myClassListView = findViewById(R.id.nonScrollListView3);
        myClassListView.setAdapter(customScheduleAdapter);

        //endregion

        // 아이템 선택 동작
        myClassListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                registeredClassInfoArray.add(dispTableList.get(pos));
                new QuickToastTask(getApplicationContext(), R.string.toastMessageRegistered).execute();
                return false;
            }
        });
    }

    public void onGoToRegisteredClick(View view) {
        switch (view.getId()) {
            case R.id.goToRegistered:
                Intent registeredIntent = new Intent(MyScheduleActivity.this, RegisteredScheduleActivity.class);
                registeredIntent.putExtra("registeredClassInfo", registeredClassInfoArray);
                startActivity(registeredIntent);
                break;
        }
    }

    public ArrayList<String[][]> simuSamplesSort(ArrayList<String[][]> simuSamples){

        //region 학점 수로 Filter한다.
        int[] point = new int[simuSamples.size()];

        // 각 시간표의 총 학점을 계산
        for(int m=0; m < simuSamples.size(); m++){
            String[][] hoge = simuSamples.get(m);
            for (int x = 0; x < 5; x++){
                for(int y = 0; y < classTime; y++){
                    if(hoge[x][y] != null) {
                        String[] pointTmp = hoge[x][y].split("_", 0);
                        String tmpPoint = pointTmp[4].replace("학점","");
                        point[m] += parseInt(tmpPoint)/3;
                    }
                }
            }
        }

        // 총 학점이 유저 (입력값 - 3)보다 작은 시간표면 삭제
        int count = 0;
        for(int l=0; l < simuSamples.size(); l++){
            if(point[l] > pointLimit - 3){
                simuSamples.remove(l);
                count++;
            }
        }
        //endregion

        //region 시간표의 총 공백 시간(emptyCount) 계산
        int[] emptyCount = new int[simuSamples.size()];
        for(int i=0; i < simuSamples.size(); i++){
            String[][] tmp = simuSamples.get(i);
            for(int x = 0; x < 5; x++){
                int[] startAndEndFlag = {0, 0};
                startAndEndFlag = getStartAndEndFlag(tmp, startAndEndFlag, x);

                for(int n = startAndEndFlag[0]; n < startAndEndFlag[1]; n++){
                    if(tmp[x][n] == null) emptyCount[i]++;
                }
            }
        }
        //endregion

        //region 빈시간이 적은 시간표에게 우선순위를 부여한다.
        simuSamples = quickSort(emptyCount, 0, emptyCount.length - 1, simuSamples);

        return simuSamples;
    }

    // quicksort로 빈시간이 적은 시간표에게 우선순위를 부여한다.
    public static ArrayList<String[][]> quickSort(int[] array, int leftIndex, int rightIndex, ArrayList<String[][]> simuSamples){
        // 左開始点と右開始点がぶつかるまで行う
        if(leftIndex <= rightIndex)
        {
            // 軸要素を中間点にとる
            int pivot = array[(leftIndex + rightIndex) / 2];
            int leftPos = leftIndex;
            int rightPos = rightIndex;

            while(leftIndex <= rightIndex)
            {
                // 左から軸要素より大きい値を探していく
                while(array[leftIndex] < pivot)
                {
                    leftIndex++;
                }
                // 右から軸要素より小さな値を探していく
                while(array[rightIndex] > pivot)
                {
                    rightIndex--;
                }

                // ぶつかったら交換
                if(leftIndex <= rightIndex)
                {
                    int tmp = array[leftIndex];
                    String[][] s_tmp = simuSamples.get(leftIndex);

                    array[leftIndex] = array[rightIndex];
                    simuSamples.set(leftIndex, simuSamples.get(rightIndex));

                    array[rightIndex] = tmp;
                    simuSamples.set(rightIndex, s_tmp);

                    // 交換したら探索続行
                    leftIndex++;
                    rightIndex--;
                }
            }

            // 軸要素より左側を再帰的にソート
            quickSort(array, leftIndex, rightPos, simuSamples);
            // 軸要素より右側を再帰的にソート
            quickSort(array, leftPos, rightIndex, simuSamples);
        }


        return simuSamples;
    }

    // 시작 교시와 마지막 교시 취득
    public int[] getStartAndEndFlag(String[][] tmp, int[] flag, int x){

        for(int y0 = 0; y0 < classTime; y0++){
            if(tmp[x][y0] != null){
                flag[0] = y0;
                break;
            }
        }
        for(int y1 = classTime - 1; y1 >= 0; y1--){
            if(tmp[x][y1] != null){
                flag[1] = y1;
                break;
            }
        }

        return flag;
    }

    public int pointCount = 0;
    // Table를 설정
    public String[][] setTable(ArrayList<String> tmp){
        String[][] table = new String[5][classTime];
        pointCount = 0;

        String[] str = new String[tmp.size()];
        tmp.toArray(str);

        for (String classInfo: str) {
            if(pointCount >= pointLimit) break;

            String[] catch1 = classInfo.split("_",0); //title, category. when. point
            String title = catch1[0];
            String when = catch1[7];
            int point = parseInt(catch1[6]);

            String displayCategory = catch1[1];
            String displayRoom = catch1[2] + "반";
            String displayProfessor = catch1[3];
            String displayPoint = catch1[6] + "학점";

            table = updateTable(table, title, when, point, displayCategory, displayRoom, displayProfessor, displayPoint);

        }

        return table;
    }

    public  String[][] updateTable(String[][] table, String title, String when, int point, String disCate, String disRoom, String disPros, String disPoin){
        boolean GoFlag = true;
        String[] split3;

        //region 이미 과목이 들어있는지 검사
        String[] split = when.split(" ",0); // 수-1,2 와 목-1,2,3
        for(int i=0; i < split.length; i++){
            String[] split2 = split[i].split("-",0); // 수 와 1,2
            switch (split2[0]){
                case "월": split2[0] = "0";
                    break;
                case "화": split2[0] = "1";
                    break;
                case "수": split2[0] = "2";
                    break;
                case "목": split2[0] = "3";
                    break;
                case "금": split2[0] = "4";
                    break;
            }
            split3 = split2[1].split(",",0); // 1 와 2

            for(int j = 0; j < split3.length; j++) {
                if (table[parseInt(split2[0])][parseInt(split3[j])] != null) {
                    GoFlag = false;
                }
            }
        }
        //endregion

        if(GoFlag){
            pointCount += point;
            for(int i=0; i < split.length; i++){   // 예) 수-1,2 와 목-1,2,3
                String[] split2 = split[i].split("-",0); // 수 와 1,2
                switch (split2[0]){
                    case "월": split2[0] = "0";
                        break;
                    case "화": split2[0] = "1";
                        break;
                    case "수": split2[0] = "2";
                        break;
                    case "목": split2[0] = "3";
                        break;
                    case "금": split2[0] = "4";
                        break;
                }
                split3 = split2[1].split(",",0); // 1 와 2
                for(int j = 0; j < split3.length; j++) {  // 예) 1 와 2
                    table[parseInt(split2[0])][parseInt(split3[j])] = title + "_" + disCate + "_" + disRoom + "_" + disPros + "_" + disPoin;
                }
            }
        }

        return table;
    }

    // 전필 과목 우선으로 정렬
    public ArrayList<String> sort(ArrayList<String> rowClasses){
        ArrayList<String> others = new ArrayList<>();
        ArrayList<String> sortedClasses = new ArrayList<>();

        for(int i = 0; i < rowClasses.size(); i++)
        {
            String[] tmp = rowClasses.get(i).split("_",0);
            String category = tmp[1];
            if(category.equals("전필")){
                sortedClasses.add(rowClasses.get(i));

            }
            else{
                others.add(rowClasses.get(i));
            }
        }

        Collections.shuffle(others);
        for(int j=0; j<others.size(); j++){
            sortedClasses.add(others.get(j));
        }

        return sortedClasses;
    }

}
