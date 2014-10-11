import java.math.*;
import java.util.*;
public class Hardening
{
	/* Size of History file */
	int h=8;
	BigInteger q;
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
		int[] c = new int[15];
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
	
	public static void main(String[] args)
	{
		Hardening Hpwd= new Hardening();
		System.out.println("Prime number is "+Hpwd.q);
		Hpwd.Polynomials(Hpwd.q);	
	}
}
