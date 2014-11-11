package engine.manager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JFrame;

import engine.Directory;
import objects.GameObject;

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
	//private HUD hud;
	private ArrayList<GameObject> hud;
	private ArrayList<GameObject> toAddToHud;
	private ArrayList<GameObject> toRemoveFromHud;

	//Accesors / Modifiers
	
	/**
	 * Gets the window the application is running in
	 * @return The JFrame the application runs in.
	 */
	public JFrame getWindow(){
		return window;
	}
	
	/**
	 * Gets the panel the application is drawing on
	 * @return The panel which is drawing the game
	 */
	public JPanel getPanel(){
		return drawPanel;
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
		return drawPanel.getWidth() / 100.0 * percent;
	}

	/**
	 * Gets a percentage of the height of the screen
	 * @param percent The percent of the height that you want
	 * @return The specified percentage of the screenHeight
	 */
	public double getPercentageHeight(double percent)
	{
		return drawPanel.getHeight() / 100.0 * percent;
	}
	
	/**
	 * Constructs a screenManager
	 */
	public ScreenManager() {
		super();
	}

	/**
	 * Initializes all member variables
	 * Sets window dimensions
	 * Creates panel
	 * Defines draw instructions
	 * Makes call to Input Manager, this Must be called after InputManager is constructed!
	 */
	@Override
	public void init()
	{
		//Set internals
		width = 800;
		height = 600;

		//Initialize hud
		//hud = new HUD();
		hud = new ArrayList<GameObject>();
		toAddToHud = new ArrayList<GameObject>();
		toRemoveFromHud = new ArrayList<GameObject>();

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
				//TODO: But background image stuff here.
				g.setColor(backgroundColor);
				g.fillRect(0, 0, width, height);

				//Cast to graphics2d
				Graphics2D g2d = (Graphics2D)g;

				//Start draw calls
				if(Directory.engine != null && Directory.engine.getCurrentState() != null)
					Directory.engine.getCurrentState().draw(g2d);

				//Draw hud
				//Declare copy list
				ArrayList<GameObject> copy;
				
				copy = new ArrayList<GameObject>(hud);
				for(GameObject hudElement : copy){
					hudElement.draw(g2d);
				}
			}

		};
		

		drawPanel.setPreferredSize(new Dimension(width, height));

		//window.setContentPane(this);
		window.add(drawPanel);

		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the background
		backgroundColor = Color.white;

		//Set input manager
		drawPanel.addKeyListener(Directory.inputManager);
		drawPanel.addMouseListener(Directory.inputManager);
		//Set as a focusable component to detect key presses
		drawPanel.setFocusable(true);
	}

	//Methods
	/**
	 * Queues an object to be Added to the HUD 
	 * @param obj Object to add to hud
	 */
	public void AddObjToHud(GameObject obj){
		toAddToHud.add(obj);
	}

	/**
	 * Queues an object to be removed from the hud
	 * @param obj Object to remove from hud
	 */
	public void RemoveFromHud(GameObject obj){
		toRemoveFromHud.add(obj);
	}

	/**
	 * Updates the screen manager
	 * Repaints the drawPanel
	 */
	@Override
	public void update() {
		drawPanel.repaint();

	}

	/**
	 * Updates every gameObject in the hud
	 * Adds objects to the hud which need to be added
	 * Removes objects from the hud which need to be removed
	 */
	public void updateHud(){
		
		for(GameObject hudElement : hud){
			hudElement.update();
		}

		//Copy toRemove list
		hud.removeAll(toRemoveFromHud);
		toRemoveFromHud.clear();

		//Copy toAdd list
		hud.addAll(toAddToHud);
		toAddToHud.clear();
		
	}
	
	/**
	 * Queues everything to be removed from the hud
	 */
	public void clearHud(){
		toRemoveFromHud.clear();
		toAddToHud.clear();
		toRemoveFromHud.addAll(hud);
	}
}
