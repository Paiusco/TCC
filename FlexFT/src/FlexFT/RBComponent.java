package FlexFT;

import java.lang.reflect.Method;

import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OCM_MultiReceptacle;

public abstract class RBComponent<T> extends FTComponent<T> implements
		IConnections, ILifeCycle, IUnknown, IMetaInterface, RBInterface<T> {

	public RBComponent(IUnknown binder) {
		super(binder);
		receptacle = new OCM_MultiReceptacle<T>(tClass);
	}

	@SuppressWarnings("unchecked")
	public final Object execute(String name, Object ... args)
			throws FTException {

		try {
			Class[] types = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				types[i] = args[i].getClass();
			}
			OCM_MultiReceptacle<T> multiReceptacle = (OCM_MultiReceptacle<T>) receptacle;
			Method m = tClass.getMethod(name, types);
			for (T element : multiReceptacle.interfaceList) {
				Object result = m.invoke(element, args);
				if (acceptanceTest(args, result)) {
					return result;
				}
			} 
		} catch (Exception e) {
			throw new FTException(e);
		}
		throw new FTException("Todas falharam");
	}
}
