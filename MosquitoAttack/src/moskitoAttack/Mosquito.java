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

	@Override
	public void contagion(Agents agent) {
		if (agent.getClass().getName() == CLASSE_HUMAIN && this.estMort == false) {
			double chance = rand.nextDouble();
			
			System.out.println("chance  : "+ chance );	
			
			if (agent.infecte == false && this.infecte == true && chance < 0.93) {
				System.out.println("un humain a infecte un moustique");
				agent.infecte = true;
				compteInfecteParMoustique();
			} else if (agent.infecte == false && this.infecte == true &&chance > 0.93 && chance < 0.95) {
				System.out.println("Un moustique c'est fait ecraser par un humain");
				this.estMort = true;
			} else if (agent.infecte == false && this.infecte == true && chance > 0.95) {
				this.estMort = true;
				agent.infecte = true;
				compteInfecteParMoustique();
			}
			// Moustique non infecte pique un humain infecte
			if (agent.infecte == true && this.infecte == false && chance < 0.90) {
				System.out.println("Un moustique a infecte un humain");
				compteMoustiqueParHumain();
				this.infecte = true;
			} else if (agent.infecte == true && chance > 0.90) {
				this.estMort = true;
				System.out.println("Un moustique c'est fait ecraser par un humain");
			}

		}
	}
	
	@Override
	public void mortAgent (Agents[][] array) {
		array[this.getX()][this.getY()] = null;
		compteMortMoustique();
	}
	
	@Override
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
	public boolean naissance(Agents agent) {
		double chance = rand.nextDouble();
		if (chance > 0.50 && agent.estFille == false && this.aBebe == false
				&& agent.getClass().getName() == CLASSE_MOSQUITO && this.jourVie > AGE_MIN_N) {
			System.out.println("un moustique est nee");
			compteNaissanceMoustique ();
			this.aBebe = true;
			return true;
		}
		return false;
	}

	// methode de resultat pour les humains

	// compte le nombre de mort humain;

	private void compteMortMoustique() {
		matriceResultat[nbSimu][COL_MORT_M] += 1;
	}

	// compte le nombre de naissance
	private void compteNaissanceMoustique() {
		matriceResultat [nbSimu][COL_NAISSANCE_M] += 1;
	}

	private void compteInfecteParMoustique() {
		matriceResultat[nbSimu][COL_INFECTE] += 1;
	}

	private void compteMoustiqueParHumain() {
		matriceResultat [nbSimu][COL_M_INFECTE] += 1;
	}

	/*********************************************/
	@Override
	public String toString() {
		if (this.estFille == true) {
			if (this.infecte == true) {
				return "F";
			} else {
				return "f";
			}
		} else {
			return "m";
		}
	}
	
	
	

	// getter & setter
	public int getJourVie() {
		return jourVie;
	}

	public void setJourVie(int jourVie) {
		this.jourVie = jourVie;
	}

}
