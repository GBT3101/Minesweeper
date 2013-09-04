package Minesweeper;


import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;


public class Board extends JPanel implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/********************************Fields*************************************/
	
	public Square[][] board; // Matrix that represents the whole squares on the board
	private int mines; // Variable that represents the number of mines in the board
	private int pcounter; // Variable to count the pressings so far (mainly to know if this is the first press)
	private boolean win; // Variable to know if you win
	private boolean lose; // Varible to know if you lose

	/*************************Constructors******************************/
	
	public Board(int rows,int cols,int mines){
		win=false;
		lose=false;
		this.board = new Square[rows][cols]; // creates the board
		this.mines = mines;
		pcounter=0;
		this.setSize(64*rows,64*cols);
		this.setLayout(new GridLayout(rows,cols));
		Init(); // restarts the board
	}

	/************************Setters - Getters**************************/

	private void Init(){ // Restarts the board
		for(int i=0; i<board.length; i++)
			for(int j=0; j<board[0].length; j++){
				board[i][j] = new Square(i,j); // creates the square
				this.add(board[i][j]); // add it to the board
				board[i][j].addMouseListener(this); // make it a mouse listener
			}
		setMines(mines); // set mined to the board
	}

	private void setMines(int mines){ // Method to set mines to the board randomly
		while(mines!=0){ // continue until no mines left to put
			int a=(int) (Math.random()*board.length); // randomly choose the x coordinate
			int b=(int) (Math.random()*board[0].length); // randomly choose the y coordinate
			if(!board[a][b].isMined()){ // set mine if possible
				board[a][b].setMine();
				mines--;
			}
		}
	}
	
	public int getwidth(){ // get the width of the board
		return board.length;
	}
	
	public int getheight(){ // get the height of the board
		return board[0].length;
	}
	
	public int getMines(){ // get the number of mines in the board
		return mines;
	}

	/********************************Methods*************************************/

	public boolean isLegalPosition(int x, int y) { //This function makes the checks to avoid array exceptions.
		return ((this.board!=null) && (x>=0) && (x<this.board.length) && (y>=0) && (y<this.board[x].length));
	} // End of isLegalPosition.

	
	/**
	 *  the pressing method is really long, because it's checking twice the neighbors of the square for mined or presses
	 *  this method is when you press one of the squares in the board
	 *  
	 * @param x
	 * @param y
	 */
	
	public void press(int x, int y){
		if(!win&&!lose){ // you can press only if the game is still going
			if(!board[x][y].fop()||(board[x][y].lastPressed!=1)){ // the pressed square is found
				board[x][y].lastPressed=0; // not longer the last pressed
				if((pcounter==0)&&(board[x][y].isMined())){ // this is the first press
					newMine(x,y); // remove the mine, the player can't lose with first move
				}
				pcounter++; // increase pressing counter
				if(board[x][y].isMined()){ // pressed on a mined square, end the game
					if(!(board[x][y].found())){
						board[x][y].Press();
						End();
					}
				}
				else
					if(!board[x][y].isFlagged()){ // when the square isn't flagged, so it's available
						for (int i=0; i<=1; i++) { // this loop is checking all the neighbors
							if ((isLegalPosition(x+i-1,y-1)) && (board[x+i-1][y-1].isMined())){
								board[x][y].counterMPlus();
							}
							else if ((!isLegalPosition(x+i-1,y-1))||(board[x+i-1][y-1].isPressed())){
								board[x][y].counterPPlus();
							}
							if ((isLegalPosition(x+1,y+i-1)) && (board[x+1][y+i-1].isMined())){
								board[x][y].counterMPlus();
							}
							else if ((!isLegalPosition(x+1,y+i-1))||(board[x+1][y+i-1].isPressed())){
								board[x][y].counterPPlus();
							}
							if ((isLegalPosition(x+1-i,y+1)) && (board[x+1-i][y+1].isMined())){
								board[x][y].counterMPlus();
							}
							else if ((!isLegalPosition(x+1-i,y+1))||(board[x+1-i][y+1].isPressed())){
								board[x][y].counterPPlus();
							}
							if ((isLegalPosition(x-1,y+1-i)) && (board[x-1][y+1-i].isMined())){
								board[x][y].counterMPlus();
							} else if ((!isLegalPosition(x-1,y+1-i))||(board[x-1][y+1-i].isPressed())){
								board[x][y].counterPPlus();
							}
						}

						board[x][y].setImageNum(); // after checking all the mined neighbors, change the image of the square
						
						//now checks pressed neighbors
						
						if((board[x][y].getCounterM()==0)&&(board[x][y].getCounterP()<8)){
							for (int i=0; i<=1; i++) {
								if ((isLegalPosition(x+i-1,y-1))&&(!board[x+i-1][y-1].recPressed())){
									if(!board[x+i-1][y-1].fop()){
										board[x+i-1][y-1].makeRecPressed();
										press(x+i-1,y-1);
									}
								}
								if ((isLegalPosition(x+1,y+i-1))&&(!board[x+1][y+i-1].recPressed())){
									if(!board[x+1][y+i-1].fop()){
										board[x+1][y+i-1].makeRecPressed();
										press(x+1,y+i-1);
									}
								}
								if ((isLegalPosition(x+1-i,y+1))&&(!board[x+1-i][y+1].recPressed())){
									if(!board[x+1-i][y+1].fop()){
										board[x+1-i][y+1].makeRecPressed();
										press(x+1-i,y+1);
									}
								}
								if ((isLegalPosition(x-1,y+1-i))&&(!board[x-1][y+1-i].recPressed())){
									if(!board[x-1][y+1-i].fop()){
										board[x-1][y+1-i].makeRecPressed();
										press(x-1,y+1-i);
									}
								}
							}
						}
					}
			}
			if(checkWin()){ // check if the player won
				win=true;
				End(); // end the game
			}
			board[x][y].restartCounter(); // restart the counters to avoid recursive problems
		}
	}


	public void End(){  // ending the game - show all mines and make all the squares unavailable
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[0].length;j++){
				board[i][j].ShowMine();
			}
		if(!win) lose=true;

	}
	
	public boolean WoL(){ // method to know if the game has ended
		return win||lose;
	}
	
	public boolean hasWon(){ // method to know if the player won
		return win;
	}
	
	public boolean hasLost(){ // method to know if the player lost
		return lose;
	}

	public boolean checkWin(){ // method to check if the player has won
		int counter=0;
		int flagCounter=0;
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[0].length;j++){
				if(board[i][j].found())
					counter++;
				if(board[i][j].isFlagged())
					flagCounter++;
			}
				
		return (counter==mines)&&(flagCounter==mines);
	}

	public void next(){ // method to find the pressed square and deal with it
		int x=-1;
		int y=-1;
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[0].length;j++){
				if(board[i][j].lastPressed==1){
					board[i][j].lastPressed=0;
					x=i;
					y=j;
				}
			}
		if((x!=-1)&&(y!=-1))
		press(x,y);
	}

	public void mouseClicked(MouseEvent e){
		next(); // the board has been pressed, find which square and deal with it
	}

	public void mouseEntered(MouseEvent e){
		//Blank
	}

	public void mouseExited(MouseEvent e){
		//Blank
	}

	public void mousePressed(MouseEvent e){
		//Blank
	}

	public void mouseReleased(MouseEvent e){
		//Blank
	}

	public void newMine(int x,int y){ // find a new place for that mine
		board[x][y].digMine(); // dig out the mine
		int i=0;
		int j=0;
		while(board[x][y].isMined()){
			i++;
			j++;
		}
		board[i][j].setMine(); // new square founded
	}




}
