package tests;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import us.lsi.common.Trio;
import us.lsi.tiposrecursivos.BinaryTree;
import utils.FicherosListas;
import utils.Resultados;
import utils.TipoAjuste;
import utils.FicherosListas.PropiedadesListas;

public class TestEj2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// generamos los datos a usar 
		PropiedadesListas props = PropiedadesListas.of(sizeMin, sizeMax, numSizes, minValue, maxValue,numListPerSize, numCasesPerList, rr);
		generaFicherosDatos(props);
	}
	
	// boilerplate
	private static Integer sizeMin = 100; // tamaño mínimo de lista
	private static Integer sizeMax = 1000; // tamaño máximo de lista
	private static Integer numSizes = 50; // número de tamaños de listas
	private static Integer minValue = 0; 
	private static Integer maxValue = 100000;
	private static Integer numListPerSize = 1; // número de listas por cada tamaño  (UTIL???) 
	private static Integer numCasesPerList = 30; // número de casos (elementos a buscar) por cada lista 
	private static Random rr = new Random(System.nanoTime()); // para inicializarlo una sola vez y compartirlo con los métodos que lo requieran
	
	
	private static Integer numMediciones = 3; // número de mediciones de tiempo de cada caso (número de experimentos)
	private static Integer numIter = 50; // número de iteraciones para cada medición de tiempo
	private static Integer numIterWarmup = 1000; // número de iteraciones para warmup
	
	
	// metodos a testear
	  List<Trio<Consumer<List<Integer>>, TipoAjuste, String>> metodos = List.of(
			//TODO
	);

	
	// ubicación del fichero a utilizar
	//
	  //creacion de los ficheros de datos

		public static void generaFicherosDatos(PropiedadesListas p) {
			Resultados.cleanFile("ficheros/Listasej3.txt"); // crea un file txt que se llama así 
			// crea un version alternativa del crea listas enteros
			generaFicheroListasEnterosV1("ficheros/Listasej3.txt", p);
			
		}
		public static void generaFicheroListasEnterosV1(String fichero, PropiedadesListas p) {
			for (int i=0; i<p.numSizes(); i++) {
				int tam = p.sizeMin() + i*(p.sizeMax()-p.sizeMin())/(p.numSizes()-1);
				for (int j=0; j<p.numListPerSize(); j++) {
					List<Integer> ls = FicherosListas.generaListaEnteros(tam, p);
					String sls = ls.stream().map(x->x.toString()).collect(Collectors.joining(",")); 
					Resultados.toFile(String.format("%s",sls), fichero, false);
				}
			}
		}
		
	
}
