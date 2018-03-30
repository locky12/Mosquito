
package moskitoAttack;

import java.util.ArrayList;

public abstract class Agents {
	public static final int SIZE = 20;
	public static MersenneTwister rand = new MersenneTwister();

	public MersenneTwister getRand() {
		return rand;
	}
	
	// Position actuelle
	protected int x;
	protected int y;
	
	// Sauvegarde de la position precedente lors d'un deplacement
	protected int copyX;					
	protected int copyY;
	
	protected boolean estFille = false;
	protected boolean infecte = false;
	
	
	public Agents() {
		this.x = generatorPosition(0, SIZE);
		this.y = generatorPosition(0, SIZE);
		this.estFille = rand.nextBoolean();
	}

	public Agents(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Agents(int x, int y, boolean sexe) {
		this.x = x;
		this.y = y;
		this.estFille = sexe;
	}

	public void modifierPosition() {
		this.x = generateX();
		this.y = generateY();
	}

	public int generateX() {
		double position = 0;
//		System.out.println("generation de x");

		position = rand.nextDouble() * ((x + 1) - (x - 1) + 1) + x - 1;
		position = (SIZE + (int) position) % SIZE;
//		System.out.println("x : " + position);
		return (int) position;
	}

	// TODO Inutile ? Supprimer ou fusionner avec generateX() ?
	public int generateY() {
		double position = -1;
		position = rand.nextDouble() * ((y + 1) - (y - 1) + 1) + y - 1;
		position = (SIZE + (int) position) % SIZE;
//		System.out.println("y : " + position);
		return (int) position;
	}

	/*******************************************************************
	 * TODO renommer en generateInt ?
	 * @param max: valeur maximale possible
	 * @param min: valeur minimale possible
	 * @return nombre pseudo aleatoire bonre par min et max
	 *******************************************************************/
	public int generatorPosition(int max, int min) {
		double position = 0;
		position = rand.nextDouble() * (max - min + 1) + min;

		return (int) position;
	}

	/*******************************************************************
	 * TODO Renommer en positionValide ?
	 * @param agent: agent dont on compare la position avec this
	 * @return False si 'agent' est sur la positon de this. True sinon
	 *******************************************************************/
	public boolean PositionControle(Agents agent) {
//		System.out.println("this :  x : " + this.x + "y : "+ this.y);
//		System.out.println("Agent param :  x : " + this.x + "y : "+ this.y);
		if ((x == agent.x && y != agent.y)) {
			return true;
		}
		if ((x != agent.x && y == agent.y)) {
			return true;
		}
		if ((x != agent.x && y != agent.y)) {
			return true;
		}
//		System.out.println("******faux ***********");
		return false;
	}

	/********************************************************************
	 * TODO Renommer en 'positionDifferente' ?
	 * @return 	True si la position actuelle est differente par rapport
	 * 			a la precedente, False sinon
	 ********************************************************************/
	public boolean restePosition() {
		if ((x == copyX && y != copyY)) {
			return true;
		}
		if ((x != copyX && y == copyY)) {
			return true;
		}
		if ((x != copyX && y != copyY)) {
			return true;
		}
		return false;
	}
	

	/* Getter and Setter */
	public void setXY(int x, int y) {
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

	public void setCopyXY(int x, int y) {
		this.copyX = x;
		this.copyY = y;
	}

	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
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
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * 
	 * /**
	 * 
	 * @return the infecte
	 */
	public boolean isInfecte() {
		return infecte;
	}

	/**
	 * @param infecte
	 *            the infecte to set
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
	 * @param copyX
	 *            the copyX to set
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
	 * @param copyY
	 *            the copyY to set
	 */
	public void setCopyY(int copyY) {
		this.copyY = copyY;
	}
}
