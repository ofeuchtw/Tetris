package tetris;


import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Scanner;

public class GamePlay extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel statusbar;
	Cell[][] board;
	int score;
	boolean falling;
	boolean started;
	boolean end;
	Timer timer;
	Mino current;
	Cell[] currentCells;
	int[][] currentCoords;
	boolean tetris;
	boolean pauseGrav;
	Scanner in;
	FileReader reader;
	
	public GamePlay(Tetris parent) throws FileNotFoundException {
		
		setFocusable(true);
		statusbar = parent.getStatusBar();
		parent.getContentPane().setBackground(Color.black);
		timer = new Timer(400, this);
		board = new Cell[20][10];
		score = 0;
		started = false;
		falling = false;
		pauseGrav = false;
		end = false;
		addKeyListener(new TAdapter());
		currentCells = new Cell[4];
		currentCoords = new int[4][2];
		
		reader = new FileReader("tscore.txt");
		in = new Scanner(reader);
	}
	
	public void start() throws IOException {
		newPiece();
		falling = true;
		started = true;
		tetris = false;
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(started && !end) {
			timer.stop();
			timer = new Timer(Math.max(400 - (50 * (score / 400)), 75), this);
			timer.start();
			if (!falling) {
				try {
					clearLines();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				update();
				falling = true;
	        } else {
	        	gravity();
	        	update();
	        }
		}
	}
	
	public void newPiece() throws IOException {
		current = new Mino();
		currentCells = current.getCells();
		for(int i = 0; i < currentCells.length; i++) {
			currentCoords[i][0] = currentCells[i].getRow();
			currentCoords[i][1] = currentCells[i].getCol();
		}
		for(Cell c: currentCells) {
			if(board[c.getRow()][c.getCol()] == null) {
				board[c.getRow()][c.getCol()] = c;
			} else {
				end = true;
				int highScore = in.nextInt();
				if(score > highScore) {
					highScore = score;
					File outFile = new File ("score.txt");
				    FileWriter fWriter = new FileWriter (outFile);
				    PrintWriter pWriter = new PrintWriter (fWriter);
				    pWriter.println(highScore);
				    pWriter.close();
				}
				
				statusbar.setText(String.valueOf(score + "     High Score: " + highScore));
				return;
			}
		}
	}
	
	public void gravity() {
		Cell[] bottom = current.getBottomCells();
		if(!pauseGrav) {
			for(Cell c : bottom) {
				if(c.getRow() >= 19) {
					falling = false;
					return;
				}
				Cell nextCell = board[c.getRow() + 1][c.getCol()];
				if(nextCell != null) {
					falling = false;
					return;
				}
			}
			current.shiftDown();
		}
	}
	
	public void update() {
		
		for(int i = 0; i < 4; i++) {
			board[currentCoords[i][0]][currentCoords[i][1]] = null;
		}
		
		currentCells = current.getCells();
		
		int[][] lastCoords = new int[4][2];
		lastCoords = currentCoords.clone();
		boolean outOfBounds = false;
		
		for(int i = 0; i < currentCells.length; i++) {
			if(currentCells[i].getRow() < 0 || currentCells[i].getCol() < 0 || currentCells[i].getRow() > 19 || currentCells[i].getCol() > 9) {
				currentCoords = lastCoords.clone();
				outOfBounds = true;
				break;
			} else {
				currentCoords[i][0] = currentCells[i].getRow();
				currentCoords[i][1] = currentCells[i].getCol();
			}
		}

		if(outOfBounds) {
			for(int j = 0; j < 4; j++) {
				board[currentCoords[j][0]][currentCoords[j][1]] = currentCells[j];
			}
			repaint();
			return;
		}
		for(Cell c: currentCells) {
			board[c.getRow()][c.getCol()] = c;
		}
		repaint();
	}
	
	public void clearLines() throws IOException {
		currentCells = current.getCells();
		int lines = 0;
		for(int i = 0; i < 20; i++) {
			int cells = 0;
			for(Cell c : board[i]) {
				if(c != null) {
					cells++;
				}	
			}
			if(cells == 10) {
				lines++;
				for(int k = i; k > 0 ; k--) {
					for(int l = 0; l < 10; l++) {
						board[k][l] = board[k-1][l];
					}
				}
			}
		}
		if(lines < 4) {
			tetris = false;
			score += 100 * lines;
		} else {
			if(tetris) {
				score += 400;
			}
			tetris = true;
			score += 800;
		}
		statusbar.setText(String.valueOf(score));
		statusbar.setHorizontalAlignment(JLabel.LEFT);
		
		newPiece();
	}
	
	public void drop() {
		pauseGrav = true;
		int drop = 20;
        while (drop > 0) {
            if (!tryMove("down"))
                break;
            --drop;
        }
	}
       
	public boolean tryMove(String move) {
		
		switch(move) {
			case "right":
				Cell[] right = current.getRightCells();
				for(Cell c : right) {
					if(c.getCol() >= 9) {
						return false;
					}  
					Cell nextCell = board[c.getRow()][c.getCol() + 1];
					if(nextCell != null) {
						return false;
					}
				}
				current.shiftRight();
				update();
				return true;
			case "left":
				Cell[] left = current.getLeftCells();
				for(Cell c : left) {
					if(c.getCol() <= 0) {
						return false;
					}  
					Cell nextCell = board[c.getRow()][c.getCol() - 1];
					if(nextCell != null) {
						return false;
					}
				}
				current.shiftLeft();
				update();
				return true;
			case "down":
				pauseGrav = true;
				Cell[] bottom = current.getBottomCells();
				for(Cell c : bottom) {
					if(c.getRow() >= 19) {
						falling = false;
						pauseGrav = false;
						return false;
					}  
					Cell nextCell = board[c.getRow() + 1][c.getCol()];
					if(nextCell != null) {
						falling = false;
						pauseGrav = false;
						return false;
					}
					
				}
				current.shiftDown();
				update();
				pauseGrav = false;
				return true;
			case "rotate":
				Cell center = current.getAxis();
				int axisRow = center.getRow();
				int axisCol = center.getCol();
				if(!current.getShape().equals("Line") && !current.getShape().equals("T")) {
					for(int i = -1; i <= 1; i++) {
						for(int j = -1; j <= 1; j++) {
							boolean same = false;
							if(axisRow + i > 19 || axisRow + i < 0 || axisCol + j > 9 || axisCol + j < 0) {
								return false;
							}
							if(board[axisRow + i][axisCol + j] != null) {
								for(Cell cell: currentCells) {
									if(board[axisRow + i][axisCol + j] == cell) {
										same = true;
									}
								}
								if(!same) {
									return false;
								}
							}
						}
					}
					current.rotateClock();
					update();
					return true;
				} else if(current.getShape().equals("Line")) {
					if(current.getRot() % 4 == 0) {
						if(axisRow < 2 || axisRow > 18) {
							return false;
						}
						for(int i = -2; i <= -1; i++) {
							for(int j = -2; j <= 0; j++) {
								if(board[axisRow + i][axisCol + j] != null) {
									return false;
								}
							}
						}
						if(board[axisRow + 1][axisCol] != null || board[axisRow + 1][axisCol + 1] != null) {
							return false;
						}
					} else if (current.getRot() % 4 == 1) {
						if(axisCol < 1 || axisCol > 7) {
							return false;
						}
						for(int i = -2; i <= 0; i++) {
							for(int j = 1; j <= 2; j++) {
								if(board[axisRow + i][axisCol + j] != null) {
									return false;
								}
							}
						}
						if(board[axisRow][axisCol - 1] != null || board[axisRow + 1][axisCol - 1] != null) {
							return false;
						}
					} else if (current.getRot() % 4 == 2) {
						if(axisRow < 1 || axisRow > 17) {
							return false;
						}
						for(int i = 1; i <= 2; i++) {
							for(int j = 0; j <= 2; j++) {
								if(board[axisRow + i][axisCol + j] != null) {
									return false;
								}
							}
						}
						if(board[axisRow - 1][axisCol] != null || board[axisRow - 1][axisCol - 1] != null) {
							return false;
						}
					} else {
						if(axisCol < 2 || axisCol > 8) {
							return false;
						}
						for(int i = 0; i <= 2; i++) {
							for(int j = -2; j <= -1; j++) {
								if(board[axisRow + i][axisCol + j] != null) {
									return false;
								}
							}
						}
						if(board[axisRow][axisCol + 1] != null || board[axisRow - 1][axisCol + 1] != null) {
							return false;
						}
					}
					current.rotateClock();
					update();
					return true;
					
				} else if(current.getShape().equals("T")) {
					if(current.getRot() % 4 == 0) {
						if(axisRow < 1 || axisRow > 18) {
							return false;
						}
						if(board[axisRow + 1][axisCol] != null) {
							return false;
						}
					} else if (current.getRot() % 4 == 1) {
						if(axisCol < 1 || axisCol > 8) {
							return false;
						}
						if(board[axisRow][axisCol - 1] != null) {
							return false;
						}
					} else if (current.getRot() % 4 == 2) {
						if(axisRow < 1 || axisRow > 18) {
							return false;
						}
						if(board[axisRow - 1][axisCol] != null) {
							return false;
						}
					} else if (current.getRot() % 4 == 3) {
						if(axisCol < 1 || axisCol > 8) {
							return false;
						}
						if(board[axisRow][axisCol + 1] != null) {
							return false;
						}
					}
					current.rotateClock();
					update();
					return true;
				}
			}
			return false;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 10; j++) {
				if(board[i][j] == null) {
					drawSquare(g, j * 20, i * 20, Color.black);
				} else {
					drawSquare(g, j * 20, i * 20, board[i][j].getColor());
				}
			}
		}
	}
	
	public void drawSquare(Graphics g, int row, int col, Color c) {
		if(c == Color.black) {
			g.setColor(c);
			g.fillRect(row , col, 20, 20);
			g.setColor(Color.DARK_GRAY);
	        g.drawLine(row, col + 19, row, col);
	        g.drawLine(row, col, row + 19, col);
		} else {
			g.setColor(c);
	        g.fillRect(row + 1, col + 1, 18, 18);

	        g.setColor(c.brighter());
	        g.drawLine(row, col + 19, row, col);
	        g.drawLine(row, col, row + 19, col);

	        g.setColor(c.darker());
	        g.drawLine(row + 1, col + 19,
	                         row + 19, col + 19);
	        g.drawLine(row + 19, col + 19,
	                         row + 19, col + 1);
		}
	}
	
	class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            if (!started) {  
               return;
            }
            
            int keycode = e.getKeyCode();

//            if (keycode == 'p' || keycode == 'P') {
//                pause();
//                return;
//            }

//            if (isPaused)
//                return;

            switch (keycode) {
            case KeyEvent.VK_LEFT:
                tryMove("left");
                break;
            case KeyEvent.VK_RIGHT:
            	 tryMove("right");
                break;
            case KeyEvent.VK_DOWN:
            	
            	tryMove("down");
           
                break;
            case KeyEvent.VK_UP:
            	 tryMove("rotate");
                break;
            case KeyEvent.VK_SPACE:
                 drop();
                break;
            }

        }
    }

}
