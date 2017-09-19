package Sample.Divide;

import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;

public class RightDivide extends OpenCOMComponent implements IUnknown, IDivide,
		IMetaInterface, ILifeCycle {

	public RightDivide(IUnknown pRuntime) {
		super(pRuntime);
	}

	public Integer divide(Integer a, Integer b) {
		final int result = a / b;
		System.out.println("[" + this + "] executando divide => " + result);
		return result;
	}

	public boolean startup(Object pIOCM) {
		return true;
	}

	public boolean shutdown() {
		return true;
	}
}
