package state.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import state.engine.BattleState;
import Engine.Directory;
import Objects.Entity;

/**
 * Class defines the behavior of the HUD object which displays battle results
 * @author Nex
 *
 */
public class BattleResultDisplayState extends ObjectState{
	
	private String heading;
	private String playerStats[];
	private String opponentStats[];
	private String profileStats[];
	
	private Font headingFont;
	private Font statsFont;
	
	private int headingWidth;
	private int headingHeight;
	private int statsHeight;
	
	private boolean victorious;
	
	/**
	 * Constructs the result display state
	 */
	public BattleResultDisplayState() {
		super();
		
	}
	
	/**
	 * Initializes all internal members of this state
	 * Initializes & Writes all content to strings
	 * Measures and stores all relevent font sizes & string dimensions
	 */
	@Override
	protected void init() {

		headingFont = new Font("Serif", Font.BOLD, 48);
		statsFont = new Font("Serif", Font.PLAIN, 24);
		
		//Write heading depending on if the player is still alive
		if(Directory.profile.getPlayer().getCurrentHealth() > 0) heading = "Victory!";
		else heading = "Defeat!";
		
		//Determine the dimensions of the heading
		//Get font metrics
		FontMetrics fMetrics = Directory.screenManager.getGraphics().getFontMetrics(headingFont);
		headingWidth = fMetrics.stringWidth(heading);
		headingHeight = fMetrics.getMaxAscent() + fMetrics.getMaxDescent();
		
		//Determine height of each line of stats
		fMetrics = Directory.screenManager.getGraphics().getFontMetrics(statsFont);
		statsHeight = fMetrics.getMaxAscent() + fMetrics.getMaxDescent();
		
		//Declare player stats
		playerStats = new String[4];
		
		//Write player's stats
		playerStats[0] = "Player Stats:";
		playerStats[1] = "Power:\t" + Directory.profile.getPlayer().getPower();
		playerStats[2] = "Health:\t" + Directory.profile.getPlayer().getTotalHealth();
		playerStats[3] = "Defense:\t" + Directory.profile.getPlayer().getDefense();
		
		//Get reference to opponent
		Entity opponent = ((BattleState)Directory.engine.getCurrentState()).getCompetitor2();
		
		//Declare opponent stats
		opponentStats = new String[4];
		//Write opponent stats
		opponentStats[0] = "Opponent Stats:";
		opponentStats[1] = "Power:\t" + opponent.getPower();
		opponentStats[2] = "Health:\t" + opponent.getTotalHealth();
		opponentStats[3] = "Defense:\t" + opponent.getDefense();
		
		//Declare profile stats
		profileStats = new String[4];
		
		//Write profile stats
		profileStats[0] = "Profile Stats:";
		profileStats[1] = "Equations solved:\t" + Directory.profile.getEquationsSolved();
		profileStats[2] = "Equations made:\t" + Directory.profile.getEquationsMade();
		profileStats[3] = "Wrong answers:\t" + Directory.profile.getWrongAnswers();
		
	}

	/**
	 * This state does not need to do anything on entering
	 */
	@Override
	public void enter() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This state does not need to update
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Draws the contents of the battle results display
	 * Saves the font currently set on G2D & sets color to black
	 * Sets font to the heading font and draws heading
	 * Sets font to stats font and draws player, opponent, and profile stats arrays
	 * Restores saved font to renderer
	 */
	@Override
	public void draw(Graphics2D g2d) {		
		//Save current font on G2D
		Font save = g2d.getFont();
		
		g2d.setColor(Color.black);
		
		//Set font to heading font
		g2d.setFont(headingFont);
		
		//Write the heading
		g2d.drawString(heading, (int)(attachedTo.getXPos() + attachedTo.getWidth() / 2 - headingWidth / 2), (int)(attachedTo.getYPos() + headingHeight));
		
		//Swap to stats font
		g2d.setFont(statsFont);
		
		//Write player stats and opponentStats
		for(int i = 0; i < playerStats.length; i++){
			g2d.drawString(playerStats[i], (int)(attachedTo.getXPos() + 10), (int)(attachedTo.getYPos() + 2 * headingHeight + i * statsHeight));
			g2d.drawString(opponentStats[i], (int)(attachedTo.getXPos() + attachedTo.getWidth() / 2), (int)(attachedTo.getYPos() + 2 * headingHeight + i * statsHeight));
		}
		
		
		for(int i = 0; i < profileStats.length; i++){
			//Write profile stats
			g2d.drawString(profileStats[i], (int)(attachedTo.getXPos() + attachedTo.getWidth() / 4), (int)(attachedTo.getYPos() + 2 * headingHeight + playerStats.length * statsHeight + i * statsHeight));
		}
		
		
		//Restore font to g2d
		g2d.setFont(save);
	}

	/**
	 * This state does not need to do anything on exit
	 */
	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

}
