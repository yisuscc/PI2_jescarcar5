package ejercicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio3 {
	public static List<String>caminoSinCaracterNArio(Tree<Character> arbol, Character K){
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
			if (t.label().equals(K)) {
				ac = ac + K;
				res.add(ac);
			}
			;
			break;

		case TNary<Character> t:
			if (t.label().equals(K)) {
				ac = ac + K;
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
	public static List<String>caminoSinCaracterNArio(BinaryTree<Character> arbol, Character K){
		List<String> res = new ArrayList<>();
		casoRecursivoBT(arbol, K, "", res);
		return res;
	}

	private static void casoRecursivoBT(BinaryTree<Character> arbol, Character K, String ac, List<String> res) {
		switch (arbol) {
		case BEmpty<Character> t: break;
		case BLeaf<Character> t : if (t.label().equals(K)) {
			ac = ac + K;
			res.add(ac);
		};
		break;
		
		case BTree<Character> t : 
			if (t.label().equals(K)) {
				ac = ac + K;
				casoRecursivoBT(t.left(), K, ac, res);
				casoRecursivoBT(t.right(), K, ac, res);
				
			}
			break;
		default: break;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
