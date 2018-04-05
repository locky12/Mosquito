
package moskitoAttack;



public abstract class Agents {

	protected static final String 	CLASSE_MOSQUITO = moskitoAttack.Mosquito.class.getName();
	protected static final String 	CLASSE_HUMAIN 	= moskitoAttack.Humain.class.getName();

	public static MersenneTwister 	rand 		= new MersenneTwister();

	// Position actuelle
	protected int x;
	protected int y;

	// Sauvegarde de la position precedente lors d'un deplacement
	protected int copyX;
	protected int copyY;

	// Variable de comportement;
	protected boolean 			estFille 		= false;
	protected boolean 			infecte 		= false;
	protected boolean 			estMort 		= false;
	protected boolean 			aBebe 			= false;

	// Resultat;
	private static final int 	NB_COL 			= 6;
	private static final int 	NB_LIGNE 		= 10;

	// Contient les resultats des differents indicateurs pour chaque simulation
	protected static int[][] 	matriceResultat = new int[NB_LIGNE + 3][NB_COL];

	// Stock le resultat des calculs statistiques a l'issue de l'experience globale
	protected static double[][] matriceAnalyse 	= new double[3][NB_COL];
	// 0 Ligne pour stocker la moyenne
	// 1 Ligne pour stocker la variance
	// 2 Ligne pour stocker l'ecart-type

	protected static int nbSimu = 0;
	// colonne des matrices de resultat;
	protected static final int COL_MORT_H 		= 0;
	protected static final int COL_NAISSANCE_H 	= 1;
	protected static final int COL_MORT_M 		= 2;
	protected static final int COL_NAISSANCE_M 	= 3;
	protected static final int COL_INFECTE 		= 4;
	protected static final int COL_M_INFECTE 	= 5;

	public static void setNbSimu(int nombre) {
		nbSimu = nombre;
	}

	public Agents(int x, int y) {
		this.x = x;
		this.y = y;
		this.estFille = rand.nextBoolean();
	}

	/* Constructeur */
	public Agents() {
		this.x = generatorPosition(0, Play.SIZE);
		this.y = generatorPosition(0, Play.SIZE);
		this.estFille = rand.nextBoolean();
	}

	/* Methodes */
	// Redefinies dans Humain et Mosquito
	public abstract boolean killAgent(Agents[][] array);

	public abstract boolean naissance(Agents agent);

	public abstract void mortAgent(Agents[][] array);

	/*
	 * public void modifierPosition() { this.x = generateX(); this.y = generateY();
	 * }
	 */

	/* cree une copie de la position avant de la changer */
	public void changePosition() {
		this.copyX = x;
		this.copyY = y;
		newPosition();
	}

	// insere la nouvelle position d'un agent en la generant aleatoirement
	public void newPosition() {
		int newX = generatorPosition(this.x + 2, this.x - 2);
		int newY = generatorPosition(this.y + 2, this.y - 2);

		this.x = (Play.SIZE + newX) % Play.SIZE;
		this.y = (Play.SIZE + newY) % Play.SIZE;
	}

	/*******************************************************************
	 * TODO renommer en generateInt ?
	 * 
	 * @param max:
	 *            valeur maximale possible
	 * @param min:
	 *            valeur minimale possible
	 * @return nombre pseudo aleatoire borne par min et max
	 *******************************************************************/
	public int generatorPosition(int max, int min) {
		double position = 0;
		position = rand.nextDouble() * (max - min + 1) + min;

		return (int) position;
	}

	/*******************************************************************
	 * TODO Renommer en positionValide ?
	 * 
	 * @param agent:
	 *            agent dont on compare la position avec this
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
	 * 
	 * @return True si la position actuelle est differente par rapport a la
	 *         precedente, False sinon
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

	public static void afficheResultatMatrice() {

		System.out.println(
				"\n\n************************************************ Resultat ************************************************\n");
		System.out.print("Simulation" + "\tH_dead\t\t" + "H_born\t\t" + "M_dead\t\t" + "M_born\t\t" + "H_infecte\t"
				+ "M_infecte\t\n");

		for (int i = 0; i < NB_LIGNE; i++) {
			System.out.printf("%d)\t\t", i + 1);
			for (int j = 0; j < NB_COL; j++) {

				System.out.print("" + matriceResultat[i][j] + "\t\t ");
			}
			System.out.println("");
		}

		// TODO afficher moyenne
		calculMoyenne();
		System.out.print("\nMoyenne\t\t");
		for (int j = 0; j < NB_COL; j++) {
			System.out.printf("%.2f\t\t", matriceAnalyse[0][j]);
		}

		calculVariance();
		System.out.print("\nVariance\t");
		for (int j = 0; j < NB_COL; j++) {
			System.out.printf("%.2f\t\t", matriceAnalyse[1][j]);
		}

		 
		 calculIntervalConfiance();
		 System.out.print("\nIntervalConfiance\t");
		 for (int j = 0; j < NB_COL; j++) {
		 System.out.printf("%.2f\t\t", matriceAnalyse[2][j]);
		 }
	}

	// Calcule la moyenne de chaque indicateur sur l'ensemble des simulations
	public static void calculMoyenne() {
		double moyenne = 0;
		for (int x = 0; x < NB_COL; x++) {
			moyenne = 0;
			for (int i = 0; i < NB_LIGNE; i++) { // Parcours du resultat de chaque simulation
				moyenne += matriceResultat[i][x];
			}

			moyenne /= NB_LIGNE;
			matriceAnalyse[0][x] = moyenne;
		}
	}

	// Calcule la variance (CalculMoyenne() necessaire au prealable)
	public static void calculVariance() {
		double variance;
		double moyenne;

		for (int x = 0; x < NB_COL; x++) { // Parcours de chaque mesure
			variance = 0;
			moyenne = matriceAnalyse[0][x];

			for (int i = 0; i < NB_LIGNE; i++) {
				// Carre de l'ecart a la moyenne de chaque valeur
				// On calcule la somme des valeurs obtenues
				variance += Math.pow(matriceResultat[i][x] - moyenne, 2);
				//System.out.println("Variance = " + variance);
			}

			variance /= (NB_LIGNE - 1);
			matriceAnalyse[1][x] = variance;
		}
	}

	// Calcule l'ecart type a partir de la variance
	public static void calculEcartType() {

		for (int x = 0; x < NB_COL; x++) {
			matriceAnalyse[2][x] = Math.sqrt(matriceAnalyse[1][x]);
		}
	}
	
	
	public static void calculIntervalConfiance () {

		for (int x = 0; x < NB_COL; x++) {
			matriceAnalyse[2][x] = 2.228 * Math.sqrt((matriceAnalyse[1][x]/NB_LIGNE));
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
