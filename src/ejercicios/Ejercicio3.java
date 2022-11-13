package ejercicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.BinaryTrees;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;
import us.lsi.tiposrecursivos.parsers.BinaryTreeParser;

public class Ejercicio3 {
	public static List<String> caminosSinCaracterNArio(Tree<Character> arbol, Character K) {
		List<String> res = new ArrayList<>();
		casoRecursivo(arbol, K, "", res);
		return res;
	}

	private static void casoRecursivo(Tree<Character> arbol, Character K, String ac, List<String> res) {
		// no se si hay que hacer un copy

		// ver la clase palindroma
		// https://blog.adamgamboa.dev/switch-expression-on-java-17/
		switch (arbol) {
		// caso empy, rompemos
		case TEmpty<Character> t:
			break;

		//			caso hoja: comprobamos que si etiqueta no sea la letrea y slo mentemos en la cadena y la cadena en la lista
		case TLeaf<Character> t:
			if (!t.label().equals(K)) {
				ac = ac + t.label();
				res.add(ac);
			}
		;
		break;

		case TNary<Character> t:
			if (!t.label().equals(K)) {
				ac = ac + t.label();
				//			con stream no sale :(
				for (Tree<Character> a : t.elements()) {
					casoRecursivo(a, K, ac, res);
				}
			}
		break;
		default:
			break;
		}

	}

	public static List<String> caminosSinCaracterBinario(BinaryTree<Character> arbol, Character K) {
		List<String> res = new ArrayList<>();
		casoRecursivoBT(arbol, K, "", res);
		return res;
	}

	private static void casoRecursivoBT(BinaryTree<Character> arbol, Character K, String ac, List<String> res) {
		switch (arbol) {
		case BEmpty<Character> t:
			break;
		case BLeaf<Character> t:
			if (!t.label().equals(K)) {
				ac = ac + t.label();
				res.add(ac);
			}
		;
		break;

		case BTree<Character> t:
			if (!t.label().equals(K)) {
				ac = ac + t.label();
				casoRecursivoBT(t.left(), K, ac, res);
				casoRecursivoBT(t.right(), K, ac, res);

			}
		break;
		default:
			break;
		}

	}

	private static void cargaDatosBT() {
		Consumer<String> cnsmr = x -> {
			String[] y = x.split("#");
			BinaryTree<Character> bt = BinaryTree.parse(y[0], s -> s.charAt(0));

			System.out.println("Arbol: " + bt);
			System.out.println("Caracter: " + y[1]);
			System.out.println(caminosSinCaracterBinario(bt, y[1].charAt(0)));
		};
		Files2.streamFromFile("ficheros/Ejercicio3DatosEntradaBinario.txt").forEach(cnsmr);
	}

	private static void cargaDatosNario() {
		Consumer<String> cnsmr = x -> {
			String[] y = x.split("#");
			Tree<Character> bt = Tree.parse(y[0], s -> s.charAt(0));

			System.out.println("Arbol: " + bt);
			System.out.println("Caracter: " + y[1]);
			System.out.println(caminosSinCaracterNArio(bt, y[1].charAt(0)));
		};
		Files2.streamFromFile("ficheros/Ejercicio3DatosEntradaNario.txt").forEach(cnsmr);
	}

	public static void main(String[] args) {
		cargaDatosBT();
		cargaDatosNario();

	}

}
