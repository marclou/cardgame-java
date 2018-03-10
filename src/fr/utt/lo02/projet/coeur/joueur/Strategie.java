package fr.utt.lo02.projet.coeur.joueur;
import java.util.ArrayList;

import fr.utt.lo02.projet.coeur.carte.Carte;
import fr.utt.lo02.projet.coeur.partie.Partie;

/**
 * Cette interface represente les diffentes strategies que peuvent posseder les joueurs virtuels. Elle est composee de differentes methodes 
 * qui sont redefinies dans les differentes classes de strategies (Novice, Expert et Agressif). Cette interface, grace au polymorphisme, permet
 * aux joueurs virtuels de jouer differement selon la strategie qu'ils possedent.
 * 
 * @see Novice
 * @see Agressif
 * @see Expert
 * @see JoueurVirtuel
 * 
 * @author Arthur Guedon et Marc Louvion
 *
 */
public interface Strategie {
	/**
	 * Cette methode est redefinie dans les classes qui implementent cette strategie (Novice, Expert, Agressif). Elle permet a un joueur virtuel 
	 * de choisir la carte jouable qu'il souhaite poser.
	 * @param main
	 * @return indiceCarteChoisie : un entier qui correspond a l'indice de la carte choisie par le joueur.
	 */
	public int choisirCarte(ArrayList<Carte> main);

	/**
	 * Cette methode est redefinie dans les classes qui implementent cette strategie (Novice, Expert, Agressif). Elle permet a un joueur virtuel 
	 * de choisir le joueur a qui il souhaite envoyer le paquet du milieu (lors de l'appel de l'effet special de l'as).
	 * @param numJoueur
	 * @param nbJoueur
	 * @return Cette methode renvoie le numero du joueur choisi (un entier).
	 */
	public int choixJoueur(int numJoueur, int nbJoueur, Partie partie);

	/**
	 * Cette methode est redefinie dans les classes qui implementent cette strategie (Novice, Expert, Agressif). Elle permet a un joueur virtuel 
	 * de choisir s'il veut ou non poser plusieurs cartes de meme valeur.
	 * @param carte
	 * @return poserPlusieursCartes : un booleen qui correspond au choix du joueur de poser ou non plusieurs cartes identiques.
	 */
	public boolean poserPlusieursCartes(Carte carte);

	/**
	 * Cette methode est redefinie dans les classes qui implementent cette strategie (Novice, Expert, Agressif). Elle permet a un joueur virtuel
	 * de choisir de poser un as ou un deux. Elle est appelee par un joueur cible par un as s'il possede un as et un deux pour contrer.
	 * @return cette methode retourne un entier qui vaut 1 ou 2 selon le choix du joueur de poser un as ou un deux.
	 */
	public int choixAsOuDeux();

	/**
	 * Cette methode est redefinie dans les classes qui implementent cette strategie (Novice, Expert, Agressif). Elle permet a un joueur virtuel
	 * d'echanger ses cartes en debut de partie.
	 * @param main
	 * @param cartesVisibles
	 * @return echangerCartes : un booleen qui vaut "true" si le joueur souhaite echanger ses cartes en debut de partie.
	 */
	public boolean echangerCarte(ArrayList<Carte> main , ArrayList<Carte> cartesVisibles);

	/**
	 * Cette methode est redefinie dans les classes qui implementent cette strategie (Novice, Expert, Agressif). Elle permet de recuperer l'indice
	 * des cartes a echanger.
	 * @param main
	 * @param cartesVisibles
	 * @return tabIndices : un tableau de deux entiers correspondant aux indices des cartes de la main et face visible a echanger.
	 */
	public int[] indicesCartesEchangees(ArrayList<Carte> main , ArrayList<Carte> cartesVisibles);
}
