package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import ejercicios.Ejercicio2;
import tests.TestEjemplo3.Problema;
import tests.TestEjemplo3.TResult;
import tests.TestEjemplo3.TResultD;
import tests.TestEjemplo3.TResultMedD;
import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataCurveFitting;
import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.BinaryTree;
import utils.FicherosListas;
import utils.GraficosAjuste;
import utils.Resultados;
import utils.TipoAjuste;
import utils.FicherosListas.PropiedadesListas;

public class TestEj2 {

	public static void main(String[] args) {

		// generamos los datos a usar
		PropiedadesListas props = PropiedadesListas.of(sizeMin, sizeMax, numSizes, minValue, maxValue, numListPerSize,
			numCasesPerList, rr);
		generaFicherosDatos(props);
		generaFicherosTiempoEjecucion();
		muestraGraficas();
	}

	// boilerplate
	private static Integer sizeMin = 100; // tamaño mínimo de lista
	private static Integer sizeMax = 1000; // tamaño máximo de lista
	private static Integer numSizes = 50; // número de tamaños de listas
	private static Integer minValue = 0;
	private static Integer maxValue = 100000;
	private static Integer numListPerSize = 1; // número de listas por cada tamaño (UTIL???)
	private static Integer numCasesPerList = 1; // número de casos (elementos a buscar) por cada lista
	private static Random rr = new Random(System.nanoTime()); // para inicializarlo una sola vez y compartirlo con los
																// métodos que lo requieran

	private static Integer numMediciones = 3; // número de mediciones de tiempo de cada caso (número de experimentos)
	private static Integer numIter = 50; // número de iteraciones para cada medición de tiempo
	private static Integer numIterWarmup = 1000; // número de iteraciones para warmup
	// otras random
	private static Integer rMin = 5;
	private static Integer rMax = 500;
	private static Integer rInt = Math2.getEnteroAleatorio(rMin, rMax);
	

	// metodos a testear
	 private static List<BiConsumer<List<Integer>, Integer>> metodos = List.of(
			// TODO
			Ejercicio2::quickSortMT // versión normal
			);
	 private static List<String> etiquetasMetodos = List.of("QSMTUmbral4", "QSMTumbral2",
			"QSMtUmbral8", "QSMTUmbralRandom");
	private static List<String> ficheroListaEntrada = List.of("ficheros/Listasej2.txt");
	private static List<Integer> umbrales = List.of(4,2,8,rInt);
	
	public static void muestraGraficas() {
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		for (Integer i=0; i<etiquetasMetodos.size(); i++) { 
			String ficheroMediosSalida = String.format("ficheros/Tiempos%s.csv",etiquetasMetodos.get(i));
			String label = etiquetasMetodos.get(i);
			TipoAjuste tipoAjuste = TipoAjuste.NLOGN_0;
			GraficosAjuste.show(ficheroMediosSalida, tipoAjuste, label);
			Pair<Function<Double, Double>, String> parCurve = GraficosAjuste.fitCurve(
					DataCurveFitting.points(ficheroMediosSalida), tipoAjuste);
			String ajusteString = parCurve.second();
			if(true) { //TODO Modificar para restringir los valores
				ficherosSalida.add(ficheroMediosSalida);
				labels.add(String.format("%s     %s", label, ajusteString));
		}
		}
		GraficosAjuste.showCombined("Quicksorts", ficherosSalida, labels);        
		

	}
	
	public static void generaFicherosTiempoEjecucion() {
		for(Integer i= 0 ; i<etiquetasMetodos.size(); i++) {
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv",etiquetasMetodos.get(i));
			System.out.println(ficheroSalida);
			String ficheroMediosSalida = String.format("ficheros/Tiempos%s.csv",etiquetasMetodos.get(i));
			testTiemposEjecucionQuickSort(ficheroListaEntrada.get(0),
					umbrales.get(i),
					metodos.get(0), //TODO Modificar para que corresponda con la  i
					ficheroSalida,
					ficheroMediosSalida);
		}
	}
	
	// creacion de los ficheros de datos

	private static void testTiemposEjecucionQuickSort(String ficheroListas, Integer umbral,
			BiConsumer<List<Integer>, Integer> quicksort, String ficheroSalida, String ficheroMediosSalida) {
		Map<Problema, Double> tiempos = new HashMap<Problema,Double>();
		Map<Integer, Double> tiemposMedios; // tiempos medios por cada tamaño
		List<String> lineasListas = Files2.linesFromFile(ficheroListas);
		// aki iria el fichero lista de umbrales,
		for(int iter=0; iter<numMediciones; iter++) {
			System.out.println(iter);
			// iterador por cada linea 
			for(int i=0; i<lineasListas.size(); i++) {
				String []lineaLista = lineasListas.get(i).split(",");
				List<Integer> ls = List2.parse(lineaLista, t-> Integer.parseInt(t));
				Integer tam = ls.size();
				
//					String lineaElem = lineasElem.get(i*numCasesPerList+j);
//					List<String> lse = List2.parse(lineaElem, "#", Function.identity());
					Problema p = Problema.of(tam, i%1, 1);
					warmup(quicksort, ls, umbral);
					
					Long t0 = System.nanoTime();
					for (int z=0; z<numIter; z++) {
						quicksort.accept(ls, umbral);
					}
					Long t1 = System.nanoTime();
					actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/numIter);
				
			}
		}
		tiemposMedios = tiempos.entrySet().stream()
				.collect(Collectors.groupingBy(x->x.getKey().tam(),
							Collectors.averagingDouble(x->x.getValue())
							)
						);
		Resultados.toFile(tiemposMedios.entrySet().stream()
				.map(x->TResultMedD.of(x.getKey(),x.getValue()))
				.map(TResultMedD::toString),
			ficheroMediosSalida,
			true);
		
	}

	private static void actualizaTiempos(Map<Problema, Double> tiempos, Problema p, double d) {
	
		if (!tiempos.containsKey(p)) {
			tiempos.put(p, d);
		} else if (tiempos.get(p) > d) {
				tiempos.put(p, d);
		}
		
	}
	private static void warmup(BiConsumer<List<Integer>, Integer> busca, List<Integer> ls, Integer umbral) {
		for (int i=0; i<numIterWarmup; i++) {
			busca.accept(ls, umbral);
		}
	
	}

	public static void generaFicherosDatos(PropiedadesListas p) {
		Resultados.cleanFile("ficheros/Listasej2.txt"); // crea un file txt que se llama así
		// crea un version alternativa del crea listas enteros
		generaFicheroListasEnterosV1("ficheros/Listasej2.txt", p);

	}

	public static void generaFicheroListasEnterosV1(String fichero, PropiedadesListas p) {
		for (int i = 0; i < p.numSizes(); i++) {
			int tam = p.sizeMin() + i * (p.sizeMax() - p.sizeMin()) / (p.numSizes() - 1);
			for (int j = 0; j < p.numListPerSize(); j++) {
				List<Integer> ls = FicherosListas.generaListaEnteros(tam, p);
				String sls = ls.stream().map(x -> x.toString()).collect(Collectors.joining(","));
				Resultados.toFile(String.format("%s", sls), fichero, false);
			}
		}
	}
	
	record TResult(Integer tam, Integer numList, Integer numCase, Long t) {
		public static TResult of(Integer tam, Integer numList, Integer numCase, Long t){
			return new TResult(tam, numList, numCase, t);
		}
		
		public String toString() {
			return String.format("%d,%d,%d,%d", tam, numList, numCase, t);
		}
	}
	
	record TResultD(Integer tam, Integer numList, Integer numCase, Double t) {
		public static TResultD of(Integer tam, Integer numList, Integer numCase, Double t){
			return new TResultD(tam, numList, numCase, t);
		}
		
		public String toString() {
			return String.format("%d,%d,%d,%.0f", tam, numList, numCase, t);
		}
	}
	
	record TResultMedD(Integer tam, Double t) {
		public static TResultMedD of(Integer tam, Double t){
			return new TResultMedD(tam, t);
		}
		
		public String toString() {
			return String.format("%d,%.0f", tam, t);
		}
	}
	
	
	record Problema(Integer tam, Integer numList, Integer numCase) {
		public static Problema of(Integer tam, Integer numList, Integer numCase){
			return new Problema(tam, numList, numCase);
		}
	}
	

}
