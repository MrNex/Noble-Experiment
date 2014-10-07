package Equations;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;


public class Equation {

	//Attributes
	private double solution;
	private ArrayList<Expression> expList;
	private Stack<Expression> expStack;
	private int numOp = 0;
	private int numAddOp = 0;
	private int numSubOp = 0;
	private int numMulOp = 0;
	private int numDivOp = 0;
	
	public Equation(ArrayList<Expression> exps) throws InvalidEquationException {
		//Create expression list and stack
		expList = new ArrayList<Expression>(exps);
		expStack = new Stack<Expression>();

		//Loop through expressions to count operators
		for(Expression e : expList){
			//Tell what type of expression e is
			if(e instanceof OperatorExpression){
				//If it's an operator, increment the number of operators
				numOp++;
				//Decide what type of operator and increment that counter
				if(e instanceof AdditionExpression){
					numAddOp++;
				}else if(e instanceof SubtractionExpression){
					numSubOp++;
				}else if(e instanceof MultiplicationExpression){
					numMulOp++;
				}else if(e instanceof DivisionExpression){
					numDivOp++;
				}
			}
		}
		
		//Solve
		solution = parseList();
	}

	/**
	 * Constructs an equation which only contains 1 numerical expression.
	 * @param val Value of the numerical expression this equation wrapper will contain
	 */
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
	
	//Accessors

	/**
	 * Gets the solution
	 * If an equation only contains 1 numerical expression the solution is the value of that expression
	 * @return Solution to the equation
	 */
	public double getSolution(){
		return solution;
	}
	
	/**
	 * Gets the number of addition operators in the equation
	 * @return the number of addition operators this equation contains
	 */
	public int getAdditionOperators(){
		return numAddOp;
	}
	
	/**
	 * Gets the number of subtraction operators in the equation
	 * @return the number of subtraction operators this equation contains
	 */
	public int getSubtractionOperators(){
		return numSubOp;
	}
	
	/**
	 * Gets the number of multiplication operators in the equation
	 * @return the number of multiplication operators this equation contains
	 */
	public int getMultiplicationOperators(){
		return numMulOp;
	}
	
	/**
	 * Gets the number of division operators in the equation
	 * @return the number of division operators this equation contains
	 */
	public int getDivisionOperators(){
		return numDivOp;
	}
	
	/**
	 * Gets the number of operators in the equation
	 * @return the number of operators this equation contains
	 */
	public int getNumOperators(){
		return numOp;
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
		catch(ArrayIndexOutOfBoundsException e){
			throw new InvalidEquationException();
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

	/**
	 * Parses an equation from a string and returns that equation.
	 * Equations do follow order of operations.
	 * Whitespace in string is ignored.
	 * @param strEquation String containing equation. Allowed characters include: +, -, *, /, and any digit.
	 * @return Equation containing the contents of strEquation
	 * @throws InvalidEquationException if string is not a proper equation (ie: 5 + - 6 //, or other such nonsense)
	 */
	public static Equation parseEquation(String strEquation) throws InvalidEquationException{
		//Create a list of expressions
		ArrayList<Expression> expressionList = new ArrayList<Expression>();

		//Create a queue of integers
		LinkedList<Integer> digits = new LinkedList<Integer>();
		//for each character in the answer string
		for(int i = 0; i < strEquation.length(); i++){
			if((Character.isDigit(strEquation.charAt(i)))){
				String digit = strEquation.substring(i, i+1);
				//Add the digit to the queue
				digits.addLast(Integer.parseInt(digit));

				//Make sure the next character is a digit, or there is a next character
				if(strEquation.length() - 1 == i || !Character.isDigit(strEquation.charAt(i+1))){
					//If it's not, get the integer represented by the digits
					int val = ListToInt(digits);

					//Add a numerical expression to the expression list
					expressionList.add(new NumericalExpression(val));
					//Clear the list of digits
					digits.clear();
				}
			}
			else if(strEquation.charAt(i) == '+'){
				expressionList.add(new AdditionExpression());
			}
			else if(strEquation.charAt(i) == '-'){
				expressionList.add(new SubtractionExpression());
			}
			else if(strEquation.charAt(i) == '*'){
				expressionList.add(new MultiplicationExpression());
			}
			else if(strEquation.charAt(i) == '/' || strEquation.charAt(i) == '/'){
				expressionList.add(new DivisionExpression());
			}
		}
		
		return new Equation(expressionList);
	}

	//Takes a linked list of integers representing digits in a number where the first value 
	//has the highest place value decreasing to the lowest in the last spot
	//And turns it into the corresponding integer returning that value
	private static int ListToInt(LinkedList<Integer> digits){
		int sum = 0;
		while(digits.size() > 0){
			int increment = digits.pollFirst();
			if(digits.size() > 0)
				increment *= (digits.size()*10);
			sum += increment;
		}
		return sum;
	}


}