package FlexFT;

import java.lang.reflect.ParameterizedType;

import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import OpenCOM.OpenCOMComponent;

public abstract class Context<T> extends OpenCOMComponent implements
		IUnknown, IMetaInterface, ILifeCycle {

	Class<?> tClass;
	String className;

	@SuppressWarnings("unchecked")
	public Context(IUnknown pRuntime) {
		super(pRuntime);
		tClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		className = this.getClass().getName();
	}

	public final boolean startup(Object pIOCM) {
		SetAttributeValue(tClass.getName(), "Interface", "className", "String",
				className);
		return true;
	}

	public final boolean shutdown() {
		return true;
	}
}