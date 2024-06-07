package model.pong;

import javafx.scene.paint.Color;

public class Ball {

	// Initial spawn
	private double xSpawn;
	private double ySpawn;
	
	private double xPos;
	private double yPos;
	
	private Color color;
	
	private int radius;

	
	public Ball() { }
	
	
	public double getxSpawn() {
		return xSpawn;
	}

	public void setxSpawn(double xSpawn) {
		this.xSpawn = xSpawn;
	}

	public double getySpawn() {
		return ySpawn;
	}

	public void setySpawn(double ySpawn) {
		this.ySpawn = ySpawn;
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
}
