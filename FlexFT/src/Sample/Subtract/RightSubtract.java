package Sample.Subtract;

import FlexFT.Context;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;

public class RightSubtract extends Context<ISubtract> implements IUnknown, ISubtract,
		IMetaInterface, ILifeCycle {

	public RightSubtract(IUnknown pRuntime) {
		super(pRuntime);
	}

	public Integer subtract(Integer a, Integer b) {
		final int result = a - b;
		System.out.println("[" + this + "] executando subtract => " + result);
		return result;
	}
}
