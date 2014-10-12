import java.lang.Math.*;
import java.math.*;
import java.util.*;
import java.security.SignatureException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Hardening extends Exception
{
	/* Size of History file */
	int h=8;

	/* Random Prime Number */
	BigInteger q;

	/* Coeffecients */
	int[] c = new int[15];

	/* Constructor. */
	public Hardening()
	{
		//create a BigInteger object
		BigInteger l;
		
		//Length of prime=160bits
		int bitlength=160;
		
		//Create a random object
		Random rnd= new Random();
		
		//assign probablePrime result to q using bitlength and rnd.	
		q=BigInteger.probablePrime(bitlength, rnd);
		String str= "ProbablePrime of bitlength"+bitlength+" is " +q;
		System.out.println(str);
	}
		
	/* To choose a random polynomial f(x)=ckxk+⋯+c1x+c0 of degree k in the polynomial ring Zq[x] simply means choosing k+1 random coefficients c0,c1,⋯,ck uniformly from the finite field Zq. However, the additional constraint that f(0)=c0 should take a particular fixed value means that, in fact, only the k coefficients c1,…,ck can be chosen randomly, while the constant coefficient c0 is fixed.
http://crypto.stackexchange.com/questions/6455/how-to-generate-a-random-polynomial-of-degree-m	*/		
		
	public void Polynomials(BigInteger q)
	{
		// We need to generate 15 Random coeffecients and the c0 should be Hpwd.
		Random rand= new Random();
		for( int i=0;i <15;i++)
		{
			//Changing the value of c[0] to the Hpwd. Assuming Hpwd=12345

			if(i==0)
				c[0]=12345;
			else	
			c[i]=rand.nextInt(100)+1;

			System.out.println("Coefficient of x^"+i+" is "+c[i]);
		}
		
	}

	public void Instruction_Table(int[] c)
	{
		//z[i] stands for the ith value of y for an account 'a'--> Similar to y(a,i,1) 
		int[] z=new int[15];
	
		//y[i] stands for the ith value of y for an account 'a'--> Similar to y(a,i,0) 
		int[] y=new int[15];
	
		//a is the alpha value for account a's ith feature
		int[] a=new int[15];

		//b is the beta value for account a's ith feature
		int[] b=new int[15];

		/* Using Formulas for alpha and beta
			a[i]=y[i] + G(r,pwd)(2i)mod q
			b[i]=z[i] + G(r,pwd)(2i)mod q*/
		
		for(int i=0;i<15;i++)
		{
			a[i]= Polynomial_Calculation(2*i) + byteArrayToInt(generateHMac("password", Integer.toString(2*i), "HmacSHA256"));
			b[i]= Polynomial_Calculation((2*i)+1) + byteArrayToInt(generateHMac("password", Integer.toString((2*i)+1), "HmacSHA256"));
		}

	}	
	/* generateHMac returns a byte array which needs to be converted to Int */
	public static int byteArrayToInt(byte[] b) 
	{
	    return   b[3] & 0xFF |
		    (b[2] & 0xFF) << 8 |
		    (b[1] & 0xFF) << 16 |
		    (b[0] & 0xFF) << 24;
	}
	/* Calculating value of Polynomial required for alpha and beta. */
	
	public int Polynomial_Calculation(int x)
	{
		int f=0;
		for (int i=0;i<15;i++)
		{
			f+=c[i]*(Math.pow(x,i));
		}
		return f;
	}
	public byte[] generateHMac(String secretKey, String data, String algorithm /* e.g. "HmacSHA256" */)
	{

	    	SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), algorithm);

		try {
			Mac mac = Mac.getInstance(algorithm);
			mac.init(signingKey);

			return mac.doFinal(data.getBytes());
		    }
		    catch(InvalidKeyException e) {
			throw new IllegalArgumentException("invalid secret key provided (key not printed for security reasons!)");
		    }
		    catch(NoSuchAlgorithmException e) {
			throw new IllegalStateException("the system doesn't support algorithm " + algorithm, e);
		    }
	}

	public static void main(String[] args)
	{
		Hardening Hpwd= new Hardening();
		System.out.println("Prime number is "+Hpwd.q);
		Hpwd.Polynomials(Hpwd.q);
			
	}
}
