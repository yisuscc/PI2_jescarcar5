package ejercicios;

import java.util.function.Consumer;

import us.lsi.common.Files2;
import us.lsi.common.String2;
import us.lsi.streams.Stream2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio4 {
	
	
	
	
	
	public static Boolean ej4Binario (BinaryTree<String> bt) {
		return casoRecursivoBt(bt, true);
		
	}
	private static Boolean casoRecursivoBt(BinaryTree<String> bt, Boolean res) {
		
		switch(bt) {
		case BEmpty<String> t: break; // rompemos directamente
		case BLeaf<String> t: break; // rompemos directamente
		case BTree<String> t: 

			Integer a = t.left().isEmpty()?0:cuentavocales(t.left().optionalLabel().get());
			Integer b = t.right().isEmpty()?0:cuentavocales(t.right().optionalLabel().get());
			if(a.equals(b)) {
				res = casoRecursivoBt(t.left(), res) && casoRecursivoBt(t.right(), res);
			}
			else res = false;
			
			break; // solo se comparan los hijos
			
		}
		return res;
		
	}
	public static Boolean ej4Nario (Tree<String> nt) {
		return casoRecursivoNario(nt, true);
		
	}
	private static Boolean casoRecursivoNario(Tree<String> nt, Boolean res) {
		
		switch(nt) {
		case TEmpty<String> t: break; // rompemos directamente
		case TLeaf<String> t: break; // rompemos directamente
		case TNary<String> t: 

		//probar con stream2.allequals 
			if(t.elements().stream().map(e->cuentavocales(e.optionalLabel().
					orElseGet(()-> ""))).distinct().count()==1) {
				res = Stream2.allEquals(t.elements().stream().map(a-> casoRecursivoNario(a, true)));
			}
			else res = false;
			
			break; // solo se comparan los hijos
			
		}
		return res;
		
	}
	
	
	
//	private static Integer nVocalesBT(BinaryTree<String> bt) {
//		 Integer res = null;
//		switch (bt) {
//		case BEmpty<String> t: res =0; //devuelve 0
//		case BLeaf<String> t: res = cuentavocales(t.label());
//		break;
//		case BTree<String> t: // comprueba sus vocales y devuelvelas sulyas mas la sum de sus hijos
//			res = cuentavocales(t.label()) +nVocalesBT(t.left()) +nVocalesBT(t.right());
//		break;
//		}
//		
//		return ac;
//		}
//	
	
	
	
	
	
	
	
	
	
	
	
	private static Integer cuentavocales(String s) {
		// complejidad  probablemente n ( tamaño de la cadena)
		//https://stackoverflow.com/questions/19160921/how-do-i-check-if-a-char-is-a-vowel
		Integer res =0;
		for(Integer i  = 0; i<s.length();i++ ) {
			if("AEIOUaeiou".indexOf(s.charAt(i)) != -1) {
				res +=1;
			}
		}
		
		return res;
	}

	
	private static void cargaDatosBT() {
		Consumer<String> cnsmr = x -> {
//			String[] y = x.split("#");
			BinaryTree<String> bt =  BinaryTree.parse(x);
			
			System.out.println("Arbol: "+ bt);
			
			System.out.println(ej4Binario(bt));
		};
		Files2.streamFromFile("ficheros/Ejercicio4DatosEntradaBinario.txt").forEach(cnsmr);
	}
	
	private static void cargaDatosNario() {
		Consumer<String> cnsmr = x -> {
			
			Tree<String> bt =  Tree.parse(x);
			
			System.out.println("Arbol: "+bt);
			
			System.out.println(ej4Nario(bt));
		};
		Files2.streamFromFile("ficheros/Ejercicio4DatosEntradaNario.txt").forEach(cnsmr);
	}
	public static void main(String[] args) {
		//cargaDatosBT();	
		cargaDatosNario();

	}

}
