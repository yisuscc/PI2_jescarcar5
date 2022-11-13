package tests;

import java.util.function.Consumer;

import ejercicios.Ejercicio3;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEj3 {
	private static void cargaDatosBT() {
		Consumer<String> cnsmr = x -> {
			String[] y = x.split("#");
			BinaryTree<Character> bt = BinaryTree.parse(y[0], s -> s.charAt(0));

			System.out.println("Arbol: " + bt);
			System.out.println("Caracter: " + y[1]);
			System.out.println(Ejercicio3.caminosSinCaracterBinario(bt, y[1].charAt(0)));
		};
		Files2.streamFromFile("ficheros/Ejercicio3DatosEntradaBinario.txt").forEach(cnsmr);
	}

	private static void cargaDatosNario() {
		Consumer<String> cnsmr = x -> {
			String[] y = x.split("#");
			Tree<Character> bt = Tree.parse(y[0], s -> s.charAt(0));

			System.out.println("Arbol: " + bt);
			System.out.println("Caracter: " + y[1]);
			System.out.println(Ejercicio3.caminosSinCaracterNArio(bt, y[1].charAt(0)));
		};
		Files2.streamFromFile("ficheros/Ejercicio3DatosEntradaNario.txt").forEach(cnsmr);
	}
	
	public static void main(String[] args) {
		System.out.println("Versión  Binaria");
		cargaDatosBT();
		System.out.println("Versión N Aria");
		cargaDatosNario();

	}

}
