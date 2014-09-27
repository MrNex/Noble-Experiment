package Objects;

import java.awt.Color;
import java.util.Random;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Engine.Directory;
import Engine.States.BattleState;  //Temp
import Equations.Equation;

public class EquationObject extends GameObject{
	
	private static double yOffset = -50;
	private Equation currentEq;
	private Entity following;
	private boolean selected;
	private double padX, padY;
	
	public EquationObject(Entity toFollow) {
		super(toFollow.getXPos(), toFollow.getYPos() + yOffset, 0, 0);
		//Set the destructable this obj is following
		following = toFollow;
		
		//Set current equation
		if(toFollow.isEquation){
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
		setShape(new Rectangle2D.Double(position.getComponent(0), position.getComponent(1) + yOffset, width + padX, height + padY), Color.red);
		//Set visible
		setVisible(true);
		//Set to running
		setRunning(true);
	}
	
	//Sets this equations position above the object it is following
	@Override
	public void update(){
		//Set position to the destructable this obj is following
		position.setComponent(0, following.getXPos());
		position.setComponent(1, following.getYPos() + yOffset);
		
		//Update the shape
		updateShape();
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
		if(following.holdsEquation())
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
