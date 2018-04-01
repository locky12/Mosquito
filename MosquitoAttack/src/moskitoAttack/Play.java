package moskitoAttack;

import java.util.ArrayList;
import java.util.Scanner;

public class Play {

	// Matrice representant notre experience
	private Agents[][] array;

	// Dimension de notre matrice de cases
	public static final int SIZE = 40;

	// Variable temporaire globale TODO (Reflechir a la virer)
	private Agents Ag;
	private int[] resultat = new int[4];
	// Liste des agents presents dans notre matrice (Necessaire pour gerer les
	// deplacements)
	private ArrayList<Agents> next = new ArrayList<Agents>();

	// Instance globale de notre MersenneTwister pour notre classe
	private MersenneTwister rand = new MersenneTwister();

	/*******************************************************************
	 * Constructeur de notre instance Play Initialise un tableau d'agents vide et de
	 * dimension 'SIZE'
	 ********************************************************************/
	public Play() {
		array = new Agents[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				array[i][j] = null;
			}
		}
	}

	/************ Methodes ************/

	/*
	 * TODO : methode pour test penser a supprimer **********************
	 * *****************************************************************
	 */

	public void initArray() {
		// Mosquito mosquito = new Mosquito();
		// mosquito.setEstFille(true);
		// mosquito.setInfecte(true);
		// Mosquito mosquito2 = new Mosquito();
		// mosquito2.setEstFille(false);
		// Humain humain = new Humain ();
		// humain.setEstFille(true);
		Humain humain2 = new Humain();
		humain2.setEstFille(false);
		// next.add(mosquito);
		// next.add(mosquito2);
		// next.add(humain);
		next.add(humain2);
	}

	/***************************************************************/

	/* Methode principal */
	public void jouer() {
		int nombreSimu = 0;

		int compteBoucle = 0;
		Scanner scan = new Scanner(System.in);
		// initArray();



		while (nombreSimu < 10) {
			System.out.println("nouvelle simulation");
			nombreSimu++;
			reInitMatrix();
			initAleaMat();
			int saisie = 1;
			while (saisie != 0) {
				initResultat();
				int compte = 0;
				compteBoucle++;
				parcoursMatrice();

				System.out.println("Nombre de tour de boucle : " + compteBoucle);
				System.out.println(this);
				System.out.println("suivant ?");
				saisie = scan.nextInt();
			}
		}
	}
	private void reInitMatrix () {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				array[i][j] = null;
			}
		}
	}

	private void resultat(Agents agent) {

		if (agent.getClass().getName() == Agents.getClasseHumain()) {
			resultat[0] += 1;
			if (agent.infecte == true) {
				resultat[1] += 1;
			}
		}
		if (agent.getClass().getName() == Agents.getClasseMosquito()) {
			resultat[2] += 1;
			if (agent.infecte == true) {
				resultat[3] += 1;
			}
		}
	}

	private void initResultat() {
		for (int i = 0; i < resultat.length; i++) {
			resultat[i] = 0;
		}
	}

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
				if (array[i][j] != null) {
					compteAgents++;
					resultat(array[i][j]);
					if (array[i][j].killAgent(array) == false) {

						chercheVoisins(array[i][j]);

						if (array[i][j].isEstMort() == false) {
							array[i][j].setaBebe(false);
							addListe(array[i][j]);

						}
						else {
							if (array[i][j].isEstMort() == true) {
								array[array[i][j].getX()][array[i][j].getY()] = null;
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
		next.add(agent);
	}

	// parcours les voisins proche et appelle les fonction de comportement sur les
	// voisins
	private void chercheVoisins(Agents agent) {
		int x = 0, y = 0, nombreVoisin = 0;

		for (int i = agent.getX() - 1; i <= agent.getX() + 1; i++) {
			for (int j = agent.getY() - 1; j <= agent.getY() + 1; j++) {
				x = (SIZE + i) % SIZE;
				y = (SIZE + j) % SIZE;
				if (array[x][y] != null) {
					nombreVoisin++;
					comportementAgents(agent, array[x][y]);
				}
				if (agent.getClass().getName() == Agents.getClasseMosquito() && nombreVoisin > 4) {
					agent.setEstMort(true);
				}
				

			}
		}

	}

	// appelle les fonction sur les comportement // TODO : je cree les nouveau
	// moustique la je n'ai pas reussi a ajouter a
	// la liste depuis d'autre class sans : nullexeption pointer
	private void comportementAgents(Agents agent, Agents voisin) {
		if (agent.isEstFille() == true && agent.isEstMort() == false) {
			if (voisin.isEstFille() == false && agent.getClass().getName() == Agents.getClasseHumain()) {
				if (agent.naissance(voisin) == true) {
					next.add(new Humain(agent.getX(), agent.getY()));
				}
			}
			if (agent.getClass().getName() == Agents.getClasseMosquito()) {
				if (agent.naissance(voisin) == true) {
					//generateBabyMosquito (agent);
					next.add(new Mosquito(agent.getX(), agent.getY(), false));
				}
				agent.contagion(voisin);
			}
		}
	}

	// pas sur que j'utilise
	private void generateBabyMosquito(Agents agent) {
		int min = 2, max = 3;
		double nombreBaby = rand.nextDouble() * (max - min + 1);
		System.out.println("nombre de bebe : " + (int) nombreBaby);
		for (int i = 0; i < (int) nombreBaby; i++) {
			next.add(new Mosquito(agent.getX(), agent.getY(), false));
		}

	}

	// verifie si la position d'un agent et libre sinon on la change est on
	// revérifie: le changement ressemble à un saute moutons :)
	private void verificationDeplacement() {
		Agents agent = null;
		int verification = 0;

		for (int index = 0; index < next.size(); index++) {
			agent = next.get(index);
			verification = 0;
			for (Agents agentControl : next) {

				if (agent.PositionControle(agentControl)) {
					verification++;

				}
			}
			if (verification == (next.size() - 1)) {
				if (agent.restePosition() == true) {
					deplaceAgent(agent);
				}
			} else {
				agent.newPosition();
				index--;
			}
		}
		next.clear();
	}

	// deplace l'agent à c=ça nouvelle position et verifie si sont ancienne
	// postition est occupe si libre on libere la case
	private void deplaceAgent(Agents agent) {
		array[agent.getX()][agent.getY()] = agent;
		if (array[agent.getCopyX()][agent.getCopyY()] == agent) {
			array[agent.getCopyX()][agent.getCopyY()] = null;
		}
	}

	// affiche la liste : pour debuger
	private void affichelist() {
		for (Agents i : next) {
			System.out.println("i : " + i.getX() + "j : " + i.getY());
		}
	}

	// genere la population au debut : je changerai surement pour la presentation
	// pour avoir un comportement ""parfait""
	public void initAleaMat() {
		double alea = 0.;
		for (int i = 0; i < 70; i++) {
			alea = rand.nextDouble();

			if (alea > 0.4) {
				next.add(new Mosquito());
			} else {
				next.add(new Humain());
			}
		}
		affichelist();
	}

	@Override
	public String toString() {
		String view = "";
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (array[i][j] == null) {
					view = view + " . ";
				} else {
					view = view + " " + array[i][j] + " ";
				}
			}
			view = view + "\n";
		}
		return view;
	}

	// methode que j'ai jamais reussi a me servir tout est dans le nom
	public void addListNext(Agents agent) {
		next.add(agent);
	}

	// methode que j'ai jamais reussi a me servir tout est dans le nom
	public void concateneList(ArrayList<Agents> naissanceList) {
		next.addAll(naissanceList);
	}

}
