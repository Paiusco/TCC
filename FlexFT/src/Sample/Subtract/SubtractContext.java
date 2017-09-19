package Sample.Subtract;

import FlexFT.ContextComponent;
import FlexFT.ContextInterface;
import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;

public class SubtractContext extends ContextComponent<ISubtract> implements
		IConnections, ILifeCycle, IUnknown, IMetaInterface, ISubtract, ContextInterface {

	public SubtractContext(IUnknown binder) {
		super(binder);
	}
	
	public Integer subtract(Integer a, Integer b) {
		Object[] args = new Object[] { a, b };
		return (Integer) this.execute("subtract", args);
	}
}