package moskitoAttack;

import java.util.ArrayList;
import java.util.Scanner;

public class Play {
	public static final int SIZE = 10;
	private Agents [][] array;
	private Agents agents;
	private ArrayList<Agents> next = new ArrayList<Agents> ();
	private MersenneTwister rand = new MersenneTwister();
	
	
	public Play () {
		array = new Agents [SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				array[i][j] = null;
			}
		}
	}
		
	public void chercheVoisin(int i, int j) throws ClassNotFoundException {
		Agents agent = array[i][j];
		Agents ag;
		for (int l = i-1; l <= i + 1; l++) {
			for (int k = j-1; k <= j+1; k++) {
				int x = (SIZE + l)%SIZE;
				int y = (SIZE + j)%SIZE;

				if (array[x][y] != null) {
					//ag = array[x][y];
				}

				if (i != x && j != l) {
					if (array [x][y] != null) {
						if(agent.getClass().getName() == moskitoAttack.Humain.class.getName() )
							if (array [x][y].getClass().getName() == moskitoAttack.Humain.class.getName() ) {

							}
						if (array [x][y].getClass().getName() == moskitoAttack.Mosquito.class.getName()) {

						}
						if(agent.getClass().getName() == moskitoAttack.Mosquito.class.getName()) {

							if (array [x][y] .getClass().getName() == moskitoAttack.Humain.class.getName() ) {
								if (agent.isInfecte() == true && agent.estFille) {
									array [x][y] .setInfecte(true);
								}
							}
							if (array [x][y] .getClass().getName() == moskitoAttack.Mosquito.class.getName()) {

							}
						}
					}
				}
			}
		}
	}
	
	
	public void moveAgents (int i, int j) {
		int 	x = 0;
		int		y = 0;
		agents = array[i][j];
		agents.setCopyXY(i, j);
//		System.out.println("move debut");
		x = agents.generateX();
		y = agents.generateY();
//		System.out.printf("x : %d, y : %d \n", x , y);
		
//			System.out.println(array[i][j]);
//			System.out.println("je passe dans move");
		agents.setXY(x,y);
		next.add(agents);
		
			//array[i][j] = null;
		
		
	}
	public void parcoursMatrice () {
		Scanner scan = new Scanner(System.in);
		
		int saisie = 1;
		int compteBoucle = 0;
		while (saisie != 0) {
			int compte = 0;
			compteBoucle++;
			System.out.println(this);
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					if (array[i][j] != null) {
						//System.out.printf("tab[%d][%d] +  ",i,j);
						//System.out.println(array[i][j]);
						compte++;
						try {
							chercheVoisin(i,j);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						moveAgents(i,j);
					}
				}
			}
			
			controlePosition();
			System.out.println("Nbre de boucle : " + compteBoucle);
			System.out.println("compte : " + compte);
			System.out.println("suivant ?");
			saisie = scan.nextInt();
			/*try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}
	
	public void initArray () {
		Mosquito mosquito = new Mosquito (2,3);
		array[2][3] = mosquito;
	}
	
	private void InsertAgents () {
		
	
	}
	/* controle les position et deplace si POSITION CORRECT*/
	public void controlePosition() {
//		System.out.println("fonction controle position");
		int control;
		Agents agent;
		Agents ag;
		System.out.println("taille next : " + next.size());
		for (int index = 0; index < next.size(); index++) {
			control = 0;
//			System.out.println(next.get(index));
			agent = next.get(index);
			//System.out.printf("Agent = x : %d , y : %d \n", agent.getX(),agent.getY());
			for (int j = 0; j < next.size(); j++) {
				ag = next.get(j);
				//System.out.println("boucle j :*********");
				if (agent != ag) {
//					System.out.println("je passe");
					//System.out.printf("Agent = x : %d , y : %d \n", agent.getX(),agent.getY());
					//System.out.printf("AgentcOPY = x : %d , y : %d \n", ag.getX(),ag.getY());
					if (agent.PositionControle(ag)) {
						control ++;
						//System.out.printf("Agent = x : %d , y : %d \n", agent.getX(),agent.getY());
						//System.out.printf("AgentcOPY = x : %d , y : %d \n", ag.getX(),ag.getY());
						//System.out.println("controle : " + control);
					}
					
				}
			}
//			System.out.printf("Agent = x : %d , y : %d \n", agent.getX(),agent.getY());
//			System.out.printf("AgentcOPY = x : %d , y : %d \n", ag.getX(),ag.getY());
//			System.out.println("controle : " + control);
//				
			if (control == next.size()-1) {
				//System.out.println("agent passe le cotrole");
				if (agent.restePosition() == true) {
					//System.out.println("l'agent bouge");
					array[agent.getX()][agent.getY()] = agent;
					if (array[agent.getCopyX()][agent.getCopyY()] == agent) {
						array[agent.getCopyX()][agent.getCopyY()] = null;
					}
				}
			}
			else {
				agent.modifierPosition();
				//System.out.printf("AgentMODIFI = x : %d , y : %d \n", agent.getX(),agent.getY());
				//System.out.println("**********la position d'un agent est modifier********");
				index --;
			}
				
				
			
		}
		next.removeAll(next);
	}
	
	private void affichelist () {
		for (Agents i : next) {
			System.out.println("i : " + i.getX() + "j : " + i.getY());
		}
	}
	
	public void initAleaMat () {
		double alea = 0.;
		Humain humain;
		Mosquito mosquito;
		for (int i = 0; i < 15; i++) {
			alea =  rand.nextDouble();
			
			if (alea > 0.5) {
				next.add(new Mosquito());
			}
			else {
				next.add(new Humain());
			}
		}
		affichelist();
	}
	
	@Override
	public String toString () {
		String view = "";
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (array[i][j] == null) {
				view = view + " . ";
				}
				else {
				view = view + " " + array[i][j] + " ";	
				}
			}
			view = view + "\n";
		}
		return view;
	}
}
