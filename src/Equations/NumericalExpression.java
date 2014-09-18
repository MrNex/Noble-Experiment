package Equations;

public class NumericalExpression extends Expression{

	double value;
	
	public NumericalExpression(double val) {
		super(0);
		
		//Set value
		value = val;
	}

	@Override
	public double evaluate() {
		//Return the value
		return value;
	}

	@Override
	public String toString() {
		//Return the value as string
		return Double.toString(value);
	}
}