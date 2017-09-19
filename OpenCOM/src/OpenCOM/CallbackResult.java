package OpenCOM;

public class CallbackResult {

	final private Object from;
	
	final private Object value;

	public CallbackResult(Object from, Object value) {
		super();
		this.from = from;
		this.value = value;
	}

	public Object getFrom() {
		return from;
	}

	public Object getValue() {
		return value;
	}
	
	
}
