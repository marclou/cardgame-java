package fr.utt.lo02.projet.coeur.joueur;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

import fr.utt.lo02.projet.coeur.carte.Carte;
import fr.utt.lo02.projet.coeur.partie.Partie;

/**
 * Cette classe represente un joueur reel qui va interagir avec le jeux.
 * Elle herite de la superclasse Joueur. Elle possede donc tous ses atributs et ses methodes et elle redefinie les methodes abstraites comme poserCarte(Partie partie)
 * Un objet de cette classe est instancie lorsque le programme s'execute. Par defaut, son attribut numJoueur vaut 1. Le joueur reel possede en plus deux attributs : 
 * 
 * <ul>
 *  <li> <b>carteInterfaceChoisi</b> : un booleen initalise a "false" qui prendra la valeur "true" uniquement lorsque le joueur reel aura appuye sur une carte quil aura choisi de poser.
 *  Il va permettre l'interaction avec l'interace graphique. En effet, tant que le joueur n'aura pas appuye sur la carte quil a choisie, ce booleen vaudra "false" 
 *  et le programme sera en attente de la reponse du joueur.
 *  <li> <b>indiceCarteInteface</b> : un entier qui peut peut prendre des valeurs allant de 1 aux nombres de cartes que possede un joueur dans sa main. Sa valeur lui sera attribuee 
 *  lorsque le joueur aura choisi la carte a poser et vaudra l'indice de la carte choisie.
 * </ul>
 * 
 * 
 * @see JoueurVirtuel
 * @see Joueur
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class JoueurReel extends Joueur{
	
/* ------------------------------------------------------------------------------------------------
* Attributs
* ------------------------------------------------------------------------------------------------*/
	/* attribut utilise pour permettre la communication avec l'Interface Graphique */
	protected boolean carteInterfaceChoisi=false; // passe a true quand un bouton de l'interface graphique représentant une carte jouable est cliqué 
	protected int indiceCarteInteface;
	
	
	
/* ------------------------------------------------------------------------------------------------
* Constructeur
* ------------------------------------------------------------------------------------------------*/
	/**
	 * Le constructeur d'un joueur reel fait appel au constructeur de sa classe mere Joueur.
	 * @param numJoueur 
	 */
	public JoueurReel(int numJoueur) {
		super(numJoueur);
	}
	
	
	
/* ------------------------------------------------------------------------------------------------
* Methodes
* ------------------------------------------------------------------------------------------------*/
	/**
	 * Cette methode permet a un joueur reel de choisir la ou les cartes qu'il desire poser. Elle est appelee dans la methode verifierCartesJouables
	 * si un joueur possede au moin une carte jouable. Ainsi, on entre directement dans une bouce "while" et on en sort lorsque le joueur reel 
	 * a appuye sur une carte qui est jouable (le booleen carteInterfaceChoisi devient alors "true"). En appuyant sur la carte que le joueur
	 *  a choisi dans sa main, on recupere dans indiceCarte l'indice de la carte qui est dans indiceCarteInteface. Ensuite, le booleen carteInterfaceChoisi
	 *  est retabli a "false" pour le tour suivant et les cartes de la main du joueur sont toutes rendues non jouables par la methode rendreCartesNonJouables.
	 *  La carte choisie par le joueur est ajoutee au paquet du milieu et dans un objet cartePosee de type Carte on reference la carte posee. Le nombre
	 *  de cartes jouees nbCartesJouees vaut 1 et le booleen poserPlusieursCartes vaut "false". On va ensuite tester si le joueur possede une 
	 *  carte de meme valeur que celle posee grace a la methode contientCarte dont on passe en parametre la main du joueur et la carte posee. Si c'est le cas
	 *  Le booleen poserPlusieursCartes peut prendre la valeur  "true" si le joueur reel choisi de poser une carte de meme valeur. Tant que le joueur
	 *  possede une carte identique a celle posee, qu'il a joue moin de 3 cartes et qu'il veut encore poser une carte identique, on rentre dans une boucle
	 *  "while". Dans cette boucle, on recupere l'indice de la carte identique dans indiceCarteDouble grace a la methode indiceCarteIdentique avec 
	 *  comme parametre la carte posee et la main du joueur. Ensuite, on ajoute la carte au milieu et on incremente nbCartesJouees qui, une fois superieur
	 *   a 3 va permettre de sortir de la boucle "while". Enfin on va verifier si le joueur possede une autre carte identique. Si c'est le cas
	 *   on va lui demander s'il veut la poser, sinon le booleen poserPlusieursCartes vaudra "false" et on sortira de la boucle "while".
	 * A la fin de cette methode, on applique l'effet special de la carte posee, on retablie le nombre de carte jouees a 0 pour le tour suivant
	 * et on fait piocher le joueur.
	 * 
	 * @param partie
	 */
	public void poserCarte(Partie partie){
		/* Attente d'une interaction avec un bouton de l'Interface Graphique */
		while (this.carteInterfaceChoisi==false){	
			System.out.println("");
		}	
	
		/* recuperation de l'indice de la carte choisie dans l'Interface  */
		int indiceCarte=this.indiceCarteInteface;
		this.setCarteInterfaceChoisi(false);
		
		/* Rend les cartes de la main non jouable */
		this.rendreCartesNonJouable(this.mainJoueur);
		
		/* Ajoute la carte choisi sur le paquet du milieu et la supprime de la main du joueur */
		this.ajouterCarteAuMilieu(indiceCarte, this.mainJoueur, partie);
	 
		Carte cartePosee=partie.getPaquetMilieu().getCartesMilieu().getFirst();		 
		this.nbCartesJouees=1;
		boolean poserPlusieursCartes=false;

		/* 	Si il existe une carte de meme valeur dans la main du joueur que celle posee */
		if (this.contientCarte(cartePosee, this.mainJoueur)==true){			
			/* 	Demande au joueur si il veut poser une carte identique presente son jeu */
			poserPlusieursCartes=this.poserPlusieursCartes(cartePosee);
			/* TANT QUE le joueur a une carte dans sa main qui est identique à celle posee
			 * ET qu'il a posee durant ce tour moins de 3 cartes
			 * ET qu'il veut poser les cartes doublée */
			while(this.contientCarte(cartePosee, this.mainJoueur)==true && this.nbCartesJouees<3 && poserPlusieursCartes==true){
				/* On recupere l'indice de la carte qui est identique */
				int indiceCarteDouble=this.indiceCarteIdentique(cartePosee, this.mainJoueur);		
				this.ajouterCarteAuMilieu(indiceCarteDouble, this.mainJoueur, partie);
				this.nbCartesJouees++;
					// 	si la main contient encore une carte identique on redemande au joueur 
				if (this.contientCarte(cartePosee, this.mainJoueur)==true && this.nbCartesJouees<3){
					poserPlusieursCartes=this.poserPlusieursCartes(cartePosee);
				}else{
					poserPlusieursCartes=false;
				}
			}//fin while
		}	
		
		partie.getPaquetMilieu().setDerniereCartePosee(cartePosee.getNom());
		cartePosee.effetCarte(partie); 
		this.nbCartesJouees=0;
		this.piocher(partie);	
	}	
	
	/**
	 * Cette methode permet a un joueur reel d'echanger ou non ses cartes en debut de partie. Elle est appelee dans le main avant de commencer le jeux.
	 * Dans cette methode on retrouve :
	 * <li> echangerCartes : un booleen initialise a "false" qui permet de savoir si un joueur veut ou non echanger ses cartes.
	 * <li> indiceMain : un entier qui represente l'indice de la carte de la main a echanger.
	 * <li> indiceCartesVisibles : un entier qui represente l'indice de la carte face visible a echanger.
	 * <li> carteTmp : un objet de type Carte dans lequel on reference la carte visible a echanger avec celle de la main.
	 * <li> reponse : un caractere initialise a 'F' qui peut devenir 'O' ou 'o' si le joueur souhaite echanger ses cartes , ou 'N', 'n' s'il refuse.
	 * il changera de valeur quand le joueur donnera sa reponse (Oui ou Non) et permet de continuer de demander le choix au joueur s'il ne donne
	 * pas une reponse correcte.
	 * 
	 * <p> Tout d'abord, on cree deux objets jop et jop2 de type JOptionPane qui vont afficher les messages aux joueurs. On entre dans une boucle "while" et on n'en sort 
	 * que si le joueur donne une reponse correcte (reponse ne vaudra alors plus 'F').  Ensuite on affiche une fenetre qui recupere le premier caractere
	 * saisi par l'utilisateur grace a la methode showInputDialog de l'objet jop. Si la reponse donnee est 'O' ou 'o' alors le booleen echangerCartes
	 * vaudra "true", si la reponse est 'N' ou 'n' echangerCartes vaudra "false". Si l'utilisateur ne rentre pas une reponse correcte, ou s'il ferme la fenetre
	 *  l'objet jop2  affichera un affichera un message d'erreur.
	 *  
	 *  <p>Ensuite, on entre dans un boucle "while" dont la seule condition de sortie est que l'utilisateur ne souhaite plus echanger ses cartes (
	 *  apres les avoir echange une premiere fois). l'objet jop2 affiche un message demandant au joueur de choisir la carte qu'il souhaite echanger.
	 *  On attend l'interaction avec l'interface graphique, des que le joueur a choisi la carte, on sort de la boucle infini "while". Dans indiceMain
	 *   on recupere l'indice de la carte a echanger. On fait de meme pour recuperer l'indice de la carte face visible que le joueur souhaite mettre
	 *   dans sa main. 
	 *   <p> Dans carteTmp on reference la carte face visible que le joueur souhaite echanger. Dans la collection de cartes visibles, on ajoute a la 
	 *   place de la carte a echanger (indiceCartesVisibles) la carte de la main du joueur a echanger (indiceMain). Dans la main du joueur on ajoute
	 *   a la place de la carte a echanger (indiceMain) la carteTmp qui reference la carte visible a echanger. On met a jour l'interface graphique 
	 *   pour que le joueur voit la nouvelle position des cartes et on redemande a l'utilisateur s'il souhaite continuer a echanger ses cartes (cf
	 *   premier paragraphe). Si le joueur souhaite continuer a echanger ses cartes, le booleen echangerCartesVisibles vaudra "true" et on demandera
	 *   a l'utilisateur les cartes a echanger (cf deuxieme paragraphe). Sinon on sort de la boucle "while"
	 *
	 * 
	 * @param partie
	 * @throws Exception si le joueur ferme la fenetre de dialogue.
	 */
	public void echangerCartes(Partie partie){
		boolean echangerCartes=false;
		int indiceMain;
		int indiceCartesVisibles;
		Carte carteTmp=null;
		char reponse='F';
		JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
		
		/* On demande au Joueur si il veut echanger ses cartes */
		do{
			try{
				reponse = (jop.showInputDialog(null, "Voulez vous echanger des cartes de votre main avec des cartes visibles ? (O/N)", "Echanger Carte", JOptionPane.QUESTION_MESSAGE)).charAt(0);
				if (reponse=='n' || reponse=='N' ){
					echangerCartes=false;
				}else if (reponse=='O' || reponse=='o' ){
					echangerCartes=true;
				}else{
					jop2.showMessageDialog(null, "erreur : Entrer O pour Oui et N pour Non recommencer", "poser plusieurs cartes", JOptionPane.ERROR_MESSAGE);
					reponse='F';
				}
			}catch(Exception e){
				jop2.showMessageDialog(null, "erreur : Recommencer", "nombre de joueurs virtuels", JOptionPane.ERROR_MESSAGE);
			}			 
		}while (reponse=='F');
		
		/* Tant que le joueur veux echanger des cartes */
		while (echangerCartes==true){
			jop2.showMessageDialog(null, "Quelle Carte de la main voulez vous echanger", "echanger carte", JOptionPane.INFORMATION_MESSAGE);
			
			/* On attend une interaction avec l'Interface Graphique*/
			while (this.carteInterfaceChoisi==false){			
				System.out.println("");					
			}				
			this.setCarteInterfaceChoisi(false);
			indiceMain=this.indiceCarteInteface;

			jop2.showMessageDialog(null, "Avec quelle carte visible voulez vous l'échanger", "echanger carte", JOptionPane.INFORMATION_MESSAGE);
			
			/* On attend une interaction avec l'Interface Graphique*/
			while (this.carteInterfaceChoisi==false){			
				System.out.println("");
			}				
			this.setCarteInterfaceChoisi(false);
			indiceCartesVisibles=this.indiceCarteInteface;
			
			/* On echange les deux carte */
			carteTmp=this.carteVisible.get(indiceCartesVisibles);
			this.carteVisible.set(indiceCartesVisibles, this.mainJoueur.get(indiceMain));
			this.mainJoueur.set(indiceMain, carteTmp);
			
			/* Mise a jour de l'Interface Graphique */
			partie.setChanged();
			partie.notifyObservers(partie);
			
			/* On demande au Joueur si il veux encore echanger des cartes */
			do{
				 try{
					 	reponse = (jop.showInputDialog(null, "voulez vous encore echanger des cartes de votre main avec des cartes visibles ?", "echanger Carte", JOptionPane.QUESTION_MESSAGE)).charAt(0);
						if (reponse=='n' || reponse=='N' ){
							echangerCartes=false;
						}else if (reponse=='O' || reponse=='o' ){
							echangerCartes=true;
						}else{
							jop2.showMessageDialog(null, "erreur : Entrer O pour Oui et N pour Non recommencer", "poser plusieurs cartes", JOptionPane.ERROR_MESSAGE);							reponse='F';
						}
				 }catch (Exception e){
						jop2.showMessageDialog(null, "erreur : Recommencer", "nombre de joueurs virtuels", JOptionPane.ERROR_MESSAGE);
						reponse='F';
				 } 
			 }while (reponse=='F');
		}//fin while		
	}
	
	/**
	 * Cette methode permet de demander a un joueur reel s'il veut ou non poser plusieurs cartes de meme valeur. Elle est appelee dans la methode 
	 * poserCarte, si la main du joueur qui vient de poser une carte contient au moins une carte de meme valeur. Dans cette methode on retrouve
	 * un booleen poserPlusieursCartes, initialise a "false" qui changera de valeur si le joueur souhaite poser plusieurs cartes. Il y a aussi un 
	 * charactere reponse, initialise a 'F', qui peut devenir 'O' ou 'o' si le joueur souhaite poser plusieurs cartes , ou 'N', 'n' s'il refuse.
	 * il changera de valeur quand le joueur donnera sa reponse (Oui ou Non) et permet de continuer de demander le choix au joueur s'il ne donne
	 * pas une reponse correcte. <p> On va ensuite cree deux objets op et jop2 de type JOptionPane, qui afficheront les messages a l'utilisateur.
	 * On entre dans une boucle "while" et on n'en sort pas tant que le joueur n'a pas donne de reponse correcte (Oui et non). l'objet jop affiche
	 * une question demandant a l'utilisateur s'il souhaite poser plusieurs cartes identiques, grace a la methode showInputDialog. Le booleen 
	 * poserPlusieursCartes prendra la valeur "true" ou "false" selon le choix de l'utilisateur. Si celui ci ne donne pas une reponse correcte
	 * ou ferme la fenetre, alors l'objetjop2 affiche un message d'erreur grace a sa methode showMessageDialog et on redemande a l'utilisateur 
	 * s'il souhaite poser a nouveau une carte de meme valeur.
	 * Une fois sorti de la boucle "while" , la methode retourne la valeur du booleen poserPlusieursCartes qui correspond au choix du joueur.
	 * 
	 * 
	 * @throws Exception si le joueur ferme la fenetre 
	 * @param cartePosee : La carte dont le joueur en possede une de meme valeur.
	 * @return Cette methode retourne un booleen qui correspond au choix du joueur de poser ou non plusieurs cartes identiques
	 */
	private boolean poserPlusieursCartes(Carte cartePosee){
		boolean poserPlusieursCartes=false;
		char reponse='F';
		JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
		 do{
			 try{
				 reponse = (jop.showInputDialog(null, "voulez vous poser a nouveau un "+cartePosee.getNom()+" ?", "poser nouvel carte", JOptionPane.QUESTION_MESSAGE)).charAt(0);		
				 if (reponse=='n' || reponse=='N' ){
					 poserPlusieursCartes=false;
				 }else if (reponse=='O' || reponse=='o' ){
					 poserPlusieursCartes=true;
				 }else{
					 jop2.showMessageDialog(null, "erreur : Entrer O pour Oui et N pour Non, recommencer", "poser plusieurs cartes", JOptionPane.ERROR_MESSAGE);						
					 reponse='F';
				 }
			 }catch (Exception e){
				 jop2.showMessageDialog(null, "erreur : Recommencer", "poser plusieurs cartes", JOptionPane.ERROR_MESSAGE);
				 reponse='F';
			 }		 
		 }while (reponse=='F');

		return poserPlusieursCartes;
	}
	
	
	/**
	 * Cette methode permet de demander a un joueur reel quel joueur il souhaite choisi. Cette methode est appelee dans l'effet special de l'as.
	 * En effet, lorsqu'un joueur pose un as il doit choisir le joueur a qui il envoie le paquet. Ainsi, on va d'abord creer deux objets JOptionPane
	 * jop et jop2 qui vont afficher les messages a l'utilisateur. Ensuite on recuperera l'indice du joueur choisi dans indiceJoueur, initialise a 0.
	 * Cet entier permettra de continuer a demander le choix de l'utilisateur s'il ne saisi pas une reponse correcte.<p>
	 * On entre alors dans une boucle "while" et on ne peut en sortir quand lorsque l'utilisateur aura choisi un numero d'utilisateur correct. On
	 *  recupere dans string de type String la reponse du joueur grace a la methode showInputDialog de l'objet jop. On va ensuite tester la reponse
	 *  du joueur. Tout d'abord, la reponse donnee par le joueur etant stockee dans un string, il est necessaire de la convertir en un entier
	 *  decimal grace a la methode parseInt.  On rentre le resultat dans indiceJoueur et on teste la valeur de l'indice. Si l'indice du joueur 
	 *  choisi est inferieur a deux (le 1 etant le joueur reel lui meme), ou superieur au nombre de joueur, indiceJoueur vaut 0 et on redemande 
	 *  a l'utilisateur son choix. 
	 *  La methode renvoie un entier correspondant au choix du joueur.
	 * 
	 * @return Cette methode renvoie un entier qui correspond au numero du joueur qu'il a choisi.
	 * @param partie
	 * @throws NumberFormatException si le joueur reel ne rentre pas un entier
	 */
	public int choisirJoueur(Partie partie){
		JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
		int indiceJoueur=0;
		String string="";
		
		while (indiceJoueur<1){
		    string = jop.showInputDialog(null, "quel joueur voulez vous choisir ?", "choix d'un joueur", JOptionPane.QUESTION_MESSAGE);
			try{
				indiceJoueur = Integer.parseInt(string);
				if( indiceJoueur < 2 || indiceJoueur > partie.getNbJoueurs()  ) {
					jop2.showMessageDialog(null, "Numero incorect", "choix d'un joueur", JOptionPane.ERROR_MESSAGE);
					indiceJoueur=0;
				}
			}catch(NumberFormatException e){
				jop2.showMessageDialog(null, "erreur : Recommencer", "choix d'un joueur", JOptionPane.ERROR_MESSAGE);		    }
	
		}
		jop2.showMessageDialog(null, "Vous avez choisi le joueur " + string, "choix d'un joueur", JOptionPane.INFORMATION_MESSAGE);	    
		return indiceJoueur;	
	}
	
	
	/**
	 * Cette methode permet de demander a un joueur reel avec quelle carte il souhaite contrer un as. Cette methode est appelee dans l'effet special de l'as.
	 * En effet, lorsqu'un joueur est cible par un as et qu'il possede un as et un deux, il doit choisir avec lequel repliquer.
	 *  Ainsi, on va d'abord creer deux objets JOptionPane
	 * jop et jop2 qui vont afficher les messages a l'utilisateur. Ensuite on recuperera le choix dun joueur sous forme d'un entier choix initialise a 0.
	 * Cet entier permettra de continuer a demander le choix de l'utilisateur s'il ne saisi pas une reponse correcte.<p>
	 * L'objet jop permet d'afficher une question a l'utilisateur grace a sa methode showInputDialog et on recupere la reponse dans un String string.
	 * Dans l'entier choix on converti la reponse de l'utilisateur donnee sous forme d'un String en un entier grace a la methode parseInt. 
	 * Si choix ne vaut pas 1 (il a choisi un As) ou 2 (un Deux) , la methode est relancee au debut.  Si l'utilisateur ferme la fenetre sans donner
	 * de reponse, une exception est levee et la methode est relancee au debut. Si la reponse de l'utilisateur est correcte la methode renvoie 
	 * le choix de l'utilisateur (1 ou 2) sous forme d'un entier.
	 * 
	 * 
	 * @return Cette methode renvoie un entier qui correspond au choix du joueur de poser un as ou un deux.
	 * @param partie
	 * @throws Exception si le joueur ferme la fenetre
	 */
	public int choisirAsOuDeux(){	
		int choix=0;
		JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();		
		String string="";

		try{
			string = jop.showInputDialog(null, "Vous avez ete designe pour cocogner suite a la pause d'un as, voulez vous contrer avec un As (tapper 1) ou un Deux (tapper 2)", "nombre de joueurs virtuels", JOptionPane.QUESTION_MESSAGE);

			choix = Integer.parseInt(string);
			if (choix != 1 || choix != 2){

				choix=this.choisirAsOuDeux();
				jop2.showMessageDialog(null, "Numero incorect", "choix As ou Deux", JOptionPane.INFORMATION_MESSAGE);
			}
		}catch(Exception e){ 
			jop2.showMessageDialog(null, "erreur recommencer", "choix As ou Deux", JOptionPane.ERROR_MESSAGE);		    	
			choix=this.choisirAsOuDeux();
		}
		jop2.showMessageDialog(null, "vous avez choisi la carte : "+choix, "choix As ou Deux", JOptionPane.INFORMATION_MESSAGE);
	    
		return choix;
	}

	
/* ------------------------------------------------------------------------------------------------
* Setter
* ------------------------------------------------------------------------------------------------*/	
	public void setCarteInterfaceChoisi(boolean carteInterfaceChoisi) {
		this.carteInterfaceChoisi = carteInterfaceChoisi;
	}

	public void setIndiceCarteInteface(int indiceCarteInteface) {
		this.indiceCarteInteface = indiceCarteInteface;
	}
	

	
}
