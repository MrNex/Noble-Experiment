package Equations;

abstract public class Expression {

	private int rank;
	
	public Expression(int r) {
		rank = r;
	}
	
	public int getRank()
	{
		return rank;
	}
	
	abstract public double evaluate();
	abstract public String toString();
	
}
