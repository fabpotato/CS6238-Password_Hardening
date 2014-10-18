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
				System.out.println("Main");
		//Variables to import features values and threshold values from the login attempt class
		BigInteger[] featureValuesReceived = new BigInteger[16];
		BigInteger[] thresholdValues = new BigInteger[16];
		String pwd;
		
		BigInteger[] alpha = new BigInteger[16];
		BigInteger[] beta = new BigInteger[16];
		
		BigInteger[] x_coordinate = new BigInteger[16];
		BigInteger[] y_coordinate = new BigInteger[16];
		BigInteger hwpd;
		System.out.println("Variables Initialized");
		
		
		//hardeningMainClass Hpwd= new hardeningMainClass();
		initialize init= new initialize();
		System.out.println("init");
		
		//Creating an encryption-decryption object. 
		encryptAndDecrypt encDec = new encryptAndDecrypt();
		System.out.println("encryptAndDecrypt");
		
		//Initializing a random polynomial through the init object
		init.Polynomials(init.q);
		System.out.println("Init.q");

		//Creating an instruction table through the init object 
		init.Instruction_Table();
		System.out.println("Instruction table");
		//Creating and initializing a history file through the init object
		init.History_File();
		System.out.println("History File");
		//Encrypting the history file through the encryption-decryption object
		encDec.His_Encrypt(init.c[0]);
		System.out.println("His_Encrypt");

		//Decrypting the history file through the encryption-decryption object
		encDec.His_Decrypt(init.c[0]);
		System.out.println("His_Decrypt");

		//importing the alpha beta values
		alpha = init.a;
		beta = init.b;
		
		
		//Creating a login attempt object to read login feature values and store them in the main method's variables
		LoginAttempt login = new LoginAttempt();
		
		featureValuesReceived = login.loginAttempt();
		//System.out.println(featureValuesReceived);
		thresholdValues = login.fetchThresholdValues();
		pwd = (String) login.pwd; //pulling password from login object
		
		//Assume that 
		for(int i=1; i<=15; i++){
		
				System.out.println("Entered Loop");
				//Now that we have the feature receieved from the client and threshold values in local arrays, compare them and pick alpha/beta 
				if(featureValuesReceived[i].compareTo(thresholdValues[i]) < 0){
					
					//assign 2i to the x coordinate and decrypt alpha to get y coordinate
					System.out.println("Alpha");
					System.out.println("Alpha value is "+alpha[i]);
					y_coordinate[i] = alpha[i].subtract(init.generateHMac("password", Integer.toString(2*i), "HmacSHA256").mod(init.q));
					x_coordinate[i] = BigInteger.valueOf(2*i);
					System.out.println("Value of X and Y chosen is ("+x_coordinate[i]+","+y_coordinate[i]+").");
					
				}else{
					
					//assign 2i to the x coordinate and decrypt alpha to get y coordinate
					System.out.println("Beta");
					System.out.println("Beta value is "+beta[i]);
					y_coordinate[i] = beta[i].subtract(init.generateHMac("password", Integer.toString(2*i+1), "HmacSHA256").mod(init.q));
					x_coordinate[i] = BigInteger.valueOf((2*i)+1);
					System.out.println("Value of X and Y chosen is ("+x_coordinate[i]+","+y_coordinate[i]+").");
					
				}
		}		
		BigInteger hpwd=login.calculateHpwdnew(y_coordinate,x_coordinate,init.q);
		System.out.println("The hardened password is "+hpwd);
		
	}
}
