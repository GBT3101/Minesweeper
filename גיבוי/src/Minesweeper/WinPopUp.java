package Minesweeper;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class WinPopUp extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/*******Fields**********/
	
	private String winnerName; // represents the winner name
	private int score; // represent the score of the player
	
	/********Constructors*********/
	
	public WinPopUp(int seconds, final HighScores highScores) {
		super("Congratulations!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(500,200);
		this.score = seconds;
		JPanel message = new JPanel(); // panel for the message
		message.setLayout(new GridLayout(4,1));
		JLabel winMessage = new JLabel("Congratulations You Have Won The Game!");
		winMessage.setHorizontalAlignment(SwingConstants.CENTER);
		message.add(winMessage);
		JLabel presentScoreLabel = new JLabel("Your Score is:  " + seconds); // label for the score
		presentScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		message.add(presentScoreLabel);
		JPanel getInfo = new JPanel();
		getInfo.setLayout(new GridLayout(1,2));
		JLabel enterNameLabel = new JLabel("Enter Name: "); // label for the name
		getInfo.add(enterNameLabel);
		final JTextField enterName = new JTextField(10);
		getInfo.add(enterName);
		message.add(getInfo);
		JButton submitScore = new JButton("Submit Score"); // button to submit the score
		submitScore.setHorizontalAlignment(SwingConstants.CENTER);
		message.add(submitScore);
		getContentPane().add(message);
		this.setVisible(true);
		this.pack();
		
		submitScore.addActionListener(new ActionListener() { // for the submitting button
			public void actionPerformed(ActionEvent event) {
				winnerName = enterName.getText(); // get the winner name
				setVisible(false); // close the popup
				Score score2 = new Score(winnerName,score); // create a score object with the name and score
				highScores.insertScore(score2); // insert the score to the highscores frame
				highScores.visible(); // make it visible
			}
		});
		
	}
	
	// Method
	
	public String getWinnerName(){ // method to get the winner's name
		return this.winnerName;
	}
	
	
}
