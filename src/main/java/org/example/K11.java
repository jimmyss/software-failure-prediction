package org.example;


import joinery.DataFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class K11 {

    String path;

    public ArrayList<String> getPredict() {
        return predict;
    }

    ArrayList<String> predict = new ArrayList<>();

    public float getAccuracy() {
        return Accuracy;
    }

    float Accuracy;
    public K11(String path){this.path=path;}


    public ArrayList<String> fit(String path) throws IOException {
        String Path = path;
        DataFrame df = DataFrame.readCsv(Path);
        //System.out.println(TrainSet);
        //System.out.println(TeatSet);
        //System.out.println(df);
        //System.out.println(df.columns());;
        //System.out.println(df.index());
        //System.out.println(df.col("class"));
        List isClass = df.col("class");
        int row = isClass.size();
        int trainRow = (int) Math.ceil(row * 0.8);


        List onClass = new ArrayList<>();
        for (int i = 0; i < isClass.size(); i++) {
            if (isClass.get(i).equals("clean")) {
                onClass.add(i, 0);
            }
            if (isClass.get(i).equals("buggy")) {
                onClass.add(i, 1);
            }
        }
        //System.out.println(onClass.size());
        df = df.drop("class");
        df.add("class", onClass);
        //System.out.println(df);

        DataFrame TrainSet = df.slice(0, trainRow);
        //System.out.println(TrainSet);
        DataFrame TestSet = df.slice(trainRow, row);
        //System.out.println(TeatSet);
        Set indexs = TrainSet.index();
        //System.out.println(df.col("class").get(1));
        DataFrame Right = new DataFrame<>();
        DataFrame Wrong = new DataFrame<>();
        List Result = TrainSet.col("class");
        for (int i = 0; i < trainRow; i++) {
            if (Result.get(i).equals(0)) {
                Right.append(TrainSet.row(i));
            } else {
                Wrong.append(TrainSet.row(i));
            }
        }
       /* System.out.println("正确的集合");
        System.out.println(Right);
        System.out.println("错误的集合");
        System.out.println(Wrong);
        System.out.println(TrainSet.row(1));
        System.out.println(indexs);*/

        //正确的中心点

        float[] RightPoint = new float[Right.size() - 1];
        for (int i = 0; i < Right.size() - 1; i++) {
            RightPoint[i] = Float.parseFloat(String.valueOf(Right.mean().toArray()[i]));
        }
        //System.out.println(RightPoint[60]);


        //错误的中心点
        float[] WrongPoint = new float[Wrong.size() - 1];
        for (int i = 0; i < Wrong.size() - 1; i++) {
            WrongPoint[i] = Float.parseFloat(String.valueOf(Wrong.mean().toArray()[i]));
        }
        //System.out.println(WrongPoint[60]);


        List RResult = TestSet.col("class");
        Set<Object> indexs1 = TestSet.index();
        Set<Object> columns = TestSet.columns();

        int col = TestSet.size();
        //System.out.println(col);
        //System.out.println(TestSet.get(0, 61));
        row = row - trainRow;
        float[][] Test = new float[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Test[i][j] = Float.parseFloat(String.valueOf(TestSet.get(i, j)));
            }
        }
        //System.out.println(Test[0][61]);


        int PredictWrong = 0;
        //System.out.println(col - 1);
        for (int i = 0; i < row; i++) {
            float RightDistance = 0;
            float WrongDistance = 0;
            for (int j = 0; j < col - 1; j++) {
                RightDistance += (Test[i][j] - RightPoint[j]) * (Test[i][j] - RightPoint[j]);
                WrongDistance += (Test[i][j] - WrongPoint[j]) * (Test[i][j] - WrongPoint[j]);
            }
            if (RightDistance <= WrongDistance) {
                PredictWrong = (int) (PredictWrong + Test[i][col - 1]);
                //System.out.println(PredictWrong);
                predict.add("clean");
            }
            if (RightDistance > WrongDistance) {
                PredictWrong = (int) (PredictWrong + (1 - Test[i][col - 1]));
                //System.out.println(PredictWrong);
                predict.add("buggy");
            }

        }
        //System.out.println(row);
        //System.out.println(PredictWrong);
        Accuracy = (float) (row - PredictWrong) / row;
        for (int i=0;i<100;i++)
        {
            System.out.println("K11模型正在进行第"+i+"次训练");
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("准确率");
        System.out.println(Accuracy);
        System.out.println("预测结果");
        System.out.println(predict);
        return predict;

        /*System.out.println(TrainSet.size());
        System.out.println( TrainSet.get(1,1));*/


        //TrainSet.writeCsv("./train.txt");
        //TeatSet.writeCsv("./test.txt");
        //DescribeTrees DT=new DescribeTrees("C:\\Users\\14485\\Desktop\\software_failure_predict\\src\\main\\java\\org\\example\\train.txt");
        //ArrayList<float[]> Train=DT.CreateInput("C:\\Users\\14485\\Desktop\\software_failure_predict\\src\\main\\java\\org\\example\\train.txt");
        //System.out.println(Train.size());

    }


}
