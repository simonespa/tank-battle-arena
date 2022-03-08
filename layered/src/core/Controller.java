package core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {

	private Tank tank;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	public Controller(Tank tank) {
		this.tank = tank;
		up = down = left = right = false;
	}
	
	private void dispatchEvent() {
		if( up && !left && !right ) {
			tank.moveUp();
		} else if( up && left && !right ) {
			tank.moveUpLeft();
		} else if( up && !left && right ) {
			tank.moveUpRight();
		} else if( down && !left && !right ) {
			tank.moveDown();
		} else if( down && left && !right ) {
			tank.moveDownLeft();
		} else if( down && !left && right ) {
			tank.moveDownRight();
		} else if( left ) {
			tank.moveLeft();
		} else if( right ) {
			tank.moveRight();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if( keyCode == KeyEvent.VK_W ) {
			up = true;
			down = false;
		} else if( keyCode == KeyEvent.VK_S ) {
			down = true;
			up = false;
		} else if( keyCode == KeyEvent.VK_A ) {
			left = true;
			right = false;
		} else if( keyCode == KeyEvent.VK_D ) {
			right = true;
			left = false;
		}
		dispatchEvent();
		tank.repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if( keyCode == KeyEvent.VK_W ) {
			up = false;
		} else if( keyCode == KeyEvent.VK_S ) {
			down = false;
		} else if( keyCode == KeyEvent.VK_A ) {
			left = false;
		} else if( keyCode == KeyEvent.VK_D ) {
			right = false;
		}
	}
	
}