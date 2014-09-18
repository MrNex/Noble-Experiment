package Equations;


abstract public class OperatorExpression extends Expression{

	protected Expression exp1, exp2;
	
	public OperatorExpression(int r) {
		super(r);
		
	}
	
	public void setE1(Expression e1){
		exp1 = e1;
	}
	
	public void setE2(Expression e2){
		exp2 = e2;
	}

}
