package FlexFT;

import java.lang.reflect.Method;

import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OCM_MultiReceptacleContext;

public abstract class ContextComponent<T> extends FTComponent<T> implements
		IConnections, ILifeCycle, IUnknown, IMetaInterface, ContextInterface {

	public ContextComponent(IUnknown binder) {
		super(binder);
		receptacle = new OCM_MultiReceptacleContext<T>(tClass);
	}

	@SuppressWarnings("unchecked")
	public final Object execute(String name, Object... args)
			throws FTException {
		try {
			Class[] types = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				types[i] = args[i].getClass();
			}
			OCM_MultiReceptacleContext<T> context = (OCM_MultiReceptacleContext<T>) receptacle;
			Method m = tClass.getMethod(name, types);
			return m.invoke(context.m_pIntf, args);
		} catch (Exception e) {
			throw new FTException(e);
		}
	}

	public final void changeContext(String className) {
		this.removeContext("className");
		this.addContext("className", className);
	}

	@SuppressWarnings("unchecked")
	private final void addContext(String Name, Object Value) {
		((OCM_MultiReceptacleContext<T>) receptacle).addContext(Name, Value);
	}

	@SuppressWarnings("unchecked")
	private final void removeContext(String Name) {
		((OCM_MultiReceptacleContext<T>) receptacle).removeContext(Name);
	}
}
