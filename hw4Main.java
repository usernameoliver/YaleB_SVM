import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import com.jmatio.io.*;
import com.jmatio.types.*;
public class hw4Main {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {

	    MatFileReader matfilereader = new MatFileReader("YaleB_32x32.mat");
	    final Map<String,MLArray> content =matfilereader.getContent();
	    final String name_fea="fea";
	    final String name_gnd="gnd";
	    
	    MLArray mlArrayRetrived_fea=matfilereader.getMLArray(name_fea);
	    MLArray mlArrayRetrived_gnd=matfilereader.getMLArray(name_gnd);
	    final double[][] feaData=((MLDouble) mlArrayRetrived_fea).getArray();
	    
	    
	    final double[][] gndData= ((MLDouble) mlArrayRetrived_gnd).getArray();
	    final int[] yData = new int[gndData.length];
	    for(int i = 0; i < gndData.length; i++){
	    	
	    		yData[i] = (int)gndData[i][0];
	    	
	    }
	    final double[][] X = transposeArray(feaData);
	    double[][] peakValley=collectPeak(X);//max and min of each feature;
	    double [][] feaDataNormalized = normalize(feaData);//normalize the data
	    double [][] feaDataCentered = center(feaData);//centralize the data
	    PrintWriter writer = new PrintWriter("./src/data.txt", "UTF-8");
	    for(int i = 0; i < feaDataCentered.length; i++){
	    	writer.print(yData[i]);
	    	for(int j = 0; j < feaDataCentered[0].length; j++){
	    		writer.print(" "+(j+1)+":"+feaDataCentered[i][j]);
	    		
	    	}
	    	writer.print("\n");
	    }
	    writer.close();
	    
	   
	    
	    /*final double[][] train = feaDataCentered;
	    svm_model model = svmTrain(train);
	    double modelEvluation = evaluate( train[0], model);*/
	    System.out.println(feaDataNormalized[1023][0]);
	    System.out.println(gndData[0].length);
	   // System.out.println(X[0][1]);
	    System.out.println(content);
	    //feaData[2414,]
	    //System.out.println(mlArrayRetrieved.contentToString());
	  }
	public static double[][] transposeArray(double [][] a){
		double [][] b = new double[a[0].length][a.length];
		for (int i = 0; i < a.length; i++) {
	            for (int j = 0; j < a[0].length; j++) {
	                b[j][i] = a[i][j];
	            }
	    }
			return b;
	}
	public static double[] findPeak(double [] b){
		double[] arr = b;
		double high = arr[0];
		double low = arr[arr.length - 1];
		for(int j = 1; j < b.length; j++){
			if(b[j]>high) high = b[j];
			if(low > b[j]) low = b[j];
		}
		double [] result = {low, high};
		return result;
	}
	public static double[][] collectPeak(double [][] c){
		double[][] arr = c;
		double[][] result= new double [1024][];
		for(int i = 0; i < 1024; i++){
			double[] oneFeature = arr[i];
			result[i] = findPeak(oneFeature);
		}
		return result;
	}
	public static double [][] normalize(double[][]d){
		for(int i = 0;i < d.length;i++){
			for(int j = 0; j < d[0].length; j++){
				d[i][j] = d[i][j]/255;
			}
		}
		return d;
	}
	public static double [][] center(double[][]d){
		double [] aver = new double [d[0].length];
		
		for(int j = 0; j < d[0].length; j++){
			double sum = 0;
			for(int i = 0;i < d.length;i++){
				sum = sum + d[i][j];
			}
			aver[j] = sum/d.length;
		}
		for(int i = 0;i < d.length;i++){
			for(int j = 0; j < d[0].length; j++){
				d[i][j] = d[i][j] - aver[j];
			}
		}
		return d;
	}
	
	
}
