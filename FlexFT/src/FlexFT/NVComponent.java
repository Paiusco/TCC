package FlexFT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import OpenCOM.IConnections;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;

public abstract class NVComponent<T> extends FTComponent<T> implements IConnections,
		ILifeCycle, IUnknown, IMetaInterface, NVInterface {

	public NVComponent(IUnknown binder) {
		super(binder);	
	}

	public Object decide(List<?> results) {
		Map<Object, Integer> map = new HashMap<Object, Integer>();

		for (Object result : results) {
			if (map.containsKey(result)) {
				Integer counter = map.get(result);
				counter++;
				map.put(result, counter);
			} else {
				map.put(result, 1);
			}
		}

		Object[] keys = map.keySet().toArray();
		int maxIndex = 0;
		int maxCount = map.get(keys[maxIndex]);

		for (int i = 0; i < keys.length; i++) {
			if (map.get(keys[i]) > maxCount) {
				maxIndex = i;
				maxCount = map.get(keys[maxIndex]);
			}
		}

		return keys[maxIndex];
	}
}
