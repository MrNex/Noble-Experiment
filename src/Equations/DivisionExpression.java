package Equations;


public class DivisionExpression extends OperatorExpression{

	public DivisionExpression() {
		super(2);
	}

	@Override
	public double evaluate() {
		// TODO Auto-generated method stub
		return exp1.evaluate() / exp2.evaluate();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " / ";
	}

}
