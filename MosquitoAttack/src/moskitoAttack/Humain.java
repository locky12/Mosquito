package moskitoAttack;

public class Humain extends Agents {
	
	private int nbJourInfecte = 0;
	private static final int MAX_JOUR_INFECTE = 25;
	
	/*Constructeur */
	public Humain () {
		super ();
	}
	public Humain (int x, int y) {
		super(x,y);
	}
	
	
	/* Methodes */
	// The best méthode : la plus sanguinaire
	@Override
	public boolean killAgent(Agents[][] array) {
		double comportement = rand.nextDouble();
		if (this.infecte == true) {
			this.nbJourInfecte++;
			if (comportement < 0.1) {
				array[this.x][this.y] = null;
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
		System.out.println("chance : " + chance);
		//System.out.println("agent : "+ this + " |voisin : " + agent);
		if (chance > 0.90 && agent.estFille == false && this.aBebe == false && agent.getClass().getName() == CLASSE_HUMAIN) {
			System.out.println("Un humain est nee");
			this.aBebe = true;
			return true;
			
			//play.addListNext(new Humain ());
		}
		return false;
	}
	
/* *****************ToString ********************/
	@Override
	public String toString () {
		if (this.estFille == true) { 
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