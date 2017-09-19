package Sample.Multiply;

import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;

public class MultiplyVariant extends OpenCOMComponent implements IUnknown,
		IMultiply, IMetaInterface, ILifeCycle {

	public MultiplyVariant(IUnknown pRuntime) {
		super(pRuntime);
	}

	public Integer multiply(Integer a, Integer b) {
		final int result = a * b;
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
