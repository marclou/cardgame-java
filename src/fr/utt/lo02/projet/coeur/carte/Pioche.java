package fr.utt.lo02.projet.coeur.carte;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Cette classe represente la pioche du jeux. Elle est instanciee par sa methode static getInstance()  qui permet de n'instancier qu'un seul objet
 * de type pioche. Un objet de type Pioche ne possede qu'un attribut, <b>cartesPioche</b> qui est une collection dynamique de cartes. Au fur et a
 *  mesure que la partie avance, le nombre de carte dans cette collection diminue jusqu'a etre nul.
 *  
 * @author marclouvion
 *
 */
public class Pioche {

	/* Attribut --------------------------------------------------- */
	private LinkedList<Carte> cartesPioche = new LinkedList<Carte>();



	/* Singleton ----------------------------------------------- */ 
	private static Pioche pioche = null;

	/**
	 * Cette methode statique permet de n'instancier qu'un objet de type Pioche grace a la structure conditionelle "if". Si un objet pioche a deja 
	 * ete instancie alors il ne pourra plus l'etre.
	 * @return Cette methode renvoie un unique objet de type Pioche.
	 */
	public static Pioche getInstance(){

		if (pioche==null){
			pioche=new Pioche();
		}
		return pioche;
	}

	/**
	 * Le constructeur d'un Singleton est prive pour n'etre qu'accessible a l'interieur de la methode statique getInstance().
	 * Ainsi il est impossible d'instancier une objet Pioche par son constructeur directement.
	 */
	private Pioche(){
	}



	/* Getter ----------------------------------------------------- */
	public LinkedList<Carte> getCartesPioche() {
		return cartesPioche;
	}



}
