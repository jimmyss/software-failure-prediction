package org.example;

import joinery.DataFrame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String Path="D:\\University\\junior\\学期实训\\课程相关材料\\Datasets\\AEEEM\\csv\\JDT.csv";
        DataFrame df= DataFrame.readCsv(Path);
        //System.out.println(TrainSet);
        //System.out.println(TeatSet);
        //System.out.println(df);
        //System.out.println(df.columns());;
        //System.out.println(df.index());
        //System.out.println(df.col("class"));
        List isClass = df.col("class");
        int row=isClass.size();
        int trainRow= (int) Math.ceil(row*0.8);



        List onClass=new ArrayList<>();
        for (int i=0;i< isClass.size();i++)
        {
            if(isClass.get(i).equals("clean"))
            {
                onClass.add(i,0);
            }
            if(isClass.get(i).equals("buggy"))
            {
                onClass.add(i,1);
            }
        }
        System.out.println(onClass.size());
        df=df.drop("class");
        df.add("class",onClass);
        System.out.println(df);

        DataFrame TrainSet = df.slice(0,trainRow );
        DataFrame TeatSet=df.slice(trainRow,row);
        TrainSet.writeCsv("./train.csv");
        TeatSet.writeCsv("./test.csv");


    }
}