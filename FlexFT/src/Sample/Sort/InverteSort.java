package Sample.Sort;

import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;

public class InverteSort extends OpenCOMComponent implements IUnknown,
		IMetaInterface, ILifeCycle, ISort {

	public InverteSort(IUnknown pRuntime) {
		super(pRuntime);
	}

	public Integer[] sort(Integer[] elements) {
		final int size = elements.length;
		Integer[] ordered = new Integer[size];
		for (int i = 0; i < size; i++) {
			ordered[size - i - 1] = elements[i];
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
