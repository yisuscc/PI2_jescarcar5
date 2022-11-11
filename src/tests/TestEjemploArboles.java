package tests;

import java.util.ArrayList;
import java.util.List;

import ejemplos.Ejemplo4;
import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.tiposrecursivos.BinaryTree;

public class TestEjemploArboles {
public static void  testEjemplo4() {
	String file = "ficheros/Ejemplo4DatosEntrada.txt";
	List<Pair<BinaryTree<Character>, List<Character>>> inputs =
			Files2.streamFromFile(file).map(linea->{ String[] aux= linea.split("#");
			BinaryTree<Character> t = 
					BinaryTree.parse(aux[0], s->s.charAt(0));
			List<Character> charList = stringToChar(aux[1]);
			return Pair.of(t, charList);
}
			).toList();
	for(Pair<BinaryTree<Character>, List<Character>> par:inputs) {
		BinaryTree<Character> t = par.first();
		List<Character> ls = par.second();
		System.out.println("Arbol" + t + "Res: " + Ejemplo4.solucuionRecursiva(t, ls));
	}
	
}
	
	
	private static List<Character> stringToChar(String s) {
	// TODO Auto-generated method stub
	 String aux = s.replace("[", "").replace("]", "").replace(",", "");
	List<Character> res = new ArrayList<>();
	for(int i =0 ; i<aux.length(); i++) {
		res.add(aux.charAt(i));
	}
	return res;
}


	public static void main(String[] args) {
		testEjemplo4();

	}
}

