package Engine.Manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Defines a component of the engine which manages, observes, and records keyboard input
 * Only records key input of keys who's ascii values are between 0 and 256.
 * 
 * @author Nex
 *
 */
public class InputManager extends Manager implements KeyListener{

	//Attributes
	private Queue<Character>keysPressed;		//Holds the keys pressed this game loop in the order of being pressed
	private boolean keys[];						//Indicates whether any key code is currently pressed


	/**
	 * Constructs an input manager
	 */
	public InputManager() {
		super();
	}

	/**
	 * Initializes any member variables
	 */
	@Override
	public void init() {
		//Initialize array (lists)
		keysPressed = new LinkedList<Character>();

		keys = new boolean[256];
	}

	/**
	 * Dequeues the next key pressed from the queue of keyPresses and returns it
	 * @return The most recent keypress that hasn't been retrieved yet.
	 */
	public Character getNextKeyPressed(){
		return keysPressed.poll();
	}


	/**
	 * Checks if a current key is pressed.
	 * Case insensitive
	 * @param key Key to check
	 * @return Status of key (true - pressed || False - unpressed)
	 */
	public boolean isKeyPressed(char key){
		return keys[(int)Character.toLowerCase(key)] || keys[(int)Character.toUpperCase(key)];
	}

	/**
	 * Clear queue of presses 
	 */
	public void clear(){
		keysPressed.clear();
	}

	/**
	 * When a key is pressed, a KeyEvent is passed here for the keypress to be recorded.
	 * Records keyPress as both marking the ascii value of the key true in the array of key states,
	 * And adds the character of the key to a queue of keyPresses (only if it was just pressed, not also pressed previous loop)
	 */
	@Override
	public void keyPressed(KeyEvent keyPress) 
	{
		//If this key was not already pressed
		if(!keys[keyPress.getKeyCode()]){
			//Set keys at index of keyCode true
			keys[keyPress.getKeyCode()] = true;
			//Add this key to the list of keys pressed this loop
			keysPressed.add(keyPress.getKeyChar());
		}
	}

	/**
	 * When a key is released, a keyRelease event is passed here for the keyrelease to
	 * be recorded. This records the keyPress by marking the ascii value of the key
	 * as false in the array of keyPResses.
	 */
	@Override
	public void keyReleased(KeyEvent keyRelease) {
		//If this key was already pressed
		if(keys[keyRelease.getKeyCode()]){
			//Set keys at index of keyCode false
			keys[keyRelease.getKeyCode()] = false;
		}

	}

	/**
	 * Unused.
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		//Dunno what I'd ever use this for.
	}

	/**
	 * Unused.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
