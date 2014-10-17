package edu.gatech.scs.pwd.hardening;
import java.math.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class LoginAttempt{

	
	BigInteger[] featureValuesReceived = new BigInteger[15];
	private String fileDir = ""; 
	public String pwd;
	
	
	BigInteger[] thresholdValues = new BigInteger[15];
	
	public BigInteger[] loginAttempt() throws NumberFormatException, IOException{
		
		//Generating a string that denotes the path to the directory of loginfeaturevalues.txt file. 
		fileDir = new String("loginAndThresholdFeatureValues"
                + File.separator);
		
		
		
		//Using an external library to parse through the CSV file of login features that we pretend we received from the client.
	
			CSVReader reader = new CSVReader(new FileReader(fileDir + "loginfeaturevalues.txt"));
			
			
			//To  temporarily read the lines from the file
		    String [] nextLine;
		    
		    
		    //Iterating through the lines
		    while ((nextLine = reader.readNext()) != null) {
		        // nextLine[] is an array of values from the line
		        	    	
		    	//Extracting password and featurevalues from the loginfeaturevalues.txt file and storing them in an integer array.
		    	
		    	pwd = nextLine[15]; //Password is the last value in the loginfeaturevalues csv file
		    	System.out.println(pwd);
		    	
		    	for(int i=0; i<15; i++){
		    
		    			featureValuesReceived[i] = BigInteger.valueOf(Integer.parseInt(nextLine[i]));
		    			System.out.println(featureValuesReceived[i]);
		    	}
		    }
			
		    reader.close();
				
		return featureValuesReceived;
		
		
	}
	
	
	
	//For reading threshold values and storing in an array of integers
	public BigInteger[] fetchThresholdValues() throws NumberFormatException, IOException{
		
		//Generating a string that denotes the path to the directory of thresholdvalues.txt file. 
		fileDir = new String("loginAndThresholdFeatureValues"
                + File.separator);
		
		
			//Using an external library to parse through the CSV file of threshold values
			CSVReader reader = new CSVReader(new FileReader(fileDir + "threshold.txt"));
						
			
			//To  temporarily read the lines from the file
		    String [] nextLine;
		    
		    //Iterating through all the lines in the file
		    while ((nextLine = reader.readNext()) != null) {
		        // nextLine[] is an array of values from the line
		   		    	
		    	
		    	//Extracting thresholdvalues from the thresholdvalues.txt file and storing them in an integer array.
		    	for(int i=0; i<15; i++){
		    		
		    			thresholdValues[i] = BigInteger.valueOf(Integer.parseInt(nextLine[i]));
		    			System.out.println("\n"+thresholdValues[i]);
		    	}
		    }
			
			
		    reader.close();
		
		return thresholdValues;
	}
	
	
	
	
	//Calculate mean of feature values by fetching them from the history file
	public int calculateMean(int[] d){
		
		
		return 0;
		
	}
	
	
	//Calculate median of feature values by fetching them from the history file
	public int calculateMedian(int[] d){
		
		return 0;
	}
	
	
	//Based on mean and standard deviation
	public int selectShare(){
		
		
		return 1;
		
	}
	
	
	//To calculate the y co-ordinates
	public BigInteger calculateHpwdnew(BigInteger[] y,BigInteger[] x){
		
		BigInteger hpwd=BigInteger.valueOf(0);
		for(int i =0;i<15;i++)
		{
			hpwd=hpwd.add(y[i].multiply(calculateLagrangeMultiplier(y[i],x[i],i),y,x));
		}
		return hpwd;
	}
	
	
	//Calculating lagrange multiplier
	public BigInteger calculateLagrangeMultiplier(BigInteger xi,int i, BigInteger[] x){
		
		BigInteger LagrangeMultiplier=BigInteger.valueOf(1);
		for(int j=0;j<15,j!=i;j++)
		{
			LagrangeMultiplier=LagrangeMultiplier.multiply(x[j]/(x[j]-x[i]))
		}
		System.out.println("LagrangeMultiplier for i = "+i+" is "+LagrangeMultiplier+".")
		return LagrangeMultiplier;
		
		
	}
	
	
}
