package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewGame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/***********Fields********/
	
	private Board board;
	
	/***********Constructors**********/
	
	public NewGame() {
		super("New Game");
		this.setLocation(500,200); // make the frame be in the center
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel diff = new JPanel(); // panel for the difficulty
		diff.setLayout(new BoxLayout(diff, BoxLayout.X_AXIS));
		diff.setBorder(BorderFactory.createTitledBorder(" Difficulty "));
		JPanel regular = new JPanel();
		regular.setLayout(new BoxLayout(regular, BoxLayout.Y_AXIS));
		regular.setAlignmentX(Component.LEFT_ALIGNMENT);

		regular.add(new JLabel("       Beginner")); // a label for the beginner difficulty
		final JRadioButton beginner = new JRadioButton("10 Mines");
		regular.add(beginner);
		regular.add(new JLabel("       9 x 9 Squares Grid"));

		regular.add(new JLabel("   "), diff);

		regular.add(new JLabel("       Intermidate")); // a label for the Intermidate difficulty
		final JRadioButton intermidate = new JRadioButton("40 Mines");
		regular.add(intermidate);
		regular.add(new JLabel("       16 x 16 Squares Grid       "));

		regular.add(new JLabel("   "), diff);

		regular.add(new JLabel("       Expert")); // a label for the Expert difficulty
		final JRadioButton expert = new JRadioButton("99 Mines");
		regular.add(expert);
		regular.add(new JLabel("       16 x 30 Squares Grid"));

		regular.add(new JLabel("   "), diff);

		JPanel costume = new JPanel(); // a label for the costume difficulty
		costume.setLayout(new GridLayout(4,1));
		costume.add(new JLabel("   "));

		JPanel upSide = new JPanel(); // a panel for the upper side
		final JRadioButton custumized = new JRadioButton("custumized", true);
		upSide.add(custumized);
		custumized.setAlignmentX(LEFT_ALIGNMENT);
		custumized.setAlignmentY(BOTTOM_ALIGNMENT);

		ButtonGroup group = new ButtonGroup(); // make the difficulties a group buttons
		group.add(beginner);
		group.add(intermidate);
		group.add(expert);
		group.add(custumized);

		JPanel downSide = new JPanel(); // panel for the downer size of the costume difficulty
		downSide.setLayout(new GridLayout(3,2));
		downSide.setAlignmentX(Component.BOTTOM_ALIGNMENT);
		final JLabel jWidth= new JLabel("Width (9 - 24):"); // label for the width
		downSide.add(jWidth);
		final JTextField tWidth = new JTextField(10); // text to input width
		downSide.add(tWidth);
		final JLabel jHeight= new JLabel("Height (9 - 30):"); // label for the height
		downSide.add(jHeight);
		final JTextField tHeight = new JTextField(10);  // text to input height
		downSide.add(tHeight);
		final JLabel jMines= new JLabel("Mines (10 - 668):"); // label for the number of mines
		downSide.add(jMines);		
		final JTextField tMines = new JTextField(10); // text to input number of mines
		downSide.add(tMines);

		beginner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tWidth.setEditable(false);
				tHeight.setEditable(false);
				tMines.setEditable(false);
				jWidth.setEnabled(false);
				jHeight.setEnabled(false);
				jMines.setEnabled(false);
			}
		});
		
		intermidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tWidth.setEditable(false);
				tHeight.setEditable(false);
				tMines.setEditable(false);
				jWidth.setEnabled(false);
				jHeight.setEnabled(false);
				jMines.setEnabled(false);
			}
		});
		
		expert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tWidth.setEditable(false);
				tHeight.setEditable(false);
				tMines.setEditable(false);
				jWidth.setEnabled(false);
				jHeight.setEnabled(false);
				jMines.setEnabled(false);
			}
		});
		
		custumized.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tWidth.setEditable(true);
				tHeight.setEditable(true);
				tMines.setEditable(true);
				jWidth.setEnabled(true);
				jHeight.setEnabled(true);
				jMines.setEnabled(true);
			}
		});

		upSide.setAlignmentX(LEFT_ALIGNMENT);
		downSide.setAlignmentY(BOTTOM_ALIGNMENT);
		
		//adds the panels
		
		costume.add(upSide);
		costume.add(downSide);
		costume.add(new JLabel("   "));

		diff.add(regular);
		diff.add(costume);

		JPanel choose = new JPanel();
		choose.setLayout(new BoxLayout(choose, BoxLayout.X_AXIS));

		JButton confirm = new JButton("Confirm"); // button to confirm the choosing
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				if (beginner.isSelected()==true){ // creates beginner board
					setBoard(9,9,10);
				}
				if (intermidate.isSelected()==true){ // creates intermidate board
					setBoard(16,16,40);
				}
				if (expert.isSelected()==true){ // creates expert board
					setBoard(16,30,99);
				}
				if (custumized.isSelected()==true) { // creates costume board
					Integer width = new Integer ((Integer.parseInt(tWidth.getText())));
					Integer height = new Integer ((Integer.parseInt(tHeight.getText())));
					Integer mines = new Integer ((Integer.parseInt(tMines.getText())));
					if((width<9)||(width>24)||(height<9)||(height>30)||(mines<10)||(mines>668)||(width*height==mines)){
						// check if the input is legal
						 throw new IllegalArgumentException("wrong cotumize input");
					}
					setBoard(width,height,mines); // creates a new board with the costumized values
				}
				setVisible(false);
				new Game(getBoard()); // creates a new game with the new board
			}
		});

		choose.add(confirm);
		confirm.setAlignmentX(RIGHT_ALIGNMENT);

		JButton decline = new JButton("Decline"); // new button for decline the request to create a new game
		decline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisibility(false);
			}
		});
		choose.add(decline);
		decline.setAlignmentX(LEFT_ALIGNMENT);
		diff.setAlignmentY(TOP_ALIGNMENT);
		choose.setAlignmentY(BOTTOM_ALIGNMENT);
		getContentPane().add(diff);
		getContentPane().add(choose);
		setVisible(true);
		pack();
	}
	
	
	/***************Getters - Setters - Methods****************/
	
	public Board getBoard() { // Method to get the board
		return this.board;
	}
	
	public void setBoard(int i, int j, int m){ // method to create new board
		this.board = new Board(i,j,m);
	}

	public void setVisibility(boolean act) { // method to make the frame visible
		this.setVisible(act);
	}

}


