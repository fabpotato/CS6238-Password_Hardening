package edu.gatech.scs.pwd.hardening;

import java.lang.Math.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class hardeningMainClass extends Exception
{
		
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		//Variables to import features values and threshold values from the login attempt class
		int[] featureValuesReceived = new int[15];
		int[] thresholdValues = new int[15];
		String pwd;
		
		long[] alpha = new long[15];
		long[] beta = new long[15];
		
		long[] x_coordinate = new long[15];
		long[] y_coordinate = new long[15];

		
		
		//hardeningMainClass Hpwd= new hardeningMainClass();
		initialize init= new initialize();
		
		
		//Creating an encryption-decryption object. 
		encryptAndDecrypt encDec = new encryptAndDecrypt();
		
		System.out.println("Prime number is "+init.q);
		
		//Initializing a random polynomial through the init object
		init.Polynomials(init.q);
		
		//Creating an instruction table through the init object 
		init.Instruction_Table();
		
		//Creating and initializing a history file through the init object
		init.History_File();
		
		//Encrypting the history file through the encryption-decryption object
		encDec.His_Encrypt(init.c[0]);
		
		//Decrypting the history file through the encryption-decryption object
		encDec.His_Decrypt(init.c[0]);
		
		//importing the alpha beta values
		alpha = init.a;
		beta = init.b;
		
		
		//Creating a login attempt object to read login feature values and store them in the main method's variables
		LoginAttempt login = new LoginAttempt();
		
		featureValuesReceived = login.loginAttempt();
		thresholdValues = login.fetchThresholdValues();
		pwd = (String) login.pwd; //pulling password from login object
		
		//Assume that 
		for(int i=0; i<15; i++){
		
			
				//Now that we have the feature receieved from the client and threshold values in local arrays, compare them and pick alpha/beta 
				if(featureValuesReceived[i] < thresholdValues[i]){
					
					//assign 2i to the x coordinate and decrypt alpha to get y coordinate
					
					y_coordinate[i] = alpha[i] - init.bytesToLong(init.generateHMac(pwd, Integer.toString(2*i), "HmacSHA256"));
					x_coordinate[i] = 2*i;
					
				}else{
					
					//assign 2i to the x coordinate and decrypt alpha to get y coordinate
					
					y_coordinate[i] = beta[i] - init.bytesToLong(init.generateHMac(pwd, Integer.toString((2*i) + 1), "HmacSHA256"));
					x_coordinate[i] = ((2*i) + 1);
					
				}
			
		}
		
	}
}
