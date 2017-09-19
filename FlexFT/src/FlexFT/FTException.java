package FlexFT;

@SuppressWarnings("serial")
public class FTException extends RuntimeException {

	public FTException(String message) {
		super(message);
	}
	
	public FTException(Throwable throwable) {
		super(throwable);
	}
}
