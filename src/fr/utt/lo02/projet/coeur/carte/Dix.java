package fr.utt.lo02.projet.coeur.carte;

import fr.utt.lo02.projet.coeur.partie.Partie;


/**
 * Cette classe herite de la superclasse CarteSpeciale et permet d'instancier les cartes "10" lors de la creation d'une partie. La valeur d'une carte
 * de type Dix vaut 10. 
 * L'effet de cette carte est de supprimer definitivement toutes les cartes du paquet du milieu. 
 * 
 * @see CarteSpeciale
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class Dix extends CarteSpeciale{

	/**
	 * Un Dix fait appel au constructeur de sa classe mere CarteSpeciale tout simplement.
	 * @param valeur
	 */
	public Dix(int valeur) {
		super(valeur);
	}

	/**
	 * Cette methode est appelee lorsqu'un Dix est pose et supprime toutes les cartes de la collection de cartes du paquet du milieu.
	 * 
	 * @param partie
	 */
	public void effetCarte(Partie partie) {

		partie.getPaquetMilieu().getCartesMilieu().removeAll(partie.getPaquetMilieu().getCartesMilieu());

	}
}
