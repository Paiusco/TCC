package FlexFT;

import java.lang.reflect.ParameterizedType;

import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IReceptacle;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;

public abstract class FTComponent<T> extends OpenCOMComponent implements
		IConnections, ILifeCycle, IUnknown, IMetaInterface {

	protected IReceptacle receptacle;
	
	protected Class<T> tClass;

	@SuppressWarnings("unchecked")
	public FTComponent(IUnknown binder) {
		super(binder);
		tClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	// IConnections Interface
	public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
		if (riid.equalsIgnoreCase(tClass.getName())) {
			return receptacle.connectToRecp(pSinkIntf, riid, provConnID);
		}
		return false;
	}

	public boolean disconnect(String riid, long connID) {

		if (riid.equalsIgnoreCase(tClass.getName())) {
			return receptacle.disconnectFromRecp(connID);
		}
		return false;
	}

	public boolean shutdown() {
		return true;
	}

	public boolean startup(Object pIOCM) {
		return true;
	}

}