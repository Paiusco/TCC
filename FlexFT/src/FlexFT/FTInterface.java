package FlexFT;

public interface FTInterface {
	Object execute(String name, Object... args)
			throws FTException;
}
