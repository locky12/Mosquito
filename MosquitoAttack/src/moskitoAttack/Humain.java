package moskitoAttack;

public class Humain extends Agents {
	
	/*** Attributs ***/
	// Compteur du nombre de jours depuis l'infection de l'humain
	private int 				nbJourInfecte 		= 0;
	
	// Limite du nombre de jours avant la guerison de l'humain
	private static final int 	MAX_JOUR_INFECTE 	= 30;
	
	/*** Constructeurs ***/
	public Humain () {
		super ();
	}
	
	public Humain (int x, int y) {
		super(x,y);
	}
	
	/*** Methodes ***/
	@Override
	/*******************************************************************
	 * killAgent:		Incremente le nombre de jours vecu par l'agent
	 * 					Les agents infectes ont une chance de mourir
	 * 					Si l'agent infecte survit toute la duree de la
	 * 					maladie, il est gueri
	 * 
	 * @param array:	Liste des agents present dans la matrice
	 * @return 			True si l'agent meurt, false sinon
	 *******************************************************************/
	public boolean killAgent(Agents[][] array) {
		double comportement = rand.nextDouble();
		if (this.infecte == true) {
			this.nbJourInfecte++;
			if (comportement < 0.15) {
				mortAgent(array);
				System.out.println("un humain est mort");
					return true;
			}
			
			if(this.nbJourInfecte == MAX_JOUR_INFECTE) {
				System.out.println("un humain est gueri");
				this.infecte = false;
			}
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
		
		if (chance > 0.95 && agent.fille == false && this.aBebe == false && agent.getClass().getName() == CLASSE_HUMAIN) {
			System.out.println("Un humain est nee");
			compteNaissanceHumain ();
			this.aBebe = true;
			return true;
		}
		return false;
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
		compteMortHumain();
	}
	
	/*** Methodes statistiques ***/
	private void compteMortHumain () {
		matriceResultat [nbSimu][COL_MORT_H] += 1;
	}
	//compte le nombre de naissance 
	private void compteNaissanceHumain () {
		matriceResultat [nbSimu][COL_NAISSANCE_H] += 1;
	}
	
	@Override
	public String toString () {
		if (this.fille == true) { 
			if (this.infecte == true) {
				return "G";
			}
			else {
				return "g";
			}
		}
		else {
			if (this.infecte == true) {
				return "B";
			}
			else {
				return "b";
			}
		}
	}
}
