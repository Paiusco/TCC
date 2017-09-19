package Sample.Add;

import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;

public class RightAdd extends OpenCOMComponent implements IUnknown, IAdd,
		IMetaInterface, ILifeCycle {

	public RightAdd(IUnknown pRuntime) {
		super(pRuntime);
	}
	
	public Integer add(Integer a, Integer b) {
		final int result = a + b;
		System.out.println("[" + this + "] executando add => "+ result);
		return result;
	}

	public boolean startup(Object pIOCM) {
		return true;
	}

	public boolean shutdown() {
		return true;
	}
}
