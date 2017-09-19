package Sample.Divide;

import FlexFT.NVSequentialComponent;
import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;

public class DivideNV extends NVSequentialComponent<IDivide> implements
		IConnections, ILifeCycle, IUnknown, IMetaInterface, IDivide {

	public DivideNV(IUnknown binder) {
		super(binder);
	}

	public Integer divide(Integer a, Integer b) {
		return (Integer) this.execute("divide", a, b);
	}
}
