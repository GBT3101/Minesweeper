package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class HighScores extends JFrame  {
	
	private static final long serialVersionUID = 1L;
	
	/**********Fields*********/
	
	private Vector<Score> vecScores = new Vector<Score>(); // Vector that represents the scores so far
	private JPanel high = new JPanel(); // Panel for the scores
	private JPanel button = new JPanel(); // panel for the button
	private JButton quit = new JButton("Quit"); // the quit button

	/********Constructors******/
	
	public HighScores(){
		super("HighScores");
		this.setLocation(500,200);
	}
	
	/**********Methods***********/
	
	public void setVisibility(boolean act) { // method to set a visibility of the JFrame
		this.setVisible(act);
	}
	
	public void insertScore(Score score){ // Method to insert a score to it's rightful place
		int sizeCounter = vecScores.size();
		if(vecScores.isEmpty()){ // the highscores panel is empty, just inserts the score
			this.vecScores.insertElementAt(score, 0);
			sizeCounter++; // adjust size
		}
		else{
			boolean inserted = false;
			int i=-1;
			while(!inserted){ // continue until the score has been inserted
				i++;
				if (i>=sizeCounter){ // we made it to the end of the vector
					this.vecScores.insertElementAt(score, i);
					sizeCounter++; // adjust size
					inserted = true; // insertion has happened
				}
				else if((this.vecScores.get(i)!=null)&&(score.getScore()<this.vecScores.get(i).getScore())){// we found the right place
					this.vecScores.insertElementAt(score, i);
					inserted = true; // insertion has happened
					sizeCounter++; // adjust size
				}
			}
		}
	}
	
	public void visible(){ // method to create the entire highscores panel and make it visible
		
		high.setLayout(new BoxLayout(high, BoxLayout.Y_AXIS));

		quit.addActionListener(new ActionListener() { // action for the quit button
			public void actionPerformed(ActionEvent arg0) {
				setVisibility(false);
			}
		});
		
		JPanel scores = new JPanel(); // creates the scores panel

		scores.setLayout(new BoxLayout(scores, BoxLayout.Y_AXIS));

		high.removeAll(); // restart to avoid doubles
		getContentPane().repaint();
		
		JLabel title = new JLabel("HighScores Table"); // labels for titles
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setFont(new Font("Serif", Font.BOLD, 16));
		title.setForeground(Color.blue);
		high.add(title); // add the title to the panel
		
		quit.setAlignmentX(CENTER_ALIGNMENT);
		
		for(int i=0; i<vecScores.size();i++){  // this loop is to add the scores to the panel
			JPanel addScore = new JPanel();
			addScore.setLayout(new GridLayout(1,2));
			addScore.add(new JLabel("  "+(i+1)+". "));
			addScore.add(new JLabel(""+vecScores.get(i)));
			high.add(addScore);
		}

		high.add(button); // add the label 
		high.add(quit); // add the button

		high.revalidate();
		
		getContentPane().add(high);
		this.pack();
		setVisible(true);
	}
}
