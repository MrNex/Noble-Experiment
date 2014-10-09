package Engine;
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

import Engine.States.*;
import Objects.GameObject;

public class ScreenManager extends JPanel {

	private JFrame window;
	private JPanel drawPanel;
	private Color backgroundColor;
	private int width, height;
	private HUD hud;
	
	public ScreenManager() {
		init();
	}
	
	public void init()
	{
		//Set internals
		width = 800;
		height = 600;
		
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
				//Refresh screen
				super.setBackground(null);
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
	
	//Gets the graphics renderer of drawPanel
	public Graphics getGraphics(){
		return drawPanel.getGraphics();
	}
	


	public double getPercentageWidth(double percent)
	{
		return width / 100.0 * percent;
	}
	
	public double getPercentageHeight(double percent)
	{
		return height / 100.0 * percent;
	}
}
