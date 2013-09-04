package Minesweeper;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Square extends JButton implements MouseListener {
	
	private static final long serialVersionUID = 1L;

	/********************************Fields*************************************/

	private int counterMinedNeighbors; // Variable that represents the number of mines near this square
	private int counterPressedNeighbors; // Variable that repressents the number of pressed squares near this square
	private boolean isMined; // a boolean variable that repressents if the square is mined
	private boolean pressed; // a boolean variable that repressents if the square is pressed
	private boolean flagged;//  a boolean variable that repressents if the square is flagged
	private boolean recPressed; //  a boolean variable that repressents if the square was pressed by a recursive method
	private int i; // Variable that repressents the position of the square by X line
	private int j;// Variable that repressents the position of the square by Y line

	public int lastPressed; // Variable that means this square was the last pressed

	/*******************************Constuctors*********************************/

	public Square(int x,int y){
		isMined=false;
		pressed=false;
		flagged=false;
		recPressed=false;
		counterMinedNeighbors=0;
		counterPressedNeighbors=0;
		lastPressed=0;
		this.i=x;
		this.j=y;
		ImageIcon buttonIcon = new ImageIcon("normal.jpg");
		setIcon(buttonIcon);
		this.setSize(22, 22);
		this.setPreferredSize(getSize());
		addMouseListener(this); 
	}
	
	/*************************Setters - Getters ******************************/
	public int getCounterM(){ // Method to get the number of mined neighbors
		return counterMinedNeighbors;
	}

	public int getCounterP(){ // Method to get the number of pressed neighbors
		return counterPressedNeighbors;
	}

	public void pressed(){ // Method that pressing the square
		pressed=true;
	}

	public int getI(){ // Method to get the X position (getX is occupied because it's JButton)
		return i;
	}

	public int getJ(){ // Method to get the Y position (getY is occupied because it's JButton)
		return j;
	}

	/*******************************Methods********************************/

	public void counterMPlus(){ // Method to increase the number of mined neighbors
		counterMinedNeighbors++;
	}

	public void counterPPlus(){ // Method to increase the number of pressed neighbors
		counterPressedNeighbors++;
	}

	public boolean isMined(){ // Method to know if the square is mined
		return isMined;
	}

	public void digMine(){ // Method that "dig" out the mine
		isMined=false;
	}

	public boolean isFlagged(){ // Method to know if the square is flagged
		return flagged;
	}

	public boolean isPressed(){ // Method to know if the square is pressed
		return pressed;
	}

	public boolean recPressed(){ // Method to know if the square is pressed by recursive
		return recPressed;
	}

	public void makeRecPressed(){ // Method that press the square by the recursive method
		recPressed=true;
	}

	public boolean found(){ // Method to know if the square has been found - means it's mined and flagged
		return flagged&&isMined;
	}

	public boolean explode(){ // Method to know if the square has been explode - means it's mined and pressed
		return pressed&&isMined;
	}

	public boolean pom(){ // Method to know if the square is mined, but has not been pressed yet
		return !pressed&&isMined;
	}

	public boolean fop(){ // Method to know if the square is flagged or pressed so it won't be available to left clicks
		return flagged||pressed;
	}

	public boolean mistaken(){ // Method to know if the square is flagged by mistake - means it's flagged but not mined
		return flagged&&!isMined;
	}
	
	public void restartCounter(){ // Method to restart the counters
		counterPressedNeighbors=0;
		counterMinedNeighbors=0;
	}

	public void setImageNum(){ // Method to set the Image of the square by the numbers of it's mined neighbors
		String s = ""+counterMinedNeighbors;
		ImageIcon buttonIcon = new ImageIcon(s+".jpg");
		setIcon(buttonIcon);
		this.pressed=true; // after changing the Image, this square has been surely pressed
	}

	public void setImageFlag(){ // Method to set the image of the square to be flag
			ImageIcon buttonIcon = new ImageIcon("flag.jpg");
			setIcon(buttonIcon);
	}

	public void setImageMine(){ // Method to set the image of the square to be exploded mine
		ImageIcon buttonIcon = new ImageIcon("mine.jpg");
		setIcon(buttonIcon);

	}

	public void ShowMine(){ // Method to show the state of the square - ONLY AT THE END OF THE GAME.
		if((isMined)&&(!found())&&(!pressed)){ // show a not exploded mine picture if the square is mined, not found and not pressed
			ImageIcon buttonIcon = new ImageIcon("safemine.jpg");
			setIcon(buttonIcon);
		} else if(found()){ // show a found mine
			ImageIcon buttonIcon = new ImageIcon("found.jpg");
			setIcon(buttonIcon);
		} else if(mistaken()){ // show a mistake (flagged not mined square)
			ImageIcon buttonIcon = new ImageIcon("mistaken.jpg");
			setIcon(buttonIcon);
		}
		pressed=true; // to avoid availability of the square now, make it pressed
		}

	public void setMine(){ // Method to set mine in this square
		this.isMined=true;
	}

	public void setFlag(){ // Method to set flag in this square (or delete it)
		if(!isPressed()){ // avoid setting flags to pressed squares
			if(!isFlagged()){ // if the square is not flag, set a flag here
				setImageFlag();
				flagged=true;
			}
			else{ // the square is already flag, so delete the flag.
				ImageIcon buttonIcon = new ImageIcon("mouse.jpg");
				setIcon(buttonIcon);
				flagged=false;
				pressed=false;
				lastPressed=0;
			}
		}
	}

	public void Press(){ // Method to press a square, if possible
		if((!isPressed())&&(!isFlagged())){
			if(isMined())
				setImageMine();
			else
				setImageNum();
			this.pressed=true;
		}
	}

	//Actions

	public void mouseClicked(MouseEvent e){
		lastPressed=1; // to know this square was the last who pressed
		if (e.getButton() == MouseEvent.BUTTON1){  // Right click
			Press(); // press the square
		}
		if (e.getButton() == MouseEvent.BUTTON3){ // Left click
			setFlag(); // set flag to the flag (or delete it)
		}
		
	}

	public void mouseEntered(MouseEvent e){ // change the image when the mouse is on the square
		if((!pressed)&&(!isFlagged())){
			ImageIcon buttonIcon = new ImageIcon("mouse.jpg");
			setIcon(buttonIcon);
		}
	}

	public void mouseExited(MouseEvent e){ // change the picture back when the mouse exiting the square
		if((!pressed)&&(!isFlagged())){
			ImageIcon buttonIcon = new ImageIcon("normal.jpg");
			setIcon(buttonIcon);
		}
	}

	public void mousePressed(MouseEvent e){ // change the picture when the square is pressed
		if((!pressed)&&(!isFlagged())){
			ImageIcon buttonIcon = new ImageIcon("pressed.jpg");
			setIcon(buttonIcon);
		}
	}

	public void mouseReleased(MouseEvent e){ // change back the picture when the square is no more pressed
		if((!pressed)&&(!isFlagged())){
			ImageIcon buttonIcon = new ImageIcon("mouse.jpg");
			setIcon(buttonIcon);
		}
	}
}
