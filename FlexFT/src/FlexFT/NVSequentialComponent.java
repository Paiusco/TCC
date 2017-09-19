package FlexFT;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OCM_MultiReceptacle;

public abstract class NVSequentialComponent<T> extends NVComponent<T> implements
		IConnections, ILifeCycle, IUnknown, IMetaInterface, NVInterface {

	public NVSequentialComponent(IUnknown binder) {
		super(binder);
		receptacle = new OCM_MultiReceptacle<T>(tClass);
	}

	@SuppressWarnings("unchecked")
	public final Object execute(String name, Object... args)
			throws FTException {

		try {
			Class[] types = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				types[i] = args[i].getClass();
			}
			List<Object> results = new ArrayList<Object>();
			OCM_MultiReceptacle<T> multiReceptacle = (OCM_MultiReceptacle<T>) receptacle;
			Method m = tClass.getMethod(name, types);
			for (T element : multiReceptacle.interfaceList) {
				results.add(m.invoke(element, args));
			}
			return decide(results);
		} catch (Exception e) {
			throw new FTException(e);
		}
	}
}
