package fr.utt.lo02.projet.coeur.joueur;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import fr.utt.lo02.projet.coeur.carte.Carte;
import fr.utt.lo02.projet.coeur.partie.Partie;

/**
 * La classe Expert represente la strategie la plus travaillee du programme. Lorsqu'un joueur possede une strategie experte, 
 * il aura tendance a jouer en premier ses cartes faibles, a l'exception des cartes speciales 
 * comme l'as ou le deux . Cette strategie est attribuee aleatoirement a un 
 * joueur dans notre programme. 
 * 
 * @see Strategie
 * @author Arthur Guedon et Marc Louvion
 * 
 *
 */
public class Expert implements Strategie {

	/**
	 * Cette methode permet a un joueur qui possede une strategie experte de jouer la premiere carte faible qu'il trouve jouable. 
	 * Elle est appelee dans poserCarte de JoueurVirtuel. On va donc trier la collection de cartes de la main du joueur dans l'ordre croissant
	 * de telle sorte que les cartes faibles soient les premieres. On itere ensuite les cartes, et le joueur pose la premiere carte qu'il trouve jouable,
	 * la plus faible donc. Cependant il ne joue pas l'as ou le deux en premier.
	 * 
	 * @return indiceCarteChoisie : un entier qui correspond a l'indice de la carte que le joueur veut poser.
	 * @param main : les cartes de la main du joueur.
	 */
	public int choisirCarte(ArrayList<Carte> main) {
		Collections.sort(main);

		int indiceCarteChoisie=0;

		Iterator<Carte> it=main.iterator();
		boolean estPosee = false;
		while (it.hasNext() && estPosee == false){
			Carte carte = it.next();

			if (carte.isJouable()==true && carte.getValeur()!=1 && carte.getValeur()!=2){
				estPosee = true;
				indiceCarteChoisie=main.indexOf(carte);			
			}
		}

		if (estPosee==false){
			while (it.hasNext() && estPosee == false){
				Carte carte = it.next();

				if (carte.isJouable()==true){
					estPosee = true;
					indiceCarteChoisie=main.indexOf(carte);					
				}
			}
		}

		return indiceCarteChoisie;
	}

	/**
	 *Cette methode permet a un joueur virtuel de choisir le joueur a qui il souhaite envoyer le paquet lorsqu'il pose un as. Ainsi , 
	 *un joueur expert choisira toujours le joueur qui possede le moins de carte dans sa main. On itere donc la main de tous les joueurs (sans
	 *iterer le joueur lui meme) et on choisi le joueur qui a le moins de cartes.
	 *
	 *@param numJoueur : le numero du joueur qui doit faire son choix
	 *@param nbJoueur : le nombre de joueur dans la partie
	 *@param partie
	 *@return indiceJoueur : l'indice du joueur choisi
	 */
	public int choixJoueur(int numJoueur, int nbJoueur, Partie partie) {
		Iterator<Joueur> it = partie.getLesJoueurs().iterator();
		int indiceJoueur =-1;
		int nbCarteMain = 1000;
		while (it.hasNext()){
			Joueur joueur = it.next();
			if ((numJoueur!=partie.getTour()+1)&&(joueur.getMainJoueur().size() < nbCarteMain)){

				nbCarteMain = joueur.getMainJoueur().size();
				indiceJoueur = joueur.getNumJoueur();
			}

		}
		return indiceJoueur;
	}



	/**
	 * Cette methode permet a un joueur virtuel expert de toujours choisir de poser plusieurs cartes sauf si cest cartes sont l'as ou le deux.
	 * 
	 * @param carte : la carte qui est presente en plusieurs exemplaires de meme valeur dans la main du joueur
	 * @return poserPlusieursCartes : un booleen qui vaudra "true"
	 */

	public boolean poserPlusieursCartes(Carte carte) {
		boolean poserPlusieursCartes=true;
		if (carte.getValeur()==1 || carte.getValeur()==2 || carte.getValeur()==13){
			poserPlusieursCartes=false;
		}
		return poserPlusieursCartes;
	}

	/**
	 * Cette methode permet a un joueur virtuel agressif de toujours repliquer par un as s'il est choisi pour cocogner et qu'il possede un as
	 * et un deux.
	 * 
	 * @return 1 : qui correspond au choix de l'as.
	 */
	public int choixAsOuDeux() {

		return 1;
	}


	/**
	 * Cette methode permet a un joueur virtuel expert d'echanger ses cartes en debut de partie. Ainsi ce joueur va echanger ses cartes si
	 * celles de sa main sont plus fortes que celles faces visibles.
	 * On itere donc les cartes de la main et faces visibles, et la methode renvoie "true" si les cartes de la main sont plus fortes que 
	 * celles faces visibles.
	 * 
	 * @param carteVisibles : les cartes visibles du joueur
	 * @param main : la main du joueur
	 * @return echangerCarte : un booleen qui correspond au choix du joueur.
	 */
	public boolean echangerCarte(ArrayList<Carte> main, ArrayList<Carte> cartesVisibles) {
		boolean echangerCarte=false;
		boolean carteSupTrouvee=false;
		Iterator<Carte> it = main.iterator();
		while (it.hasNext() && carteSupTrouvee==false){
			Carte carteMain=it.next();
			Iterator<Carte> it2 = cartesVisibles.iterator();
			while (it2.hasNext() && carteSupTrouvee==false){
				Carte carteVisible=it2.next();
				if ( carteVisible.getValeur()<carteMain.getValeur() && carteVisible.getValeur()!=1 && carteVisible.getValeur()!=2 ){
					carteSupTrouvee=true;
					echangerCarte=true;
				}
			}
		}	
		return echangerCarte;
	}



	/**
	 * Cette methode permet de recuperer un tableau de deux entiers qui correspondent chacun a l'indice de la carte de la main et face visible a echanger.
	 *
	 *@param main : le cartes de la main du joueur 
	 *@param cartesVisibles : les cartes visibles du joueur
	 *@return tabIndices : un tableau de deux entiers qui sont les indices des cartes a echanger.
	 */
	public int[] indicesCartesEchangees(ArrayList<Carte> main, ArrayList<Carte> cartesVisibles) {
		int tabIndices[]={0,0};


		boolean indicesTrouves=false;

		Iterator<Carte> it = main.iterator();
		while (it.hasNext() && indicesTrouves==false){
			Carte carteMain=it.next();
			Iterator<Carte> it2 = cartesVisibles.iterator();
			while (it2.hasNext() && indicesTrouves==false){
				Carte carteVisible=it2.next();
				if ( carteVisible.getValeur()<carteMain.getValeur() && carteVisible.getValeur()!=1 && carteVisible.getValeur()!=2 ){
					tabIndices[0]= main.indexOf(carteMain);	
					tabIndices[1]= cartesVisibles.indexOf(carteVisible);
					indicesTrouves=true;

				}
			}
		}	


		return tabIndices;
	}



}
