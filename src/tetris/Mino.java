package tetris;

import java.awt.Color;
import java.util.Random;

public class Mino {

	private Cell a;
	private Cell b;
	private Cell c;
	private Cell d;
	private final Color RED = new Color(244, 92, 66);
	private final Color ORANGE = new Color(252, 159, 10);
	private final Color YELLOW = new Color(252, 211, 7);
	private final Color GREEN = new Color(139, 198, 129);
	private final Color BLUE = new Color(139, 167, 239);
	private final Color PURPLE = new Color(208, 135, 221);
	private final Color CYAN = new Color(153, 218, 221);
    private String shape;
    int rotation = 0;
	
	private Cell[] cells;
	
	public enum Tetrominoes {
		L, Line, RevL, Z, S, T, Square;
		
	}
	
	public Mino() {
		cells = new Cell[4];
		Random rand = new Random();
		int variant = rand.nextInt(7);
		setShape(variant);
		//setShape(2);
		rotation = 0;
	}
	
	 public void setShape(int variant) {
		
		if(variant == 0) {
			a = new Cell(0,5, ORANGE, 2);
			b = new Cell(1,3, ORANGE, 1);
			c = new Cell(1,4, ORANGE, 0);
			d = new Cell(1,5, ORANGE, 1);
	        shape = "L";
		} else if (variant == 1) {
			a = new Cell(0,3, BLUE, 2);
			b = new Cell(1,3, BLUE, 1);
			c = new Cell(1,4, BLUE, 0);
			d = new Cell(1,5, BLUE, 1);
			shape = "RevL";
		} else if (variant == 2){
			a = new Cell(0,3, CYAN, -2);
			b = new Cell(0,4, CYAN, -1);
			c = new Cell(0,5, CYAN, 0);
			d = new Cell(0,6, CYAN, 1);
			shape = "Line";
		} else if (variant == 3) {
			a = new Cell(0,4, GREEN, 1);
			b = new Cell(0,5, GREEN, 2);
			c = new Cell(1,3, GREEN, -1);
			d = new Cell(1,4, GREEN, 0);
			shape = "S";
		} else if (variant == 4) {
			a = new Cell(0,4, RED, 2);
			b = new Cell(0,5, RED, 1);
			c = new Cell(1,5, RED, 0);
			d = new Cell(1,6, RED, 1);
			shape = "Z";
		} else if (variant == 5) {
			a = new Cell(0,4, YELLOW);
			b = new Cell(0,5, YELLOW);
			c = new Cell(1,4, YELLOW);
			d = new Cell(1,5, YELLOW);
			shape = "Square";
		} else if (variant == 6) {
			a = new Cell(0,4, PURPLE);
			b = new Cell(1,3, PURPLE);
			c = new Cell(1,4, PURPLE);
			d = new Cell(1,5, PURPLE);
			shape = "T";
		}
		
//		int variant = (int)(Math.random() * 7);
//		switch(variant) {
//			case 0:
//				a = new Cell(0,5, ORANGE);
//				b = new Cell(1,3, ORANGE);
//				c = new Cell(1,4, ORANGE);
//				d = new Cell(1,5, ORANGE);
//				mE = 3;
//			case 1:
//				a = new Cell(0,3, BLUE);
//				b = new Cell(1,3, BLUE);
//				c = new Cell(1,4, BLUE);
//				d = new Cell(1,5, BLUE);
//				mE = 3;
//			case 2:
//				a = new Cell(0,3, CYAN);
//				b = new Cell(0,4, CYAN);
//				c = new Cell(0,5, CYAN);
//				d = new Cell(0,6, CYAN);
//				mE = 4;
//			case 3:
//				a = new Cell(0,4, GREEN);
//				b = new Cell(0,5, GREEN);
//				c = new Cell(1,3, GREEN);
//				d = new Cell(1,4, GREEN);
//				mE = 3;
//			case 4:
//				a = new Cell(0,4, RED);
//				b = new Cell(0,5, RED);
//				c = new Cell(1,5, RED);
//				d = new Cell(1,6, RED);
//				mE = 3;
//			case 5:
//				a = new Cell(0,4, YELLOW);
//				b = new Cell(0,5, YELLOW);
//				c = new Cell(1,4, YELLOW);
//				d = new Cell(1,5, YELLOW);
//				mE = 2;
//			case 6:
//				a = new Cell(0,4, PURPLE);
//				b = new Cell(1,3, PURPLE);
//				c = new Cell(1,4, PURPLE);
//				d = new Cell(1,5, PURPLE);
//				mE = 3;
////			default:
////				a = new Cell(0,4, PURPLE);
////				b = new Cell(1,3, PURPLE);
////				c = new Cell(1,4, PURPLE);
////				d = new Cell(1,5, PURPLE);
////				mE = 3;
//		}
		
		cells[0] = a;
		cells[1] = b;
		cells[2] = c;
		cells[3] = d;

    }
	 
	public String getShape() {
		return shape;
	}
	
	public int getRot() {
		return rotation;
	}
	
	public Cell getAxis() {
		if(shape.equals("S")) {
			return d;
		} else {
			return c;
		}
	}
	
	public void shiftDown() {
		for(Cell cell : cells) {
			cell.setRow(cell.getRow() + 1);
		}
	}
	
	public void shiftLeft() {
		for(Cell cell : cells) {
			cell.setCol(cell.getCol() - 1);
		}
	}
	
	public void shiftRight() {
		for(Cell cell : cells) {
			cell.setCol(cell.getCol() + 1);
		}
	}
	
	public void rotateClock() {
		if(shape.equals("Line")) {
			int originRow = c.getRow();
			int originCol = c.getCol();
			double angle = (Math.PI/2) * (rotation + 1);
			for(Cell cell: cells) {
				cell.setRow(originRow + cell.getRadius() * (int)Math.sin(angle));
				cell.setCol(originCol + cell.getRadius() * (int)Math.cos(angle));
			}
		} else if(shape.equals("Square")) {
			return;
		} else if(shape.equals("T")) {
			int originRow = c.getRow();
			int originCol = c.getCol();
			double angle = (Math.PI/2) * (rotation);
			a.setRow(originRow - (int)Math.cos(angle + (Math.PI/2)));
			a.setCol(originCol + (int)Math.sin(angle + (Math.PI/2)));
			b.setRow(originRow - (int)Math.cos(angle));
			b.setCol(originCol + (int)Math.sin(angle));
			d.setRow(originRow - (int)Math.cos(angle + 2* (Math.PI/2)));
			d.setCol(originCol + (int)Math.sin(angle + 2*(Math.PI/2)));
		} else if (shape.equals("L")) {
			int originRow = c.getRow();
			int originCol = c.getCol();
			double angle = (Math.PI/2) * (rotation);
			if(rotation % 4 == 0) {
				a.setRow(originRow + 1);
				a.setCol(originCol + 1);
			} else if (rotation % 4 == 1) {
				a.setRow(originRow + 1);
				a.setCol(originCol - 1);
			} else if (rotation % 4 == 2) {
				a.setRow(originRow - 1);
				a.setCol(originCol - 1);
			} else {
				a.setRow(originRow - 1);
				a.setCol(originCol + 1);
			}
			b.setRow(originRow - (int)Math.cos(angle));
			b.setCol(originCol + (int)Math.sin(angle));
			d.setRow(originRow - (int)Math.cos(angle + 2* (Math.PI/2)));
			d.setCol(originCol + (int)Math.sin(angle + 2*(Math.PI/2)));
		} else if (shape.equals("RevL")) {
			int originRow = c.getRow();
			int originCol = c.getCol();
			double angle = (Math.PI/2) * (rotation);
			if(rotation % 4 == 0) {
				a.setRow(originRow - 1);
				a.setCol(originCol + 1);
			} else if (rotation % 4 == 1) {
				a.setRow(originRow + 1);
				a.setCol(originCol + 1);
			} else if (rotation % 4 == 2) {
				a.setRow(originRow + 1);
				a.setCol(originCol - 1);
			} else {
				a.setRow(originRow - 1);
				a.setCol(originCol - 1);
			}
			b.setRow(originRow - (int)Math.cos(angle));
			b.setCol(originCol + (int)Math.sin(angle));
			d.setRow(originRow - (int)Math.cos(angle + 2* (Math.PI/2)));
			d.setCol(originCol + (int)Math.sin(angle + 2*(Math.PI/2)));
		} else if (shape.equals("S")) {
			int originRow = d.getRow();
			int originCol = d.getCol();
			double angle = (Math.PI/2) * (rotation);
			a.setRow(originRow - (int)Math.cos(angle + (Math.PI/2)));
			a.setCol(originCol + (int)Math.sin(angle + (Math.PI/2)));
			c.setRow(originRow - (int)Math.cos(angle));
			c.setCol(originCol + (int)Math.sin(angle));
			if(rotation % 4 == 0) {
				b.setRow(originRow + 1);
				b.setCol(originCol + 1);
			} else if (rotation % 4 == 1) {
				b.setRow(originRow + 1);
				b.setCol(originCol - 1);
			} else if (rotation % 4 == 2) {
				b.setRow(originRow - 1);
				b.setCol(originCol - 1);
			} else {
				b.setRow(originRow - 1);
				b.setCol(originCol + 1);
			}
		} else if (shape.equals("Z")) {
			int originRow = c.getRow();
			int originCol = c.getCol();
			double angle = (Math.PI/2) * (rotation);
			b.setRow(originRow - (int)Math.cos(angle + (Math.PI/2)));
			b.setCol(originCol + (int)Math.sin(angle + (Math.PI/2)));
			if(rotation % 4 == 0) {
				a.setRow(originRow - 1);
				a.setCol(originCol + 1);
			} else if (rotation % 4 == 1) {
				a.setRow(originRow + 1);
				a.setCol(originCol + 1);
			} else if (rotation % 4 == 2) {
				a.setRow(originRow + 1);
				a.setCol(originCol - 1);
			} else {
				a.setRow(originRow - 1);
				a.setCol(originCol - 1);
			}
			d.setRow(originRow - (int)Math.cos(angle + 2* (Math.PI/2)));
			d.setCol(originCol + (int)Math.sin(angle + 2*(Math.PI/2)));
		}
		rotation++;
	}
	
//	public void rotateCounter() {
//		for(Cell cell : cells) {
//			int oldY = cell.getCol();
//			cell.setCol(cell.getRow());
////			cell.setRow(1 - (oldY - (mE - 2)));
//		}
//		rotation--;
//	}
	
	public Cell[] getCells() {
		return cells;
	}
	
	public Cell[] getBottomCells() {
		if(shape.equals("Line")) {
			if(rotation % 4 == 3) {
				Cell[] bottom = {a};
				return bottom;
			} else if(rotation % 4 == 1) {
				Cell[] bottom = {d};
				return bottom;
			} else {
				return cells;
			}
		} else if (shape.equals("L")) {
			if(rotation % 4 == 0) {
				Cell[] bottom = {b,c,d};
				return bottom;
			} else if (rotation % 4 == 3) {
				Cell[] bottom = {a,b};
				return bottom;
			} else if (rotation % 4 == 2) {
				Cell[] bottom = {a,b,c};
				return bottom;
			} else {
				Cell[] bottom = {a,d};
				return bottom;
			}
		} else if (shape.equals("RevL")) {
			if(rotation % 4 == 0) {
				Cell[] bottom = {b,c,d};
				return bottom;
			} else if (rotation % 4 == 3) {
				Cell[] bottom = {a,b};
				return bottom;
			} else if (rotation % 4 == 2) {
				Cell[] bottom = {a,c,d};
				return bottom;
			} else {
				Cell[] bottom = {a,d};
				return bottom;
			}
		} else if (shape.equals("S")) {
			if(rotation % 4 == 0) {
				Cell[] bottom = {b,c,d};
				return bottom;
			} else if (rotation % 4 == 3) {
				Cell[] bottom = {a,c};
				return bottom;
			} else if (rotation % 4 == 2) {
				Cell[] bottom = {a,b,c};
				return bottom;
			} else {
				Cell[] bottom = {b,d};
				return bottom;
			}
		} else if (shape.equals("Z")) {
			if(rotation % 4 == 0) {
				Cell[] bottom = {a,c,d};
				return bottom;
			} else if (rotation % 4 == 3) {
				Cell[] bottom = {a,c};
				return bottom;
			} else if (rotation % 4 == 2) {
				Cell[] bottom = {a,b,d};
				return bottom;
			} else {
				Cell[] bottom = {b,d};
				return bottom;
			}
		} else if (shape.equals("Square")) {
			if(rotation % 4 == 0) {
				Cell[] bottom = {c,d};
				return bottom;
			} else if (rotation % 4 == 3) {
				Cell[] bottom = {a,c};
				return bottom;
			} else if (rotation % 4 == 2) {
				Cell[] bottom = {a,b};
				return bottom;
			} else {
				Cell[] bottom = {b,d};
				return bottom;
			}
		} else if (shape.equals("T")) {
			if(rotation % 4 == 0) {
				Cell[] bottom = {b,c,d};
				return bottom;
			} else if (rotation % 4 == 3) {
				Cell[] bottom = {a,b};
				return bottom;
			} else if (rotation % 4 == 2) {
				Cell[] bottom = {a,b,d};
				return bottom;
			} else {
				Cell[] bottom = {a,d};
				return bottom;
			}
		} else {
			return cells;
		}
		
	}

	public Cell[] getTopCells() {
		if(shape.equals("Line")) {
			if((rotation + 2) % 4 == 3) {
				Cell[] top = {a};
				return top;
			} else if((rotation + 2)  % 4 == 1) {
				Cell[] top = {d};
				return top;
			} else {
				return cells;
			}
		} else if (shape.equals("L")) {
			if((rotation + 2) % 4 == 0) {
				Cell[] top = {b,c,d};
				return top;
			} else if ((rotation + 2) % 4 == 3) {
				Cell[] top = {a,b};
				return top;
			} else if ((rotation + 2) % 4 == 2) {
				Cell[] top = {a,b,c};
				return top;
			} else {
				Cell[] top = {a,d};
				return top;
			}
		} else if (shape.equals("RevL")) {
			if((rotation + 2) % 4 == 0) {
				Cell[] top = {b,c,d};
				return top;
			} else if ((rotation + 2) % 4 == 3) {
				Cell[] top = {a,b};
				return top;
			} else if ((rotation + 2) % 4 == 2) {
				Cell[] top = {a,c,d};
				return top;
			} else {
				Cell[] top = {a,d};
				return top;
			}
		} else if (shape.equals("S")) {
			if((rotation + 2) % 4 == 0) {
				Cell[] top = {b,c,d};
				return top;
			} else if ((rotation + 2) % 4 == 3) {
				Cell[] top = {a,c};
				return top;
			} else if ((rotation + 2) % 4 == 2) {
				Cell[] top = {a,b,c};
				return top;
			} else {
				Cell[] top = {b,d};
				return top;
			}
		} else if (shape.equals("Z")) {
			if((rotation + 2) % 4 == 0) {
				Cell[] top = {a,c,d};
				return top;
			} else if ((rotation + 2) % 4 == 3) {
				Cell[] top = {a,c};
				return top;
			} else if ((rotation + 2) % 4 == 2) {
				Cell[] top = {a,b,d};
				return top;
			} else {
				Cell[] top = {b,d};
				return top;
			}
		} else if (shape.equals("Square")) {
			if((rotation + 2) % 4 == 0) {
				Cell[] top = {c,d};
				return top;
			} else if ((rotation + 2) % 4 == 3) {
				Cell[] top = {a,c};
				return top;
			} else if ((rotation + 2) % 4 == 2) {
				Cell[] top = {a,b};
				return top;
			} else {
				Cell[] top = {b,d};
				return top;
			}
		} else if (shape.equals("T")) {
			if((rotation + 2) % 4 == 0) {
				Cell[] top = {b,c,d};
				return top;
			} else if ((rotation + 2) % 4 == 3) {
				Cell[] top = {a,b};
				return top;
			} else if ((rotation + 2) % 4 == 2) {
				Cell[] top = {a,b,d};
				return top;
			} else {
				Cell[] top = {a,d};
				return top;
			}
		} else {
			return cells;
		}
		
	}
	
	public Cell[] getRightCells() {
		if(shape.equals("Line")) {
			if(rotation % 4 == 0) {
				Cell[] right = {d};
				return right;
			} else if(rotation % 4 == 2) {
				Cell[] right = {a};
				return right;
			} else {
				return cells;
			}
			
		} else if (shape.equals("L")) {
			if(rotation % 4 == 0) {
				Cell[] right = {a,d};
				return right;
			} else if (rotation % 4 == 3) {
				Cell[] right = {b,c,d};
				return right;
			} else if (rotation % 4 == 2) {
				Cell[] right = {a,b};
				return right;
			} else {
				Cell[] right = {a,b,c};
				return right;
			}
		} else if (shape.equals("RevL")) {
			if(rotation % 4 == 0) {
				Cell[] right = {a,d};
				return right;
			} else if (rotation % 4 == 3) {
				Cell[] right = {b,c,d};
				return right;
			} else if (rotation % 4 == 2) {
				Cell[] right = {a,b};
				return right;
			} else {
				Cell[] right = {a,c,d};
				return right;
			}
		} else if (shape.equals("S")) {
			if(rotation % 4 == 0) {
				Cell[] right = {b,d};
				return right;
			} else if (rotation % 4 == 3) {
				Cell[] right = {b,c,d};
				return right;
			} else if (rotation % 4 == 2) {
				Cell[] right = {a,c};
				return right;
			} else {
				Cell[] right = {a,b,c};
				return right;
			}
		} else if (shape.equals("Z")) {
			if(rotation % 4 == 0) {
				Cell[] right = {b,d};
				return right;
			} else if (rotation % 4 == 3) {
				Cell[] right = {a,c,d};
				return right;
			} else if (rotation % 4 == 2) {
				Cell[] right = {a,c};
				return right;
			} else {
				Cell[] right = {a,b,d};
				return right;
			}
		} else if (shape.equals("Square")) {
			if(rotation % 4 == 0) {
				Cell[] right = {b,d};
				return right;
			} else if (rotation % 4 == 3) {
				Cell[] right = {c,d};
				return right;
			} else if (rotation % 4 == 2) {
				Cell[] right = {a,c};
				return right;
			} else {
				Cell[] right = {a,b};
				return right;
			}
		} else if (shape.equals("T")) {
			if(rotation % 4 == 0) {
				Cell[] right = {a,d};
				return right;
			} else if (rotation % 4 == 3) {
				Cell[] right = {b,c,d};
				return right;
			} else if (rotation % 4 == 2) {
				Cell[] right = {a,b};
				return right;
			} else {
				Cell[] right = {a,b,d};
				return right;
			}
		} else {
			return cells;
		}
	}
	
	public Cell[] getLeftCells() {
		if(shape.equals("Line")) {
			if(rotation % 4 == 0) {
				Cell[] left = {a};
				return left;
			} else if(rotation % 4 == 2) {
				Cell[] left = {d};
				return left;
			} else {
				return cells;
			}
			
		} else if (shape.equals("L")) {
			if(rotation % 4 == 0) {
				Cell[] left = {a,b};
				return left;
			} else if (rotation % 4 == 3) {
				Cell[] left = {a,b,c};
				return left;
			} else if (rotation % 4 == 2) {
				Cell[] left = {a,d};
				return left;
			} else {
				Cell[] left = {b,c,d};
				return left;
			}
		} else if (shape.equals("RevL")) {
			if(rotation % 4 == 0) {
				Cell[] left = {a,b};
				return left;
			} else if (rotation % 4 == 3) {
				Cell[] left = {a,c,d};
				return left;
			} else if (rotation % 4 == 2) {
				Cell[] left = {a,d};
				return left;
			} else {
				Cell[] left = {b,c,d};
				return left;
			}
		} else if (shape.equals("S")) {
			if(rotation % 4 == 0) {
				Cell[] left = {a,c};
				return left;
			} else if (rotation % 4 == 3) {
				Cell[] left = {a,b,c};
				return left;
			} else if (rotation % 4 == 2) {
				Cell[] left = {b,d};
				return left;
			} else {
				Cell[] left = {b,c,d};
				return left;
			}
		} else if (shape.equals("Z")) {
			if(rotation % 4 == 0) {
				Cell[] left = {a,c};
				return left;
			} else if (rotation % 4 == 3) {
				Cell[] left = {a,b,d};
				return left;
			} else if (rotation % 4 == 2) {
				Cell[] left = {b,d};
				return left;
			} else {
				Cell[] left = {a,c,d};
				return left;
			}
		} else if (shape.equals("Square")) {
			if(rotation % 4 == 0) {
				Cell[] left = {a,c};
				return left;
			} else if (rotation % 4 == 3) {
				Cell[] left = {a,b};
				return left;
			} else if (rotation % 4 == 2) {
				Cell[] left = {b,d};
				return left;
			} else {
				Cell[] left = {c,d};
				return left;
			}
		} else if (shape.equals("T")) {
			if(rotation % 4 == 0) {
				Cell[] left = {a,b};
				return left;
			} else if (rotation % 4 == 3) {
				Cell[] left = {a,b,d};
				return left;
			} else if (rotation % 4 == 2) {
				Cell[] left = {a,d};
				return left;
			} else {
				Cell[] left = {b,c,d};
				return left;
			}
		} else {
			return cells;
		}
	}
}
