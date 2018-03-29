package moskitoAttack;

public class Mosquito extends Agents {

	public Mosquito (int i, int j) {
		super(i,j);
	}
	public Mosquito () {
		super();
		this.infecte = rand.nextBoolean();
		
		
	}
	public Mosquito (boolean infecte) {
		super ();
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
	@Override
	protected void Generate() {
		// TODO Auto-generated method stub
		
	}
}
