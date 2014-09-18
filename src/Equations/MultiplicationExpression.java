package Equations;


public class MultiplicationExpression extends OperatorExpression{

	public MultiplicationExpression() {
		super(2);
	}

	@Override
	public double evaluate() {
		// TODO Auto-generated method stub
		return exp1.evaluate() * exp2.evaluate();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " * ";
	}

}
