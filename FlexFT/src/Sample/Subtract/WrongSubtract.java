package Sample.Subtract;

import FlexFT.Context;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;

public class WrongSubtract extends Context<ISubtract> implements IUnknown, ISubtract,
		IMetaInterface, ILifeCycle {
	
	public WrongSubtract(IUnknown pRuntime) {
		super(pRuntime);
	}

	public Integer subtract(Integer a, Integer b) {
		final int result = a - b + 8;
		System.out.println("[" + this + "] executando subtract => " + result);
		return result;
	}
}
