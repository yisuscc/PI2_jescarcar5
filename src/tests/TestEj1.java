package tests;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import ejercicios.Ejercicio1;
import tests.TestEjemplo2.Problema;
import tests.TestEjemplo2.TResultD;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataCurveFitting;
import utils.GraficosAjuste;
import utils.Resultados;
import utils.TipoAjuste;

public class TestEj1 {

	public static void main(String[] args) {
		generaFicherosTiempoEjecucion();
		muestraGraficas();
	}

	private static Integer nMin = 2; // n mínimo
	private static Integer nMaxBigInt = 10000; // n máximo para el fibonacci recursivo sin memoria
	private static Integer nMaxDouble = 10000; // n máximo para el fibonacci no exponencial
	private static Integer numSizes = 30; // número de problemas
	private static Integer numMediciones = 10; // 10; // número de mediciones de tiempo de cada caso (número de
												// experimentos)
	// para exponencial se puede reducir
	private static Integer numIter = 50; // 50; // número de iteraciones para cada medición de tiempo
	// para exponencial se puede reducir
	private static Integer numIterWarmup = 1000; // número de iteraciones para warmup
	// cuasi boilerplate
	private static List<Trio<Function<Integer, Number>, TipoAjuste, String>> metodosDeDouble = List.of(
			Trio.of(Ejercicio1::facIterDouble, TipoAjuste.POWERANB, "Iterativo double (lineal)"),
			Trio.of(Ejercicio1::facRdouble, TipoAjuste.POWERANB, "Recursivo double (lineal)"));
	private static List<Trio<Function<Integer, Number>, TipoAjuste, String>> metodosDeBigInteger = List.of(
			Trio.of(Ejercicio1::facIterBigInteger, TipoAjuste.EXP2_0, "Iterativo Big Integer(exponencial) "),
			Trio.of(Ejercicio1::facRBigInteger, TipoAjuste.EXP2_0, "Recursivo Big Integer(exponencial)"));


	private static Boolean esExponencial(TipoAjuste ta) {
		Boolean r = false;
		if (ta.equals(TipoAjuste.EXP) || ta.equals(TipoAjuste.EXP2) || ta.equals(TipoAjuste.EXP2_0))
			r = true;
		return r;
	}

	private static <E> void generaFicherosTiempoEjecucionMetodos(
			List<Trio<Function<Integer, Number>, TipoAjuste, String>> metodos) {
		// cuasi boilerplate

		for (int i = 0; i < metodos.size(); i++) {
			Trio<Function<Integer, Number>, TipoAjuste, String> tm = metodos.get(i);// TODO cambiarlo a <E>
			int numMax = esExponencial(tm.second()) ? nMaxBigInt : nMaxDouble;
			Boolean flagExp = esExponencial(tm.second()) ? true : false;// TODO Acortar la función

			String ficheroSalida = String.format("ficheros/Tiempos%s.csv", metodos.get(i).third());

			testTiemposEjecucion(nMin, numMax, metodos.get(i).first(), ficheroSalida, flagExp);
		}

	}

	public static void generaFicherosTiempoEjecucion() {

		generaFicherosTiempoEjecucionMetodos(metodosDeDouble);
		generaFicherosTiempoEjecucionMetodos(metodosDeBigInteger);
	}

	public static <E> void muestraGraficasMetodos(List<Trio<Function<E, Number>, TipoAjuste, String>> metodos,
			List<String> ficherosSalida, List<String> labels) {
		// boilerplate
		for (int i = 0; i < metodos.size(); i++) {

			String ficheroSalida = String.format("ficheros/Tiempos%s.csv", metodos.get(i).third());
			ficherosSalida.add(ficheroSalida);
			String label = metodos.get(i).third();
			System.out.println(label);

			TipoAjuste tipoAjuste = metodos.get(i).second();
			GraficosAjuste.show(ficheroSalida, tipoAjuste, label);

			// Obtener ajusteString para mostrarlo en gráfica combinada
			Pair<Function<Double, Double>, String> parCurve = GraficosAjuste
					.fitCurve(DataCurveFitting.points(ficheroSalida), tipoAjuste);
			String ajusteString = parCurve.second();
			labels.add(String.format("%s     %s", label, ajusteString));
		}
	}

	public static void muestraGraficas() {
		// cuasi boilerplate
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();

		muestraGraficasMetodos(metodosDeBigInteger, ficherosSalida, labels);
		muestraGraficasMetodos(metodosDeDouble, ficherosSalida, labels);

		GraficosAjuste.showCombined("Factorial", ficherosSalida, labels);
	}

	@SuppressWarnings("unchecked")
	public static <E> void testTiemposEjecucion(Integer nMin, Integer nMax,
			// cuasi boilerplate
			Function<E, Number> funcion, String ficheroTiempos, Boolean flagExp) {
		Map<Problema, Double> tiempos = new HashMap<Problema, Double>();
		Integer nMed = flagExp ? 1 : numMediciones;
		for (int iter = 0; iter < nMed; iter++) {
			for (int i = 0; i < numSizes; i++) {
				Double r = Double.valueOf(nMax - nMin) / (numSizes - 1);
				Integer tam = (Integer.MAX_VALUE / nMax > i) ? nMin + i * (nMax - nMin) / (numSizes - 1)
						: nMin + (int) (r * i);
				Problema p = Problema.of(tam);
				System.out.println(tam);
				warmup(funcion, 10);
				Integer nIter = flagExp ? 50 : numIter;
				Number[] res = new Number[nIter];
				Long t0 = System.nanoTime();
				for (int z = 0; z < nIter; z++) {
					res[z] = funcion.apply((E) tam);
				}
				Long t1 = System.nanoTime();
				actualizaTiempos(tiempos, p, Double.valueOf(t1 - t0) / nIter);
			}

		}

		Resultados.toFile(tiempos.entrySet().stream().map(x -> TResultD.of(x.getKey().tam(), x.getValue()))
				.map(TResultD::toString), ficheroTiempos, true);

	}

	private static void actualizaTiempos(Map<Problema, Double> tiempos, Problema p, double d) {
		// boilerplate
		if (!tiempos.containsKey(p)) {
			tiempos.put(p, d);
		} else if (tiempos.get(p) > d) {
			tiempos.put(p, d);
		}
	}

	private static <E> BigInteger warmup(Function<E, Number> fun, Integer n) {
		// boilerplate
		BigInteger res = BigInteger.ZERO;
		BigInteger z = BigInteger.ZERO;
		for (int i = 0; i < numIterWarmup; i++) {
			if (fun.apply((E) n).equals(z))
				z.add(BigInteger.ONE);
		}
		res = z.equals(BigInteger.ONE) ? z.add(BigInteger.ONE) : z;
		return res;
	}

	record TResultD(Integer tam, Double t) {
		// boilerplate
		public static TResultD of(Integer tam, Double t) {
			return new TResultD(tam, t);
		}

		public String toString() {
			// boilerplate
			return String.format("%d,%.0f", tam, t);
		}
	}

	record Problema(Integer tam) {
		// boilerplate
		public static Problema of(Integer tam) {
			return new Problema(tam);
		}
	}
}
