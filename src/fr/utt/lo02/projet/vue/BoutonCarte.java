package fr.utt.lo02.projet.vue;
import javax.swing.JButton;

import fr.utt.lo02.projet.coeur.carte.Carte;

/** 
* Classe heritant de JButton mais ayant la particularite de stocker dans une variable la un objet de type Carte lors de son instanciation
* @author Marc Louvion & Arthur Guedon
*/
public class BoutonCarte extends JButton{
	
	private Carte carte;
	
	/**
	 * C'est le constructeur de la classe BoutonCarte. 
	 * 
	 * @param carte : la carte sur lequel le bouton va permettre d'appuyer.
	 */
	public BoutonCarte (Carte carte){
		super (carte.getNom());
		this.carte=carte;
	}

	public Carte getCarte() {
		return carte;
	}
}
