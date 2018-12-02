package cookandroid.com.schoolschedule;

import java.util.Random;

public class TableData {
    private long _id;
    private int _sumPoints;
    private int _sumClasses;
    private String[][] _theTable = new String[5][14];


    // constructor
    public TableData(String[][] tableInfo, int[] sum){
        final int classTimes = 14;
        this._id = (new Random()).nextLong();
        this._sumPoints = sum[0];
        this._sumClasses = sum[1];

          for(int x = 0; x < 5; x++){
              for(int y = 0; y < classTimes; y++){
                  this._theTable[x][y] = tableInfo[x][y];
              }
          }


    }

    // getter
    public long getId(){ return this._id; }
    public int getSumPoints() { return this._sumPoints; }
    public int getSumClasses() { return this._sumClasses; }
    public String[][] getTheTable() { return this._theTable; }
}
