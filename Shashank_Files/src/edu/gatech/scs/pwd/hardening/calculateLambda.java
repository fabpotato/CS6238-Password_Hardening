package edu.gatech.scs.pwd.hardening;

public class calculateLambda {

	
	public long[] calculate_Lambda(long[] x){
		
		long[] Lambda = new long[15];
		for(int i=0; i<15; i++){
			for(int j=0; j<15; j++){
			
			Lambda[i] = Lambda[i] * ( x[i] / ( x[j] - x[i] ) );
			
					
					
			}
			
		}
		
		return Lambda;
		
	}
	
	
}
