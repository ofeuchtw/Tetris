package tetris;


import java.awt.Color;

public class Cell {

	private int row;
	private int col;
	private Color color;
	int radius;

	
	public Cell(int row, int col, Color color, int radius) {
		this.row = row;
		this.col = col;
		this.color = color;
		this.radius = radius;
	}
	
	public Cell(int row, int col, Color color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void addRow(int rows) {
		row += rows;
	}
	
	public void addCol(int cols) {
		col += cols;
	}
	
	public int getRadius() {
		return radius;
	}

}
