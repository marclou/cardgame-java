package fr.utt.lo02.projet.coeur.carte;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Cette classe represente le paquet du milieu ou les joueurs posent les cartes au cours de la partie. Le patron de conception Singleton permet 
 * de n'instancier qu'un seul objet de type PaquetMilieu. Cet objet possede une collection de cartes, cartesMillieu, qui est constituee des cartes 
 * posees par les joueur. Elle peut etre vide si un joueur "cocogne". 
 * 
 * 
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class PaquetMilieu {

	/* Attribut --------------------------------------------------- */
	private LinkedList<Carte> cartesMilieu = new LinkedList<Carte>();
	private String derniereCartePosee="vide";



	/* Singleton ----------------------------------------------- */ 
	private static PaquetMilieu paquetMilieu = null;

	/**
	 * Cette methode statique permet de n'instancier qu'un objet de type PaquetMilieu grace a la structure conditionelle "if". Si un objet paquetMilieu
	 *  a deja 
	 * ete instancie alors il ne pourra plus l'etre.
	 * @return Cette methode renvoie un unique objet de type PaquetMilieu.
	 */
	public static PaquetMilieu getInstance(){

		if (paquetMilieu==null){
			paquetMilieu=new PaquetMilieu();
		}
		return paquetMilieu;
	}


	/**
	 * Le constructeur d'un Singleton est prive pour n'etre qu'accessible a l'interieur de la methode statique getInstance().
	 * Ainsi il est impossible d'instancier une objet PaquetMilieu par son constructeur directement.
	 */
	private PaquetMilieu(){

	}



	/* Getter ----------------------------------------------------- */
	public LinkedList<Carte> getCartesMilieu() {
		return cartesMilieu;
	}

	public String getDerniereCartePosee() {
		return derniereCartePosee;
	}

	public void setDerniereCartePosee(String derniereCartePosee) {
		this.derniereCartePosee = derniereCartePosee;
	}




}

