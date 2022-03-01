package tankBattle.view;

import java.awt.Graphics2D;
import java.awt.Image;

import tankBattle.model.component.Bullet;
import tankBattle.utilities.ImageLoader;

/**
 * Questa classe visualizza l'animazione dell'esplosione del proiettile.
 * 
 * @author Simone Spaccarotella
 *
 */
public final class BulletExplosion extends Thread {

	/* la lista dei frame che compongono l'animazione dell'immagine */
	private Image[] view;
	/* il riferimento all'oggetto Graphics */
	private Graphics2D graphics;
	/* il riferimento al proiettile */
	private Bullet bullet;
	/* il riferimento alla vista che visualizza il movimento del proiettile */
	private TankBulletView bulletView;

	/**
	 * Crea un visualizzatore del movimento del proiettile.
	 * 
	 * @param g l'oggetto grafico su cui disegnare l'animazione.
	 * @param b il riferimento al proiettile da disegnare.
	 * @param view il riferimento alla vista del proiettile da visualizzare.
	 */
	public BulletExplosion(Graphics2D g, Bullet b, TankBulletView view) {
		this.graphics = g;
		this.bullet = b;
		this.bulletView = view;
		this.view = ImageLoader.getInstance().getExplosion1();
	}
	
	@Override
	public void run() {
		
		for( Image i : view ) {
			graphics.drawImage(i, (int) bullet.getX() - 50, (int) bullet.getY() - 50, null);
			try {
				Thread.sleep(35);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bulletView.repaint();
		}
	}
	
}
