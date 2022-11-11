package ejemplos;

import java.util.List;

import javax.xml.stream.events.Characters;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

public class Ejemplo4 {

	// no funciona 

	/*
	 * Implemente una función booleana que, dados un árbol binario de caracteres y una lista
de caracteres, determine si existe un camino en el árbol de la raíz a una hoja que sea igual
a la lista.
	 */
	public static  Boolean solucuionRecursiva(BinaryTree<Character> tree, List<Character>ls ) {
		return recursivo (tree,ls,0);
	}
	
	private static Boolean recursivo(BinaryTree<Character> tree, List<Character> ls, Integer i) {
		Integer n = ls.size();
		return switch (tree) {
		
		case BEmpty<Character> t -> false;
		
		case BLeaf<Character> t ->n-1 == i&&ls.get(i).equals(t.label());
		
		case BTree<Character>t ->
		ls.get(i).equals(t.label())&& (recursivo(t.left(), ls, ++i)&& recursivo(t.right(), ls, ++i));

		};
	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
