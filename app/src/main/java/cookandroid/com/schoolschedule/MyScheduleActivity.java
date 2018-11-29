package cookandroid.com.schoolschedule;

import android.support.v7.app.AppCompatActivity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import static java.lang.Integer.parseInt;


public class MyScheduleActivity extends AppCompatActivity {
    SearchClasses searchClasses = new SearchClasses();
    public int classTime = 10;
    public int pointLimit = 22; // 19, 16

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region JSON파일 읽기
        AssetManager assetManager = getResources().getAssets(); // asset Folder안에 있는 파일을 취득하기 위한 인스탄스
        InputStream is = null; // text파일을 읽기 위한 인스탄스
        BufferedReader bf = null; // 한글자씩이 아니라 한줄씩 읽기 위한 인스탄스
        try {
            // JSON파일 읽기
            is = assetManager.open("subject_data.json"); // asset 내에 있는 JSON파일 취득
            bf = new BufferedReader(new InputStreamReader(is)); //JSON파일 읽기
            String jsonString = "";
            String str = bf.readLine();
            while(str != null){
                jsonString += str;
                str = bf.readLine();
            }
            is.close();
            bf.close();
            searchClasses.parseJSON(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // endregion

        int count = 0;
        // 장바구니 데이터 작성    과목명_이수구분_교시
        ArrayList<String> tmp = new ArrayList<>();
        for(int i=0; i<searchClasses.getNumClasses(); i ++) {
            if (i % 4 == 0) {
                count++;
                tmp.add(searchClasses.getTitle(i) + "_" + searchClasses.getCategory(i) + "_" + searchClasses.getWhen(i) + "_" + searchClasses.getPoint(i));
            }
        }

        ArrayList<String[][]> simuSamples = new ArrayList<>();

        // 경우 (sample)를 생성
        int sampleNum = 10;
        for(int n = 0; n < sampleNum; n++){
            tmp = sort(tmp, count); // ArrayList의 과목 순서를 결정
            String[][] table = setTable(tmp); // table에 과목 정보 넣기
            simuSamples.add(table); // table를 ArrayList에 추가
        }

        simuSamples = simuSamplesSort(simuSamples); // table의 우선순위를 맺음

        //TODO: 총 학점 표시
        int tableNum = 3;
        int[] point = new int[simuSamples.size()];
        for(int g=0; g < simuSamples.size(); g++){
            point[g] = 0;
        }

        //시간표 중북 검사 및 제거
        boolean[] notSameFlag = new boolean[simuSamples.size()]; // 초기값 false
        notSameFlag[0] = true;
        for(int v = 0; v < simuSamples.size() - 1; v++){
            for(int b = v + 1; b < simuSamples.size(); b++){
                String[][] tableA = simuSamples.get(v);
                String[][] tableB = simuSamples.get(b);
                for(int x = 0; x < 5; x++){
                    for(int y = 0; y < classTime; y++){
                        if(tableA[x][y] != tableB[x][y]) { // 하나라도 불일치하면 플래그를 true로하고 유효 시간표임을 표시
                            notSameFlag[b] = true;
                        }
                    }
                }
            }
        }


        //String.valueOf(point[i])
        // textView에 값 설정 (학점 포함)
        for(int i = 0; i < tableNum; i++){
            if(notSameFlag[i]) setTextView(simuSamples.get(i), i, "0"); // textView에 값 설정
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
                        point[m] += parseInt(pointTmp[1])/3;
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
        simuSamples = quickSort(emptyCount, 0, emptyCount.length-1, simuSamples);

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

    // textView 취득 및 설정
    public void setTextView(String[][] table, int i, String pointSum){
        switch (i){
            case 0:
                TextView countClasses1 = findViewById(R.id.countClasses1);
                //region 1번째 시간표의 textView 취득
                TextView m0 = findViewById(R.id.monday0);
                TextView m1 = findViewById(R.id.monday1);
                TextView m2 =  findViewById(R.id.monday2);
                TextView m3 = findViewById(R.id.monday3);
                TextView m4 = findViewById(R.id.monday4);
                TextView m5 = findViewById(R.id.monday5);
                TextView m6 = findViewById(R.id.monday6);
                TextView m7 = findViewById(R.id.monday7);
                TextView m8 = findViewById(R.id.monday8);
                TextView m9 = findViewById(R.id.monday9);
                TextView t0 = findViewById(R.id.tuesday0);
                TextView t1 = findViewById(R.id.tuesday1);
                TextView t2 = findViewById(R.id.tuesday2);
                TextView t3 = findViewById(R.id.tuesday3);
                TextView t4 = findViewById(R.id.tuesday4);
                TextView t5 = findViewById(R.id.tuesday5);
                TextView t6 = findViewById(R.id.tuesday6);
                TextView t7 = findViewById(R.id.tuesday7);
                TextView t8 = findViewById(R.id.tuesday8);
                TextView t9 = findViewById(R.id.tuesday9);
                TextView w0 = findViewById(R.id.wndnesday0);
                TextView w1 = findViewById(R.id.wndnesday1);
                TextView w2 = findViewById(R.id.wndnesday2);
                TextView w3 = findViewById(R.id.wndnesday3);
                TextView w4 = findViewById(R.id.wndnesday4);
                TextView w5 = findViewById(R.id.wndnesday5);
                TextView w6 = findViewById(R.id.wndnesday6);
                TextView w7 = findViewById(R.id.wndnesday7);
                TextView w8 = findViewById(R.id.wndnesday8);
                TextView w9 = findViewById(R.id.wndnesday9);
                TextView th0 = findViewById(R.id.thursday0);
                TextView th1 = findViewById(R.id.thursday1);
                TextView th2 = findViewById(R.id.thursday2);
                TextView th3 = findViewById(R.id.thursday3);
                TextView th4 = findViewById(R.id.thursday4);
                TextView th5 = findViewById(R.id.thursday5);
                TextView th6 = findViewById(R.id.thursday6);
                TextView th7 = findViewById(R.id.thursday7);
                TextView th8 = findViewById(R.id.thursday8);
                TextView th9 = findViewById(R.id.thursday9);
                TextView f0 = findViewById(R.id.friday0);
                TextView f1 = findViewById(R.id.friday1);
                TextView f2 = findViewById(R.id.friday2);
                TextView f3 = findViewById(R.id.friday3);
                TextView f4 = findViewById(R.id.friday4);
                TextView f5 = findViewById(R.id.friday5);
                TextView f6 = findViewById(R.id.friday6);
                TextView f7 = findViewById(R.id.friday7);
                TextView f8 = findViewById(R.id.friday8);
                TextView f9 = findViewById(R.id.friday9);
                // endregion
                //region 1번째 시간표의 textVeiw 설정
                countClasses1.setText(pointSum);

                m0.setText(table[0][0]);
                m1.setText(table[0][1]);
                m2.setText(table[0][2]);
                m3.setText(table[0][3]);
                m4.setText(table[0][4]);
                m5.setText(table[0][5]);
                m6.setText(table[0][6]);
                m7.setText(table[0][7]);
                m8.setText(table[0][8]);
                m9.setText(table[0][9]);

                t0.setText(table[1][0]);
                t1.setText(table[1][1]);
                t2.setText(table[1][2]);
                t3.setText(table[1][3]);
                t4.setText(table[1][4]);
                t5.setText(table[1][5]);
                t6.setText(table[1][6]);
                t7.setText(table[1][7]);
                t8.setText(table[1][8]);
                t9.setText(table[1][9]);

                w0.setText(table[2][0]);
                w1.setText(table[2][1]);
                w2.setText(table[2][2]);
                w3.setText(table[2][3]);
                w4.setText(table[2][4]);
                w5.setText(table[2][5]);
                w6.setText(table[2][6]);
                w7.setText(table[2][7]);
                w8.setText(table[2][8]);
                w9.setText(table[2][9]);

                th0.setText(table[3][0]);
                th1.setText(table[3][1]);
                th2.setText(table[3][2]);
                th3.setText(table[3][3]);
                th4.setText(table[3][4]);
                th5.setText(table[3][5]);
                th6.setText(table[3][6]);
                th7.setText(table[3][7]);
                th8.setText(table[3][8]);
                th9.setText(table[3][9]);

                f0.setText(table[4][0]);
                f1.setText(table[4][1]);
                f2.setText(table[4][2]);
                f3.setText(table[4][3]);
                f4.setText(table[4][4]);
                f5.setText(table[4][5]);
                f6.setText(table[4][6]);
                f7.setText(table[4][7]);
                f8.setText(table[4][8]);
                f9.setText(table[4][9]);
                //endregion
                break;
            case 1:
                //region 2번째 시간표의 textView 취득
                TextView countClasses2 = findViewById(R.id.countClasses2);

                TextView m00 = findViewById(R.id.monday00);
                TextView m11 = findViewById(R.id.monday11);
                TextView m22 =  findViewById(R.id.monday22);
                TextView m33 = findViewById(R.id.monday33);
                TextView m44 = findViewById(R.id.monday44);
                TextView m55 = findViewById(R.id.monday55);
                TextView m66 = findViewById(R.id.monday66);
                TextView m77 = findViewById(R.id.monday77);
                TextView m88 = findViewById(R.id.monday88);
                TextView m99 = findViewById(R.id.monday99);
                TextView t00 = findViewById(R.id.tuesday00);
                TextView t11 = findViewById(R.id.tuesday11);
                TextView t22 = findViewById(R.id.tuesday22);
                TextView t33 = findViewById(R.id.tuesday33);
                TextView t44 = findViewById(R.id.tuesday44);
                TextView t55 = findViewById(R.id.tuesday55);
                TextView t66 = findViewById(R.id.tuesday66);
                TextView t77 = findViewById(R.id.tuesday77);
                TextView t88 = findViewById(R.id.tuesday88);
                TextView t99 = findViewById(R.id.tuesday99);
                TextView w00 = findViewById(R.id.wndnesday00);
                TextView w11 = findViewById(R.id.wndnesday11);
                TextView w22 = findViewById(R.id.wndnesday22);
                TextView w33 = findViewById(R.id.wndnesday33);
                TextView w44 = findViewById(R.id.wndnesday44);
                TextView w55 = findViewById(R.id.wndnesday55);
                TextView w66 = findViewById(R.id.wndnesday66);
                TextView w77 = findViewById(R.id.wndnesday77);
                TextView w88 = findViewById(R.id.wndnesday88);
                TextView w99 = findViewById(R.id.wndnesday99);
                TextView th00 = findViewById(R.id.thursday00);
                TextView th11 = findViewById(R.id.thursday11);
                TextView th22 = findViewById(R.id.thursday22);
                TextView th33 = findViewById(R.id.thursday33);
                TextView th44 = findViewById(R.id.thursday44);
                TextView th55 = findViewById(R.id.thursday55);
                TextView th66 = findViewById(R.id.thursday66);
                TextView th77 = findViewById(R.id.thursday77);
                TextView th88 = findViewById(R.id.thursday88);
                TextView th99 = findViewById(R.id.thursday99);
                TextView f00 = findViewById(R.id.friday00);
                TextView f11 = findViewById(R.id.friday11);
                TextView f22 = findViewById(R.id.friday22);
                TextView f33 = findViewById(R.id.friday33);
                TextView f44 = findViewById(R.id.friday44);
                TextView f55 = findViewById(R.id.friday55);
                TextView f66 = findViewById(R.id.friday66);
                TextView f77 = findViewById(R.id.friday77);
                TextView f88 = findViewById(R.id.friday88);
                TextView f99 = findViewById(R.id.friday99);
                // endregion
                //region 2번째 시간표의 textVeiw 설정
                countClasses2.setText(pointSum);

                m00.setText(table[0][0]);
                m11.setText(table[0][1]);
                m22.setText(table[0][2]);
                m33.setText(table[0][3]);
                m44.setText(table[0][4]);
                m55.setText(table[0][5]);
                m66.setText(table[0][6]);
                m77.setText(table[0][7]);
                m88.setText(table[0][8]);
                m99.setText(table[0][9]);

                t00.setText(table[1][0]);
                t11.setText(table[1][1]);
                t22.setText(table[1][2]);
                t33.setText(table[1][3]);
                t44.setText(table[1][4]);
                t55.setText(table[1][5]);
                t66.setText(table[1][6]);
                t77.setText(table[1][7]);
                t88.setText(table[1][8]);
                t99.setText(table[1][9]);

                w00.setText(table[2][0]);
                w11.setText(table[2][1]);
                w22.setText(table[2][2]);
                w33.setText(table[2][3]);
                w44.setText(table[2][4]);
                w55.setText(table[2][5]);
                w66.setText(table[2][6]);
                w77.setText(table[2][7]);
                w88.setText(table[2][8]);
                w99.setText(table[2][9]);

                th00.setText(table[3][0]);
                th11.setText(table[3][1]);
                th22.setText(table[3][2]);
                th33.setText(table[3][3]);
                th44.setText(table[3][4]);
                th55.setText(table[3][5]);
                th66.setText(table[3][6]);
                th77.setText(table[3][7]);
                th88.setText(table[3][8]);
                th99.setText(table[3][9]);

                f00.setText(table[4][0]);
                f11.setText(table[4][1]);
                f22.setText(table[4][2]);
                f33.setText(table[4][3]);
                f44.setText(table[4][4]);
                f55.setText(table[4][5]);
                f66.setText(table[4][6]);
                f77.setText(table[4][7]);
                f88.setText(table[4][8]);
                f99.setText(table[4][9]);
                //endregion
                break;
            case 2:
                //region 3번째 시간표의 textView 취득
                TextView countClasses3 = findViewById(R.id.countClasses3);

                TextView m000 = findViewById(R.id.monday000);
                TextView m111 = findViewById(R.id.monday111);
                TextView m222 =  findViewById(R.id.monday222);
                TextView m333 = findViewById(R.id.monday333);
                TextView m444 = findViewById(R.id.monday444);
                TextView m555 = findViewById(R.id.monday555);
                TextView m666 = findViewById(R.id.monday666);
                TextView m777 = findViewById(R.id.monday777);
                TextView m888 = findViewById(R.id.monday888);
                TextView m999 = findViewById(R.id.monday999);
                TextView t000 = findViewById(R.id.tuesday000);
                TextView t111 = findViewById(R.id.tuesday111);
                TextView t222 = findViewById(R.id.tuesday222);
                TextView t333 = findViewById(R.id.tuesday333);
                TextView t444 = findViewById(R.id.tuesday444);
                TextView t555 = findViewById(R.id.tuesday555);
                TextView t666 = findViewById(R.id.tuesday666);
                TextView t777 = findViewById(R.id.tuesday777);
                TextView t888 = findViewById(R.id.tuesday888);
                TextView t999 = findViewById(R.id.tuesday999);
                TextView w000 = findViewById(R.id.wndnesday000);
                TextView w111 = findViewById(R.id.wndnesday111);
                TextView w222 = findViewById(R.id.wndnesday222);
                TextView w333 = findViewById(R.id.wndnesday333);
                TextView w444 = findViewById(R.id.wndnesday444);
                TextView w555 = findViewById(R.id.wndnesday555);
                TextView w666 = findViewById(R.id.wndnesday666);
                TextView w777 = findViewById(R.id.wndnesday777);
                TextView w888 = findViewById(R.id.wndnesday888);
                TextView w999 = findViewById(R.id.wndnesday999);
                TextView th000 = findViewById(R.id.thursday000);
                TextView th111 = findViewById(R.id.thursday111);
                TextView th222 = findViewById(R.id.thursday222);
                TextView th333 = findViewById(R.id.thursday333);
                TextView th444 = findViewById(R.id.thursday444);
                TextView th555 = findViewById(R.id.thursday555);
                TextView th666 = findViewById(R.id.thursday666);
                TextView th777 = findViewById(R.id.thursday777);
                TextView th888 = findViewById(R.id.thursday888);
                TextView th999 = findViewById(R.id.thursday999);
                TextView f000 = findViewById(R.id.friday0);
                TextView f111 = findViewById(R.id.friday1);
                TextView f222 = findViewById(R.id.friday2);
                TextView f333 = findViewById(R.id.friday3);
                TextView f444 = findViewById(R.id.friday4);
                TextView f555 = findViewById(R.id.friday5);
                TextView f666 = findViewById(R.id.friday6);
                TextView f777 = findViewById(R.id.friday7);
                TextView f888 = findViewById(R.id.friday8);
                TextView f999 = findViewById(R.id.friday9);
                // endregion
                //region 3번째 시간표의 textVeiw 설정
                countClasses3.setText(pointSum);

                m000.setText(table[0][0]);
                m111.setText(table[0][1]);
                m222.setText(table[0][2]);
                m333.setText(table[0][3]);
                m444.setText(table[0][4]);
                m555.setText(table[0][5]);
                m666.setText(table[0][6]);
                m777.setText(table[0][7]);
                m888.setText(table[0][8]);
                m999.setText(table[0][9]);

                t000.setText(table[1][0]);
                t111.setText(table[1][1]);
                t222.setText(table[1][2]);
                t333.setText(table[1][3]);
                t444.setText(table[1][4]);
                t555.setText(table[1][5]);
                t666.setText(table[1][6]);
                t777.setText(table[1][7]);
                t888.setText(table[1][8]);
                t999.setText(table[1][9]);

                w000.setText(table[2][0]);
                w111.setText(table[2][1]);
                w222.setText(table[2][2]);
                w333.setText(table[2][3]);
                w444.setText(table[2][4]);
                w555.setText(table[2][5]);
                w666.setText(table[2][6]);
                w777.setText(table[2][7]);
                w888.setText(table[2][8]);
                w999.setText(table[2][9]);

                th000.setText(table[3][0]);
                th111.setText(table[3][1]);
                th222.setText(table[3][2]);
                th333.setText(table[3][3]);
                th444.setText(table[3][4]);
                th555.setText(table[3][5]);
                th666.setText(table[3][6]);
                th777.setText(table[3][7]);
                th888.setText(table[3][8]);
                th999.setText(table[3][9]);

                f000.setText(table[4][0]);
                f111.setText(table[4][1]);
                f222.setText(table[4][2]);
                f333.setText(table[4][3]);
                f444.setText(table[4][4]);
                f555.setText(table[4][5]);
                f666.setText(table[4][6]);
                f777.setText(table[4][7]);
                f888.setText(table[4][8]);
                f999.setText(table[4][9]);
                //endregion
                break;
        }
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
            String when = catch1[2];
            int point = parseInt(catch1[3]);

            table = updateTable(table, title, when, point);

        }

        return table;
    }

    public  String[][] updateTable(String[][] table, String title, String when, int point){
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
                    table[parseInt(split2[0])][parseInt(split3[j])] = title + "_" + Integer.valueOf(point);
                }
            }
        }

        return table;
    }

    // 전필 과목 우선으로 정렬
    public ArrayList<String> sort(ArrayList<String> rowClasses, int count){
        ArrayList<String> others = new ArrayList<>();
        ArrayList<String> sortedClasses = new ArrayList<>();

        for(int i = 0; i < count; i++)
        {
            String[] tmp = rowClasses.get(i).split("_",0);
            String category = tmp[1];
            if(category.equals("전필")){
                sortedClasses.add(rowClasses.get(i));
                //others.add(rowClasses.get(i));

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
