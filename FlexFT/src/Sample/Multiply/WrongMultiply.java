package Sample.Multiply;

import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;

public class WrongMultiply extends OpenCOMComponent implements IUnknown, IMultiply,
		IMetaInterface, ILifeCycle {

	public WrongMultiply(IUnknown pRuntime) {
		super(pRuntime);
	}

	public Integer multiply(Integer a, Integer b) {
		final int result = 0; 
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
