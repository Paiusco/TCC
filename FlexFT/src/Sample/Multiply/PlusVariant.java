package Sample.Multiply;

import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;

public class PlusVariant extends OpenCOMComponent implements IUnknown,
		IMultiply, IMetaInterface, ILifeCycle {

	public PlusVariant(IUnknown pRuntime) {
		super(pRuntime);
	}

	public Integer multiply(Integer a, Integer b) {
		final int times = b > 0 ? b : -b;
		final int value = b > 0 ? a : -a;
		
		int result = 0;
		for (int i = 0; i < times; i++) {
			result += value;
		}
		System.out.println("[" + this + "] executando multiply => " + result);
		return result;
	}

	public boolean startup(Object pIOCM) {
		return true;
	}

	public boolean shutdown() {
		return true;
	}
}
