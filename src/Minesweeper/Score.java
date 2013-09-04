package Minesweeper;

public class Score {
	
	/*****************Fields************/
	
	private String name; // represents the name of the player
	private int score; // represents the score of the player
	
	/************Constructors**********/
	
	public Score(String name,int score){
		this.name=name;
		this.score=score;
	}
	/*************Methods**************/
	
	public int getScore(){ // Method to get the score of the player
		return score;
	}
	
	public String toString(){ // String the name and the score of the player
		String ans = ""+name+" - "+score; 
		return ans;
	}
}
