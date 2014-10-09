package Objects;

import java.awt.Color;
import java.util.Random;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Engine.Directory;
import Engine.States.BattleState;
import Equations.Equation;
import Objects.ObjStates.HoverState;
import Objects.ObjStates.ObjBattleState;

public class EquationObject extends GameObject{
	
	private Equation currentEq;
	private Entity following;
	private boolean selected;
	private double padX, padY;
	
	public EquationObject(Entity toFollow) {
		super(toFollow.getXPos(), toFollow.getYPos() - 50, 0, 0);
		//Set the destructable this obj is following
		following = toFollow;
		
		//Set current equation
		if(((ObjBattleState)toFollow.currentState).holdsEquation()){
			currentEq = Equation.GenRndEquation(following.defense);
		}
		else{
			Random rnd = new Random();
			currentEq = new Equation(rnd.nextInt(10 * following.defense));
			
		}
		selected = false;
		
		//Generate the shape for this equationObject
		//First find the width
		//Get font metrics
		FontMetrics fMetrics = Directory.screenManager.getGraphics().getFontMetrics();
		//Set width + Padding
		width = fMetrics.stringWidth(currentEq.toString());
		padX = width / 2;
		width += padX;
		
		//Set height + padding
		height = fMetrics.getMaxAscent() + fMetrics.getMaxDescent();
		padY = height / 2;
		height += padY;
		
		//Create shape
		setShape(new Rectangle2D.Double(position.getComponent(0), position.getComponent(1) - 50, width + padX, height + padY), Color.red);
		//Set visible
		setVisible(true);
		
		//Attach state
		setState(new HoverState(toFollow));
		
		//Set to running
		setRunning(true);
	}

	
	//My attempt at the most confusing line of code I've ever written.
	//Toggles selected boolean and sets the color to green if this equationObject is selected, or red if it's not.
	public void toggleSelected(){ setColor((selected = !selected) ? Color.green : Color.red); }
	
	public boolean isSelected(){
		return selected;
	}
	
	//Draws normally then writes equation if visible
	@Override
	public void draw(Graphics2D g2d){
		if(isVisible()){
			super.draw(g2d);
			g2d.setColor(Color.black);
			g2d.drawString(currentEq.toString(), (int)(position.getComponent(0) + padX / 2), (int)(position.getComponent(1) + height - padY));
		}
	}
	
	public Equation getEquation()
	{
		return currentEq;
	}
	
	
	//Generates a new equation and returns true if correct, else false
	public boolean attemptSolution(double guess){
		if(guess == currentEq.getSolution()){
			
			return true;
		}
		
		return false;
	}
	
	public void generateNewEquation()
	{
		if(((ObjBattleState)following.currentState).holdsEquation())
		{
			currentEq = Equation.GenRndEquation(BattleState.difficulty);
		}
		else
		{
			Random rnd = new Random();
			currentEq = new Equation(rnd.nextInt(10 * following.defense));
		}
	}

}
