
package moskitoAttack;

import java.util.ArrayList;

public abstract class Agents {
	public static final int SIZE = 10;
	protected MersenneTwister rand = new MersenneTwister();
	protected int x;
	protected int y;
	protected int copyX;
	protected int copyY;
	protected boolean estFille = false;
	protected boolean infecte = false;
	
	public Agents () {
		
	}
	public Agents (int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Agents (int x, int y ,boolean sexe,boolean infecte ) {
		
	}
	
	public  int generateX () {
		double position = -1;
		int r =0;
		System.out.println("génération de x");
		
		position = rand.nextDouble() *((x+1) - (x-1)+1) + x-1;
		position = (SIZE + (int)position)%SIZE;
		System.out.println("x : " + position);
		return (int)position;
	}
	
	public int generateY () {
		double position = -1;
		position = rand.nextDouble() *((y+1) - (y-1)+1) + y-1;
		position = (SIZE + (int)position)%SIZE;
		System.out.println("y : " + position);
		return (int)position;
	}
	
	public boolean restePosition () {
		if ((x == copyX && y != copyY) || (x!=copyX && y==copyY) || (x!=copyX && y!=copyY) ) {
			return true;
		}
		return false;
	}
	
	protected abstract void Generate ();

	/* Getter and Setter */
	public void setXY (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	
	public boolean isEstFille() {
		return estFille;
	}
	public void setEstFille(boolean estFille) {
		this.estFille = estFille;
	}
	/**
	 * @return the x
	 */
	
	public void setCopyXY (int x, int y) {
		this.copyX = x;
		this.copyY = y;
	}
	
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**

	/**
	 * @return the infecte
	 */
	public boolean isInfecte() {
		return infecte;
	}
	/**
	 * @param infecte the infecte to set
	 */
	public void setInfecte(boolean infecte) {
		this.infecte = infecte;
	}
	/**
	 * @return the copyX
	 */
	public int getCopyX() {
		return copyX;
	}
	/**
	 * @param copyX the copyX to set
	 */
	public void setCopyX(int copyX) {
		this.copyX = copyX;
	}
	/**
	 * @return the copyY
	 */
	public int getCopyY() {
		return copyY;
	}
	/**
	 * @param copyY the copyY to set
	 */
	public void setCopyY(int copyY) {
		this.copyY = copyY;
	}
}

