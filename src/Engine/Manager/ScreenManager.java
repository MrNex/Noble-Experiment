package Engine.Manager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

import Engine.Directory;
import Engine.States.*;
import Objects.GameObject;

/**
 * Defines a component of the engine which handles managing the current state 
 * of the screen.
 * @author Nex
 *
 */
public class ScreenManager extends Manager {

	//Attributes
	private JFrame window;
	private JPanel drawPanel;
	private Color backgroundColor;
	private int width, height;
	private HUD hud;
	
	/**
	 * Constructs a screenManager
	 */
	public ScreenManager() {
		super();
	}
	
	/**
	 * Initializes all member variables
	 * Makes call to Input Manager, Must be called after InputManager is constructed!
	 */
	@Override
	public void init()
	{
		//Set internals
		width = 800;
		height = 600;
		
		//Initialize hud
		hud = new HUD();
		
		//Create the window
		window = new JFrame("Mathemancy V 0.1");
		window.setSize(width, height);
		
		//Create the panel
		drawPanel = new JPanel(){

			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				//Paint background
				g.setColor(backgroundColor);
				g.fillRect(0, 0, width, height);
				
				//Cast to graphics2d
				Graphics2D g2d = (Graphics2D)g;
				
				
				
				//Start draw calls
				Directory.engine.getCurrentState().draw(g2d);
				
				if(Directory.engine.getCurrentState() instanceof BattleState){
					hud.healthBar.draw(g2d);
					hud.updateHealth();
				}	
			}
			
		};
		
		drawPanel.setPreferredSize(new Dimension(800, 600));
		
		//window.setContentPane(this);
		window.add(drawPanel);
		
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set the background
		backgroundColor = Color.white;
		
		//Set input manager
		drawPanel.addKeyListener(Directory.inputManager);
		//Set as a focusable component to detect key presses
		drawPanel.setFocusable(true);
	}
	
	/**
	 * Gets the graphics renderer of drawPanel
	 * @return An instance of the graphics renderer in drawPanel.
	 */
	public Graphics getGraphics(){
		return drawPanel.getGraphics();
	}
	


	/**
	 * Gets a percentage of the width of the screen
	 * @param percent The percent you want
	 * @return The specified percentage of the screenWidth
	 */
	public double getPercentageWidth(double percent)
	{
		return width / 100.0 * percent;
	}
	
	/**
	 * Gets a percentage of the height of the screen
	 * @param percent The percent of the height that you want
	 * @return The specified percentage of the screenHeight
	 */
	public double getPercentageHeight(double percent)
	{
		return height / 100.0 * percent;
	}

	/**
	 * Updates the screen manager
	 * Repaints the drawPanel
	 */
	@Override
	public void update() {
		drawPanel.repaint();
		
	}
}
