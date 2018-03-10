package fr.utt.lo02.projet.coeur.carte;

import fr.utt.lo02.projet.coeur.partie.Partie;


/**
 * La classe Deux herite de CarteSpeciale et represente toutes les cartes "2" qui seront instanciees lors de la creation de la partie. La valeur d'un objet de type Deux est "2",
 * et le booleen jouable, qui permet de savoir si une carte peut etre jouee, est initialise a vrai car un Deux est forcement jouable. 
 * Cette carte permet ainsi de faire repartir le jeux a Deux, et peut etre utilise pour contrer l'effet special d'un As.
 * @see CarteSpeciale
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class Deux extends CarteSpeciale{

	/**
	 * Un Deux fait appel au constructeur de sa classe mere CarteSpeciale tout simplement. De plus, son booleen jouable est initialise a vrai
	 * car un Deux est forcement jouable.
	 * @param valeur
	 */
	public Deux(int valeur) {
		super(valeur);
		this.jouable=true;
	}
	/**
	 * Cette methode est appelee a la fin d'un tour d'un joueur, par la methode rendreCarteNonJouables(). Cette methode donne la valeur "false"
	 * a l'attribut jouable de chaque carte. Cependant, le Deux etant toujours jouable, cette methode lui permet de garder son booleen a "true".
	 * @param jouable
	 */

	public void setJouable(boolean jouable) {
		this.jouable = true;
	}

	/**
	 * Cette methode n'a aucun effet car un Deux ne provoque aucun effet special, il est juste jouable a n'importe quel moment.
	 * @param partie
	 */
	public void effetCarte(Partie partie) {


	}


}
