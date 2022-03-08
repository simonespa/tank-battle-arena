package core;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Terreno extends JPanel {

	private int width;
	private int height;
	private Image[] ground;;
	
	public Terreno() {
		super();
		width = Toolkit.getDefaultToolkit().getScreenSize().width;
		height = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(0, 0, width, height);
		setOpaque(false);
		ground = new Image[5];
		for( int i = 0; i < 5; i++ ) {
			ground[i] = new ImageIcon("images/ground/ground-" + i + ".png").getImage();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// determina il numero di colonne nota la larghezza
		// del monitor e la larghezza della texture
		int column = width / 64;
		// se le texture non coprono per intero il monitor
		// viene aggiunta una colonna per riempire lo sfrido
		if( column % 64 != 0 )
			column++;
		// stessa cosa dicasi per  le righe
		int row = height / 64;
		if( row % 64 != 0 )
			row++;
		// questi due cicli di for innestati servono a coprire tutta l'area
		// del monitor
		Random r = new Random(System.currentTimeMillis());
		for( int i = 0; i < column; i++ ) {
			for( int j = 0; j < row; j++ ) {
				g.drawImage(ground[r.nextInt(5)], 64 * i, 64 * j, null);
			}
		}
	}
	
}
