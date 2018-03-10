package fr.utt.lo02.projet.coeur.carte;

import fr.utt.lo02.projet.coeur.partie.Partie;


/**
 * Cette classe herite de CarteSpeciale et represente les cartes "7" qui seront instanciees lors de la creation de la partie. Ainsi, elle prend
 * comme valeur 7.
 * L'effet special de cette carte est de forcer le joueur suivant a ne jouer que des cartes de valeur inferieure ou egale a 7.
 * @see CarteSpeciale
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class Sept extends CarteSpeciale{

	/* Constructeur ----------------------------------------------------*/
	/**
	 * Un Sept fait appel au constructeur de sa classe mere CarteSpeciale tout simplement.
	 * @param valeur
	 */
	public Sept(int valeur) {
		super(valeur);
	}

	/**
	 * Cette methode n'a aucun effet car l'action special du 7 prend effet  lorsqu'un joueur verifie si ses cartes sont jouables.
	 * 
	 * @param partie
	 */
	public void effetCarte(Partie partie) {
		System.out.println("le prochain joueur doit jouer une carte de valeur inferieure a 7 ");
	}

}
