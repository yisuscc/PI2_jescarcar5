package ejercicios;

import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;


public class Ejercicio3 {

	private static  List<String> casoRecursivo(Tree<String> arbol, String K,String ac,List<String>res ){
		//no se si hay que hacer un copy 
		//https://blog.adamgamboa.dev/switch-expression-on-java-17/
		switch (arbol) {
			// caso empy, rompemos
		//case TEmpty<String>  t-> yield ac;
		
//			caso hoja: comprobamos que si etiqueta no sea la letrea y slo mentemos en la cadena y la cadena en la lista
		case TLeaf< String >  t-> if (!t.containsLabel(K)) {
			arbol.elements().stream().forEach(a-> casoRecursivo(a, K, ac, res));
		}
		// casoo nary: comprobamos que si etiqueta no sea la letra y llamamos a sus hjos 
		default  break;
		
		}
		return res;
	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	}

}
