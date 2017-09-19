package Sample.Sort;

import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;

public class NothingSort extends OpenCOMComponent implements IUnknown,
		IMetaInterface, ILifeCycle, ISort {

	public NothingSort(IUnknown pRuntime) {
		super(pRuntime);
	}
	
	public Integer[] sort(Integer[] elements) {
		System.out.print("[" + this + "] executando sort => ");
		GeradorDeArranjos.imprimeArranjo(elements);
		return elements;
	}

	public boolean startup(Object pIOCM) {
		return true;
	}

	public boolean shutdown() {
		return true;
	}
}
