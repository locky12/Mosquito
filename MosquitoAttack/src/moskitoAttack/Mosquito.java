package moskitoAttack;

public class Mosquito extends Agents {

	private static final int 	MAX_VIE 	= 30;
	private static final int 	AGE_MIN_N 	= 4;
	
	private int 				jourVie     = 0;
	

	/* Constructeur de depart */
	public Mosquito() {
		super();
		this.infecte = rand.nextBoolean();
	}

	/* Constructeur pour la reproduction du moustique */
	public Mosquito(int x, int y, boolean infecte) {
		super(x, y);
		this.infecte = infecte;

	}

	/******** Methodes *********/

	public void contagion(Agents agent) {
		if (agent.getClass().getName() == CLASSE_HUMAIN && this.mort == false) {
			double chance = rand.nextDouble();
			
			System.out.println("chance  : "+ chance );	
			
			if (agent.infecte == false && this.infecte == true && chance < 0.93) {
				System.out.println("un humain a infecte un moustique");
				agent.infecte = true;
				compteInfecteParMoustique();
			} else if (agent.infecte == false && this.infecte == true &&chance > 0.93 && chance < 0.95) {
				System.out.println("Un moustique s'est fait tuer par un humain");
				this.mort = true;
			} else if (agent.infecte == false && this.infecte == true && chance > 0.95) {
				this.mort = true;
				agent.infecte = true;
				compteInfecteParMoustique();
			}
			// Moustique non infecte pique un humain infecte
			if (agent.infecte == true && this.infecte == false && chance < 0.90) {
				System.out.println("Un moustique a infecte un humain");
				compteMoustiqueParHumain();
				this.infecte = true;
			} else if (agent.infecte == true && chance > 0.90) {
				this.mort = true;
				System.out.println("Un moustique s'est fait tuer par un humain");
			}

		}
	}
	
	@Override
	/*******************************************************************
	 * mortAgent:		Supprime l'agent de la liste des agents 'array'
	 * 					Et incrementre le nombre de deces de son espece
	 * 
	 * @param array:	Liste des agents present dans la matrice
	 *******************************************************************/
	public void mortAgent (Agents[][] array) {
		array[this.getX()][this.getY()] = null;
		compteMortMoustique();
	}
	
	@Override
	/*******************************************************************
	 * killAgent:		Incremente le nombre de jours vecu par l'agent
	 * 					Si ce nombre atteint la limite fixee, il meurt
	 * 
	 * @param array:	Liste des agents present dans la matrice
	 * @return 			True si l'agent meurt, false sinon
	 *******************************************************************/
	public boolean killAgent(Agents[][] array) {
		this.jourVie++;
		if (this.jourVie == MAX_VIE) {
			System.out.println("un moustique est mort");
			mortAgent(array);
			return true;
		}
		return false;

	}

	@Override
	/*******************************************************************
	 * @param agent:	Agent avec qui l'agent this tente de s'accoupler
	 * @return 			True si l'accouplement donne une naissance,
	 * 					False sinon
	 *******************************************************************/
	public boolean naissance(Agents agent) {
		double chance = rand.nextDouble();
		if (chance > 0.50 && agent.fille == false && this.aBebe == false
				&& agent.getClass().getName() == CLASSE_MOSQUITO && this.jourVie > AGE_MIN_N) {
			System.out.println("un moustique est nee");
			compteNaissanceMoustique ();
			this.aBebe = true;
			return true;
		}
		return false;
	}

	/*** Methodes de comptabilisation ***/
	
	/* Compte le nombre de mort humain */
	private void compteMortMoustique() {
		matriceResultat[nbSimu][COL_MORT_M] += 1;
	}

	/* Compte le nombre de naissances */
	private void compteNaissanceMoustique() {
		matriceResultat [nbSimu][COL_NAISSANCE_M] += 1;
	}

	private void compteInfecteParMoustique() {
		matriceResultat[nbSimu][COL_INFECTE] += 1;
	}

	private void compteMoustiqueParHumain() {
		matriceResultat [nbSimu][COL_M_INFECTE] += 1;
	}

	@Override
	public String toString() {
		if (this.fille == true) {
			if (this.infecte == true) {
				return "F";
			} else {
				return "f";
			}
		} else {
			return "m";
		}
	}

	/* Getters ans Setters */
	public int getJourVie() {
		return jourVie;
	}

	public void setJourVie(int jourVie) {
		this.jourVie = jourVie;
	}
}
