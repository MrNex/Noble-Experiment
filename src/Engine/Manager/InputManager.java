package Engine.Manager;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Engine.Directory;
import MathHelp.Vector;

/**
 * Defines a component of the engine which manages, observes, and records keyboard input
 * Only records key input of keys who's ascii values are between 0 and 256.
 * 
 * @author Nex
 *
 */
public class InputManager extends Manager implements KeyListener, MouseListener{

	//Attributes
	private Queue<Integer>keysPressed;		//Holds the keys pressed this game loop in the order of being pressed
	private boolean keys[];					//Indicates whether any key code is currently pressed
	private boolean mButtons[];
	private Vector mousePosition;
	private Vector previousMousePosition;

	//Accessors
	/**
	 * Get the mouses current position in window space
	 * @return A vector storing the mouses position in window space
	 */
	public Vector getMousePosition(){
		return mousePosition;
	}


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
		keysPressed = new LinkedList<Integer>();

		keys = new boolean[256];

		mButtons = new boolean[MouseInfo.getNumberOfButtons()];

		mousePosition = new Vector(2);
		previousMousePosition = new Vector(2);
	}

	/**
	 * Dequeues the next key pressed from the queue of keyPresses and returns it
	 * @return The keycode of most recent keypress that hasn't been retrieved yet.
	 */
	public Integer getNextKeyPressed(){
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
	 * Checks if a certain keycode is being pressed
	 * Case sensitive
	 * @param keyCode Keycode of key to check
	 * @return Whether the key with the corresponding keycode is pressed
	 */
	public boolean isKeyPressed(int keyCode){
		return keys[keyCode];
	}

	/**
	 * Gets whether a mouse button is pressed.
	 * 1 - Left mouse button
	 * 2 - Middle mouse button
	 * 3 - Right mouse button
	 * @param mouseButton Mouse button to check
	 * @return If the specified mouse button is pressed
	 */
	public boolean isMouseButtonPressed(int mouseButton){
		return mButtons[mouseButton];
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
			keysPressed.add(keyPress.getKeyCode());
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
	 * Updates the mouse position in window space
	 */
	@Override
	public void update() {
		previousMousePosition.copy(mousePosition);
		//mousePosition = getUpdatedMousePosition();

		Point mousePos = Directory.screenManager.getPanel().getMousePosition();
		if(mousePos != null){
			mousePosition.setComponent(0, mousePos.x);
			mousePosition.setComponent(1, mousePos.y);
		}


	}

	/**
	 * Gets the current mouse position in window space
	 * @return A vector containing the mouse positionin window space
	 */
	private Vector getUpdatedMousePosition(){
		//Get the mouse's screen position
		Point globalMousePos = MouseInfo.getPointerInfo().getLocation();
		//Retrieve reference to screen manager
		ScreenManager ref = (ScreenManager)Directory.screenManager;
		//Retrieve windows position
		Point windowPos = ref.getWindow().getLocation();

		//Create a vector for the mouse position
		Vector relMousePos = new Vector(2);
		relMousePos.setComponent(0, globalMousePos.x - windowPos.x);
		relMousePos.setComponent(1, globalMousePos.y - windowPos.y);

		return relMousePos;
	}

	/**
	 * Not currently in use
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Not currently in use
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Not currently in use
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	/**
	 * Registers a mouse button press and stores true under the correct index of mouse button presses
	 */
	@Override
	public void mousePressed(MouseEvent mousePress) {
		mButtons[mousePress.getButton()] = true;

	}

	/**
	 * Registers a mouse button release and stores false under the correct index of mouse button presses
	 */
	@Override
	public void mouseReleased(MouseEvent mouseRelease) {
		mButtons[mouseRelease.getButton()] = false;

	}

}
