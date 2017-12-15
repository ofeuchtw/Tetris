package tetris;

import java.awt.BorderLayout;
//import java.awt.Color;

import java.awt.Color;
import java.awt.Font;
//import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class Tetris extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel statusbar;


    public Tetris() throws IOException {

        statusbar = new JLabel("0");
        statusbar.setHorizontalAlignment(JLabel.LEFT);
        Font f = new Font(Font.MONOSPACED, Font.BOLD, 14);
		statusbar.setFont(f);
		statusbar.setForeground(new Color(232, 157, 191));
        add(statusbar, BorderLayout.SOUTH);
        GamePlay game = new GamePlay(this);
        add(game);
        game.start();

        setSize(200, 440);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        

//    	setBackground(Color.black);
   }

   public JLabel getStatusBar() {
       return statusbar;
   }

    public static void main(String[] args) throws IOException {

    	Tetris game = new Tetris();
        game.setLocationRelativeTo(null);
        game.setVisible(true);

    } 
}
