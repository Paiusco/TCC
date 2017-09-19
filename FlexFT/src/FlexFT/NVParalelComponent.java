package FlexFT;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import OpenCOM.CallbackResult;
import OpenCOM.ICallback;
import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OCM_MultiReceptacleParallel;

public abstract class NVParalelComponent<T> extends NVComponent<T> implements
		IConnections, ILifeCycle, IUnknown, IMetaInterface, NVInterface,
		ICallback {

	List<Object> results;

	public NVParalelComponent(IUnknown binder) {
		super(binder);
		receptacle = new OCM_MultiReceptacleParallel<T>(tClass, this);
	}

	@SuppressWarnings("unchecked")
	public final Object execute(String name, Object... args) throws FTException {
		try {
			Class[] types = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				types[i] = args[i].getClass();
			}
			results = new ArrayList<Object>();
			OCM_MultiReceptacleParallel<T> paralel = (OCM_MultiReceptacleParallel<T>) receptacle;
			Method m = tClass.getMethod(name, types);
			m.invoke(paralel.m_pIntf, args);
			return decide(results);
		} catch (Exception e) {
			throw new FTException(e);
		}
	}

	synchronized public final void sendResult(CallbackResult result) {
		results.add(result.getValue());
	}
}
