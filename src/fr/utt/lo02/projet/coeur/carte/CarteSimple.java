package fr.utt.lo02.projet.coeur.carte;

import fr.utt.lo02.projet.coeur.partie.Partie;


/**
 * La classe fille CarteSimple herite de la superclasse Carte. Elle represente toutes les cartes du jeux qui n'ont aucun effet particulier
 * comme la carte"5" par exemple. Ainsi, une carte simple est une carte dont la methode effetCarte(Partie partie) n'a aucun effet.
 * 
 * @see Carte
 * @see CarteSpeciale
 * @author Arthur Guedon et Marc Louvion
 * 
 *
 */
public class CarteSimple extends Carte {

	/**
	 * Une carte simple fait appel au constructeur de sa classe mere Carte tout simplement.
	 * @param valeur
	 */
	public CarteSimple(int valeur) {
		super(valeur);
	}

	/**
	 * Cette methode est appelee a chaque fois qu'un joueur pose une carte. Le corps de cette methode est vide car une carte simple n'a aucun effet.
	 * 
	 * @param partie
	 */
	public void effetCarte(Partie partie) {
		
		
	}
	
}
