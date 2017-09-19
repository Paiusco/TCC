package Sample.Sort;

import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;

public class SelectionSort extends OpenCOMComponent implements IUnknown,
		IMetaInterface, ILifeCycle, ISort {

	public SelectionSort(IUnknown pRuntime) {
		super(pRuntime);
	}

	public Integer[] sort(Integer[] elements) {
		int i, j, indiceDoMinimo, temp;
		int fim = elements.length;

		final int size = elements.length;
		Integer[] ordered = new Integer[size];
		for (i = 0; i < size; i++) {
			ordered[i] = elements[i];
		}

		for (i = 0; i < fim - 1; i++) {

			// Inicialmente o menor elemento ja visto eh o i-esimo elemento

			indiceDoMinimo = i;
			for (j = i + 1; j < fim; j++) {
				if (ordered[j] < ordered[indiceDoMinimo]) {
					indiceDoMinimo = j;
				}
			}

			// Coloca o menor elemento no inicio do sub-vetor atual. Para isso,
			// troca de lugar os elementos nos indices e e indiceDoMinimo.

			temp = ordered[i];
			ordered[i] = ordered[indiceDoMinimo];
			ordered[indiceDoMinimo] = temp;
		}
		System.out.print("[" + this + "] executando sort => ");
		GeradorDeArranjos.imprimeArranjo(ordered);
		return ordered;
	}

	public boolean startup(Object pIOCM) {
		return true;
	}

	public boolean shutdown() {
		return true;
	}
}
