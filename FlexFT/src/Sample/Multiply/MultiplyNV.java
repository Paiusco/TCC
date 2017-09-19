package Sample.Multiply;

import FlexFT.NVParalelComponent;
import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;

public class MultiplyNV extends NVParalelComponent<IMultiply> implements
		IConnections, ILifeCycle, IUnknown, IMetaInterface, IMultiply {

	public MultiplyNV(IUnknown binder) {
		super(binder);
	}

	public Integer multiply(Integer a, Integer b) {
		return (Integer) this.execute("multiply", a,b);
	}
}
