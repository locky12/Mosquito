package moskitoAttack;

import java.util.ArrayList;
import java.util.Scanner;

public class Play {
	public static final int SIZE = 10;
	private Agents [][] array;
	private Agents agents;
	private ArrayList<Agents> next = new ArrayList<Agents> ();
	
	
	
	public Play () {
		array = new Agents [SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				array[i][j] = null;
			}
		}
	}
		
	public void chercheVoisin(int i, int j) throws ClassNotFoundException {
		for (int l = i-1; l <= i + 1; l++) {
			for (int k = j-1; k <= j+1; k++) {
				int x = (SIZE + l)%SIZE;
				int y = (SIZE + j)%SIZE;
				agents = array[x][y];
				if (i != x && j != l) {
					if(array[i][j].getClass() == Class.forName("Humain"))
						if (array[x][y].getClass() == Class.forName("Humain") ) {
						
						}
						if (array[x][y].getClass() == Class.forName("Mosquito")) {
							
						}
					if(this.getClass() == Class.forName("Mosquito")) {
						if (array[x][y].getClass() == Class.forName("Humain") ) {
							
						}
						if (array[x][y].getClass() == Class.forName("Mosquito")) {
							
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
		System.out.println("move debut");
		x = agents.generateX();
		y = agents.generateY();
		System.out.printf("x : %d, y : %d \n", x , y);
		if (array[x][y] == null) {
			System.out.println(array[i][j]);
			System.out.println("je passe dans move");
			agents.setXY(x,y);
			next.add(agents);
		if(x != i && j != y) {}
			//array[i][j] = null;
		}
		
	}
	public void parcoursMatrice () {
		Scanner scan = new Scanner(System.in);
		int saisie = 1;
		while (saisie != 0) {
			System.out.println(this);
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					
					if (array[i][j] != null) {
						System.out.printf("tab[%d][%d] +  ",i,j);
						System.out.println(array[i][j]);
						moveAgents(i,j);
					}
				}
			}
			controlePosition();
			System.out.println("suivant ?");
			saisie = scan.nextInt();
			//Thread.sleep(2000);
		}
	}
	
	public void initArray () {
		Mosquito mosquito = new Mosquito (2,3);
		array[2][3] = mosquito;
	}
	
	private void InsertAgents () {
		
	
	}
	/* controle les position et deplace si POSITION CORRECT*/
	private void controlePosition() {
		int control;
		System.out.println("taille next : " + next.size());
		for (Agents agent : next) {
			control = 0;
			for (Agents ag : next) {
				if (agents != ag) {
					System.out.println("je passe");
					if (agent.getX() != ag.getX() && agent.getY() != ag.getY()) {
						control ++;
					}
					
				}
				System.out.printf("Agent = x : %d , y : %d \n", agent.getX(),agent.getY());
				System.out.printf("AgentcOPY = x : %d , y : %d \n", agent.getCopyX(),agent.getCopyY());
				System.out.println("controle : " + control);
				if (control == next.size()-1) {
						System.out.println("agent passe le cotrole");
					if (agent.restePosition() == true) {
						System.out.println("l'agent bouge");
						array[agent.getX()][agent.getY()] = agent;
						array[agent.getCopyX()][agent.getCopyY()] = null;
					}
				}
				
				
			}
		}
		next.removeAll(next);
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