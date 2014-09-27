package Objects.ObjStates;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Engine.Directory;
import MathHelp.Vector;
import Objects.*;

//Projectile state defines ai which seek over time and try to hit a target entity
public class ProjectileState extends ObjState{

	//Attributes
	//TODO: Find way to make this slower!
	static double speed = .05;		//speed in px/s
	Vector heading, initialPosition;
	double time, distance;
	Entity target;
	long timeStart;
	
	public ProjectileState(Entity x) {
		//X marks the spot
		target = x;
	
	}

	@Override
	public void enter() {
		//Determine the amount of miliseconds until this projectile hits it's target
		//Find distance from this obj to target
		//Find vector from this obj to target
		Vector direction = new Vector(target.getPos());
		direction.subtract(attachedTo.getPos());
		
		//Find the magnitude of this vector and store as distance.
		distance = direction.getMag();
		
		//Find the time it will take to reach target
		//v = x/t, vt = x, x/v = t
		time = (distance / speed) / 1000;
		System.out.println(time);
		
		
		
		//Normalize direction vector and set as heading
		direction.normalize();
		heading = direction;
		
		//Set initial Position
		initialPosition = attachedTo.getPos();
		
		timeStart = System.currentTimeMillis();
		
		//If running is not true, set obj to running
		if(!attachedTo.isRunning()){
			attachedTo.setRunning(true);
		}
		
		//Start the timer
		//timer.start();
	}

	@Override
	public void update() {
		//iP + tM = fP
		
		//GEt elapsed time since "firing" in seconds
		double elapsedTime = (System.currentTimeMillis() - timeStart) / 1000.0;
		
		//Set position to initialPosition + heading * distance(elapsedTime/time) to get current Position
		attachedTo.setPos(Vector.add(initialPosition, Vector.setMag(heading, distance * (elapsedTime/time))));
		
		if(attachedTo.isColliding(target))
		{
			target.decrementCurrentHealth(((Entity)attachedTo).getPower());
			
			System.out.println("Hurt");
			
			Directory.engine.getCurrentState().removeObj(attachedTo);
			attachedTo.setRunning(false);
			attachedTo.setVisible(false);
		}
		
		//Update the objects shape
		attachedTo.updateShape();
	}

	@Override
	public void exit() {
		
	}

}
