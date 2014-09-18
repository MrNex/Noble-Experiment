package Objects.ObjStates;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

import javax.swing.Timer;

import Engine.Directory;
import Objects.Destructable;

public class EnemyState extends ObjState{

	//Attributes
	private static int attackSpeed = 5000;				//SEt all enemies attack speeds to 10 seconds to start
	private Timer attackTimer;
	
	public EnemyState() {
		//Create attackTimer
		attackTimer = new Timer(attackSpeed, new ActionListener(){

			//When the timer is finished
			@Override
			public void actionPerformed(ActionEvent e) {
				//Create a destructable projectile
				Destructable projectile = new Destructable(attachedTo.getXPos(), attachedTo.getYPos(), 50, 20, 1, 1);
				//SEt the shape
				projectile.setShape(new Ellipse2D.Double(), Color.black);
				//Set visible
				projectile.setVisible(true);
				//Set the state
				projectile.setState(new ProjectileState(Directory.profile.getPlayer()));
				//SEt projectile as running
				projectile.setRunning(true);
				//Add projectile to current engine state
				Directory.engine.getCurrentState().addObj(projectile);
			}
		});
		
		//SEt the timer to repeat
		attackTimer.setRepeats(true);
	}

	@Override
	public void enter() {
		//Upon entering this state, start the timer
		attackTimer.start();
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		//Upon exiting this state stop attackTimer
		attackTimer.stop();
		
	}

}
