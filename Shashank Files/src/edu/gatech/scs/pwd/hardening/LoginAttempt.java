package edu.gatech.scs.pwd.hardening;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class LoginAttempt{

	
	int[] featureValuesReceived = new int[15];
	private String fileDir = ""; 
	public String pwd;
	
	
	int[] thresholdValues = new int[15];
	
	public int[] loginAttempt() throws NumberFormatException, IOException{
		
		//Generating a string that denotes the path to the directory of loginfeaturevalues.txt file. 
		fileDir = new String("src" + File.separator + "loginAndThresholdFeatureValues"
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
		    	
		    	
		    	for(int i=0; i<15; i++){
		    
		    			featureValuesReceived[i] = Integer.parseInt(nextLine[i]);
		    			System.out.println(featureValuesReceived[i]);
		    	}
		    }
			
		    reader.close();
				
		return featureValuesReceived;
		
		
	}
	
	
	
	//For reading threshold values and storing in an array of integers
	public int[] fetchThresholdValues() throws NumberFormatException, IOException{
		
		//Generating a string that denotes the path to the directory of thresholdvalues.txt file. 
		fileDir = new String("src" + File.separator + "loginAndThresholdFeatureValues"
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
		    		
		    			thresholdValues[i] = Integer.parseInt(nextLine[i]);
		    		
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
	
	
	//Based on mean and 
	public int selectShare(){
		
		
		return 1;
		
	}
	
	
	//To calculate the y co-ordinates
	public void calculateYcoordinates(){
		
	}
	
	
	//Calculating lagrange multiplier
	public float calculateLagrangeMultiplier(){
		
		
		return 0;
		
		
	}
	
	
}
