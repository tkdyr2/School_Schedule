package cookandroid.com.schoolschedule;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileIO {
    private FileReader reader;
    private BufferedReader bufferedReader;
    private FileWriter writer = null;
    private BufferedWriter bufferedWriter = null;
    Context context;


    public FileIO(Context context) {
        this.context = context;
        //System.out.println("context const success");
    }

    // 데이터를 파일에서 읽어 오기
    public ArrayList<String> loadClassDataFromFile(String fileName) {

        String tmp;

        ArrayList<String> classes = new ArrayList<>();
        // file object Create
        File file = new File(context.getFilesDir(), fileName);

        // 파일이 존재하지 않으면 새로은 파일을 생성.
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {  // 파일이 존재할때.
            System.out.println(fileName + "을 찾았습니다.");
        }

//         파일이 존재하고 입력 가능
        if (file.exists() & file.canWrite()) {
            try {
                reader = new FileReader(file);
                bufferedReader = new BufferedReader(reader);

                while ((tmp = bufferedReader.readLine()) != null) {
                    classes.add(tmp);
                }
                bufferedReader.close();
                reader.close();

                System.out.println(fileName + " 파일 읽기에 성공");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return classes;
    }


    // 장바구니 리스트 지우기
    public void resetClassDataFile(String fileName){
        File file = new File(context.getFilesDir(), fileName);

        try {
            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);
            System.out.println("장바구니 내용 삭제");
            bufferedWriter.write("");
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (writer != null) {
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 데이터를 파일에 저장

    public void storeClassDataToFile(ArrayList<String> classData, String fileName) {
        File file = new File(context.getFilesDir(), fileName);

        try {
            writer = new FileWriter(file, true); //true : 데이터 덮어 쓰기가 아니라 마지막에 추가
            bufferedWriter = new BufferedWriter(writer);
            System.out.println("data " + classData);
            for (String str : classData) {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
            //file write
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (writer != null) {
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

