package equations;


public class SubtractionExpression extends OperatorExpression{

	public SubtractionExpression() {
		super(1);
	}

	@Override
	public double evaluate() {
		// TODO Auto-generated method stub
		return exp1.evaluate() - exp2.evaluate();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " - ";
	}

}
