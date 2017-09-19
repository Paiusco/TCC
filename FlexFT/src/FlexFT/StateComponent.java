package FlexFT;

import java.lang.reflect.Method;

import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IOpenCOM;
import OpenCOM.IUnknown;
import OpenCOM.OCM_SingleReceptacle;

public abstract class StateComponent<T> extends FTComponent<T> implements
		IConnections, ILifeCycle, IUnknown, IMetaInterface, StateInterface {

	private IOpenCOM openCOM;
	private long connID;

	public StateComponent(IUnknown binder) {
		super(binder);
		openCOM = (IOpenCOM) binder.QueryInterface(IOpenCOM.class.getName());
		connID = Long.MIN_VALUE;
		receptacle = new OCM_SingleReceptacle<T>(tClass);
	}

	@SuppressWarnings("unchecked")
	public final Object execute(String name, Object... args) throws FTException {
		try {
			Class[] types = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				types[i] = args[i].getClass();
			}
			OCM_SingleReceptacle<T> single = (OCM_SingleReceptacle<T>) receptacle;
			Method m = tClass.getMethod(name, types);
			return m.invoke(single.m_pIntf, args);
		} catch (Exception e) {
			throw new FTException(e);
		}
	}

	public final void changeState(Class<?> stateClass) {
		String stateName = stateClass.getName();
		IUnknown state;
		ILifeCycle pILife;

		if (connID != Long.MIN_VALUE) {
			openCOM.disconnect(connID);
		}

		state = openCOM.getComponentPIUnknown(stateName);

		if (state == null) {
			state = (IUnknown) openCOM.createInstance(stateClass.getName(),
					stateClass.getName());
			pILife = (ILifeCycle) state.QueryInterface(ILifeCycle.class
					.getName());
			pILife.startup(openCOM);
		}

		connID = openCOM.connect(this, state, tClass.getName());
	}
}
