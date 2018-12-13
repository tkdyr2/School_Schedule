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
    public final ArrayList<String> registeredAssumptionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        Intent thisintent = getIntent();
        ArrayList<String> tmp = thisintent.getStringArrayListExtra("bucketData");
        pointLimit = parseInt(thisintent.getStringExtra("pointLimit"));

          /* 10개의 정보
                          this.title[i], this.category[i],this.room[i], this.professor[i],
                     this.major[i], this.grade[i],this.point[i],
                     this.when[i], this.where[i], this.limit[i]
         */

        ArrayList<String[][]> simuSamples = new ArrayList<>();

        // 경우 (sample)를 생성
        int sampleNum = 10;
        for(int n = 0; n < sampleNum; n++){
            tmp = sort(tmp);                  // ArrayList의 과목 순서를 결정
            String[][] table = setTable(tmp); // table에 과목 정보 넣기
            simuSamples.add(table);           // table를 ArrayList에 추가
        }

        ArrayList<String[][]> newSimuSamples = new ArrayList<>();
        //region 학점으로 필터
        boolean[] removeFlag = new boolean[simuSamples.size()];
        for(int i = 0; i < simuSamples.size(); i++){
            int[] checkPointAndNumOfClass = getNumPointAndClasses(simuSamples.get(i));
            if(checkPointAndNumOfClass[0] <= pointLimit && checkPointAndNumOfClass[0] > (pointLimit-3)){
                removeFlag[i] = false;
            }
            else{
                removeFlag[i] = true;
            }
        }
        for(int g = 0; g < simuSamples.size(); g++){
            if(!removeFlag[g]) newSimuSamples.add(simuSamples.get(g));
        }
        //endregion

        // 중간 공백시간으로 필터
        simuSamples = simuSamplesSort(newSimuSamples);
        ArrayList<String[][]> simuSamples2 = simuSamplesSort(simuSamples);

        //region 시간표 중북 검사 및 제거
        boolean[] notSameFlag = new boolean[simuSamples.size()]; // 초기값 false
        notSameFlag[0] = true;
        for(int v = 0; v < simuSamples.size() - 1; v++){
            for(int b = v + 1; b < simuSamples.size(); b++) {
                String[][] tableA = simuSamples.get(v);
                String[][] tableB = simuSamples.get(b);
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < classTime; y++) {
                        if (tableA[x][y] != tableB[x][y]) { // 하나라도 불일치하면 플래그를 true로하고 유효 시간표임을 표시. null 에러 회피를 위해 ! .equals이 아니라 != 를 씀
                            notSameFlag[b] = true;
                        }
                    }
                }
            }
        }
        final ArrayList<String[][]> dispTableList = new ArrayList<>();
        final ArrayList<int[]> assumptionList = new ArrayList<>();
        ArrayList<String[][]> dispTableList2 = new ArrayList<>();
        for(int m=0; m < simuSamples2.size(); m++){
            String[][] dispTable2 = simuSamples2.get(m);
            if(notSameFlag[m]) dispTableList2.add(dispTable2);
        }
        //endregion

        //총 학점, 과목 수 표시
        for(int i = 0; i < dispTableList2.size(); i++) {
            assumptionList.add(getNumPointAndClasses(dispTableList2.get(i)));
        }

        // 표시를 위해 정리
        for(int i = 0; i < simuSamples.size(); i++){
            String[][] dispTable = simuSamples.get(i);
            for(int x = 0; x < 5; x++){
                for(int y = 0; y < classTime; y++){
                    if(dispTable[x][y]!=null) dispTable[x][y] = dispTable[x][y].replace("_", " ");
                }
            }
            if(notSameFlag[i]) {
                dispTableList.add(dispTable);
            }
        }

        //region ListView로 시간표 표시
        final ArrayList<TableData> tableData = new ArrayList<>();
        for(int i = 0; i < dispTableList.size(); i++) {
            TableData data = new TableData(dispTableList.get(i), assumptionList.get(i));
            tableData.add(data);
        }

        // customScheduleAdapter 생성 (R.layout.schedule_layout : 자기가 만든 리스트뷰 레이아웃)
        final MyScheduleAdapter customScheduleAdapter = new MyScheduleAdapter(this,tableData, R.layout.schedule_layout);

        // ListView를 취득
        NonScrollListView myClassListView = findViewById(R.id.nonScrollListView3);
        myClassListView.setAdapter(customScheduleAdapter);

        //endregion

        // 아이템 선택 동작
        myClassListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                registeredClassInfoArray.add(dispTableList.get(pos));
                int[] hoge = assumptionList.get(pos);
                String hoge2 = hoge[0] + "," + hoge[1];
                registeredAssumptionList.add(hoge2);
                new QuickToastTask(getApplicationContext(), R.string.toastMessageRegistered).execute();
                return false;
            }
        });
    }

    public int[] getNumPointAndClasses(String[][] dispTable){
        int[] tmpNum = {0,0};  // [0] = 학점 , [1] = 과목수
        int n = 0;
        String[] titleTmp = new String[5*classTime];
        String[] pointTmp = new String[5*classTime];
        for (int x = 0; x < 5; x++){
            for(int y = 0; y < classTime; y++){
                if(dispTable[x][y] != null) {
                    String[] hoge = dispTable[x][y].split("_",0);
                    titleTmp[n] = hoge[0];
                    pointTmp[n] = hoge[4].replace("학점","");
                    n++;
                }
            }
        }
        for (int i = 0; i < titleTmp.length-1; i++) {
            for(int j = i + 1; j < titleTmp.length; j++){
                if(titleTmp[i] != null && titleTmp[j] != null){
                    if(titleTmp[i].equals(titleTmp[j])){
                        titleTmp[j] = null;
                        pointTmp[j] = null;
                    }
                }
            }
        }
        for (String tmp: pointTmp) {
            if(tmp != null){
                tmpNum[0] += parseInt(tmp);
                tmpNum[1]++;
            }
        }

        return tmpNum;
    }

    public void onGoToRegisteredClick(View view) {
        switch (view.getId()) {
            case R.id.goToRegistered:
                ArrayList<String> regiList = remakeStringListToString(registeredClassInfoArray);

                // 로컬 파일에 나의 시간표 리스트 저장
                FileIO fileIO = new FileIO(this);
                String dataFileName = "registeredData.txt";
                fileIO.storeClassDataToFile(regiList, dataFileName);
                // 로컬 파일에 총 학점, 과목수 저장
                dataFileName = "assumptionData.txt";
                fileIO.storeClassDataToFile(registeredAssumptionList, dataFileName);

                // 화면 이동
                Intent registeredIntent = new Intent(MyScheduleActivity.this, RegisteredScheduleActivity.class);
                startActivity(registeredIntent);

                // 화면을 이동할 때 registeredClassInfoArray를 초기화
                for(int h = 0; h < registeredClassInfoArray.size(); h++){
                    registeredClassInfoArray.remove(h);
                }
                // 화면을 이동할 때 registeredAssumptionList를 초기화
                for(int h = 0; h < registeredAssumptionList.size(); h++){
                    registeredAssumptionList.remove(h);
                }

                break;
        }
    }

    public ArrayList<String> remakeStringListToString(ArrayList<String[][]> tmp){
        ArrayList<String> returnList = new ArrayList<>();
        String tmpString = "";
        for(int i = 0; i < tmp.size(); i++){
            String[][] tmpStringArray = tmp.get(i);
            for(int y=0; y < classTime; y++){
                for(int x = 0; x < 5; x++){
                    if(tmpStringArray[x][y] == null) {
                        tmpStringArray[x][y] = "empty";
                        tmpString += tmpStringArray[x][y] + "\t";
                    }
                    else{
                        tmpString += tmpStringArray[x][y] + "\t";
                    }
                }
            }
         //   System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        //    System.out.println(tmpString);
            returnList.add(tmpString);
        }
        return returnList;
    }

    public ArrayList<String[][]> simuSamplesSort(ArrayList<String[][]> simuSamples){
        // 시간표의 총 공백 시간(emptyCount) 계산
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

        // 빈시간이 적은 시간표에게 우선순위를 부여한다.
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

            String[] catch1 = classInfo.split("_",0);
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
