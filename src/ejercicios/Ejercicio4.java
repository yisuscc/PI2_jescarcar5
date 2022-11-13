package ejercicios;

import java.util.function.Consumer;
import java.util.function.Function;

import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.String2;
import us.lsi.common.Trio;
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

	public static Boolean ej4BinarioV1(BinaryTree<String> bt) {
		//		version mala
		// la buena es la v2
		return casoRecursivoBtV1(bt, true);

	}

	private static Boolean casoRecursivoBtV1(BinaryTree<String> bt, Boolean res) {

		switch (bt) {
		case BEmpty<String> t:
			break; // rompemos directamente
		case BLeaf<String> t:
			break; // rompemos directamente
		case BTree<String> t:

			Integer a = t.left().isEmpty() ? 0 : nVocalesBT(t.left());
		Integer b = t.right().isEmpty() ? 0 : nVocalesBT(t.right());
		if (a.equals(b)) {
			res = casoRecursivoBtV1(t.left(), res) && casoRecursivoBtV1(t.right(), res);
		} else
			res = false;

		break; // solo se comparan los hijos

		}
		return res;
	}
	public static Boolean ej4BinarioV2(BinaryTree<String> bt) {
		// versión buena
		return casoRecursivoBinarioV2(bt).second();
	}
	private static Pair<Integer,Boolean>casoRecursivoBinarioV2(BinaryTree<String> bt) {
		Pair<Integer,Boolean> res = Pair.of(0, true);
		switch(bt) {
		 case BEmpty<String> t: 
		 break;
		 case BLeaf<String> t: res =  Pair.of(cuentavocales(t.label()), true);
		 break;
		 case BTree<String> t: 
			 Integer l = casoRecursivoBinarioV2(t.left()).first();
		 	Integer r = casoRecursivoBinarioV2(t.right()).first();
		 	res = Pair.of(l+r, l==r);
		 	break;
		
		}
		return res;
	}

	public static Boolean ej4NarioV1(Tree<String> nt) {
		//		version mala
		// la buena es la v2
		return casoRecursivoNarioV1(nt);

	}

	private static Boolean casoRecursivoNarioV1(Tree<String> nt) {
		//		version mala
		// la buena es la v2
		Boolean res = true;
		switch (nt) {
		case TEmpty<String> t:
			break; // rompemos directamente
		case TLeaf<String> t:
			break; // rompemos directamente
		case TNary<String> t:

			if (Stream2.allEquals(t.elements().stream().map(Ejercicio4::nVocales))) {

				res = t.elements().stream().map(Ejercicio4::casoRecursivoNarioV1).reduce((r, u) -> r && u).get();

			} else
				res = false;

		break; // solo se comparan los hijos

		}
		return res;

	}
	public static Boolean ej4NarioV2(Tree<String> nt) {
		//versión buena
		return casoRecursivoNarioV2(nt).second();
	}
	private static Pair<Integer, Boolean> casoRecursivoNarioV2(Tree<String> nt){
		// creo que consw expressions se simplifica mas
		// pero acosumbro a utilizar statements
		Pair<Integer, Boolean> aux;
		switch(nt) {
		case TEmpty<String> t: aux= Pair.of(0, true); break;
		case TLeaf<String> t : aux = 
				Pair.of(cuentavocales(t.label()), true);
		break;
		case TNary<String> t: 
			// seguramente se pueda hacer con stream
			//se puede hacer con for y es mucho mas legible
			// pero queria practicar con stream
		 aux= t.elements().stream().
		mapToInt(a->casoRecursivoNarioV2(a).first()).
		mapToObj(i -> Trio.of(i, i, true)).
		reduce((q,w)-> Trio.of(q.first(),q.second()+w.second(),
				q.first().equals(w.first())&&q.third()&&w.third())).
		map(e->Pair.of(e.second(),e.third())).get();
			break;
		}
		return aux;
	}

	private static Integer nVocalesBT(BinaryTree<String> tree) {
		Integer res = 0;
		switch (tree) {
		case BEmpty<String> t:
			res = 0;
		break;// devuelve 0
		case BLeaf<String> t:
			res = cuentavocales(t.label());
		break;
		case BTree<String> t: // comprueba sus vocales y devuelvelas sulyas mas la sum de sus hijos

			res = cuentavocales(t.label()) + nVocalesBT(t.left()) + nVocalesBT(t.right());
		break;
		}

		return res;
	}

	private static Integer nVocales(Tree<String> tree) {
		Integer res = null;
		switch (tree) {
		case TEmpty<String> t:
			res = 0; // devuelve 0
		break;
		case TLeaf<String> t:
			res = cuentavocales(t.label());
		break;
		case TNary<String> t: // comprueba sus vocales y devuelvelas sulyas mas la sum de sus hijos
			Integer a = t.elements().stream().mapToInt(k -> nVocales(k)).sum();
		res = cuentavocales(t.label()) + a;
		break;
		}

		return res;
	}

	private static Integer cuentavocales(String s) {
		// complejidad probablemente n ( tamaño de la cadena)
		// https://stackoverflow.com/questions/19160921/how-do-i-check-if-a-char-is-a-vowel
		Integer res = 0;
		for (Integer i = 0; i < s.length(); i++) {
			if ("AEIOUaeiou".indexOf(s.charAt(i)) != -1) {
				res += 1;
			}
		}

		return res;
	}

	private static void cargaDatosBT() {
		Consumer<String> cnsmr = x -> {
			//			String[] y = x.split("#");
			BinaryTree<String> bt = BinaryTree.parse(x);

			System.out.println("Arbol: " + bt);

			System.out.println(ej4BinarioV2(bt));
		};
		Files2.streamFromFile("ficheros/Ejercicio4DatosEntradaBinario.txt").forEach(cnsmr);
	}

	private static void cargaDatosNario() {
		Consumer<String> cnsmr = x -> {

			Tree<String> bt = Tree.parse(x);

			System.out.println("Arbol: " + bt);
			System.out.println("N vocales" + nVocales(bt));
			System.out.println(ej4NarioV2(bt));
		};
		Files2.streamFromFile("ficheros/Ejercicio4DatosEntradaNario.txt").forEach(cnsmr);
	}

	public static void main(String[] args) {
		cargaDatosBT();
		//cargaDatosNario();

	}

}
