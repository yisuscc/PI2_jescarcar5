package ejercicios;

import java.math.BigInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import us.lsi.common.Pair;

public class Ejercicio1 {

	// version double recursiva
	public static Double facRdouble(Integer n) {
		Double r = null;
		if (n == 0 || n == 1) {
			r = (double) 1;
		} else {
			r = n * facRdouble(n - 1);
		}
		return r;
	}

//	 version double iteratva

	public static Double facIterDouble(Integer n) {
		Double ac = (double) 1;
		for (Double i = (double) 1; i <= n; i++) {
			ac = ac * i;
		}
		return ac;

	}

	// version bigInt recursiv
	public static BigInteger facRBigInteger(Integer n) {
		BigInteger r = null;
		if (n == 1) {
			r = BigInteger.ONE;
		} else {

			r = facRBigInteger(n - 1).multiply(BigInteger.valueOf(n));
		}
		return r;
	}


	public static BigInteger facIterBigInteger(Integer N) {
		BigInteger f = new BigInteger("1");
		for (int i = 2; i <= N; i++)
			f = f.multiply(BigInteger.valueOf(i));
		return f;
	}

	public static void main(String[] args) {
		System.out.println("Recursivo double" + facRdouble(14));
		System.out.println("Iterativo double" + facIterDouble(14));
		System.out.println("Iterativo Big Integer" + facIterBigInteger(14));
		System.out.println("RecursivoBig Integer" + facRBigInteger(14));
	}
//	// verisón long recursiva
//	
//	public static Long facRLong(Long n) {
//		Long r = null;
//		if (n == 0 || n == 1) {
//			r = (long) 1;
//		}
//		else {
//			r = n * facRLong(n-1);
//		}
//		return r; 
//	}
//	
//	
//	// version long iterativa
//	public static Long facIterLong(Long n) {
//		Long ac = 1L;
//		for(Long i = 1L;i<= n; i++ ) {
//			ac = ac*i;
//		}
//		return ac;
//		
//	}
//		// versión big int iterativa
//	public static BigInteger facIterBigInt(Integer n) {
//	BigInteger ac = BigInteger.ONE;
//	for(BigInteger i  =  BigInteger.ONE;i.compareTo(n)<= 0; i.add(BigInteger.ONE) ) {
//		ac = ac.multiply(i);
//	}
//	return ac;
//	
//}
//	
//	// versión long funcional 
//	
//	public static Long facFuncLong(Long n) {
//		UnaryOperator<Pair<Long, Long>> next = x -> {
//		Long m = x.first() +1;
//		Long ac = x.second()* m; 
//		return Pair.of(m, ac);
//		};
//		Pair<Long, Long> seed = Pair.of(0L, 1L);
//		return Stream.iterate(seed, next).dropWhile(y -> y.first()<n).findFirst().get().second();
//	}

//	// version double funcional 
//	
//	public static Double facFuncDouble(Double n) {
//		UnaryOperator<Pair<Double, Double>> next = x -> {
//		Double m = x.first() +1;
//		Double ac = x.second()* m; 
//		return Pair.of(m, ac);
//		};
//		Pair<Double, Double> seed = Pair.of(0., 1.);
//		return Stream.iterate(seed, next).dropWhile(y -> y.first()<n).findFirst().get().second();
//	}
//	
//	// versión big int funcional 
//	public static BigInteger facFuncBigInteger(BigInteger n) {
//		UnaryOperator<Pair<BigInteger, BigInteger>> next = x -> {
//		BigInteger m = x.first().add(BigInteger.ONE);
//		BigInteger ac = x.second().multiply(m); 
//		return Pair.of(m, ac);
//		};
//		Pair<BigInteger, BigInteger> seed = Pair.of(BigInteger.ZERO,BigInteger.ONE);
//		return Stream.iterate(seed, next).dropWhile(y -> y.first().compareTo(n)<0).findFirst().get().second();
//	}

}
