package org.example.logistic;
import java.io.*;

public class LoadData {
    //导入样本特征
    public static double[][] Loadfeature(String filename) throws IOException{
        File f = new File(filename);
        FileInputStream fip = new FileInputStream(f);
        // 构建FileInputStream对象
        InputStreamReader reader = new InputStreamReader(fip,"UTF-8");
        // 构建InputStreamReader对象
        StringBuffer sb = new StringBuffer();
        while(reader.ready()) {
            sb.append((char) reader.read());
        }
        reader.close();
        fip.close();
        //将读入的数据流转换为字符串
        String sb1 = sb.toString();
        //按行将字符串分割,计算二维数组行数
        String [] a = sb1.split("\n");
        int n = a.length;
        System.out.println("二维数组行数为:" + n);
        //计算二维数组列数
        String [] a0 = a[0].split(",");
        int m = a0.length;
        System.out.println("二维数组列数为:" + m);

        double [][] feature = new double[n-1][m];
        for (int i = 0; i < n-1; i ++) {
            String [] tmp = a[i+1].split(",");
            for(int j = 0; j < m; j ++) {
                if (j == m-1) {
                    if (tmp[j].equals("clean") || tmp[j].equals("0")) {
                        feature[i][j] = (double) 0;
                    }else if (tmp[j].equals("buggy") || tmp[j].equals("1")) {
                        feature[i][j]=(double) 1;
                    }
                }
                else {
                    feature[i][j] = Double.parseDouble(tmp[j]);
                }
            }
        }
        return feature;
    }
    //导入样本标签
    public static double[] LoadLabel(String filename) throws IOException{
        File f = new File(filename);
        FileInputStream fip = new FileInputStream(f);
        // 构建FileInputStream对象
        InputStreamReader reader = new InputStreamReader(fip,"UTF-8");
        // 构建InputStreamReader对象,编码与写入相同
        StringBuffer sb = new StringBuffer();
        while(reader.ready()) {
            sb.append((char) reader.read());
        }
        reader.close();
        fip.close();
        //将读入的数据流转换为字符串
        String sb1 = sb.toString();
        //按行将字符串分割,计算二维数组行数
        String [] a = sb1.split("\n");
        int n = a.length;
        System.out.println("二维数组行数为:" + n);
        //计算二维数组列数
        String [] a0 = a[0].split(",");
        int m = a0.length;
        System.out.println("二维数组列数为:" + m);

        double [] Label = new double[n-1];
        for (int i = 0; i < n-1; i ++) {
            String [] tmp = a[i+1].split(",");
            if (tmp[m-1].equals("clean")|| tmp[m-1].equals("0")) {
                Label[i] = (double) 0;
            }else if(tmp[m-1].equals("buggy")|| tmp[m-1].equals("1")){
                Label[i]=(double) 1;
            }
            //Label[i] = Double.parseDouble(tmp[m-1]);
        }
        return Label;
    }

    //读取weight.txt
    public static double[] ReadWeights(String filename)throws IOException{
        File f= new File(filename);
        FileInputStream fip=new FileInputStream(f);
        InputStreamReader reader = new InputStreamReader(fip,"UTF-8");
        // 构建InputStreamReader对象,编码与写入相同
        StringBuffer sb = new StringBuffer();
        while(reader.ready()) {
            sb.append((char) reader.read());
        }
        reader.close();
        fip.close();
        String sb1 = sb.toString();

        String[] strs=sb1.split("\t");
        int n=strs.length;
        double[] weights=new double[n];
        for (int i=0;i<n;i++){
            weights[i]=Double.parseDouble(strs[i]);
        }
        return weights;
    }
}

