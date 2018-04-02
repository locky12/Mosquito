
package moskitoAttack;

import java.util.ArrayList;

public abstract class Agents {
	public static final int SIZE 					= 40;
	protected static final 	String CLASSE_MOSQUITO 	= moskitoAttack.Mosquito.class.getName();
	protected static final 	String CLASSE_HUMAIN 	= moskitoAttack.Humain.class.getName() ;
	

	public static MersenneTwister rand 				= new MersenneTwister();
	
	

	// Position actuelle
	protected int 		x;
	protected int 		y;
	
	// Sauvegarde de la position precedente lors d'un deplacement
	protected int 		copyX;					
	protected int 		copyY;
	
	
	// Variable de comportement;
	protected boolean 	estFille 	 	 = false;
	protected boolean 	infecte  		 = false;
	protected boolean 	estMort 		 = false;
	protected boolean 	aBebe 			 = false;
	
	// Resultat;
	private static final int NB_COL 	= 6;
	private static final int NB_LIGNE 	= 10;
	
	protected static int [][] 	matriceResultat = new int [NB_LIGNE][NB_COL];
	
	protected static int 	   	nbSimu   		 = 0;
	// colonne des matrices de resultat;
	protected static final int COL_MORT_H   	= 0;
	protected static final int COL_NAISSANCE_H 	= 1;
	protected static final int COL_MORT_M   	= 2;
	protected static final int COL_NAISSANCE_M 	= 3;
	protected static final int COL_INFECTE 		= 4;
	protected static final int COL_M_INFECTE 	= 5;
	
	public static void setNbSimu (int nombre) {
		nbSimu = nombre;
	}
	


	public Agents (int x, int y) {
		this.x = x;
		this.y = y;
		this.estFille = rand.nextBoolean();
	}
	
	/* Constructeur */
	public Agents() {
		this.x = generatorPosition(0, SIZE);
		this.y = generatorPosition(0, SIZE);
		this.estFille = rand.nextBoolean();
	}

	/* Methode */
	//TODO a voir dans humain et moustqiue
	public abstract boolean killAgent(Agents[][] array);
	public abstract boolean naissance (Agents agent);
	public abstract void mortAgent (Agents [][]  array);
	

	/*public void modifierPosition() {
		this.x = generateX();
		this.y = generateY();
	}*/
	
	/* crée une copie de la position avant de la changer */
	public void changePosition () {
		this.copyX = x;
		this.copyY = y;
		newPosition();
	}

	
	
	// insere la nouvelle position d'un agent en la generant aleatoirement
	public void newPosition () {
		int newX = generatorPosition(this.x+2, this.x-2);
		int newY = generatorPosition(this.y+2, this.y-2);
	
		this.x = (SIZE + newX) % SIZE;
		this.y = (SIZE + newY) % SIZE;
	}

	/*******************************************************************
	 * TODO renommer en generateInt ?
	 * @param max: valeur maximale possible
	 * @param min: valeur minimale possible
	 * @return nombre pseudo aleatoire borne par min et max
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

		if ((x == agent.x && y != agent.y)) {
			return true;
		}
		if ((x != agent.x && y == agent.y)) {
			return true;
		}
		if ((x != agent.x && y != agent.y)) {
			return true;
		}

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
	
	
	// Affichage de la matrice de resultat
	
	public static void afficheResultatMatrice () {
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j <6; j++ ) {
				System.out.print(" " + matriceResultat[i][j] + " ");
			}
			System.out.println("");
		}
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
	
	public boolean isEstMort() {
		return estMort;
	}

	public void setEstMort(boolean estMort) {
		this.estMort = estMort;
	}

	public void setaBebe(boolean aBebe) {
		this.aBebe = aBebe;
	}

	
	public static String getClasseMosquito() {
		return CLASSE_MOSQUITO;
	}

	public static String getClasseHumain() {
		return CLASSE_HUMAIN;
	}

	public void contagion(Agents agent) {
		// TODO Auto-generated method stub
		
	}
		
}
