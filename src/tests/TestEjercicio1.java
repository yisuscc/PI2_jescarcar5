package tests;

import java.math.BigInteger;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import ejercicios.Ejercicio1;
import us.lsi.common.Trio;
//import us.lsi.math.Math2;
import utils.TipoAjuste;

public class TestEjercicio1 {
// boilerplate puro y duro  
	
//	public static void main(String[] args) {
//		generaFicherosTiempoEjecucion();
//		muestraGraficas();
//	}
	
	
	private static Integer nMin = 2; // n mínimo
	private static Integer nMaxRec = 30; // n máximo para el fibonacci recursivo sin memoria 
	private static Integer nMaxIter = 1000; // n máximo para el fibonacci no exponencial
	private static Integer numSizes = 10; // número de problemas
	private static Integer numMediciones = 2; //10; // número de mediciones de tiempo de cada caso (número de experimentos)
												// para exponencial se puede reducir 
	private static Integer numIter = 10; //50; // número de iteraciones para cada medición de tiempo
											// para exponencial se puede reducir 
	private static Integer numIterWarmup = 100; // número de iteraciones para warmup
	
	private static List<Trio<Function<Double, Number>, TipoAjuste, String>> metodosDouble = 
			List.of(
					Trio.of(Ejercicio1::facIterDouble, TipoAjuste.LINEAL, "Factorial Iterativo Double(Lineal)"), 
					Trio.of(Ejercicio1::facRdouble, TipoAjuste.LINEAL, "Factorial Recursivo Double(Lineal)")
			);
	private static List<Trio<Function<BigInteger, Number>, TipoAjuste, String>> metodosBigInteger = 
			List.of(
					Trio.of(Ejercicio1::facIterBigInt, TipoAjuste.EXP2_0, "Factorial Iterativo BigInteger(Exponencial)"), 
					Trio.of(Ejercicio1::facRBigInt, TipoAjuste.EXP2_0, "Factorial Recursivo Big Integer(Exponencial)")
			);
	
	private static <E> void generaFicherosTiempoEjecucionMetodos(List<Trio<Function<E, Number>, TipoAjuste, String>> metodos) {
		
		for (int i=0; i<metodos.size(); i++) { 
			int numMax = i==0 ? nMaxRec : nMaxIter; 
			Boolean flagExp = i==0 ? true : false;
			
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv",
					metodos.get(i).third());
			
			testTiemposEjecucion(nMin, numMax, 
						metodos.get(i).first(),
						ficheroSalida,
						flagExp);
			}
		
	}
	
	
}
