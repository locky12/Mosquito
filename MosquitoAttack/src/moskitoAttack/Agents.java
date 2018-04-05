package moskitoAttack;

public abstract class Agents {

	protected static final String 	CLASSE_MOSQUITO		= moskitoAttack.Mosquito.class.getName();
	protected static final String 	CLASSE_HUMAIN		= moskitoAttack.Humain.class.getName();

	public static MersenneTwister 	rand 				= new MersenneTwister();

	// Position actuelle
	protected int 					x;
	protected int 					y;

	// Sauvegarde de la position precedente lors d'un deplacement
	protected int 					copyX;
	protected int 					copyY;

	// Variable de comportement;
	protected boolean 				fille 				= false;
	protected boolean 				infecte 			= false;
	protected boolean 				mort 				= false;
	protected boolean 				aBebe 				= false;

	// Resultat;
	private static final int		NB_COL 				= 6;
	private static final int		NB_LIGNE 			= 10;

	// Contient les resultats des differents indicateurs pour chaque simulation
	protected static int[][]		matriceResultat		= new int[NB_LIGNE + 3][NB_COL];

	// Stock le resultat des calculs statistiques a l'issue de l'experience globale
	protected static double[][]		matriceAnalyse		= new double[4][NB_COL];
	// 0 Ligne pour stocker la moyenne
	// 1 Ligne pour stocker la variance
	// 2 Ligne pour stocker l'ecart-type
	// 3 Ligne pour stocker l'intervalle de confiance

	protected static int			nbSimu				= 0;

	// Colonne des matrices de resultat;
	protected static final int		COL_MORT_H 			= 0;
	protected static final int		COL_NAISSANCE_H 	= 1;
	protected static final int		COL_MORT_M 			= 2;
	protected static final int		COL_NAISSANCE_M 	= 3;
	protected static final int		COL_INFECTE 		= 4;
	protected static final int		COL_M_INFECTE		= 5;


	/*** Constructeurs ***/
	public Agents(int x, int y) {
		this.x	    = x;
		this.y	    = y;
		this.fille  = rand.nextBoolean();
	}

	public Agents() {
		this.x	    = generatorPosition(0, Play.SIZE);
		this.y	    = generatorPosition(0, Play.SIZE);
		this.fille  = rand.nextBoolean();
	}

	/*** Methodes ***/
	/* Redefinies dans Humain et Mosquito */
	public abstract boolean killAgent(Agents[][] array);

	public abstract boolean naissance(Agents agent);

	public abstract void mortAgent(Agents[][] array);

	/* Met a jour les attributs de sauvegarde de la position et la modifie */
	public void changePosition() {
		this.copyX = x;
		this.copyY = y;
		newPosition();
	}

	/* Insere la nouvelle position d'un agent en la generant aleatoirement */
	public void newPosition() {
		int newX    = generatorPosition(this.x + 2, this.x - 2);
		int newY    = generatorPosition(this.y + 2, this.y - 2);

		this.x	    = (Play.SIZE + newX) % Play.SIZE;
		this.y	    = (Play.SIZE + newY) % Play.SIZE;
	}

	/*******************************************************************
	 * @param max:	valeur maximale possible
	 * @param min:  valeur minimale possible
	 * 
	 * @return nombre pseudo aleatoire borne par min et max
	 *******************************************************************/
	public int generatorPosition(int max, int min) {
		double position = 0;
		position	= rand.nextDouble() * (max - min + 1) + min;

		return (int) position;
	}

	/*******************************************************************
	 * @param agent:  agent dont on compare la position avec this
	 * @return False si 'agent' est sur la positon de this. True sinon
	 *******************************************************************/
	public boolean hasPositionValide(Agents agent) {

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
	 * TODO Renommer en 'hasPositionDifferente' ?
	 * 
	 * @return True si la position actuelle est differente par rapport a la
	 *         precedente, False sinon
	 ********************************************************************/
	public boolean hasPositionDifferente() {
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

	/**
	 * Affiche: 
	 * 	'matriceResultat' contenant les valeurs des indicateurs de notre simulation
	 * 	'matriceAnalyse'  contenant les analyses statistiques sur ces indicateurs
	 */
	public static void afficheResultatMatrice() {

		System.out.println(
				"\n\n************************************************ Resultat ************************************************\n");
		System.out.print("Simulation" + "\tH_dead\t\t" + "H_born\t\t" + "M_dead\t\t" + "M_born\t\t" + " H_infecte\t"
				+ "M_infecte\t\n");

		for (int i = 0; i < NB_LIGNE; i++) {
			System.out.printf("%d)\t\t", i + 1);
			for (int j = 0; j < NB_COL; j++) {

				System.out.print("" + matriceResultat[i][j] + " \t\t ");
			}
			System.out.println("");
		}

		calculMoyenne();
		System.out.print("\nMoyenne\t\t");
		for (int j = 0; j < NB_COL; j++) {
			System.out.printf("%.2f\t\t", matriceAnalyse[0][j]);
		}

		calculVariance();
		calculEcartType();
		System.out.print("\nEcart Type\t");
		for (int j = 0; j < NB_COL; j++) {
			System.out.printf("%.2f\t\t", matriceAnalyse[2][j]);
		}

		calculIntervalleConfiance();
		System.out.print("\nIntervConfiance\t");
		for (int j = 0; j < NB_COL; j++) {
			System.out.printf("%.2f\t\t", matriceAnalyse[3][j]);
		}
	}

	/*** Methodes de calcul statistique et d'analyse ***/
	/* Calcule la moyenne de chaque indicateur sur l'ensemble des simulations */
	public static void calculMoyenne() {
		double moyenne = 0;
		for (int x = 0; x < NB_COL; x++) {

			moyenne = 0;


			// Reinitialisation de la moyenne pour la colonne suivante
			moyenne	= 0;

			for (int i = 0; i < NB_LIGNE; i++) { // Parcours du resultat de chaque simulation
				moyenne += matriceResultat[i][x];
			}

			moyenne /= NB_LIGNE;
			matriceAnalyse[0][x] = moyenne;
		}
	}

	/* Calcule la variance (CalculMoyenne() est necessaire au prealable) */
	public static void calculVariance() {
		double variance;
		double moyenne;

		for (int x = 0; x < NB_COL; x++) { // Parcours de chaque mesure
			variance	= 0;
			moyenne	= matriceAnalyse[0][x];

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

	/* Calcule l'ecart type a partir de la variance */
	public static void calculEcartType() {
		for (int x = 0; x < NB_COL; x++) {
			matriceAnalyse[2][x] = Math.sqrt(matriceAnalyse[1][x]);
		}
	}

	/* Calcule l'intervalle de confiance */
	public static void calculIntervalleConfiance () {

		for (int x = 0; x < NB_COL; x++) {
			matriceAnalyse[3][x] = 2.228 * Math.sqrt((matriceAnalyse[1][x]/NB_LIGNE));
		}	

	}

	/*** Getters and Setters ***/
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean isFille() {
		return fille;
	}

	public void setFille(boolean estFille) {
		this.fille = estFille;
	}

	public void setCopyXY(int x, int y) {
		this.copyX = x;
		this.copyY = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isInfecte() {
		return infecte;
	}

	public void setInfecte(boolean infecte) {
		this.infecte = infecte;
	}

	public int getCopyX() {
		return copyX;
	}

	public void setCopyX(int copyX) {
		this.copyX = copyX;
	}

	public int getCopyY() {
		return copyY;
	}

	public void setCopyY(int copyY) {
		this.copyY = copyY;
	}

	public boolean isMort() {
		return mort;
	}

	public void setMort(boolean estMort) {
		this.mort = estMort;
	}

	public void setaBebe(boolean aBebe) {
		this.aBebe = aBebe;
	}

	public static void setNbSimu(int nombre) {
		nbSimu	    = nombre;
	}
	public static String getClasseMosquito() {
		return CLASSE_MOSQUITO;
	}

	public static String getClasseHumain() {
		return CLASSE_HUMAIN;
	}

	public void contagion(Agents voisin) {

	}
}
