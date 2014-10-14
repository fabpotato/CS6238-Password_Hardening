package edu.gatech.scs.pwd.hardening;

import java.io.File;
import java.io.FileOutputStream;

import edu.gatech.scs.pwd.hardening.encryptAndDecrypt;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class initialize {


	/* Random Prime Number */
	BigInteger q;

	/* Coefficients */
	int[] c = new int[15];

	
	//a is the alpha value for account a's ith feature
	long[] a=new long[15];

	//b is the beta value for account a's ith feature
	long[] b=new long[15];

	
	
	/* Constructor. */
	public initialize(){
		//create a BigInteger object
		BigInteger l;
		
		//Length of prime=160bits
		int bitlength=160;
		
		//Create a random object
		Random rnd= new Random();
		
		//assign probablePrime result to q using bit length and rnd.	
		q=BigInteger.probablePrime(bitlength, rnd);
		String str= "ProbablePrime of bitlength"+bitlength+" is " +q;
		System.out.println(str);
	}

	
	/* To choose a random polynomial f(x)=ckxk+⋯+c1x+c0 of degree k in the polynomial ring Zq[x] simply means choosing k+1 random coefficients c0,c1,⋯,ck uniformly from the finite field Zq. However, the additional constraint that f(0)=c0 should take a particular fixed value means that, in fact, only the k coefficients c1,…,ck can be chosen randomly, while the constant coefficient c0 is fixed.
	http://crypto.stackexchange.com/questions/6455/how-to-generate-a-random-polynomial-of-degree-m	*/		
			
	public void Polynomials(BigInteger q){
		// We need to generate 15 Random co-efficients and the c0 should be Hpwd.
		Random rand= new Random();
		for( int i=0;i <15;i++)
		{
			//Changing the value of c[0] to the Hpwd. Assuming Hpwd=12345
			if(i==0){
				
				c[0]= 1234567890;
			}
				
			else	{
			c[i]=rand.nextInt(1000)+1;  //Changed this from q to 100: Do Check again(Consult TA)
			System.out.println("Coefficient of x^"+i+" is "+c[i]);
			}
		}
		
	}


	/* Function to compute the value of alpha and beta */
	public void Instruction_Table()
	{
		//z[i] stands for the ith value of y for an account 'a'--> Similar to y(a,i,1) 
		long[] z=new long[15];
	
		//y[i] stands for the ith value of y for an account 'a'--> Similar to y(a,i,0) 
		long[] y=new long[15];
	

		/* Using Formulas for alpha and beta
			a[i]=y[i] + G(r,pwd)(2i)mod q
			b[i]=z[i] + G(r,pwd)(2i)mod q */
		
		for(int i=0;i<15;i++)
		{
			a[i]= Polynomial_Calculation(2*i) + bytesToLong(generateHMac("password", Integer.toString(2*i), "HmacSHA256"));
			//System.out.println(a[i]);
			b[i]= Polynomial_Calculation((2*i)+1) + bytesToLong(generateHMac("password", Integer.toString((2*i)+1), "HmacSHA256"));
			//System.out.println(b[i]);
		}

	}

	
	
	public long bytesToLong(byte[] bytes) 
	{
	    
        long r = 0;
        for (int i = 0; i < bytes.length; i++) {
            r = r << 8;
            r += bytes[i];
        }
	//System.out.println(r);
        return r;
    }

	
	
	/* Calculating value of Polynomial required for alpha and beta. */
	
	public long Polynomial_Calculation(int x)
	{
		long f=0;
		for (int i=0;i<15;i++)
		{
			f+=c[i]*(Math.pow(x,i));
		}
		//System.out.println(f);
		return f;
	}
	
	
	/* Function to Compute Keyed Hash */
	public byte[] generateHMac(String secretKey, String data, String algorithm /* e.g. "HmacSHA256" */)
	{

	    	SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), algorithm);

		try {
			Mac mac = Mac.getInstance(algorithm);
			mac.init(signingKey);
			//System.out.println(mac.doFinal(data.getBytes()));
			return mac.doFinal(data.getBytes());
		    }
		    catch(InvalidKeyException e) {
			throw new IllegalArgumentException("invalid secret key provided (key not printed for security reasons!)");
		    }
		    catch(NoSuchAlgorithmException e) {
			throw new IllegalStateException("the system doesn't support algorithm " + algorithm, e);
		    }
	}

	
	/* History File Creation and Updation. */
	public void History_File()
	{
		try
		{
			
			File f = new File("History_File.txt");
			FileOutputStream History_file= new FileOutputStream("History_File.txt");
			//long size = f.getTotalSpace();
			//System.out.println("Size of file: " + size);
			History_file.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0".getBytes());
			
			History_file.close();
		}
		catch(Exception e){
			System.err.println(e);}
		
	}
	
	
}
