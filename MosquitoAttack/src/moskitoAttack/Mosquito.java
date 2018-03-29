package moskitoAttack;

public class Mosquito extends Agents {

	public Mosquito (int i, int j) {
		super(i,j);
	}
	public Mosquito () {
		super();
		this.infecte = rand.nextBoolean();
	}
	public Mosquito ( int x, int y , boolean sexe , boolean infecte) {
		super (x,y,sexe);
		this.infecte = infecte;
	}
	
	
	@Override
	public String toString () {
		if (this.estFille == false) {
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
}
