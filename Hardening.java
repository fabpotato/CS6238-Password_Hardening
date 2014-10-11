import java.math.*;
import java.util.*;
import java.security.SignatureException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Hardening
{
	/* Size of History file */
	int h=8;

	/* Random Prime Number */
	BigInteger q;

	/* Coeffecients */
	int[] c = new int[15];

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

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
			c[i]=rand.nextInt(q.intValue())+1;

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
			b[i]=z[i]+G(r,pwd)(2i)mod */
		
		for(inti=0;i<15;i++)
		{
			
		}

	}	
	
		/**
		Source: http://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/AuthJavaSampleHMACSignature.html
		* Computes RFC 2104-compliant HMAC signature.
		* * @param data
		* The data to be signed.
		* @param key
		* The signing key.
		* @return
		* The Base64-encoded RFC 2104-compliant HMAC signature.
		* @throws
		* java.security.SignatureException when signature generation fails
		*/
		public static String calculateRFC2104HMAC(String data, String key)
		throws java.security.SignatureException
		{
		String result;
		try {

		// get an hmac_sha1 key from the raw key bytes
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

		// get an hmac_sha1 Mac instance and initialize with the signing key
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);

		// compute the hmac on input data bytes
		byte[] rawHmac = mac.doFinal(data.getBytes());

		// base64-encode the hmac
		result = Encoding.EncodeBase64(rawHmac);

		} catch (Exception e) {
		throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
		}
}

	public static void main(String[] args)
	{
		Hardening Hpwd= new Hardening();
		System.out.println("Prime number is "+Hpwd.q);
		Hpwd.Polynomials(Hpwd.q);
			
	}
}
