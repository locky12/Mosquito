package moskitoAttack;

public class Mosquito extends Agents {
	
	private static final int MAX_VIE = 30;
	private int jourVie = 0;

	/* Constructeur de depart */
	public Mosquito () {
		super();
		this.infecte = rand.nextBoolean();
	}
	
	/* Constructeur pour la reproduction du moustique */
	public Mosquito (int x, int y, boolean infecte) {
		super (x,y);
		this.infecte = infecte;
		
	}
	
	/******** Methodes *********/
	/*@Override
	public void contagion (Agents agent) {
		double chance = rand.nextDouble();
		if (agent.infecte == false && agent.getClass().getName() == CLASSE_HUMAIN) {
			System.out.println("un humain a infecte un moustique");
			agent.infecte = true;
		}
		else if () {
			
		}
		if (agent.infecte == true && agent.getClass().getName() == CLASSE_HUMAIN) {
			System.out.println("un moustique a un infecte un humain");
			this.infecte = true;
		}
		
		
	}*/
	@Override
	public void contagion (Agents agent) {
		if (agent.getClass().getName() == CLASSE_HUMAIN && this.estMort == false) {
			double chance = rand.nextDouble();
			// un moustique infecte pique un humain infecte
			if (agent.infecte == false && chance > 0.90) {
				System.out.println("un humain a infecte un moustique");
				agent.infecte = true;
			}
			else if (agent.infecte == false && chance >  0.90 && chance <0.95){
				System.out.println("Un moustique c'est fait ecraser par un humain");
				this.estMort = true;
			}
			else if (agent.infecte == false && chance >  0.95) {
				this.estMort = true;
				agent.infecte = true;
			}
			// Moustique non infecte pique un humain infecte
			if (agent.infecte == true && chance < 0.80) {
				System.out.println("un moustique a un infecte un humain");
				this.infecte = true;
			}
			else if (agent.infecte == true && chance > 0.80) {
				this.estMort = true;
				System.out.println("Un moustique c'est fait ecraser par un humain");
			}
			
		}
	}
		
	@Override
	public boolean killAgent(Agents [][] array) {
		this.jourVie++;
		if (this.jourVie == MAX_VIE) {
			System.out.println("un moustique est mort");
			array[this.x][this.y] = null;
			return true;
		}
		return false;
		
	}
	
	@Override
	public boolean naissance(Agents agent) {
		double chance = rand.nextDouble();
		if (chance > 0.50 && agent.estFille == false  && this.aBebe == false && agent.getClass().getName() == CLASSE_MOSQUITO && this.jourVie > 4) {
			System.out.println("un moustique est nee도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도도�");
			this.aBebe = true;
			return true;
		}
		return false;
	}
	/*********************************************/
	@Override
	public String toString () {
		if (this.estFille == true) {
			if (this.infecte == true) {
				return "F";
			}
			else {
				return "f";
			}
		}
		else {
			return "m";
		}
	}
	
	public int getJourVie() {
		return jourVie;
	}
	public void setJourVie(int jourVie) {
		this.jourVie = jourVie;
	}



}

