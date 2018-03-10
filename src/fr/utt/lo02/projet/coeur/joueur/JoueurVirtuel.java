package fr.utt.lo02.projet.coeur.joueur;

import fr.utt.lo02.projet.coeur.carte.Carte;
import fr.utt.lo02.projet.coeur.partie.Partie;


/**
 * Cette represente les joueur virtuels. Le nombre de joueur virtuel est choisi par l'utilisateur au debut du programme. A la difference des 
 * joueurs reels, leur interaction avec le programme n'est pas choisi par l'utilisateur. En effet, ces joueurs possedent un attribut <b>strategie</b> (novice, agressif ou aleatoire) et leurs actions dans le jeux sont dictees par la strategie qu'ils possendent.
 * Ainsi les methodes comme poserCarte(Partie partie), echangerCarte(Partie partie) sont differentes selon la strategie qu'un joueur virtuel possede.
 * 
 * @see Joueur
 * @see JoueurReel
 * @see Strategie
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class JoueurVirtuel  extends Joueur{
	
	/* Attribut --------------------------------------------*/
	private Strategie strategie;
	
	
	
	/* Constructeur ------------------------------------- */
	/**
	 * Un joueur virtuel est cree a partir du constructeur de sa classe mere Joueur. De plus, une strategie lui es attribuee.
	 * @param numJoueur
	 * @param strategie
	 */
	public JoueurVirtuel(int numJoueur, Strategie strategie) {
		super(numJoueur);
		this. strategie= strategie;
		System.out.println("le joueur "+this.numJoueur+" a une strategie de type "+this.strategie);
	}
	
	
	
	/* Méthode */
	/**
	 * Cette methode permet a un joueur virtuel de poser une ou plusieurs cartes identiques. Elle est appelee dans la methode verifierCartesJouables
	 * si un joueur possede au moins une carte jouable. Cette methode, bien que la meme pour tous les joueurs virtuels, aura des resultats bien differents
	 * car on appelle la strategie du joueur pour choisir la carte a poser. <p>
	 * Dans un entier indiceCarteChoisie on rentre l'indice de la carte de la main du joueur virtuel qu'il a choisi de poser. C'est grace a la 
	 * methode choisirCarte de la strategie du joueur qu'on recupere cet indice. Les cartes de la main du joueur sont ensuite rendues non jouables
	 * pour le tour suivant (une carte jouable a un tour ne l'est pas forcement au suivant). La carte est ajoutee au paquet du milieu. On reference 
	 * la carte qui vient d'etre posee dans cartePosee et on regarde si le joueur virtuel possede une autre carte de meme valeur dans sa main grace 
	 * a la methode contientCarte. On initialise nbCartesJouees a 1 et on entre dans une boucle "while". Les conditions pour sortir de cette 
	 * boucle sont : la main du joueur contient encore une carte identique a celle posee, le joueur a pose moins de 3 cartes (nbCartesPosees <3) et 
	 * le joueur souhaite encore poser une carte identique. <p> Si toutes ces conditions sont verifiees, on recupere l'indice de la carte identique
	 * , on l'ajoute au milieu, et on incremente le nombre de cartes jouees. Enfin, on applique l'effet special de la (des) carte(s) posee(s), on 
	 * donne la valeur 0 a nbCarteJouees pour le tour suivant et on appelle la methode piocher.
	 * 
	 * @param partie
	 * 
	 */
	public void poserCarte(Partie partie){
		
		
		/* récupere l'indice de la carte que veux jouer le joueur*/
		int indiceCarteChoisie = this.strategie.choisirCarte(this.mainJoueur);
		
		/* reinitialise la valeur des carte de la main du joueur a non jouable*/
		this.rendreCartesNonJouable(this.mainJoueur);
		
		/* place la carte choisie sur le paquet du milieu */
		System.out.println("Je pose la carte "+this.mainJoueur.get(indiceCarteChoisie).getNom());
		this.ajouterCarteAuMilieu(indiceCarteChoisie, this.mainJoueur, partie);
		
		
		/* ligne pour simplifier le nom de la carte qui vient d'être posée */
		Carte cartePosee=partie.getPaquetMilieu().getCartesMilieu().get(0);
		
		/* lignes permettant de verifier lors de l'execuion du code*/
		if(this.contientCarte(cartePosee, this.mainJoueur)==true){
			System.out.println("la main contient un autre "+cartePosee.getNom());
		}else{
			System.out.println("la main ne contient pas d'autre "+cartePosee.getNom());
		}
			
		/* donne le nombre de carte deja posée durant le tour du joueur */
		this.nbCartesJouees=1;
		
		/* TANT QUE le joueur a une carte dans sa main sui est identique à celle posee
		 * ET qu'il à posee durant ce tour moins de 3 cartes
		 * ET qu'il veut poser les cartes doublée */
		while(this.contientCarte(cartePosee, this.mainJoueur)==true && this.nbCartesJouees<3 && strategie.poserPlusieursCartes(cartePosee)){

			int indice=this.indiceCarteIdentique(cartePosee, this.mainJoueur);
			System.out.println("Je pose à nouveau la carte "+this.mainJoueur.get(indice).getNom());
			this.ajouterCarteAuMilieu(indice, this.mainJoueur, partie);
			this.nbCartesJouees++;
		}

		partie.getPaquetMilieu().setDerniereCartePosee(cartePosee.getNom());
		cartePosee.effetCarte(partie);
		this.nbCartesJouees=0;	
		this.piocher(partie);
	}

	/**
	 * Cette methode permet a un joueur virtuel d'echanger les cartes de sa main avec celles faces visibles. Elle est appelee dans le main avant
	 * que la partie ne commence . Tout d'abord, on va creer un tableau de deux entiers. Dans la premiere case, on aura l'indice de la carte a 
	 * echanger de la main du joueur, dans la deuxieme, celui de la carte face visible a echanger. On utilisera une reference a la carte face 
	 * visible a echanger carteTmp, initialisee a null. On va ensuite rentrer dans une boucle "while" si le joueur virtuel souhaite echanger ses 
	 * cartes. La methode echangerCarte est appelee et renvoie un booleen qui correspond au choix du joueur d'echanger ses cartes ou non. Cette methode
	 * renvoie des valeurs differentes selon la strategie qui le joueur virtuel possde. Si le joueur souhaite echanger ses cartes, on recupere dans
	 * le tableau d'indice les valeurs des indices qui cartes a echanger. On reference la carte face visible a echanger dans carteTmp (l'indice 
	 * de cette carte est dans tabIndice[1]). On remplace ensuite la carte visible a echanger par celle de la main (dont l'indice ets dans tabIndice[0]).
	 * Dans la main du joueur on remplace aussi la carte de la main a echanger par celle face visible (dont la reference est carteTmp). 
	 * 
	 * 
	 * @param partie
	 */
	public void echangerCartes(Partie partie){
		/* à redefinir */
		int tabIndice[];
		tabIndice=new int [2]; 	// tabIndice[0] : indice de la carte a echanger de la main du joueur 
								// tabIndice[1] : indice de la carte a echanger des carte visibles
		Carte carteTmp=null;
		
		
		while (this.strategie.echangerCarte(this.mainJoueur, this.carteVisible)==true){
			
			/* recuperation des indices des cartes a echanger*/
			tabIndice=this.strategie.indicesCartesEchangees(this.mainJoueur, this.carteVisible);
			
			/* echange des cartes*/
			carteTmp=this.carteVisible.get(tabIndice[1]);
			this.carteVisible.set(tabIndice[1], this.mainJoueur.get(tabIndice[0]));
			this.mainJoueur.set(tabIndice[0], carteTmp);
		}
	}
	
	/**
	 * Cette methode permet de recuperer le numero d'un joueur que le joueur virtuel a choisi. Elle est appelee dans l'effet special de l'as.
	 * On appelle la methode  choixJoueur definie dans les strategies (Novice, Expert et Agressif) dans laquel on passe en parametre le numero
	 * du joueur qui appelle cette methode et le nombre de joueurs dans la partie.
	 * On donne a l'entier indiceJoueur la valeur retournee par cette methode.
	 * @param partie
	 * @return indiceJoueur : cet entier correspond au numero du joueur a qui le joueur a choisi d'envoyer le paquet du milieu.
	 */
	public int choisirJoueur(Partie partie){
		
		int indiceJoueur = this.strategie.choixJoueur(this.getNumJoueur(), partie.getNbJoueurs(), partie);
		return indiceJoueur;
	}
	
	/**
	 * Cette methode permet de recuperer le choix d'un joueur virtuel entre un as ou un deux. Elle est appelee lorsque le joueur est cible par 
	 * l'effet special d'un as et que celui ci possede un as et un deux pour repliquer.  Cette methode va donc appeler la methode choisirAsOuDeux
	 * definie dans les strategies (Novice, Expert, Agressif) et renvoie un entier.
	 * @return choix : un entier qui vaut 1 ou 2 selon le choix du joueur.
	 */
	public int choisirAsOuDeux(){
		int choix = this.strategie.choixAsOuDeux();
		return choix;
	}
	

	
	
	
}
