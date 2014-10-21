package Engine.Manager;
import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

import Objects.GameObject;
import Engine.Directory;


public class HUD {
	//private List<GameObject> hudObjects= new ArrayList<GameObject>();
	public GameObject healthBar;
	
	public HUD() {
		init();
	}
	
	public void init()
	{
		initHealthBar();
	}
	
	private void initHealthBar()
	{
		healthBar = new GameObject(5,15,250,25);
		healthBar.setShape(new Rectangle2D.Double());
		healthBar.setColor(Color.green);
		healthBar.setVisible(true);
	}
	
	// Update HealthBar based on player health
	public void updateHealth()
	{
		healthBar.setWidth(Directory.profile.getPlayer().getCurrentHealth() * 10);
		healthBar.updateShape();
	}
}
