package org.example;

import joinery.DataFrame;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String Path="C:\\Users\\14485\\Desktop\\software_failure_predict\\Lucene.csv";
        DataFrame df= DataFrame.readCsv(Path);


        System.out.println(df.index());


    }
}