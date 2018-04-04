package moskitoAttack;

import java.util.ArrayList;
import java.util.Scanner;

public class Play {

	/*** Attributs ***/
	// Matrice representant notre experience
	private Agents[][] 			matrix;

	// Dimension de notre matrice
	public static final int 	SIZE 				= 20;
	
	// Nombre total d'agents (Humain ou Moustique) au depart de la simulation
	public static final int 	NB_AGENTS_DEPART  	= 30;
	
	// Nombre de milisecondes ecoulees entre chaque jour de simulation
	// On peut le passer a 2000 pour debuguer par exemple
	private static final int 	TEMPS 				= 1;
	
	// Nombre de simulations souhaite
	private static final int 	NB_SIMU				= 10;
	

	// Affiche les statistiques a l'issue de la journee simulee
	private int[] 				resultat 			= new int[4];
	
	// Tableau qui gere si les indicateurs de chaque simulation sont nuls ou pas
	private boolean[][] 		resultatSimulation 	= new boolean[10][3];
	
	// Liste des agents presents dans notre matrice
	private ArrayList<Agents> 	nextList 			= new ArrayList<Agents>();

	// Instance globale de notre MersenneTwister pour notre classe
	private MersenneTwister rand 					= new MersenneTwister();

	
	/*** Constructeur ***/
	/*******************************************************************
	 * Constructeur de notre instance Play
	 * Initialise un tableau d'agents vide avec une dimension 'SIZE'
	 ********************************************************************/
	public Play() {
		matrix = new Agents[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				matrix[i][j] = null;
			}
		}
	}

	/*** Methodes ***/
	/*******************************************************************
	 * jouer:			Methode principale qui appelle toutes les autres
	 * 					et gere l'ensemble des simulations
	 *******************************************************************/
	public void jouer() {
		int nombreSimu = 0;

		int compteBoucle = 0;
	
		while (nombreSimu < NB_SIMU) {
			System.out.println("***********************nouvelle simulation*******************************");
			Agents.setNbSimu(nombreSimu);
			boolean arret = false;
			reInitMatrix();
			initAleaMat();
			compteBoucle = 0;
			while (arret != true) {

				compteBoucle++;
				parcoursMatrice();

				System.out.println("Nombre de tour de boucle : " + compteBoucle);
				System.out.println(this); // affiche la matrice
				try {
					Thread.sleep(TEMPS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				arret = testArretSimulation(nombreSimu);
				initResultat();
			}
			nombreSimu++;
		}
		MatriceRSimu();
	}

	/* vide la matrice au debut de chaque simulation */
	private void reInitMatrix() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				matrix[i][j] = null;
			}
		}
	}
	
	/*******************************************************************
	 * @param nombreSimu:	Numero actuel de la simulation
	 * 
	 * @return	True si un des indicateur de 'resultatSimulation' = True
	 * 			False sinon	
	 *******************************************************************/ 
	private boolean testArretSimulation(int nombreSimu) {
		System.out.println("nombre simu : " + nombreSimu);
		if (resultat[0] == 0) {
			System.out.println("reslut [0] : " + resultat[0]);

			resultatSimulation[nombreSimu][0] = true;
			return true;
		}
		if (resultat[2] == 0) {
			resultatSimulation[nombreSimu][1] = true;
			return true;
		}
		if (resultat[1] == 0 && resultat[3] == 0) {
			resultatSimulation[nombreSimu][2] = true;
			 return true;
		}
		return false;
	}
	
	/*******************************************************************
	 * resultat:	Analyse si l'agent est un Humain ou un Moustique,
	 * 				s'il est infecte ou non
	 * 				et incremente les indicateurs associes en consquence
	 * 
	 * @param agent:	Agent dont on recupere les indicateurs
	 *******************************************************************/
	private void resultat(Agents agent) {

		if (agent.getClass().getName() == Agents.getClasseHumain()) {
			resultat[0] ++;
			if (agent.infecte == true) {
				resultat[1] ++;
			}
		}
		if (agent.getClass().getName() == Agents.getClasseMosquito()) {
			resultat[2] ++;
			if (agent.infecte == true && agent.isFille() == true) {
				resultat[3] ++;
			}
		}
	}
	
	// initialise le tableau resultat a 0
	private void initResultat() {
		for (int i = 0; i < resultat.length; i++) {
			resultat[i] = 0;
		}
	}
	// affiche le tableau de resultat
	private void afficheResultat() {
		for (int i = 0; i < resultat.length; i++) {
			switch (i) {
			case 0:
				System.out.println("Le nombre d'humain : " + resultat[i]);
				break;
			case 1:
				System.out.println("Le nombre d'humain infecte : " + resultat[i]);
				break;
			case 2:
				System.out.println("Le nombre de moustique : " + resultat[i]);
				break;
			case 3:
				System.out.println("Le nombre de moutique infecte : " + resultat[i]);
				break;

			}
		}
	}

	// parcour la matrice appelle cherche voisin compte le nombre d'agents de
	// moustqiue et humain et le nombre de chaque infecte
	private void parcoursMatrice() {
		int compteAgents = 0;

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (matrix[i][j] != null) {
					compteAgents++;
					resultat(matrix[i][j]);
					if (matrix[i][j].killAgent(matrix) == false) {

						chercheVoisins(matrix[i][j]);

						if (matrix[i][j].isMort() == false) {
							matrix[i][j].setaBebe(false);
							addListe(matrix[i][j]);

						} else {
							if (matrix[i][j].isMort() == true) {
								matrix[i][j].mortAgent(matrix);
							}

						}
					}
				}
			}
		}

		verificationDeplacement();
		afficheResultat();
		System.out.println("Le nombre d'agents est " + compteAgents);
	}

	// Modifie la position et ajoute a la liste
	private void addListe(Agents agent) {
		agent.changePosition();
		nextList.add(agent);
	}

	// parcours les voisins proche et appelle les fonction de comportement sur les
	// voisins
	private void chercheVoisins(Agents agent) {
		int x = 0, y = 0, nombreVoisin = 0;

		for (int i = agent.getX() - 1; i <= agent.getX() + 1; i++) {
			for (int j = agent.getY() - 1; j <= agent.getY() + 1; j++) {
				x = (SIZE + i) % SIZE;
				y = (SIZE + j) % SIZE;
				if (matrix[x][y] != null) {
					nombreVoisin++;
					comportementAgents(agent, matrix[x][y]);
				}
				if (agent.getClass().getName() == Agents.getClasseMosquito() && nombreVoisin > 4) {
					agent.setMort(true);
				}

			}
		}

	}

	// appelle les fonction sur les comportement // TODO : je cree les nouveau
	// moustique la je n'ai pas reussi a ajouter a
	// la liste depuis d'autre class sans : nullexeption pointer
	private void comportementAgents(Agents agent, Agents voisin) {
		if (agent.isFille() == true && agent.isMort() == false) {
			if (voisin.isFille() == false && agent.getClass().getName() == Agents.getClasseHumain()) {
				if (agent.naissance(voisin) == true) {
					nextList.add(new Humain(agent.getX(), agent.getY()));
				}
			}
			if (agent.getClass().getName() == Agents.getClasseMosquito()) {
				if (agent.naissance(voisin) == true) {
					
					nextList.add(new Mosquito(agent.getX(), agent.getY(), false));
				}
				agent.contagion(voisin);
			}
		}
	}

	

	// verifie si la position d'un agent et libre sinon on la change est on
	// reverifie: le changement ressemble a un saute moutons :)
	private void verificationDeplacement() {
		Agents agent = null;
		int verification = 0;

		for (int index = 0; index < nextList.size(); index++) {
			agent = nextList.get(index);
			verification = 0;
			for (Agents agentControl : nextList) {

				if (agent.hasPositionValide(agentControl)) {
					verification++;

				}
			}
			if (verification == (nextList.size() - 1)) {
				if (agent.hasPositionDifferente() == true) {
					deplaceAgent(agent);
				}
			} else {
				agent.newPosition();
				index--;
			}
		}
		nextList.clear();
	}

	// deplace l'agent a sa nouvelle position et verifie si sont ancienne
	// postition est occupe si libre on libere la case
	private void deplaceAgent(Agents agent) {
		matrix[agent.getX()][agent.getY()] = agent;
		if (matrix[agent.getCopyX()][agent.getCopyY()] == agent) {
			matrix[agent.getCopyX()][agent.getCopyY()] = null;
		}
	}

	

	// genere la population au debut : je changerai surement pour la presentation
	// pour avoir un comportement ""parfait""
	public void initAleaMat() {
		double alea = 0.;
		for (int i = 0; i < 30; i++) {
			alea = rand.nextDouble();

			if (alea > 0.4) {
				nextList.add(new Mosquito());
			} else {
				nextList.add(new Humain());
			}
		}
		compteAgentsDepart();
	}

	@Override
	public String toString() {
		String view = "";
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (matrix[i][j] == null) {
					view = view + " . ";
				} else {
					view = view + " " + matrix[i][j] + " ";
				}
			}
			view = view + "\n";
		}
		return view;
	}

	private void MatriceRSimu() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.println(" " + resultatSimulation[i][j] + " ");
			}
			System.out.println("");
		}
	}

	private void compteAgentsDepart() {
		for (Agents agent : nextList) {
			resultat(agent);
		}
	}


}
