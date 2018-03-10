package fr.utt.lo02.projet.coeur.carte;

import fr.utt.lo02.projet.coeur.partie.Partie;



/**
 * Cette superclasse represente les cartes du jeux. Cependant cette classe est declaree abstraite, aucun objet de type Carte ne sera donc 
 * instancie. Les cartes du jeux seront donc creees a partir des sous classes CarteSimple, As, Deux, Sept, Huit et Dix.
 * Un objet de type Carte possede ainsi trois attributs, declares protected pour etre visible par les classes filles. Ces attributs sont : 
 * 
 *  <ul>
 *  <li> <b>valeur</b> : un entier qui represente la valeur de la carte. Cet entier peut prendre des valeurs allant de 2 (pour la carte Deux) 
 *  a 14 (pour la carte As).
 *  <li> <b>nom</b> : un string qui decrit les cartes "fortes", il prend des valeurs comme "Vallet","Roi" par exemple, en fonction de la valeur 
 *  de la carte.
 *  <li> <b>jouable </b>: un booleen initialise a "false", qui peut prendre la valeur "true" lorsque la methode jouable(Partie partie) de cette classe
 *  est appelee. Une carte sera jouable pendant le tour d'un joueur si elle est superieure a la carte sur le paquet du millieu (ou inferieure si 
 *  la derniere carte posee est une 7).
 *  
 * </ul>
 * @see CarteSpeciale
 * @see CarteSimple
 * @author Marc Louvion et Arthur Guedon
 *
 */
abstract public class Carte implements Comparable<Carte>{

	/* ------------------------------------------------------------------------------------------------
	 * Attributs
	 * ------------------------------------------------------------------------------------------------*/
	protected int valeur;
	protected String nom;
	protected boolean jouable;



	/* ------------------------------------------------------------------------------------------------
	 * Constructeur
	 * ------------------------------------------------------------------------------------------------*/	
	/**
	 * Le constructeur de la classe carte est appele par ses classes filles (As, CarteSimple...) par le biais du concept "super" dans le 
	 * constructeur des classes filles. Le parametre valeur correspond a la valeur de la carte. Une carte n'est pas jouable lorsqu'elle est creee.
	 * Dans la boucle switch, on attribue un nom aux cartes fortes selon leur valeur. Si ce n'est pas une carte forte qui est creee, son nom correspond
	 * a sa valeur.
	 * @param valeur
	 */
	public Carte (int valeur) {

		this.valeur=valeur;
		this.jouable=false;
		switch (this.valeur)
		{
		case 14:
			this.nom="As";
			break;
		case 11:
			this.nom="Valet";
			break;
		case 12:
			this.nom="Dame";
			break;
		case 13:
			this.nom="Roi";
			break;
		default:
			this.nom=String.valueOf(this.valeur);
		}
	}



	/* ------------------------------------------------------------------------------------------------
	 * Methodes Abstraites
	 * ------------------------------------------------------------------------------------------------*/
	public abstract void effetCarte(Partie partie);



	/* ------------------------------------------------------------------------------------------------
	 * Methodes
	 * ------------------------------------------------------------------------------------------------*/
	/**
	 * Cette methode permet de verifier si une carte est jouable ou non en la comparant avec la carte au dessus du paquet du milieu.
	 * Cette mathode est appele par la methode verifierCarteJouable() de la classe joueur qui va en realite appele la methode jouable sur chaque carte.
	 * Ainsi on entre dans une stucture "if/else" : 
	 * <li> Si il y a au moin une carte sur le paquet du milieu, on entre alors dans une autre stucture "if/else". Si la premiere carte du paquet
	 * du milieu, donc la derniere carte posee par un joueur, est un 7, alors la carte est jouable si sa valeur est inferieur ou egale a 7. le 
	 * booleen jouable de la carte qui appelle la methode devient alors "true". 
	 * Si la la carte sur le paquet du milieu n'est pas un 7 alors la carte qui appelle la methode jouable doit etre de valeur egale ou superieure pour
	 * avoir son attribut jouable a "true".
	 * <li> En revanche, si le paquet du milieu est vide, la carte est forcement jouable.
	 * 
	 * 
	 * @param partie
	 */
	public void jouable(Partie partie){
		/* si il y a des cartes dans le paquet du milieu on compare les valeur des cartes pour determiner si elle est jouable*/
		if (partie.getPaquetMilieu().getCartesMilieu().size() != 0){

			if ( partie.getPaquetMilieu().getCartesMilieu().getFirst().getValeur() == 7 ){
				if (this.valeur  <=   partie.getPaquetMilieu().getCartesMilieu().get(0).getValeur() ){
					this.jouable=true ;
				}
			}
			else{
				if (this.valeur  >=   partie.getPaquetMilieu().getCartesMilieu().get(0).getValeur() ){
					this.jouable=true ;
				}

			}


			/* si il n y a pas de carte dans le paquet du milieux, la carte et forcement jouable*/

		}else{
			this.jouable=true ;
		}
	}


	/**
	 * Cette methode est redefinie de l'interface Comparable et va permettre de trier les listes de carte selon leur valeur. Cela sera utilise 
	 * pour les streategie. Elle compare la carte qui appelle la methode avec une carte passe en parametre. 
	 * L'entier resultat prendra alors une des 3 valeurs selon si elle est superieur, inferieure ou egale. 
	 * 
	 * 
	 * @param carte
	 * 
	 * @return un entier qui peut prendre 3 valeurs. Il sera utilise pour trier les cartes selon leur valeur
	 */
	public int compareTo(Carte carte){
		int resultat=0; 

		if (this.valeur>carte.getValeur()){
			resultat=1;
		}
		else if (this.valeur == carte.getValeur()){
			resultat = 0;
		}
		else {
			resultat =-1;
		}
		return resultat;
	}



	/* ------------------------------------------------------------------------------------------------
	 * Getter
	 * ------------------------------------------------------------------------------------------------*/
	public int getValeur() {
		return valeur;
	}

	public String getNom() {
		return nom;
	}

	public boolean isJouable() {
		return jouable;
	}



	/* ------------------------------------------------------------------------------------------------
	 * Setter
	 * ------------------------------------------------------------------------------------------------*/
	public void setJouable(boolean jouable) {
		this.jouable = jouable;
	}	


}
