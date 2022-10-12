package org.example.logistic;

import java.io.*;
public class LRMain {
    private String filePath;
    private double[][] feature;

    public static void main(String[] args) throws IOException{
        // filename
        String trainFile = "D:\\University\\junior\\学期实训\\software_predict\\software_failure_predict\\algorithm\\train.csv";
        // 导入样本特征和标签
        double [][] feature = LoadData.Loadfeature(trainFile);
        double [] Label = LoadData.LoadLabel(trainFile);
        // 参数设置
        int samNum = feature.length;
        int paraNum = feature[0].length;
        double rate = 0.000127;
        int maxCycle = 900000;
        // LR模型训练
        LRtrainGradientDescent LR = new LRtrainGradientDescent(feature,Label,paraNum,rate,samNum,maxCycle);
        double [] W = LR.Updata(feature, Label, maxCycle, rate);
        //保存模型
        String model_path = "weights.txt";
        SaveModel.savemodel(model_path, W);


        //模型测试
        //double[] W=LoadData.ReadWeights("wrights.txt");
        double [] pre_results = LRTest.lrtest(paraNum, samNum, feature, W);
        //保存测试结果
        String results_path = "pre_results.txt";
        SaveModel.saveresults(results_path, pre_results);


        String testFile="D:\\University\\junior\\学期实训\\software_predict\\software_failure_predict\\algorithm\\test.csv";
        double [][] testFeature = LoadData.Loadfeature(testFile);
        double[] testLabel=LoadData.LoadLabel(testFile);
        int testSam=testFeature.length;
        LRtrainGradientDescent LR_test=new LRtrainGradientDescent();
        double[] predict=LRTest.lrtest(paraNum, testSam, testFeature, W);
        System.out.println("分类正确数量："+LR_test.Precisioin(testLabel, predict));
    }

//    public void LogisticTraining(String filePath){
//        /**
//         * 本方法用于实现后端调用接口
//         * 传入参数：
//         * 传出参数：
//         */
//        this.filePath=filePath;
//        this.feature=LoadData.Loadfeature();
//    }


}

