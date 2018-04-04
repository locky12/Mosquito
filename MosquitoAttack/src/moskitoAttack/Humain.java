package moskitoAttack;

public class Humain extends Agents {
	
	private int nbJourInfecte = 0;
	private static final int MAX_JOUR_INFECTE = 30;
	
	/*Constructeur */
	public Humain () {
		super ();
	}
	public Humain (int x, int y) {
		super(x,y);
	}
	
	
	/* Methodes */
	// The best methode : la plus sanguinaire
	@Override
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
	// PErmet de faire des bebes
	@Override
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
	public void mortAgent (Agents[][] array) {
		array[this.getX()][this.getY()] = null;
		compteMortHumain();
	}
	
	// resultat
	
	private void compteMortHumain () {
		matriceResultat [nbSimu][COL_MORT_H] += 1;
	}
	//compte le nombre de naissance 
	private void compteNaissanceHumain () {
		matriceResultat [nbSimu][COL_NAISSANCE_H] += 1;
	}
	
/* *****************ToString ********************/
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
