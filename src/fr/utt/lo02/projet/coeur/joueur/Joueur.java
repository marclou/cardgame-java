package fr.utt.lo02.projet.coeur.joueur;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import fr.utt.lo02.projet.coeur.carte.Carte;
import fr.utt.lo02.projet.coeur.partie.Partie;

/**
 * La superclasse Joueur est une classe abstraite. 
 * Elle est n'est donc jamais instanciee, ceux sont ses deux classes filles JoueurReel et JoueurVirtuel qui le seront.
 * Cette classe contient des methodes abstraites comme poserCarte(Partie partie) redefinie dans les classes filles
 * ainsi que des methodes non abstraites comme piocher(Partie partie) qui sont les memes pour les classes filles de cette superclasse.
 * Les attributs de cette classe sont declares protected pour etre accessibles aux classes filles. On retrouve : 
 * <ul>
 *  <li> <b>numJoueur</b> : le numero du joueur, un entier (le numero 1 est reserve au joueur reel).
 *  <li> <b>mainJoueur, carteCachee, carteVisible</b> : trois collections de cartes, qui correspondent aux cartes faces cachees, faces visibles et dans la main du joueur.
 *  <li> <b>aGagne </b>: un booleen initialise ˆ faux, et qui devient vrai lorsque lorsqu'un joueur n'a plus aucune carte. Il peut etre modifie 
 *  lors de l'appel de la methode testerFinPartie() de la classe Partie.
 *  <li> <b>nbCartesJouees</b> : un entier initalise a zero , qui est incremente a chaque fois qu'un joueur pose une carte. Sa valeur maximum est deux. Sa valeur tombe a 0 quand le joueur a fini de poser ses cartes.
 * </ul>
 * 
 * 
 * @see JoueurReel
 * @see JoueurVirtuel
 * @author Arthur Guedon et Marc Louvion
 *
 */
abstract public class Joueur {

	/* ------------------------------------------------------------------------------------------------
	 * Attributs
	 * ------------------------------------------------------------------------------------------------*/
	protected int numJoueur;
	protected ArrayList<Carte> mainJoueur = new ArrayList<Carte>();
	protected LinkedList<Carte> carteCachee = new LinkedList<Carte>();
	protected ArrayList<Carte> carteVisible = new ArrayList<Carte>();
	protected boolean aGagne;
	protected int nbCartesJouees=0;


	/* ------------------------------------------------------------------------------------------------
	 * Constructeur
	 * ------------------------------------------------------------------------------------------------*/
	/**
	 * Le constructeur d'un joueur est appele par le constructeur de ses classes filles JoueurReel et JoueurVirtuel. Il attribu a un joueur 
	 * un numero de joueur ainsi qu'un booleen aGagne a "false".
	 * @param numJoueur
	 */
	public Joueur (int numJoueur){		
		this.numJoueur=numJoueur;
		this.aGagne=false;
	}



	/* ------------------------------------------------------------------------------------------------
	 * Methodes Abstraites
	 * ------------------------------------------------------------------------------------------------*/
	/**
	 * Cette methode abstraite ajoute une carte sur le paquet du milieu et la supprime de la main du joueur.
	 * Elle est redefinie dans les classes filles JoueurReel et JoueurVirtuel car son fonctionement differe selon le joueur qui pose la carte 
	 * et sa strategie.
	 * 
	 * @param partie
	 */
	abstract public void poserCarte(Partie partie); 

	/**
	 * Cette methode abstraite permet a un joueur d'echanger ou non ses cartes en main avec celles face visible en debut de partie
	 * Elle est redefinie dans les classes filles JoueurReel et JoueurVirtuel car son fonctionement differe selon le joueur 
	 * et sa strategie.
	 * @param partie
	 */
	abstract public void echangerCartes(Partie partie);

	/**
	 * Cette methode abstraite permet a un joueur de choisir s'il veut poser un As ou un Deux pour repliquer lorsqu'il est cible un As.
	 * Elle est redefinie dans les classes filles JoueurReel et JoueurVirtuel car son fonctionement differe selon le joueur 
	 * et sa strategie.
	 * @return Cette methode renvoie un entier qui vaut 1 si le joueur choisi de poser un As, ou 2 s'il veut poser un Deux.
	 */
	abstract public int choisirAsOuDeux();

	/**
	 * Cette methode abstraite permet a un joueur de choisir le numero du joueur a qui il envoie le paquet. Elle sera redefinie dans les classes
	 * filles JoueurReel et JoueurVirtuel car son fonctionement differe selon le type de joueur et sa strategie.
	 * @param partie
	 * @return un entier correspondant au numero du joueur qui va etre choisi pour amasser le paquet.
	 */
	abstract public int choisirJoueur(Partie partie);


	/* ------------------------------------------------------------------------------------------------
	 * Methodes
	 * ------------------------------------------------------------------------------------------------*/
	/**
	 * Cette methode permet de verifier si un joueur possede des cartes jouables par rapport a la derniere carte posee sur le paquet du milieu.
	 * Elle est appelee dans la boucle "while" du main. Tout d'abord, un booleen carteJouables est initialise a "false" et vaudra "true" si le 
	 * joueur actuel possede au moins une carte jouable. Ensuite, on va iterer toutes les cartes de la main du joueur  et la methode jouable()
	 * de chacune des cartes iterees est appelee. Si une carte est jouable, son booleen jouable vaudra alors "true" et le booleen carteJouables 
	 * vaudra "true". Cela veut dire que le joueur possede au moin une carte jouable.
	 * Il faut ensuite mettre a jour l'interface graphique par les methodes setChanged et notifyObserver.
	 * Enfin, on entre dans une structure "if/else".
	 * <li> si le joueur possede une carte jouable, la methode poserCarte de joueur est appelee et le joueur choisira la carte jouable qu'il veut
	 * poser.
	 * <li> si le joueur n'a aucune carte jouable, il cocogne, c'est a dire qu'il amasse toutes les cartes du paquet du milieu.
	 * @param partie
	 */
	public void verifierCartesJouables(Partie partie){
		boolean cartesJouables=false;
		/* on parcourt la main du joueur en appelant la methode jouable() des cartes pour qu'elle verifie elle meme si elles sont jouables*/
		Iterator<Carte> it=this.mainJoueur.iterator();
		while (it.hasNext()){
			Carte carte = it.next();
			carte.jouable(partie);   
			if (carte.isJouable()==true){
				cartesJouables=true;
			}
		}

		/* Mise à jour de l'interface graphique */
		partie.setChanged();
		partie.notifyObservers(partie);

		/* Jouer une cartes ou cocogner */
		if (cartesJouables==true ){
			this.poserCarte(partie);
		}
		else {
			this.cocogner(partie);
		}
	}

	/**
	 * Cette methode permet a un joueur de recuperer toutes les cartes du paquet du milieu. Elle est appelee si le joueur actuel ne possede aucune
	 * carte jouable.
	 * Tout d'abord un objet de type JOptionPane est cree pour informer le joueur qu'il cocogne. Si le joueur qui cocogne est le joueur reel,
	 * le message affiche est "vous cocognez", en revanche, si c'est un joueur virtuelqui cocogne, le message affiche est " le joueur X cocogne".
	 * C'est un mesage d'information. Ensuite, toutes les cartes de la collection de cartes du paquet du milieu sont ajoutees a la main du joueur
	 * qui cocogne puis sont supprimees.
	 * @param partie
	 */
	public void cocogner(Partie partie){
		JOptionPane jop = new JOptionPane();

		/* On informe le joueur que quelqu'un cocogne */
		if (this.numJoueur==1){
			jop.showMessageDialog(null, "Vous cocognez", "nombre de joueurs virtuels", JOptionPane.INFORMATION_MESSAGE);		    	
		}else{
			jop.showMessageDialog(null, "le joueur "+this.numJoueur+" Cocogne", "nombre de joueurs virtuels", JOptionPane.INFORMATION_MESSAGE);		    	
		}

		/* Ajoute toutes les cartes du paquet du milieu dans la main du Joueur */
		this.getMainJoueur().addAll(partie.getPaquetMilieu().getCartesMilieu());

		/* Suprimme ensuite ces cartes du paquet du millieu */
		partie.getPaquetMilieu().getCartesMilieu().removeAll(partie.getPaquetMilieu().getCartesMilieu());	
	}

	/**
	 * La methode piocher permet a un joueur de piocher une ou plusieurs cartes, ou de recuperer ses cartes face visible/cachee. Elle est appelee 
	 * a la fin du tour d'un joueur.
	 * <li> si la pioche n'est pas vide, un booleen piocheEstVide est initialise a "false" et change de valeur pour couvrir le cas ou le joueur
	 * piocherait plusieurs cartes et la pioche se viderait en cours de pioche. On entre ensuite dans une boucle "while" ou le joueur va piocher 
	 * les cartes une par une. la premiere condition de sortie est que le joueur ai plus de 3 cartes en mains, ou que le pioche se soit videe au cours 
	 * de la pioche (a chaque fois que le joueur pioche une carte, la pioche est testee pour savoir si elle est vide. si c'est le cas, le booleen
	 * piocheEstVide devient "true"). 
	 * <li> si la pioche est vide, que le joueur n'a pas de carte en main et qu'il a encore ses 3 cartes visibles, alors le joueur recupere les cartes 
	 * visibles.  Si le joueur n'a plus de cartes visibles et plus de cartes en main, alors il recupere ses cartes cachees.<ul>
	 * @param partie
	 */
	public void piocher(Partie partie){
		/* Prendre une ou plusieurs cartes de la pioche */
		if (partie.getPioche().getCartesPioche().size() != 0){
			boolean piocheEstVide = false; // Cf boucle if plus bas
			// A chaque tour : pioche une carte jusqu'a ce que le joueur en ai 3 en main, et verifie si la piohe ne s'est pas vide
			while (this.getMainJoueur().size() < 3 && piocheEstVide == false){
				this.getMainJoueur().add(partie.getPioche().getCartesPioche().getFirst());
				partie.getPioche().getCartesPioche().removeFirst();

				//Condition particuliere de sortie si jamais le joueur pioche 2 cartes (par exemple) et qu'il en reste qu'une dans la pioche
				if (partie.getPioche().getCartesPioche().size() == 0){ 
					piocheEstVide = true;
					System.out.println("\nLa pioche est maintenant vide");
				}
			}
			/* Cas ou la pioche est vide */	
		} if (partie.getPioche().getCartesPioche().size() == 0){

			// cas ou je joueur n'a plus de carte en main ET plus de carte dans la pioche ET encore ses cartes faces visibles
			if (this.getMainJoueur().size()==0 && this.getCarteVisible().size()==3){
				this.prendreCartesVisibles(partie);
			}

			// cas ou je joueur n'a plus de carte en main ET plus de carte dans la pioche ET plus de cartes faces visibles
			if (this.getMainJoueur().size()==0 && this.getCarteVisible().size()==0){
				if(this.getCarteCachee().size()!=0){
					this.prendreCarteCachee(partie);
				}
			}
		}
	}

	/** 
	 * Cette methode simple permet a un joueur de recuperer ses cartes face visible en les mettant dans sa main et en les supprimant
	 * de la collection carteVisible. Elle est appelee dans la methode piocher dans le cas ou la pioche est vide et que le joueur n'a plus de carte
	 * en main mais possede encore ses 3 cartes visibles.
	 * @param partie
	 */
	public void prendreCartesVisibles(Partie partie){
		this.getMainJoueur().addAll(this.carteVisible);
		this.carteVisible.removeAll(this.carteVisible);
	}

	/** 
	 * Cette methode simple permet a un joueur de recuperer une carte face cachee en la mettant dans sa main et en la supprimant
	 * de la collection carteCachee. Il n'est pas necessaire de choisir l'indice de la carte a recuperer car les cartes ne sont pas visibles,
	 * c'est le hasard qui choisi la carte. La methode est appelee dans la methode piocher dans le cas ou la pioche est vide et que le joueur n'a plus de carte
	 * en main et n'a plus ses 3 cartes visibles. La methode verifierCartesJouables est alors appelee pour tester si la cache cachee recuperee 
	 * est jouable. Si elle l'est, le joueur pose automatiquement cette carte.
	 * @param partie
	 */	
	public void prendreCarteCachee(Partie partie){
		this.getMainJoueur().add(this.getCarteCachee().get(0));
		this.getCarteCachee().removeFirst();

		this.verifierCartesJouables(partie);
	}

	/**
	 * Cette methode permet de modifier le booleen jouable de toutes les cartes de la collection passee en parametre pour le mettre a "false".
	 * Elle va donc iterer toutes les cartes de la collection et appeler la methode setJouable de chaque cartes pour modifier le booleen.
	 * La seule carte qui gardera son booleen a "true" est le Deux car sa methode setJouable rend ce booleen "true". Cette methode est appelee
	 * dans la methode poserCarte, c'est a dire lorsqu'un joueur a pose une carte. Le but de cette methode est donc de rendre les cartes du joueur
	 * non  jouables pour le tour suivant car la carte sur le paquet du milieu aura changee. En effet, une carte peut etre jouable a un tour, 
	 * mais pas forcement au suivant.
	 * @param listeCartes : la collection de cartes a rendre non jouables.
	 */
	public void rendreCartesNonJouable(ArrayList<Carte> listeCartes){

		/* 	parcours la liste des cartes envoyees en parametre et les rend non jouable (sauf pour le 2 et l'As)*/
		Iterator<Carte> it=listeCartes.iterator();
		while (it.hasNext()){
			Carte carte = it.next();
			carte.setJouable(false);	
		}				
	}



	/**
	 * Cette methode permet de savoir si un joueur possede deux cartes de meme valeur. Elle est appelee dans la methode poserCarte de Joueur, apres 
	 * que le joueur ai pose une carte au milieu. le parametre carte passe a la methode est donc la carte que le joueur vient de poser. La methode 
	 * permet donc de savoir si le joueur possede une autre carte de meme valeur qu'il pourrait poser. C'est pourquoi dans cette methode on itere
	 * une collection de cartes (la main du joueur) et si la valeur de la carte posee est la meme qu'une carte de la main du joueur, alors cette 
	 * methode renverra un booleen a "true".
	 * @param carte : la carte dont on cherche s'il en existe une de meme valeur
	 * @param listeCartes: la liste dans laquelle on regarde s'il existe une carte de meme valeur
	 * @return Cette methode renvoie un booleen qui vaudra "true" si le joueur a deux cartes de meme valeur.
	 */
	protected boolean contientCarte(Carte carte, ArrayList<Carte> listeCartes){
		boolean contientCarte=false;
		Iterator<Carte> it=listeCartes.iterator();
		while (it.hasNext() && contientCarte==false){
			Carte carteDeListe=it.next();
			if (carteDeListe.getValeur()==carte.getValeur()){
				contientCarte=true;
			}
		}
		return contientCarte;
	}


	/**
	 * Cette methode permet de retrouver l'indice d'une carte de meme valeur a celle passee en parametre. Elle est appelee dans la methode poserCarte
	 * dans le cas ou le joueur desire poser une autre carte identique. Ainsi on passe en parametre a cette methode la carte que le joueur vient
	 * de poser et la main du joueur pour y retrouver une carte identique. On va ainsi iterer chaque de la main du joueur et tester si sa valeur
	 * est la meme que celle de la carte posee. Si c'est le cas on recupere l'indice de la carte identique dans la main du joueur grace a la methode 
	 * indexOf des ArrayList.
	 * @param carte : la carte dont on cherche l'identique
	 * @param listeCartes : la collection ou l'on cherche la carte identique
	 * @return Cette methode renvoie une entier qui correspond a l'indice de la carte de meme valeur dans la main du joueur a celle passee en parametre.
	 */
	protected int indiceCarteIdentique(Carte carte, ArrayList<Carte> listeCartes){		
		int indice=0;
		boolean carteTrouvee=false;	
		Iterator<Carte> it=listeCartes.iterator();
		while (it.hasNext() && carteTrouvee==false){
			Carte carteDeListe=it.next();
			if (carteDeListe.getValeur()==carte.getValeur()){
				carteTrouvee=true;
				indice=listeCartes.indexOf(carteDeListe);
			}
		}
		return indice;
	}

	/**
	 * Cette methode simple permet d'ajouter une carte sur le paquet du milieu en premiere position et de la supprimer de la main du joueur.
	 * @param indiceCarte : l'indice de la carte a ajouter 
	 * @param listeCarte : la main du joueur 
	 * @param partie
	 */
	protected void ajouterCarteAuMilieu(int indiceCarte, ArrayList<Carte> listeCarte, Partie partie ){
		partie.getPaquetMilieu().getCartesMilieu().addFirst(this.mainJoueur.get(indiceCarte) );
		this.mainJoueur.remove(indiceCarte);
	}






	/* ------------------------------------------------------------------------------------------------
	 * Getter
	 * ------------------------------------------------------------------------------------------------*/
	public ArrayList<Carte> getMainJoueur() {
		return mainJoueur;
	}

	public int getNumJoueur() {
		return numJoueur;
	}

	public LinkedList<Carte> getCarteCachee() {
		return carteCachee;
	}

	public ArrayList<Carte> getCarteVisible() {
		return carteVisible;
	}

	public int getNbCartesJouees() {
		return nbCartesJouees;
	}

	public boolean isaGagne() {
		return aGagne;
	}



	/* ------------------------------------------------------------------------------------------------
	 * Setter
	 * ------------------------------------------------------------------------------------------------*/
	public void setMainJoueur(ArrayList<Carte> mainJoueur) {
		this.mainJoueur = mainJoueur;
	}

	public void setCarteCachee(LinkedList<Carte> carteCachee) {
		this.carteCachee = carteCachee;
	}

	public void setCarteVisible(ArrayList<Carte> carteVisible) {
		this.carteVisible = carteVisible;
	}

	public void setaGagne(boolean aGagne) {
		this.aGagne = aGagne;
	}

}
