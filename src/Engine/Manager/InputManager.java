package Engine.Manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class InputManager implements KeyListener{

	//Attributes
	private Queue<Character>keysPressed;		//Holds the keys pressed this game loop in the order of being pressed
	private boolean keys[];						//Indicates whether any key code is currently pressed


	public InputManager() {
		//Initialize array (lists)
		keysPressed = new LinkedList<Character>();

		keys = new boolean[256];
	}

	//Dequeues the next key pressed from the queue of keyPresses and returns it
	public Character getNextKeyPressed(){
		return keysPressed.poll();
	}
	

	//Checks if a current key is pressed
	public boolean isKeyPressed(char key){
		return keys[(int)Character.toLowerCase(key)] || keys[(int)Character.toUpperCase(key)];
	}
	
	//Clear queue of presses 
	public void clear(){
		keysPressed.clear();
	}

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

	@Override
	public void keyReleased(KeyEvent keyRelease) {
		//If this key was already pressed
		if(keys[keyRelease.getKeyCode()]){
			//Set keys at index of keyCode false
			keys[keyRelease.getKeyCode()] = false;
			//Add this key to the list of keys pressed this loop
			//keysReleased.add(keyRelease.getKeyChar());
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//Dunno what I'd ever use this for.
	}
}
