package Minesweeper;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.event.*;

public class Eye extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	/********Fields*******/
	private final int delay = 400; // delay for the timer
	public boolean lose; // boolean to know if the player lost
	public boolean win; // boolean to know if the player won
	private Timer normal = new Timer(delay,this); // timer for the eye (animation reasons)


	/******Constructors******/
	
	public Eye(){
		lose=false;
		win=false;
		ImageIcon buttonIcon = new ImageIcon("eyeNormal.jpg");
		setIcon(buttonIcon);
		this.setSize(42, 42);
		this.setPreferredSize(getSize());
		addActionListener(this);
	}
	
	/*******Methods*********/
	
	public void lost(){ // the eye is now dead, kill it.
		ImageIcon buttonIcon = new ImageIcon("Dead.jpg");
		setIcon(buttonIcon);
		lose=true;
		normal.stop();
	}

	public void win(){ // change the eye to win
		ImageIcon buttonIcon = new ImageIcon("Win.jpg");
		setIcon(buttonIcon);
		win=true;
		normal.stop();
	}
	
	public boolean WoL(){ // checks if the eye is available
		return lose||win;
	}

	public void normal(){ // make the eye normal again
		ImageIcon buttonIcon = new ImageIcon("eyeNormal.jpg");
		setIcon(buttonIcon);
		normal.stop();
	}

	public void blink(){ // make the eye blink
		ImageIcon buttonIcon = new ImageIcon("eyeBlink.jpg");
		setIcon(buttonIcon);
	}

	public void close(){ // make the eye closed
		ImageIcon buttonIcon = new ImageIcon("eyeStart.jpg");
		setIcon(buttonIcon);
	}

	public void stress(){ // make the eye being stressed
		ImageIcon buttonIcon = new ImageIcon("eyePressed1.jpg");
		setIcon(buttonIcon);
	}

	public void noMine(){ // make the eye calm because there is no mine
		if(!lose){
			ImageIcon buttonIcon = new ImageIcon("noMine1.jpg");
			setIcon(buttonIcon);
			normal.start(); // starts the timer
		}
	}

	public void actionPerformed(ActionEvent e) {
		normal(); 
	}


}
