/*
 * OCM_MultiReceptacle.java
 *
 * Created on 23 July 2004, 13:52
 */

package OpenCOM;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Programming abstraction for a multi-receptacle with parallel invocations.
 * Multiple components all implementing the same interface type can be connected
 * to this receptacle. When invoked each connection executes in a separate
 * thread. Note, there are no return values; hence void methods are appropriate.
 * We advocate the use of callbacks to handle the return of results from
 * multiple executing methods.
 * 
 * <p>
 * public OCM_MultiReceptacleParallel<IInterfaceType> m_PSR_IIntfType = new
 * OCM_MultiReceptacleParallel<IInterfaceType>(IInterfaceType.class);
 * <p>
 * m_PSR_IIntfType.m_pIntf.foo(params);
 * 
 * @author Paul Grace
 * @version 1.2.3
 */

public class OCM_MultiReceptacleParallel<InterfaceType> implements IReceptacle {

	class DebugProxy implements java.lang.reflect.InvocationHandler {

		class invocationThread extends Thread {
			int index;
			Method method;
			Object[] args;
			ICallback callback;

			public invocationThread(ThreadGroup group, int index, Method m,
					Object[] arguments, ICallback callback) {
				super(group, "Thread" + index);
				this.index = index;
				this.method = m;
				this.args = arguments;
				this.callback = callback;
			}

			public void run() {
				try {
					Object object = interfaceList.get(index);
					Object result = method.invoke(object, args);
					callback.sendResult(new CallbackResult(object, result));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private Object obj;
		private ICallback callback;

		public Object newInstance(Object obj, ICallback callback) {
			return java.lang.reflect.Proxy.newProxyInstance(obj.getClass()
					.getClassLoader(), obj.getClass().getInterfaces(),
					new DebugProxy(obj, callback));
		}

		private DebugProxy(Object obj, ICallback callback) {
			this.obj = obj;
			this.callback = callback;
		}

		public Object invoke(Object proxy, Method m, Object[] args)
				throws Throwable {
			Object result = null;
			try {
				ThreadGroup group = new ThreadGroup("ThreadGroup");
				for (int i = 0; i < interfaceList.size(); i++) {
					invocationThread newThr = new invocationThread(group, i, m,
							args, callback);
					newThr.start();
					while (group.activeCount() != 0) {
						Thread.sleep(1000);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException("unexpected invocation exception: "
						+ e.getMessage());
			}
			return result;
		}
	}

	/* The reference point of the interface this receptacle is connected to. */
	public Class class_type;
	public InterfaceType m_pIntf;

	/** List of interface pointers this receptacle is connected to. */
	private Vector<Object> interfaceList;

	/** List of connIDS for each connection of this receptacle. */
	private Vector<Long> connIDS;

	private int numberOfConnections;
	private Hashtable<String, TypedAttribute> metaData; // Meta-data stored on

	// this receptacle

	/**
	 * Constructor creates a new instance of OCM_MultiReceptacle object. Usually
	 * called from within OpenCOM component constructors.
	 * 
	 * @param interfaceType
	 *            The type of interface to initialse this receptacle to
	 */
	public OCM_MultiReceptacleParallel(Class<InterfaceType> cls_type,
			ICallback callback) {
		interfaceList = new Vector<Object>();
		connIDS = new Vector<Long>();
		numberOfConnections = 0;
		class_type = cls_type;
		ClassLoader cl2 = cls_type.getClassLoader();
		m_pIntf = (InterfaceType) Proxy.newProxyInstance(cl2,
				new Class[] { cls_type }, new DebugProxy((InterfaceType) this,
						callback));
	}

	// ! Implementation of IReceptacle interface
	// //////////////////////////////////////////////////////////////////////////////
	/**
	 * This method connects the recpetacle to given component on the given
	 * interface type.
	 * 
	 * @param pIUnkSink
	 *            Reference to the sink component who hosts the interface that
	 *            the receptacle is to be connected to.
	 * @param riid
	 *            A string representing the interface type of the connection.
	 * @provConID A long representing the generated unqiue ID of this particular
	 *            connection.
	 * @return A boolean indicating the success of this operation
	 **/
	public boolean connectToRecp(IUnknown pIUnkSink, String riid,
			long provConnID) {
		// Get the reference to the component hosting the interface
		try {
			InterfaceType pIntfRef = (InterfaceType) pIUnkSink
					.QueryInterface(riid);
			interfaceList.add(pIntfRef);
		} catch (ClassCastException e) {
			System.err
					.println("Connect Failed: Connecting Receptacle and Interface of different types");
			return false;
		}

		// Add the component, reference and id to the receptacles object stores
		connIDS.add(new Long(provConnID));

		numberOfConnections++;
		return true;
	}

	/**
	 * This method disconnects a given receptacle
	 * 
	 * @provConID A long representing the generated unqiue ID of this particular
	 *            connection.
	 * @return A boolean indicating the success of this operation
	 **/
	public boolean disconnectFromRecp(long connID) {
		// Traverse the receptacle data looking for the required connection ID
		for (int i = 0; i < numberOfConnections; i++) {
			Long vecConnID = connIDS.elementAt(i);
			if (vecConnID.longValue() == connID) {
				// Found it - now remove all pieces of information about that
				// connection
				numberOfConnections--;
				interfaceList.remove(i);
				connIDS.remove(i);
			}
			if (numberOfConnections == 0) {
				m_pIntf = null;
				return true;
			}
			return true;
		}

		return false;
	}

	/**
	 * This method adds meta-data name-value pair attributes to the receptacle
	 * instance.
	 * 
	 * @param name
	 *            The attribute name.
	 * @param type
	 *            The attribute name.
	 * @param value
	 *            An Object holding the attribute value.
	 * @return A boolean describing if the pair was added or not.
	 */
	public boolean putData(String name, String type, Object value) {
		try {
			TypedAttribute aNewAttr = new TypedAttribute(type, value);
			metaData.put(name, aNewAttr);
		} catch (NullPointerException n) {
			return false;
		}
		return true;
	}

	/**
	 * This method gets the value of a named meta-data attribute.
	 * 
	 * @param name
	 *            The attribute name.
	 * @return The TypedAttribute object storing the value.
	 */
	public TypedAttribute getValue(String name) {
		return (TypedAttribute) metaData.get(name);
	}

	/**
	 * This method returns all name-value meta-data pairs on this receptacle
	 * instance.
	 * 
	 * @return A Hashtable storing the pairs.
	 */
	public Hashtable getValues() {
		return metaData;
	}

}
