package Sample.Add;

import FlexFT.RBComponent;
import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;

public class AddRB extends RBComponent<IAdd> implements IConnections,
		ILifeCycle, IUnknown, IMetaInterface, IAdd {

	public AddRB(IUnknown binder) {
		super(binder);
	}

	public boolean acceptanceTest(Object[] args, Object result) {
		Integer soma = (Integer) result;
		Integer a = (Integer) args[0];
		Integer b = (Integer) args[1];
		return (soma == a + b);
	}

	public Integer add(Integer a, Integer b) {
		return (Integer) this.execute("add", a, b);
	}

}
