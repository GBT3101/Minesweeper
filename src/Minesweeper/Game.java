package Minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class Game extends JFrame implements  MouseListener {

	private static final long serialVersionUID = 1L;
	
	/*******************Fields*****************/
	
	private Board board; // represents the board of the game
	private JLabel lMinesCounter = new JLabel(""+10); // the label of the mines
	private MyTimer timer = new MyTimer(); // the label of the timer
	private Eye eye = new Eye(); // the eye button

	/******************Constructors***********/

	public Game(Board b) { // constructors when you have board
		
		this.setLocation(500,200); // set the frame at the center of the screen
		setBoard(b); // creates the board
		lMinesCounter = new JLabel(""+board.getMines()); // label for the mines counter
		JPanel upper = new JPanel(); // creates the upper panel
		upper.setLayout(new GridLayout(1,3));

		// Left Side

		JPanel mcp = new JPanel(); // adds the mines counter panel
		mcp.setLayout(new GridLayout(2,1));
		JLabel mcl1 = new JLabel("Mines Counter");
		mcl1.setFont(new Font("Serif", Font.BOLD, 16));
		mcl1.setForeground(Color.red);
		lMinesCounter.setFont(new Font("Serif", Font.BOLD, 16));
		lMinesCounter.setForeground(Color.yellow);
		mcp.add(mcl1);
		mcl1.setHorizontalAlignment(SwingConstants.CENTER);
		mcp.setBackground(Color.black);
		lMinesCounter.setHorizontalAlignment(SwingConstants.CENTER);
		mcp.add(lMinesCounter);
		upper.add(mcp);

		// Center
		
		upper.add(eye); // adds the eye button
		eye.addMouseListener(this);

		// Right Side
		
		JPanel timePanel = new JPanel(); // adds the timer panel
		timePanel.setLayout(new GridLayout(2,1));
		JLabel upLabel = new JLabel("Timer");
		upLabel.setFont(new Font("Serif", Font.BOLD, 16));
		upLabel.setForeground(Color.red);
		timePanel.add(upLabel);
		upLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timePanel.setBackground(Color.black);
		timePanel.add(timer);
		upper.add(timePanel);
		upper.setVisible(true);
		JPanel lower = new JPanel();
		
		// Lower Side
		
		lower.add(b); // adds the board
		for(int i=0; i<b.board.length; i++)
			for(int j=0; j<b.board[0].length; j++)
				b.board[i][j].addMouseListener(this); //adds the squares to be mouse listener in the game
		JPanel game = new JPanel(); // creates the game panel
		game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
		game.add(upper); // add the upper panel
		game.add(lower); // add the lower pannel
		this.getContentPane().add(game); // add the game to the frame
		this.setVisible(true);// make the frame seen
		initUI(); // creates the menu bar
		this.pack();
	}
	
	/******************Methods***********/
	
	public final void initUI() {

		JMenuBar menubar = new JMenuBar(); // creates the menu bar
		JMenu file = new JMenu("File"); // creates the file menu
		file.setMnemonic(KeyEvent.VK_F);
		JMenuItem fileNew = new JMenuItem("New Game"); // create the new game option
		fileNew.setMnemonic(KeyEvent.VK_N);
		JMenuItem fileHigh = new JMenuItem("HighScores"); // create the highscores option
		fileNew.setMnemonic(KeyEvent.VK_M);
		JMenuItem fileExit = new JMenuItem("Exit"); // create the exit option
		fileExit.setMnemonic(KeyEvent.VK_C);
		fileExit.setToolTipText("Exit application");
		fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));

		fileExit.addActionListener(new ActionListener() { // make the exit option an action listener
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}

		});

		fileHigh.addActionListener(new ActionListener() { // make the highscores option an action listener
			public void actionPerformed(ActionEvent event) {
				Constants.highScores.visible(); // get the highscores from the constants class
			}

		});

		fileNew.addActionListener(new ActionListener() { // make the new game option an action listener
			public void actionPerformed(ActionEvent event) {
				Game.this.dispose();
				NewGame popupDialog = new NewGame();
				popupDialog.setVisible(true);
			}
		});
		
		file.add(fileNew); // add the new game to the file menu
		file.add(fileHigh); // add the highscores to the file menu
		file.addSeparator();
		file.addSeparator();
		file.add(fileExit); // add the exit to the file menu
		menubar.add(file); // add the file menu
		setJMenuBar(menubar);
		setTitle("MineSweeper"); // set title to the frame
		setSize(360, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void setBoard(Board b) { // Method to set the board
		this.board = b;
	}

	public Board getBoard() { // method to get the board
		return this.board;
	}

	public void mouseClicked(MouseEvent e){ // mouse clicked on something in the game
		
		if (e.getSource() instanceof Eye){ // the click was on the eye
			if(eye.WoL()){ // only if the player won or lost
				setVisible(false);
				new Game(new Board(board.getwidth(),board.getheight(),board.getMines()));
				// creates a new game with the same status
			}
			
		} else{ // the click is on one of the squares
			int counter = 0;
			for(int i=0; i<board.board.length; i++)
				for(int j=0; j<board.board[0].length; j++)
					if (board.board[i][j].isFlagged()) counter++;
			lMinesCounter.setText(""+(board.getMines()-counter)); // update the mines counter label
		}
		
		if(!board.WoL()) // make the timer start at first click
			timer.start();
		else // make the timer stop if the game has ended
			timer.stop();
		
		if(board.hasWon()&&!eye.WoL()){ // when the player wins the game
			eye.win(); // change the picture of the eye
			@SuppressWarnings("unused")
			// make a popup when the player wins to enter his name
			WinPopUp popUpDialog = new WinPopUp(timer.getSeconds(),Constants.highScores);
		}
	}

	public void mouseEntered(MouseEvent e){
		if(getBoard().hasLost())
			eye.lost(); // kills the eye
	}

	public void mouseExited(MouseEvent e){
		if(getBoard().hasLost())
			eye.lost(); // kills the eye

	}
	public void mousePressed(MouseEvent e){
		if(!getBoard().WoL())
			this.eye.stress();  // changes the picture of the eye to be stressed

	}

	public void mouseReleased(MouseEvent e){
		if(!getBoard().WoL())
			this.eye.noMine(); // changes the picture of the eye to be calm (animation)

	}

	public void startNewGame() { // Method to create a new game
		Game.this.dispose();
	}

	public static void main(String[] args) { // Main method
		
		SwingUtilities.invokeLater(new Runnable() { // run the game
			Board b = new Board(9,9,10);// creates new board
			public void run() {
				Game ex = new Game(b); // creates a new game
				ex.setVisible(true);
			}
		});
	}
}