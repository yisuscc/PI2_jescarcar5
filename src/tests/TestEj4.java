package tests;

import java.util.function.Consumer;

import ejercicios.Ejercicio4;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEj4 {

	private static void cargaDatosBT() {
		Consumer<String> cnsmr = x -> {
			//			String[] y = x.split("#");
			BinaryTree<String> bt = BinaryTree.parse(x);

			System.out.println("Arbol: " + bt);

			System.out.println(Ejercicio4.ej4BinarioV1(bt));
		};
		Files2.streamFromFile("ficheros/Ejercicio4DatosEntradaBinario.txt").forEach(cnsmr);
	}

	private static void cargaDatosNario() {
		Consumer<String> cnsmr = x -> {

			Tree<String> bt = Tree.parse(x);

			System.out.println("Arbol: " + bt);
			
			System.out.println(Ejercicio4.ej4NarioV1(bt));
		};
		Files2.streamFromFile("ficheros/Ejercicio4DatosEntradaNario.txt").forEach(cnsmr);
	}

	public static void main(String[] args) {
		cargaDatosBT();
		cargaDatosNario();

	}

}
