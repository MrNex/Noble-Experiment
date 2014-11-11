package state;

import java.awt.Graphics2D;
import java.util.ArrayList;

import objects.GameObject;

/**
 * Defines a state in a finite state machine.
 * Implementations are used in Object states as well as Engine States
 * @author Nex
 *
 */
public abstract class State {
	
	/**
	 * Constructs the state
	 */
	public State() {
		init();
		
	}
	
	abstract protected void init();
	
	/**
	 * This method is called upon this state being pushed onto a state stack
	 */
	abstract public void enter();
	
	abstract public void update();
	abstract public void draw(Graphics2D g2d);
	
	/**
	 * This method is called when this state is popped from the top of a stateStack
	 */
	abstract public void exit();

}
