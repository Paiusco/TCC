package Sample.Connection;

import FlexFT.StateComponent;
import FlexFT.StateInterface;
import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;

public class ConnectionState extends StateComponent<IConnection> implements
		IConnections, ILifeCycle, IUnknown, IMetaInterface, IConnection, StateInterface {

	public ConnectionState(IUnknown binder) {
		super(binder);
	}

	public boolean send(String message) {
		Object[] args = new Object[] { message };
		this.execute("send", args);
		return true;
	}
}
