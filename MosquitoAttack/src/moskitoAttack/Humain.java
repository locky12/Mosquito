package moskitoAttack;

public class Humain extends Agents {
	

	public Humain () {
		super ();
	}
	
	public Humain (int i, int j) {
		super (i,j);
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

	@Override
	protected void Generate() {
		// TODO Auto-generated method stub
		
	}
}