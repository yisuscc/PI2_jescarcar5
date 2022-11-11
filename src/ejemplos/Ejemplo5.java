package ejemplos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejemplo5 {
	public static <E> List<Boolean> solucionRecursiva(Tree<E>t, Predicate<E> pred){
		return recursivo(t,pred,0, new ArrayList());
	}
	private static <E> List<Boolean> recursivo(Tree<E>tree, Predicate<E> pred, Integer nivel, List<Boolean> res){
		if (res.size()<= nivel) res.add(true);
		
		return switch(tree) {
		case TEmpty<E> t->  res;
		case TLeaf<E> t-> {
			Boolean r = pred.test(t.label())&&res.get(nivel);
			res.set(nivel, r);
			yield res;
		} 
		case TNary<E> t -> {
		Boolean r = pred.test(t.label())&&res.get(nivel);
		res.set(nivel, r);
		t.elements().forEach(child -> recursivo(child, pred, nivel+1, res));
		yield res;
		}
		};
	}
	
	public static void testEjemplo5() {
		String file = "ficheros/Ejemplo5DatosEntrada.txt";
		List<Tree<Integer>> inputs= 
				Files2.streamFromFile(file).map(linea-> Tree.parse(linea,s->Integer.parseInt(s))).toList();
		Predicate<Integer> par = x-> x%2== 0;
		for(Tree<Integer> tree: inputs) {
			System.out.println("Arbol: " +tree+ "Res: " +Ejemplo5.solucionRecursiva(tree, par));
		}
				
	}

	public static void main(String[] args) {
		testEjemplo5();

	}

}
