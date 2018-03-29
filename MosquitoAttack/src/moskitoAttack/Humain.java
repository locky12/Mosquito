package moskitoAttack;

public class Humain extends Agents {
	

	public Humain () {
		super ();
	}
	
	public Humain (int i, int j) {
		super (i,j);
	}
	
	public Humain ( int x, int y , boolean sexe , boolean infecte) {
		super (x,y,sexe);
	}
	
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
