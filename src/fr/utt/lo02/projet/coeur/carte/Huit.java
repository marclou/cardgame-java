package fr.utt.lo02.projet.coeur.carte;

import fr.utt.lo02.projet.coeur.partie.Partie;


/**
 * Cette classe herite de la superclasse CarteSpeciale et permet d'instancier les cartes "8" lors de la creation de la partie. La valeur 
 * d'un objet de type Huit vaut 8.
 * Cette carte speciale permet de passer le tour du joueur suivant, ou des joueurs suivants si plusieurs 8 sont poses. 
 * 
 * @see CarteSpeciale
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class Huit extends CarteSpeciale{

	/**
	 * Un Huit fait appel au constructeur de sa classe mere CarteSpeciale tout simplement.
	 * @param valeur
	 */
	public Huit(int valeur) {
		super(valeur);
	}

	/**
	 * Cette methode permet de sauter le tour des joueurs suivants en fonction du nombre de huit pose. Dans l'entier nbHuitsJoues on rentre le 
	 * nombre de cartes identiques qu'a joue un joueur pendant son tour. Ensuite, par l'accesseur setNbTouraPasser de Partie, on ajoute 
	 * autant de tour a passer que de huits joues.
	 * 
	 * @param partie
	 */
	public void effetCarte(Partie partie) {

		int nbHuitsJoues = partie.getLesJoueurs().get(partie.getTour()).getNbCartesJouees();

		partie.setNbTouraPasser(  partie.getNbTouraPasser() + (1*nbHuitsJoues)  );

		if (nbHuitsJoues==1)
			System.out.println("--> Le joueur suivant passe son tour");
		else
			System.out.println("--> Les "+nbHuitsJoues+" joueurs suivants passent leurs tour");




	}

}
