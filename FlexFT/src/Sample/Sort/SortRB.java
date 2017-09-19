package Sample.Sort;

import FlexFT.RBComponent;
import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;

public class SortRB extends RBComponent<ISort> implements IConnections,
		ILifeCycle, IUnknown, IMetaInterface, ISort {

	public SortRB(IUnknown binder) {
		super(binder);
	}

	public boolean acceptanceTest(Object[] args, Object result) {
		Integer[] elements = (Integer[]) result;

		boolean isOrdered = true;

		for (int i = 0; i < elements.length - 1 && isOrdered; i++) {
			if (elements[i] > elements[i + 1]) {
				isOrdered = false;
			}
		}
		return isOrdered;
	}

	public Integer[] sort(Integer[] elements) {
		return (Integer[]) this.execute("sort", (Object) elements);
	}
}
