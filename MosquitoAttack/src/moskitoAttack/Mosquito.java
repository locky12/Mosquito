package moskitoAttack;

public class Mosquito extends Agents {

	public Mosquito (int i, int j) {
		super(i,j);
	}
	public Mosquito () {
		super();
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
			return "h";
		}
	}
	@Override
	protected void Generate() {
		// TODO Auto-generated method stub
		
	}
}