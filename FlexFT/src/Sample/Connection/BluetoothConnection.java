package Sample.Connection;

import FlexFT.State;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;

public class BluetoothConnection extends State<IConnection> implements
		IUnknown, IConnection, IMetaInterface, ILifeCycle {

	public BluetoothConnection(IUnknown pRuntime) {
		super(pRuntime);
	}

	public void send(String message) {
		System.out.println("[" + this + "] executando send => " + message);
	}
}
