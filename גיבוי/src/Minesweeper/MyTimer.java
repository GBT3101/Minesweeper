package Minesweeper;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;

public class MyTimer extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	/***********************Fields******************/
	
	private final int delay = 1000; // delay for the timer
	private Timer timer; // the timer
	private int second=0; // represents seconds
	private JLabel timeLabel=new JLabel(""+second); // the label
	
	/*********************Constructors************/
	
	public MyTimer(){
		timeLabel.setFont(new Font("Serif", Font.BOLD, 16));
		timeLabel.setForeground(Color.yellow);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBackground(Color.BLACK);
		addActionListener(this);
		timer =  new Timer(delay,this);
		this.timeLabel.setName(""+second+1);
		add(timeLabel);
	}
	
	/********************Methods******************/
	
	public void start(){ // method to start the timer
		timer.start();
	}

	public void stop(){ // method to stop the timer
		timer.stop();
	}
	
	public int getSeconds(){ // method to get the seconds
		return this.second;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(!timer.isRunning()){ // start the timer if it's not running
			start();
		}
		second++; // increase seconds
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.timeLabel.setText(""+second); // write seconds in the label
	}


}
