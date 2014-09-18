package Equations;


public class AdditionExpression extends OperatorExpression{

	public AdditionExpression() {
		super(1);
	}

	@Override
	public double evaluate() {
		return exp1.evaluate() + exp2.evaluate();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " + ";
	}

}
