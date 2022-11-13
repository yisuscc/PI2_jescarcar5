package ejercicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.IntPair;
import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.math.Math2;

public class Ejercicio2InsertionSort {
	public static void quickSortInsSort(List<Integer> ls, Integer um) {
		Comparator<Integer> ord = Comparator.naturalOrder();
		Integer i = 0;
		Integer j = ls.size();
		quickSort(ls, i, j, um, ord);
	}

	private static <E> void quickSort(List<E> lista, int i, int j, Integer um, Comparator<? super E> ord) {
		Preconditions.checkArgument(j >= i);
		if (j - i <= um) {
			insertionSort(lista, i, j, ord);
		} else {
			E pivote = lista.get(Math2.getEnteroAleatorio(i, j));
			IntPair p = banderaHolandesa(lista, pivote, i, j, ord);
			quickSort(lista, i, p.first(), um, ord);
			quickSort(lista, p.second(), j, um, ord);
		}
	}

	public static <T> void insertionSort(List<T> lista, Integer inf, Integer sup, Comparator<? super T> ord) {
		//		https://www.youtube.com/watch?v=JU767SDMDvA
		for (int i = inf; i < sup; i++) {
			int j = i;
			while (j > 0 && ord.compare(lista.get(j - 1), lista.get(j)) > 0) {
				List2.intercambia(lista, j, j - 1);
				j--;
			}
		}
	}

	public static <E> IntPair banderaHolandesa(List<E> ls, E pivote, Integer i, Integer j, Comparator<? super E> cmp) {
		Integer a = i, b = i, c = j;
		while (c - b > 0) {
			E elem = ls.get(b);
			if (cmp.compare(elem, pivote) < 0) {
				List2.intercambia(ls, a, b);
				a++;
				b++;
			} else if (cmp.compare(elem, pivote) > 0) {
				List2.intercambia(ls, b, c - 1);
				c--;
			} else {
				b++;
			}
		}
		return IntPair.of(a, b);
	}

	public static void test() {
		Files2.linesFromFile("ficheros/Listasej2IS.txt").forEach(l -> {
			String[] e = l.split(",");
			List<Integer> ls = new ArrayList<>();
			for (int i = 0; i < e.length; i++)
				ls.add(Integer.parseInt(e[i]));
			System.out.println("Lista. " + ls);
			System.out.println("¿Esta ordenada?:" + List2.isOrdered(ls));
			quickSortInsSort(ls, 20);
			System.out.println("¿Y ahora?:" + List2.isOrdered(ls));
			System.out.println(ls);
		});

	}

	public static void main(String[] args) {
		test();
		//			List<Integer > ls = new ArrayList<>();
		//			for(int i = 0; i<15; i++)
		//				ls.add(Math2.getEnteroAleatorio(0, 999));
		//			System.out.println("Sin ordenar:" + ls);
		//			quickSortMT(ls);
		//			System.out.println("Ordenada. "+ ls);
		//			System.out.println(List2.isOrdered(ls, Comparator.naturalOrder()));

	}

}
