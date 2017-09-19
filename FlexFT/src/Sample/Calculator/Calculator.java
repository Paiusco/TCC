package Sample.Calculator;

import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OCM_SingleReceptacle;
import OpenCOM.OpenCOMComponent;
import Sample.Add.IAdd;
import Sample.Divide.IDivide;
import Sample.Multiply.IMultiply;
import Sample.Subtract.ISubtract;

public class Calculator extends OpenCOMComponent implements ICalculator,
		IConnections, ILifeCycle, IUnknown, IMetaInterface {

	public OCM_SingleReceptacle<IAdd> add;

	public OCM_SingleReceptacle<ISubtract> subtract;

	public OCM_SingleReceptacle<IMultiply> multiply;

	public OCM_SingleReceptacle<IDivide> divide;

	public Calculator(IUnknown binder) {
		super(binder);
		add = new OCM_SingleReceptacle<IAdd>(IAdd.class);
		subtract = new OCM_SingleReceptacle<ISubtract>(ISubtract.class);
		multiply = new OCM_SingleReceptacle<IMultiply>(IMultiply.class);
		divide = new OCM_SingleReceptacle<IDivide>(IDivide.class);
	}

	public Integer add(Integer a, Integer b) {
		return (Integer) add.m_pIntf.add(a, b);
	}

	public Integer subtract(Integer a, Integer b) {
		return (Integer) subtract.m_pIntf.subtract(a, b);
	}

	public Integer multiply(Integer a, Integer b) {
		return (Integer) multiply.m_pIntf.multiply(a, b);
	}

	public Integer divide(Integer a, Integer b) {
		return (Integer) divide.m_pIntf.divide(a, b);
	}

	public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
		if (riid.equalsIgnoreCase(IAdd.class.getName())) {
			return add.connectToRecp(pSinkIntf, riid, provConnID);
		} else if (riid.equalsIgnoreCase(ISubtract.class.getName())) {
			return subtract.connectToRecp(pSinkIntf, riid, provConnID);
		} else if (riid.equalsIgnoreCase(IMultiply.class.getName())) {
			return multiply.connectToRecp(pSinkIntf, riid, provConnID);
		} else if (riid.equalsIgnoreCase(IDivide.class.getName())) {
			return divide.connectToRecp(pSinkIntf, riid, provConnID);
		}
		return false;
	}

	public boolean disconnect(String riid, long connID) {

		if (riid.equalsIgnoreCase(IAdd.class.getName())) {
			return add.disconnectFromRecp(connID);
		} else if (riid.equalsIgnoreCase(ISubtract.class.getName())) {
			return subtract.disconnectFromRecp(connID);
		} else if (riid.equalsIgnoreCase(IMultiply.class.getName())) {
			return multiply.disconnectFromRecp(connID);
		} else if (riid.equalsIgnoreCase(IDivide.class.getName())) {
			return divide.disconnectFromRecp(connID);
		}
		return false;
	}

	// ILifeCycle Interface
	public boolean shutdown() {
		return true;
	}

	public boolean startup(Object pIOCM) {
		return true;
	}
}