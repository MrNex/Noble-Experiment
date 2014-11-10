package state.object;
import interfaces.Delegate;

import java.awt.Color;
import java.awt.Graphics2D;

import state.object.movable.entity.PlayerBattleState;
import Engine.Directory;

abstract public class OperatorDisplayState extends ObjectState implements Delegate<Integer>{

	
	static protected int maxFill = 25;
	static private Color fillColor = Color.green;
	static private Color fontColor = Color.white;
	
	
	//Attributes
	private String label;
	private double width;
	private double height;
	
	public OperatorDisplayState(String display) {
		super();
		
		label = display;
	}

	@Override
	protected void init() {
		if(label == null){
			label = "";
		}
		
		width = 0;
		height = 0;
		
	}

	@Override
	public void enter() {
		//width = calculateWidth();
		//height = attachedTo.getHeight();
	}

	@Override
	public void update() {

		width = attachedTo.getWidth();
		height =  calculateHeight();
	}
	
	private double calculateHeight(){
		//Get the current amount of operators
		int currentOps;
		
		try{
			currentOps = invoke();
		}
		catch(Exception e){
			currentOps = 0;
		}
		
		//If greater than max, set to max
		if(currentOps > maxFill) currentOps = maxFill;
		
		System.out.println(label + ": " + currentOps);
		
		return((double)currentOps / (double)maxFill)*attachedTo.getHeight();
		
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(OperatorDisplayState.fillColor);
		g2d.fillRect((int)(attachedTo.getXPos()), (int)(attachedTo.getYPos() + attachedTo.getHeight() - height), (int)width, (int)height);
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
