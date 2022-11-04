package ejercicios;

import java.math.BigInteger;

public class Ejercicio1 {
	
	// verisón long recursiva
	
	public static Long facRLong(Long n) {
		Long r = null;
		if (n == 0 || n == 1) {
			r = (long) 1;
		}
		else {
			r = n * facRLong(n-1);
		}
		return r; 
	}
	
	
	// version long iterativa
	public static Long facIterLong(Long n) {
		Long ac = 1L;
		for(Long i = 1L;i<= n; i++ ) {
			ac = ac*i;
		}
		return ac;
		
	}
	
	
	// versión long funcional 
	
	
	
	
	// version double recursiva 
	public static Double facRdouble(Double n) {
		Double r = null;
		if (n == 0 || n == 1) {
			r =  (double) 1;
		}
		else {
			r = n * facRdouble(n-1);
		}
		return r; 
	}
	
	
//	 version double iteratva
	
	public static Double facIterDouble(Double n) {
		Double ac = (double) 1;
		for(Double i = (double) 1;i<= n; i++ ) {
			ac = ac*i;
		}
		return ac;
		
	}
	
	// version double funcional 
	
	
	
	
	// version bigInt  recursiv
	public static BigInteger facRBigInt(BigInteger n) {
		BigInteger r = null;
		if (n.equals(BigInteger.ZERO)|| n.equals(BigInteger.ONE)) {
			r =  BigInteger.ONE;
		}
		else {
			BigInteger u = facRBigInt(n.add(BigInteger.valueOf(-1)));
			r =n.multiply(u);
		}
		return r; 
	}
	
	// versión big int   iterativa 
	public static BigInteger facIterBigInt(BigInteger n) {
		BigInteger ac = BigInteger.ONE;
		for(BigInteger i  =  BigInteger.ONE;i.compareTo(n)<= 0; i.add(BigInteger.ONE) ) {
			ac = ac.multiply(i);
		}
		return ac;
		
	}
	
	// versión big int funcional 

	
	

}
