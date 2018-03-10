package fr.utt.lo02.projet.coeur.partie;
import java.util.ArrayList;

import fr.utt.lo02.projet.coeur.carte.JeuDeCartes;
import fr.utt.lo02.projet.coeur.carte.PaquetMilieu;
import fr.utt.lo02.projet.coeur.carte.Pioche;
import fr.utt.lo02.projet.coeur.joueur.Agressif;
import fr.utt.lo02.projet.coeur.joueur.Expert;
import fr.utt.lo02.projet.coeur.joueur.Joueur;
import fr.utt.lo02.projet.coeur.joueur.JoueurReel;
import fr.utt.lo02.projet.coeur.joueur.JoueurVirtuel;
import fr.utt.lo02.projet.coeur.joueur.Novice;
import fr.utt.lo02.projet.vue.Observable;

/**
 * Cette classe represente une partie ainsi que toutes les methodes relatives au deroulement des tour (tourSuivant(), testerFinPartie()). 
 * Cet objet est instancie dans le main et va creer tous les objets necessaires pour une partie, c'est a dire les joueurs, la pioche, le paquet du milieu...
 * Ainsi, on retrouve dans les attributs de cet objet partie tous les autres objets du programme. C'est donc a partir d'une partie qu'on aura
 * acces aux autres objets. Les attributs d'une partie sont :
 * 
 * <ul>
 *  <li> <b>lesJoueurs</b> : une ArrayList composee d'un joueur reel et d'au moin un joueur virtuel. C'est la methode creerJoueurs qui instancie
 *  des objets Joueur et les range dans cette collection.
 *  <li> <b>nbJoueurs</b> : un entier qui est represente le nombre de joueur dans une partie. En debut de partie, l'utilisateur choisi le nombre
 *  de joueur avec lequel il souhaite jouer et on ajoute 1 (qui correspond au joueur reel) a ce nombre pour avoir la valeur de nbJoueurs. Cet
 *  attribut est l'unique parametre donne au constructeur d'une partie. Ses valeurs peuvent alle de 2 a 21. 
 *  <li> <b>tour</b> : cet entier represente le numero du tour. il peut prendre des valeurs allant de 0 a nbJoueurs-1. Il permet de determiner
 *  l'indice du joueur qui va jouer et il est incremente de 1 (ou plus si un 8 est pose) a chaque tour. Des que cet attribut a atteint la comme 
 *  valeur nbJoueurs-1, il repart a 0.
 *  <li> <b>nbTouraPasser</b> : c'est un entier qui peut vaut autant que le nombre de 8 pose par un joueur et qui permet de savoir le nombre de 
 *  joueur qui vont passer leur tour. il sera ajoute a l'attribut tour si un ou plusieurs 8 ont ete pose. 
 *  <li> <b>nbTour</b> : c'est un entier qui est initialise a 1 et qui est incremente de 1 a chaque fois qu'un tour complet a ete joue, c'est 
 *  a dire lorsque l'attribut tour vaut 0.
 *  <li> <b>finPartie</b> : c'est un booleen qui est initialise a "false" et qui devient "true" lorsqu'un joueur n'a pas aucun carte. c'est 
 *  la methode testerFinPartie() qui modifie la valeur de finPartie. Tant que ce booleen vaut "false" la partie continu et on ne sort pas 
 *  de la boucle while dans le main. 
 *  <li> <b>pioche</b> : c'est un objet de type Pioche qui est cree lorsque la partie est creee.
 *  <li> <b>paquetMilieu</b> : c'est un objet de type PaquetMilieu qui est cree lorsque la partie est creee.
 * </ul>
 * 
 * 
 * Une partie herite de la classe Observable qui va lui permettre de mettre a jour l'interface graphique au cours du jeux. 
 * @author marclouvion
 *@see PaquetMilieu
 *@see Pioche
 *@see Joueur
 *@see main
 */
public class Partie extends Observable{

	/* ------------------------------------------------------------------------------------------------
	 * Attributs
	 * ------------------------------------------------------------------------------------------------*/
	private ArrayList<Joueur> lesJoueurs = new ArrayList<Joueur>();
	private int nbJoueurs;
	private int tour;
	private int nbTouraPasser;
	private int nbTour;
	private boolean finPartie;
	private PaquetMilieu paquetMilieu;
	private Pioche pioche;


	/* ------------------------------------------------------------------------------------------------
	 * Constructeur
	 * ------------------------------------------------------------------------------------------------*/
	/**
	 * Un partie est construite dans le main, le nombre de joueurs de la partie est choisi par l'utilisateur et lui ai passe en parametre.
	 * La creation d'une partie va engendrer la creation du paquet du milieu, de la pioche, des joueurs et du jeu de cartes.
	 * @param nbJoueurs
	 */
	public Partie(int nbJoueurs){
		this.nbJoueurs=nbJoueurs;
		this.finPartie=false;
		this.tour=0;
		this.nbTour=1;

		this.paquetMilieu = PaquetMilieu.getInstance();
		this.pioche = Pioche.getInstance();
		this.creerJoueurs();

		JeuDeCartes jeuDeCarte = new JeuDeCartes(this);
	}



	/* ------------------------------------------------------------------------------------------------
	 * Methodes
	 * ------------------------------------------------------------------------------------------------*/
	/**
	 * C'est la methode qui permet de creer les joueurs de la partie. 
	 * Tout d'abord, un joueur reel est cree et se voit attribue un numero de joueur qui vaut 1. Il est place dans la collection de joueur lesJoueurs
	 * d'une partie. 
	 * Ensuite, l'utilisateur a choisi dans le main le nombre de joueur virtuel contre qui il va jouer. Dans une boucle "for" qui ittere autant de fois que de 
	 * joueur virtuel choisi, un entier strategieAleatoire se voit attribue une valeur aleatoire (allant de 0 a 2) par la methode random de la classe Math.
	 * Selon la valeur de cette entier, un joueur virtuel possede une strategie precise. C'est la valeur de l'entier "i" auquel on on ajoute 1
	 * qui est passee en parametre aux joueur virtuel pour en faire son numero de joueur.
	 * 
	 *   
	 */
	public void creerJoueurs(){
		/* ajoute le joueur réel */
		this.lesJoueurs.add(new JoueurReel(1));

		/* ajoute tous les autres joueur géré par l'ordinateur */
		for (int i =1; i<this.nbJoueurs; i++){
			/* Definie une strategie aleatoire pour chaque joueur virtuel*/
			int strategieAleatoire = (int) (Math.random()*3);
			switch (strategieAleatoire)
			{
			case 0:	    		
				this.lesJoueurs.add(new JoueurVirtuel(i+1, new Novice()));
				break;
			case 1:
				this.lesJoueurs.add(new JoueurVirtuel(i+1, new Agressif()));
				break;
			case 2:
				this.lesJoueurs.add(new JoueurVirtuel(i+1, new Expert()));
				break;
			}
		}
	}

	/**
	 * Cette methode est appele par la methode testerFinPartie().  Elle permet va incrementer l'entier "tour" pour permettre au joueur suivant 
	 * de jouer. Des qu'un joueur a fini son tour, cette methode verifie si un tour complet a ete termine dans la boucle "if". Si c'est le 
	 * cas, l'entier nbTour est incremente. Ensuite, l'entier "tour" est modifie. Tout d'abord, on lui ajoute nbTouraPasser, qui correspond
	 * au nombre de 8 que le joueur vient de poser. Enfin, le "tour" est incremente de 1, et la fonction mathematique "modulo" le nombres de joueurs
	 * permet de faire repartir le "tour" a 0 quand un tour complet a ete joue. Dans ce cas la, ce sera au joueur 1 de jouer.
	 */
	public void tourSuivant(){
		/* Calcul du nombre de tour */
		if (this.tour+this.nbTouraPasser+1>= this.nbJoueurs ){
			this.nbTour++;
		}	

		/* Determination de l'indice du prochain joueur qui va jouer*/
		this.tour=this.tour+this.nbTouraPasser;
		this.tour=(this.tour+1)%(this.nbJoueurs);		
	}

	/**
	 * Cette methode est appele dans le main, apres le tour de chaque joueur. Elle verifie si le joueur qui vient de jouer a gagne dans une 
	 * structure "if/else" 
	 * <li> Si le joueur n'a pas de cartes cachees ET n'a plus de carte en main, il a forcement gagne. Dans ce cas la, le booleen finPartie 
	 * de la partie et celui du joueur aGagne deviennent "true". le booleen finPartie a "true" est l'unique condition de sortie de la boucle while
	 * dans le main
	 * <li> Si les deux conditions ne sont pas verifiees, la methode tourSuivant() est appele pour changer de tour et le nombre de tour a passer 
	 * retombe a 0.
	 */
	public void testerFinPartie(){
		//Si il n'y a plus de cartes cachees devant le joueur et qu'il n'a plus de carte dans sa main, il a gagne
		if (this.lesJoueurs.get(tour).getCarteCachee().size()==0 && this.lesJoueurs.get(tour).getMainJoueur().size()==0){
			this.setFinPartie(true);
			this.lesJoueurs.get(tour).setaGagne(true);
		}
		else{
			this.tourSuivant();
			this.setNbTouraPasser(0);
		}
	}

	/**
	 * Cette methode est redefinie pour pouvoir etre utilisable lorsque l'effet special de l'As est appele. Le parametre de cette methode est un 
	 * entier "choix" dont la valeur est determinee par l'appel de la methode choixJoueur() de la classe Joueur. Ainsi, le fonctionnement de cette methode
	 * est identique a celle precedement. Cependant la methode verifie si le joueur qui a replique par un As vient de poser sa derniere carte, et 
	 * non le joueur actuel. De plus, la structure "else" n'existe" pas car le tour de jeu n'est pas fini (le joueur choisi pour amasser le paquet
	 *  a replique par un As et doit a son tour choisir le joueur qui amassera le paquet).
	 * @param choix
	 */
	public void testerFinPartie( int choix){
		if (this.lesJoueurs.get(choix).getCarteCachee().size()==0 && this.lesJoueurs.get(choix).getMainJoueur().size()==0){
			this.setFinPartie(true);
			this.lesJoueurs.get(choix).setaGagne(true);
		}

	}



	/* ------------------------------------------------------------------------------------------------
	 * Getter
	 * ------------------------------------------------------------------------------------------------*/
	public int getNbJoueurs() {
		return nbJoueurs;
	}

	public ArrayList<Joueur> getLesJoueurs() {
		return lesJoueurs;
	}

	public boolean isFinPartie() {
		return finPartie;
	}

	public PaquetMilieu getPaquetMilieu() {
		return paquetMilieu;
	}

	public Pioche getPioche() {
		return pioche;
	}

	public int getNbTour() {
		return nbTour;
	}

	public int getTour() {
		return tour;
	}

	public int getNbTouraPasser() {
		return nbTouraPasser;
	}



	/* ------------------------------------------------------------------------------------------------
	 * Setter
	 * ------------------------------------------------------------------------------------------------*/
	public void setFinPartie(boolean finPartie) {
		this.finPartie = finPartie;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public void setNbTouraPasser(int nbTouraPasser) {
		this.nbTouraPasser = nbTouraPasser;
	}

}
