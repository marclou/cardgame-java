package fr.utt.lo02.projet.coeur.carte;

import fr.utt.lo02.projet.coeur.partie.Partie;



/**
 * La classe CarteSpeciale herite de la classe Carte. Elle n'est jamais instanciee directement. Ceux sont ses classes filles (As,Deux,Sept,Huit et Dix)
 * qui le sont. Ainsi cette classe a pour unique but de rassembler les cartes speciales afin de les manier plus simplement. La difference avec un
 * objet de cette classe et de la classe CarteSimple se trouve dans le contenu de la methode effetCarte(Partie partie).
 * @author Arthur Guedon et Marc Louvion
 * @see As
 * @see Deux
 * @see Sept 
 * @see Huit
 * @see Dix
 * @see Carte
 * @see CarteSimple
 *
 */
public class CarteSpeciale extends Carte {

	/**
	 * Une carte speciale fait appel au constructeur de sa classe mere Carte tout simplement.
	 * @param valeur
	 */
	public CarteSpeciale(int valeur) {
		super(valeur);
	}

	/**
	 * L'effet d'une carte etant different pour chaque carte speciale, il sera alors redefini directement au sein des classes filles (As,Deux,
	 * Sept Huit et Dix).
	 * @param partie
	 */
	public void effetCarte(Partie partie) {

	}

}
