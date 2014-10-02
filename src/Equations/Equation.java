package Equations;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;


public class Equation {

	//Attributes
	private double solution;
	private ArrayList<Expression> expList;
	private Stack<Expression> expStack;
	
	public Equation(ArrayList<Expression> exps) throws InvalidEquationException {
		//Create expression list and stack
		expList = new ArrayList<Expression>(exps);
		expStack = new Stack<Expression>();
		
		//Solve
		solution = parseList();
	}
	
	public Equation(int val) {
		//Create expression list and stack
		expList = new ArrayList<Expression>();
		expStack = new Stack<Expression>();
		
		expList.add(new NumericalExpression(val));
		
		//solve
		try{
			solution = parseList();
		}
		catch(InvalidEquationException iEE)
		{
			//IDeally this catch should never be triggered. Equation is a single number. Really hard to make a number invalid.
			System.out.println("From int constructor of Equation: " + iEE.getMessage());
		}
		
		
	}
	
	public double getSolution(){
		return solution;
	}
	
	///Gets a copy of the expression list for this equation
	public ArrayList<Expression>getExpressionList()
	{
		return new ArrayList<Expression>(expList);
	}
	
	//Loop through the expression list filling the expStack and solving when ready
	//REturns solution to equation
	private double parseList() throws InvalidEquationException{
		//Create a variable to keep track of last relevant rank
		int lastRank = 0;
		

		//For each index of the list
		for(int i = 0; i < expList.size(); i++){
			//If the next rank is 0 it is a numerical expression and should just be added without question
			if(expList.get(i).getRank() == 0){
				//Add to stack
				expStack.push(expList.get(i));
			}
			//else If the nextRank in the list is greater than the last rank add it to the stack
			else if(expList.get(i).getRank() > lastRank){
				//Add to stack
				expStack.push(expList.get(i));
				//Track the lastRank
				lastRank = expList.get(i).getRank();
			}
			//Else, the nextRank is less than or equal to the lastRank
			else{
				//Set the lastRank
				lastRank = solveStack(expList.get(i).getRank());
				
				//Decrement i to re-visit this expression
				i--;
			}
			
		}
		
		//If the stack contains more than 1 thing, solve it
		if(expStack.size() > 1)
			solveStack(0);
		
		try{
			//Return the final numericalExpression's value on the stack
			return expStack.get(0).evaluate();
		}
		catch(NullPointerException nPE){
			throw new InvalidEquationException();
		}
		
	}
	
	//Throws invalid Equation exception if equation is invalid
	//Solves the current expression stack until the addition of the nextRank to the expStack is permitted
	private int solveStack(int nextRank) throws InvalidEquationException {
		//Create variables for the current section of equation
		NumericalExpression e2 = null;
		NumericalExpression e1 = null;
		OperatorExpression op = null;
		
		try{
			//Pop the first numerical expression off stack (rhs)
			e2 = (NumericalExpression)expStack.pop();
			
			//Pop the operatorExpression off the stack
			op = (OperatorExpression)expStack.pop();
			
			//Pop the second numrical expression off stack
			e1 = (NumericalExpression)expStack.pop();
		}
		catch(ClassCastException cCE){
			//If any one of these expressons is not what we expect, this is an invalid equation
			throw new InvalidEquationException();
		}
		catch(EmptyStackException eSE){
			//If any one of these expressions isn't there, this is an invalid equation.
			throw new InvalidEquationException();
		}
		
		
		
		//Set operator expressions
		op.setE1(e1);
		op.setE2(e2);
		
		//Evaluate the operator
		double result = op.evaluate();
		
		//Store the new lastRAnk or 0 if there is not one
		int lastRank = expStack.size() > 0 ? expStack.get(expStack.size() - 1).getRank() : 0;
		
		//Store the result as a numericExpression on the stack
		expStack.push(new NumericalExpression(result));
		
		//If the new lastRank is still greater or equal to the nextRank we must continue solving and then return the result
		if(expStack.size() > 1 || lastRank > nextRank)
			return solveStack(nextRank);
		else
			return lastRank;
	}
	
	//GEnerates a random equation containing the specified number of operators and returns a
	//new equation. If an InvalidEquationException is thrown while trying to generate an equation (which shouldn't happen)
	//A message is printed and the process is recursively tried again.
	public static Equation GenRndEquation(int numOperators){
		//Create arrayList of expressions
		ArrayList<Expression> eList = new ArrayList<Expression>();
		//Create a random
		Random rnd = new Random();
		
		//Add a first numerical expression to equationList (First thing in an equation must be a number)
		eList.add(new NumericalExpression(rnd.nextInt(10) + 1));
		
		//For each proceeding operator
		for(int i = 0; i < numOperators; i++){
			
			//Choose an operator
			int opChoice = rnd.nextInt(4);
			
			//Declare a variable for the operator expression
			OperatorExpression o = null;
			
			
			
			//Declare variable for second random numerical expression
			NumericalExpression e2 = null;
			
			//Add that operator to the list
			switch(opChoice){
			case 0:
				//SEt operator
				o = new AdditionExpression();
				break;
			case 1:
				//Set operator
				o = new SubtractionExpression();
				break;
			case 2:
				//SEt operator
				o = new MultiplicationExpression();
				break;
			case 3:
				//If division, find every factor of the first numerical expression
				ArrayList<Integer> validDivisors = new ArrayList<Integer>();

				for(int j = 1; j <= eList.get(eList.size() - 1).evaluate(); j++){
					if(eList.get(eList.size() - 1).evaluate() % j == 0){
						//If e1 % j == 0, j is a factor of e1 and should be added to the list
						validDivisors.add(j);
					}
				}
				
				//Set o
				o = new DivisionExpression();
				
				//CHoose random e2
				int randE2Index = rnd.nextInt(validDivisors.size());
				//Create e2
				e2 = new NumericalExpression(validDivisors.get(randE2Index));
				
				break;
			default:
				//Shit went down
				break;
			}
			
			//Create e2 (if not already created)
			if(e2 == null){
				e2 = new NumericalExpression(rnd.nextInt(10));
			}
			
			//Add operator
			eList.add(o);
			
			//Add a final numericalexpression
			eList.add(e2);
		}
		
		//Declare a returnEquation
		Equation returnEquation;
		try{
			returnEquation = new Equation(eList);
		}
		catch(InvalidEquationException iEE){
			//If the equation is invalid, make another?
			System.out.println("From generating a random equation: " + iEE.getMessage());
			returnEquation = GenRndEquation(numOperators);
		}
		
		//Return a put together equation
		return returnEquation;
	}
	
	public String toString(){
		String returnString = "";
		for(Expression e : expList){
			returnString += e.toString();
		}
		
		return returnString;
	}
	
	
}