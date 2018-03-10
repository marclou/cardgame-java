package fr.utt.lo02.projet.coeur.joueur;
import java.util.ArrayList;
import java.util.Iterator;

import fr.utt.lo02.projet.coeur.carte.Carte;
import fr.utt.lo02.projet.coeur.partie.Partie;

/**
 * La classe novice represente un type de strategie. C'est la strategie la plus basique qu'un joueur virtuel peut posseder. Celui ci jouera 
 * ainsi les premiere carte qu'il trouve jouable, n'echangera jamais ses cartes par exemple. Cette strategie est attribuee aleatoirement a un 
 * joueur dans notre programme. 
 * 
 * @see Strategie
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class Novice implements Strategie {


	/**
	 * Cette methode permet a un joueur de choisir la carte jouable qu'il souhaite poser. Il va ainsi poser la premiere carte qu'il trouve jouable.
	 * La methode est appelee dans la methode poserCarte de JoueurVirtuel.
	 * L'indice de la carte choisie est initialise a 0, et on va iterer toutes les cartes de la main du joueur grace a un iterateur de Carte. Le booleen
	 * estPosee est initialise a "false" et vaudra "true" des qu'une carte jouable est trouvee. Chaque carte iteree appelle sa methode isJouable 
	 * qui renvoie la valeur du booleen jouable d'une carte.
	 * Si  une carte est jouable, estPosee devient "true", on recupere l'indice de la carte et on sort de la boucle.
	 * 
	 * @return indiceCarteChoisie : un entier correspondant a l'indice de la carte que le joueur a choisi de poser.
	 * @param main
	 */
	public int choisirCarte(ArrayList<Carte> main) {
		int indiceCarteChoisie=0;

		Iterator<Carte> it=main.iterator();

		// estPosee permet de sortir de la boucle quand le joueur a pose une carte ( on prend le cas ou le joueur pose juste une carte)
		boolean estPosee = false;
		while (it.hasNext() && estPosee == false){
			Carte carte = it.next();

			if (carte.isJouable()==true){
				estPosee = true;
				indiceCarteChoisie=main.indexOf(carte);

			}
		}


		return indiceCarteChoisie;
	}

	/**
	 * Cette methode permet de recuperer le choix d'un joueur virtuel de poser plusieurs cartes. Cette methode est appelee si le joueur vient
	 * de poser une carte et qu'il possede au moins une carte de meme valeur dans sa main. Elle correspond donc au choix du joueur de poser cette
	 * carte de meme valeur. Un joueur novice ne choisira jamais de poser plusieurs cartes, donc le booleen retourne vaut "false".
	 * 
	 * @param carte : la carte dont dont le joueur en peossede au moins une autre de meme valeur
	 * @return poserPlusieursCartes : cette methode renvoie un booleen qui vaut "false".
	 */
	public boolean poserPlusieursCartes(Carte carte){
		boolean poserPlusieursCartes=true;
		return poserPlusieursCartes;
	}


	/**
	 * Cette methode permet a un joueur virtuel novice de choisir suivant ou precedent pour l'effet special de l'as.
	 * 
	 * @param numJoueur : le numero du joueur qui a pose l'as
	 * @param nbJoueur : le nombre de joueurs
	 * @param partie 
	 * @return un entier qui correspond au joueur suivant ou precedent
	 */
	public int choixJoueur(int numJoueur, int nbJoueur, Partie partie){
		if (numJoueur == nbJoueur){
			return numJoueur-1;
		}else {
			return numJoueur +1;
		}
	}

	/**
	 * Cette methode permet a un joueur virtuel novice de toujours repliquer par un deux s'il est choisi pour cocogner et qu'il possede un as
	 * et un deux.
	 * 
	 * @return 2 : qui correspond au choix du deux.
	 */
	public int choixAsOuDeux(){
		return 2;
	}



	/**
	 * Cette methode permet a un joueur virtuel novice de ne jamais echanger ses cartes en debut de partie.
	 *
	 *@return false : un joueur novice n'echange jamais ses cartes.
	 */
	public boolean echangerCarte(ArrayList<Carte> main, ArrayList<Carte> carteVisible) {
		return false;
	}


	/**
	 * Cette methode permet de recuperer les indices des cartes a echanger.
	 * Comme un joueur novice n'echange jamais ses cartes, la methode ne renvoie rien.
	 * 
	 * @return null
	 */
	public int[] indicesCartesEchangees(ArrayList<Carte> main, ArrayList<Carte> cartesVisibles) {

		return null;
	}
}
