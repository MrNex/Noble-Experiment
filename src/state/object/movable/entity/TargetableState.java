package state.object.movable.entity;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import state.object.NestedState;
import Engine.Directory;
import Equations.Equation;
import Objects.Entity;

/**
 * TargetableState describes an entity state which can be targetted.
 * It keeps track of a solvable equation, 
 * accepts submissions, and will draw the equation
 * 
 * Note that this state MUST be placed on an entity
 * @author Nex
 *
 */
public class TargetableState extends NestedState{

	//Attributes
	private Equation equation;
	private double hoverMag;
	private double padX, padY;
	private double width, height;
	private boolean holdsEquation;
	private boolean selected;
	private Rectangle2D equationBox;
	private Color equationColor;

	
	
	//Accessors and mutators
	/**
	 * Gets the attached game object as an entity
	 * @return The attached game object (attachedTo) cast to an entity
	 */
	protected Entity getAttachedEntity(){
		return (Entity)attachedTo;
	}
	
	/**
	 * Gets whether or not this targetable state is selected
	 * @return Is this targetable state selected
	 */
	public boolean isSelected(){
		return selected;
	}
	
	/**
	 * Toggles selected boolean and sets the color to green if this 
	 * targetableState is selected, or red if it's not.
	 */
	public void toggleSelected(){ 
		selected = !selected;
		if(selected){
			equationColor =  Color.green;
		}
		else{ 
			equationColor = Color.red;
		}
	}
	
	/**
	 * GEts the current equation
	 * @return This state's current equation
	 */
	public Equation getEquation(){
		return equation;
	}
	
	/**
	 * Sets the current equation
	 * @param newEq The new equation this state should have
	 */
	public void setEquation(Equation newEq){
		equation = newEq;
	}
	
	/**
	 * Gets whether this targetable state holds equations or numerical values
	 * @return True if state holds equations, false if state holds a single value wrapped in an equation.
	 */
	public boolean holdsEquation(){
		return holdsEquation;
	}
	
	
	
	//Constructors
	/**
	 * Constructs a targetable state
	 * @param holdsEq Does this targetable state hold a solvable equation or a numerical value?
	 */
	public TargetableState(boolean holdsEq) {
		super();
		holdsEquation = holdsEq;


	}

	
	//Methods
	/**
	 * Iitializes all internal member variables
	 */
	@Override
	protected void init() {		
		//Not selected yet
		selected = false;
		equationColor = Color.red;
		
		//SEt the magnitude that the equation will hover above the attached entity
		hoverMag = 50.0;
		//initialize equationbox
		equationBox = new Rectangle2D.Double();
	}
	
	
	/**
	 * Generates a new equation based on the attached object
	 */
	@Override
	public void enter() {
		super.enter();
		
		//If an equation has not been manually assigned create one conforming to attached object
		if(equation == null){
			generateNewEquation();
		}
		else{
			//Else, an equation already exists, measure a box to fit it (USually called by generate new equation)
			measureEquation();
		}
	}

	/**
	 * Updates the equation's box
	 */
	@Override
	public void update() {
		updateEquationBox();

	}

	/**
	 * Draws the effects of a targetableState
	 * Sets the color of the equationBox
	 * Fills in the equationBox
	 * Sets color to black, and draws equation
	 */
	@Override
	public void draw(Graphics2D g2d) {
		//If the equation isn't null
		if(equation != null){
			g2d.setColor(equationColor);
			g2d.fill(equationBox);
			g2d.setColor(Color.black);
			g2d.drawString(equation.toString(),(int)(equationBox.getX() + padX / 2.0), (int)(equationBox.getY() + height - padY));
		}
	}

	
	
	/**
	 * Checks specified answer with actual answer to current equation
	 * @param guess Specified answer to the current equation
	 * @return True if the answer was correct, false if the answer was incorrect.
	 */
	public boolean attemptSolution(double guess){
		if(guess == equation.getSolution()){
			
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Checks a potential solution to see if it is the answer
	 * to the current equation
	 * 
	 * If it is the answer, decrements the attached entities health by pow
	 * If the entity dies, after decrementing health the entity is
	 * set to no longer be running or visible, has it's state set to null, 
	 * and is removed from the current state of the engine.
	 * 
	 * @param answer Equation representing answer to this states current equation
	 * @param pow Damage to deal to the attached entity if answer is correct
	 * @return True if answered correctly, false if answered incorrectly.
	 */
	public boolean submitAnswer(Equation answer, int pow){
		//Check answer
		if(answer.getSolution() == equation.getSolution()){
			//If correct, damage entity by submitted power
			getAttachedEntity().damage(pow);
			
			System.out.println("Damaged!");
			
			//Check if the entity has died
			if(getAttachedEntity().getCurrentHealth() <= 0){
				//Dead targetable is no longer in this state because dead targetable is no longer targetable
				attachedTo.setState(null);
				
				//Dead targetable is no longer visible
				attachedTo.setVisible(false);
				//Remove from the current state of the engine
				Directory.engine.getCurrentState().removeObj(attachedTo);
				
			}
			
			//Return true because correct answer
			return true;
		}
		
		//Return false if wrong
		return false;
	}

	
	/**
	 * Generates a new equation
	 * 
	 * IF this targetable state holds an equation this will generate an equation with
	 * an equal number of operators to that of the attached entities defense.
	 * 
	 * IF this targetable state does not hold an equation this will generate an equation
	 * with a single numerical value between 0 and 10 * the attached entities defense.
	 * 
	 * Makes call to measureEquation once a new equation is generated
	 */
	public void generateNewEquation(){
		//Generate equation
		if(holdsEquation){
			equation = Equation.GenRndEquation(getAttachedEntity().getDefense());
		}
		else{
			Random rnd = new Random();
			equation = new Equation(rnd.nextInt(10 * getAttachedEntity().getDefense()));
		}

		//After an equation is generated, measure it
		measureEquation();
	}

	
	/**
	 * Utilizes font metrics to get proper sizing and padding
	 * on the equation which must hover above the attached obj
	 * 
	 * Once new dimensions are created, makes call to updateEquationBox
	 */
	private void measureEquation(){
		//First find the width
		//Get font metrics
		FontMetrics fMetrics = Directory.screenManager.getGraphics().getFontMetrics();
		//Set width + Padding
		width = fMetrics.stringWidth(equation.toString());
		padX = width / 2;
		width += padX;

		//Set height + padding
		height = fMetrics.getMaxAscent() + fMetrics.getMaxDescent();
		padY = height / 2;
		height += padY;

		//After an equation is measured, update it's box
		updateEquationBox();
	}

	/**
	 * Updates the shape and position of the equationBox to fit the current measurements 
	 * and attached entities position.
	 */
	private void updateEquationBox(){
		equationBox.setFrame((getAttachedEntity().getXPos() + getAttachedEntity().getWidth() / 2) - width/2, getAttachedEntity().getYPos() - hoverMag, width, height);
	}
	
	/**
	 * Exits the current targetableState
	 */
	@Override
	public void exit() {
		super.exit();
		
	}
}
