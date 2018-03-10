package fr.utt.lo02.projet.coeur.carte;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import fr.utt.lo02.projet.coeur.joueur.Joueur;
import fr.utt.lo02.projet.coeur.partie.Partie;
import fr.utt.lo02.projet.vue.Observable;

/** 
 * Cette classe represente le jeux de cartes qui sera cree en debut de partie. Lors qu'un jeux de cartes est instancie, le constructeur de celui ci
 * appelle les methodes de sa classe pour creer le jeux. Ainsi, il faut d'abord calculer le nombre de jeux de 52 cartes necessaire, creer les 
 * objets de type Carte qui sont stockees dans une LinkedList lesCartes, melanger cette collection et enfin distribuer les cartes aux joueurs.
 * 
 * @see Carte
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class JeuDeCartes extends Observable{

	/* ------------------------------------------------------------------------------------------------
	 * Attributs
	 * ------------------------------------------------------------------------------------------------*/
	private LinkedList<Carte> lesCartes = new LinkedList<Carte>();
	private int nbCartesParJeu=52;
	private int nbJeuDeCartes=1;



	/* ------------------------------------------------------------------------------------------------
	 * Constructeur
	 * ------------------------------------------------------------------------------------------------*/	
	/**
	 * Un Jeu de carte est instancie dans la constructeur de Partie. Il calcule d'abord le nombre de jeux de cartes necessaires, cree les cartes 
	 * , les melange et les distribue.
	 * @param partie
	 */
	public JeuDeCartes(Partie partie){

		this.calculerNbJeuDeCartes(partie);
		this.creerJeuDeCartes();
		this.melanger();
		this.distribuer(partie);
	}


	/* ------------------------------------------------------------------------------------------------
	 * Methodes
	 * ------------------------------------------------------------------------------------------------*/
	/**
	 * Cette methode permet de calculer le nombre de jeux de cartes necessaires pour jouer. Par exemple s'il y a 6 joueurs, il faudra 2 jeux de cartes.
	 * L'entier nbJeuDeCartes  prendra la valeur de l'entier superieur de la division du nombre de joueur par 4. On appelle la methode ceil de la 
	 * classe Math pour recuperer l'entier superieur de la division, et on cast le resultats en un entier car cette methode renvoie un double.
	 * @param partie
	 */
	private void calculerNbJeuDeCartes(Partie partie){

		/*on prend l'entier superieur de la divition du nombre de joueur par 4*/
		this.nbJeuDeCartes = (int)Math.ceil( (float)partie.getNbJoueurs()/4 );
	}

	/** Cette methode permet de creer tous les cartes d'un jeux de cartes.
	 * Tout d'abord, on entre dans une boucle "for" qui va iterer douze fois (une pour chaque type de carte : as, deux, trois, ..). Dans cette boucle
	 * on rentre alors dans une autre boucle for qui va iterer un nombre multiple de 4 (4 fois s'il y a un jeux de cartes, 8 s'il y en a 2..).
	 * On fonction de la valeur de l'indice de la premiere boucle,dans une structure switch, le constructeur d'une carte est appele avec comme parametre cet indice incremente de 1.
	 */
	private void creerJeuDeCartes(){

		/* pour chaque type de carte (As, rois, deux ...) */
		for (int i=1; i<(this.nbCartesParJeu/4)+1; i++){
			/* on cree 4 cartes par jeu de carte utilisé */
			for (int j=0; j<4*this.nbJeuDeCartes; j++){
				switch (i+1)
				{
				case 14:
					this.lesCartes.addFirst(new As(i+1));
					break;
				case 2:
					this.lesCartes.addFirst(new Deux(i+1));
					break;
				case 7:
					this.lesCartes.addFirst(new Sept(i+1));
					break;
				case 8:
					this.lesCartes.addFirst(new Huit(i+1));
					break;
				case 10:
					this.lesCartes.addFirst(new Dix(i+1));
					break;
				default:
					this.lesCartes.addFirst(new CarteSimple(i+1));
				}// fin switch
			} // fin for j
		}// fin for i
	}

	/**
	 * Cette methode tres simple utilise la methode shuffle de la classe Collection pour melanger aleatoirement les cartes apres leur creation.
	 * 
	 * 
	 */
	private void melanger(){

		Collections.shuffle(lesCartes);
	}

	/**
	 * Cette methode est appele apres que les cartes ai ete melange et permet de distribuer 9 cartes a tous les joueurs. On va d'abord
	 * iterer chaque joueur avec un Iterator. Ensuite, On distrubue au joueur les 3 premieres cartes qui iront dans sa main , puis 3 faces 
	 * visibles et 3 faces cachees. Dans chacune des boucles "for" on distribue 3 cartes au joueur puis on les supprime de la collection lesCartes. 
	 * Quand tous les joueurs iteres ont recu 9 cartes, on entre dans une boucle "while" qui distribue les cartes a la pioche tant que la collection
	 * de cartes creees lesCartes n'est pas vide.
	 * @param partie
	 */
	public void distribuer(Partie partie){

		Iterator<Joueur> it = partie.getLesJoueurs().iterator();

		/* distribue les cartes aux joueurs*/
		while (it.hasNext()){
			Joueur joueur = it.next();
			System.out.println("\n\nCartes du joueur "+joueur.getNumJoueur());
			System.out.print("main : \t\t");

			/* on distribue la main*/
			for (int j=0; j<3; j++){
				joueur.getMainJoueur().add(this.lesCartes.getFirst());
				System.out.print("\t"+this.lesCartes.getFirst().getNom());
				this.lesCartes.removeFirst();
			}

			/* on distribue les cartes visibles */
			System.out.print("\ncartes visibles : ");
			for (int j=0; j<3; j++){
				joueur.getCarteVisible().add(this.lesCartes.getFirst());
				System.out.print("\t"+this.lesCartes.getFirst().getNom());
				this.lesCartes.removeFirst();
			}

			/* on distribue les cartes cachées */
			System.out.print("\ncartes cachées : ");
			for (int j=0; j<3; j++){
				joueur.getCarteCachee().addFirst(this.lesCartes.getFirst());
				System.out.print("\t"+this.lesCartes.getFirst().getNom());
				this.lesCartes.removeFirst();
			}

		}

		/* distribue le reste des carte dans la pioche */
		System.out.println("\n\nPioche : ");
		while (!this.lesCartes.isEmpty()){	

			partie.getPioche().getCartesPioche().addFirst(this.lesCartes.getFirst());
			System.out.print(this.lesCartes.getFirst().getNom()+" ; ");
			this.lesCartes.removeFirst();
		}
		System.out.println("\nnombre de carte dans la pioche : "+partie.getPioche().getCartesPioche().size());
	}



	/* ------------------------------------------------------------------------------------------------
	 * Getter
	 * ------------------------------------------------------------------------------------------------*/
	public LinkedList<Carte> getLesCartes(){
		return this.lesCartes;
	}

}

