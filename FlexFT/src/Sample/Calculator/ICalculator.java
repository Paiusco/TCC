package Sample.Calculator;

import OpenCOM.IUnknown;

public interface ICalculator extends IUnknown {
	public Integer add(Integer x, Integer y);

	public Integer subtract(Integer x, Integer y);

	public Integer multiply(Integer x, Integer y);
	
	public Integer divide(Integer x, Integer y);
}
