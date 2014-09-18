package Engine.States;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

abstract public interface InputState {
	
	
	abstract public void keyPressed(KeyEvent keyPress);
	abstract public void keyReleased(KeyEvent keyRelease);
	abstract public boolean isKeyPressed(char key);
	abstract public ArrayList<Character> getKeysPressed();

}
