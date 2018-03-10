package fr.utt.lo02.projet.coeur.carte;
import java.util.Iterator;

import fr.utt.lo02.projet.coeur.partie.Partie;

/**
 * Cette classe herite de CarteSpeciale et represente les cartes "As" qui seront instanciees lors de la creation de la partie. Un "As" possede une une valeur de 14, 
 * et une methode effetCarte() redefinie.
 * L'effet special de cette carte est d'envoyer le paquet du milieu a un joueur choisi. Cette carte peut aussi permettre de contrer l'effet special enonce precedement.
 * 
 * @see CarteSpeciale
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class As extends CarteSpeciale {

	/* Constructeur ----------------------------------------------------*/
	/**
	 * Un As fait appel au constructeur de sa classe mere CarteSpeciale tout simplement.
	 * @param valeur
	 */
	public As(int valeur) {
		super(valeur);
	}



	/**
	 * Cette methode est appele lorsqu'un as est pose sur le paquet du milieu. Deux booleen possedeAs et possedeDeux sont initialise a "false"
	 * et vaudront "true" si le joueur a qui on a choisi d'envoyer le paquet possede un As ou un Deux pour contrer. 
	 * De même, indiceDeux et indiceAs vont permettre de recuperer l'indice de l'As ou du Deux avec lequel le joueur choisi de repliquer.
	 * La valeur de l'entier joueurChoisi lui est attribuee par l'appel de la methode choisirJoueur. Ainsi, le joueur qui pose un As doit choisir
	 * le joueur a qui il envoie le paquet du milieu. On va ensuite faire appel a un iterateur, qui va parcourir les cartes de la main du joueur
	 * choisi. Si le joueur possede un As ou un Deux, l'un des deux booleen (ou les deux) prendra la valeur "true".On attribuera la valeur  a l'entier 
	 * indiceDeux ou indiceAs la place de l'as ou du deux dans la main du joueur cible par la methode indexOf(carteDeListe). Une fois qu'on a verifie
	 * toutes les cartes du joueur cible, on se retrouve face a 4 cas possibles :
	 * 
	 * <li> le joueur cible n'a ni un as ni un deux (les deux booleens sont a "false") : la methode cocogner() est appelee par le joueur cible et il 
	 * amasse donc tout le paquet du milieu.
	 * <li> Le joueur cible possede uniquement un deux (possedeDeux a "true", possedeAs a "false") : on supprime le Deux de la main du joueur cible, on l'ajoute
	 * au paquet du milieu. Le joueur cible doit piocher et la methode redefinie testerFinPartie(int choix) est appliquee sur le joueur cible.
	 * <li> Le joueur cible possede uniquement un As (possedeDeux a "false", possedeAs a "true") : il se passe la même chose que precedement sauf que 
	 * la methode redefinie effetCarte(Partie partie, int joueurChoisi) est appele pour relancer l'effet de l'as. Elle sera detaillee plus bas.
	 * <li> Le joueur cible possede un As et un Deux (possedeAs et possedeDeux a "true") : la methode choisirAsOuDeux() permet de recuperer le choix
	 * du joueur de poser un As ou un Deux. Une fois ce choix recupere, on entre alors dans une structure "if/else" : le joueur choisi de poser un Deux
	 * ou un As.
	 * 
	 * 
	 * @param partie
	 */
	public void effetCarte(Partie partie) {


		boolean possedeDeux = false, possedeAs=false;
		int indiceDeux=-1, indiceAs=-1;

		int joueurChoisi = partie.getLesJoueurs().get(partie.getTour()).choisirJoueur(partie)-1;

		Iterator<Carte> it=partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().iterator();
		while (it.hasNext() ){
			Carte carteDeListe = it.next();
			if (carteDeListe.getValeur()==2){
				possedeDeux = true;
				indiceDeux = partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().indexOf(carteDeListe);

			}
			if (carteDeListe.getValeur()==14){
				possedeAs = true;
				indiceAs = partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().indexOf(carteDeListe);
			}
		}

		if (possedeDeux && !possedeAs){
			partie.getPaquetMilieu().getCartesMilieu().add(0, partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceDeux) );
			partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().remove(partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceDeux));
			System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+"replique et pose un Deux");
			partie.getLesJoueurs().get(joueurChoisi).piocher(partie);
			partie.testerFinPartie(joueurChoisi);



		}
		if (!possedeDeux && possedeAs){
			partie.getPaquetMilieu().getCartesMilieu().add(0, partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceAs) );
			partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().remove(partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceAs));
			System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+"replique et pose un As");
			partie.getLesJoueurs().get(joueurChoisi).piocher(partie);
			partie.testerFinPartie(joueurChoisi);
			this.effetCarte(partie, joueurChoisi);


		}

		if (possedeDeux && possedeAs){

			System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+"replique et choisi la carte qu'il pose");
			int choix = partie.getLesJoueurs().get(joueurChoisi).choisirAsOuDeux();
			if (choix == 1){
				partie.getPaquetMilieu().getCartesMilieu().add(0, partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceAs) );
				partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().remove(partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceAs));
				System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+" a choisi l'As");
				partie.getLesJoueurs().get(joueurChoisi).piocher(partie);
				partie.testerFinPartie(joueurChoisi);
				this.effetCarte(partie, joueurChoisi);
			}else {
				partie.getPaquetMilieu().getCartesMilieu().add(0, partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceDeux) );
				partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().remove(partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceDeux));
				System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+" a choisi le Deux");
				partie.getLesJoueurs().get(joueurChoisi).piocher(partie);
				partie.testerFinPartie(joueurChoisi);
			}


		}
		if (!possedeDeux && !possedeAs) {
			partie.getLesJoueurs().get(joueurChoisi).cocogner(partie);
			System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+" amasse le paquet");
		}

	}

	/**
	 * Cette methode est redefinie pour pouvoir etre appliquee dans le cas ou le joueur cible d'un As possede lui aussi un As, auquel cas il 
	 * devra choisir a son tour le joueur a qui il souhaite renvoyer le paquet. Ainsi la methode choisirJoueur() ne s'applique plus sur le joueur qui
	 * vient de jouer son tour, mais sur le joueur qui a ete cible par le joueur actuel. Ensuite cette methode s'execute exactement comme la 
	 * methode precedente.	
	 * @param partie
	 * @param joueurReplique
	 */
	public void effetCarte(Partie partie, int joueurReplique) {



		boolean possedeDeux = false, possedeAs=false;
		int indiceDeux=-1, indiceAs=-1;
		int joueurChoisi = partie.getLesJoueurs().get(joueurReplique).choisirJoueur(partie)-1;
		System.out.println("\nLe joueur "+(joueurReplique+1)+" qui replique a choisi le joueur "+(joueurChoisi+1));


		Iterator<Carte> it=partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().iterator();
		while (it.hasNext() ){
			Carte carteDeListe = it.next();
			if (carteDeListe.getValeur()==2){
				possedeDeux = true;
				indiceDeux = partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().indexOf(carteDeListe);

			}
			if (carteDeListe.getValeur()==14){
				possedeAs = true;
				indiceAs = partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().indexOf(carteDeListe);
			}
		}

		if (possedeDeux && !possedeAs){
			partie.getPaquetMilieu().getCartesMilieu().add(0, partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceDeux) );
			partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().remove(partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceDeux));
			System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+" replique et pose un Deux");
			partie.getLesJoueurs().get(joueurChoisi).piocher(partie);
			partie.testerFinPartie(joueurChoisi);



		}
		if (!possedeDeux && possedeAs){
			partie.getPaquetMilieu().getCartesMilieu().add(0, partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceAs) );
			partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().remove(partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceAs));
			System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+" replique et pose un As");
			partie.getLesJoueurs().get(joueurChoisi).piocher(partie);
			partie.testerFinPartie(joueurChoisi);
			this.effetCarte(partie, joueurChoisi);


		}

		if (possedeDeux && possedeAs){

			System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+" replique et choisi la carte qu'il pose");
			int choix = partie.getLesJoueurs().get(joueurChoisi).choisirAsOuDeux();
			if (choix == 1){
				partie.getPaquetMilieu().getCartesMilieu().add(0, partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceAs) );
				partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().remove(partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceAs));
				System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+" a choisi l'As");
				partie.getLesJoueurs().get(joueurChoisi).piocher(partie);
				partie.testerFinPartie(joueurChoisi);
				this.effetCarte(partie, joueurChoisi);
			}else {
				partie.getPaquetMilieu().getCartesMilieu().add(0, partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceDeux) );
				partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().remove(partie.getLesJoueurs().get(joueurChoisi).getMainJoueur().get(indiceDeux));
				System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+" a choisi le Deux");
				partie.getLesJoueurs().get(joueurChoisi).piocher(partie);
				partie.testerFinPartie(joueurChoisi);
			}


		}
		if (!possedeDeux && !possedeAs) {
			partie.getLesJoueurs().get(joueurChoisi).cocogner(partie);
			System.out.println("Le joueur "+partie.getLesJoueurs().get(joueurChoisi).getNumJoueur()+" amasse le paquet");
		}

	}




}
