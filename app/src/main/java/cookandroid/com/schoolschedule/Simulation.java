package cookandroid.com.schoolschedule;

import java.util.ArrayList;

public class Simulation {
    // 시뮬레이션 메서드
    public void simulate(){
        // 과목 더미 데이터
        ArrayList<String[]> dummyArray = new ArrayList<>();
        String[] hoge = {"English Reading and Writing I","053510","18","김흥수","영어","컴퓨터공학부","컴퓨터공학(2-4학년)2학년","3학점","월-4,5 수-6","인문405","49"};
        // 19과목 넣기
        for(int i =0; i<9; i++){
            dummyArray.add(hoge);
        }

        // 여기서 시뮬레이션 작업?
        String[] tmpArray = new String[5*13];

        for(int i=0; i< 5*13 ; i++){
            String[] tmp = dummyArray.get(i);
            tmpArray[i] = tmp[0]; // 과목명만
        }

        // log 표시
        for(int j=0; j<13; j++){
            for(int i=0; i<5; i++){
                System.out.print(tmpArray[i+j]);
            }
            System.out.print("\r\n");
        }

    }
}
