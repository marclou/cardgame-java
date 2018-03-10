package fr.utt.lo02.projet.coeur.partie;

import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.utt.lo02.projet.coeur.joueur.Joueur;
import fr.utt.lo02.projet.vue.VueApplication;

/**
 * Cette classe represente le main de notre programme. C'est son point de depart. 
 * 
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class main{

	/**
	 * Cette methode est le point de depart de notre programme.
	 * Tout d'abord on demande au joueur de choisir le nombre de joueur virtuel contre qui il va joueur.
	 * Ensuite un tour d'echange de carte est propose au joueur et les joueurs virtuels vont echanger leurs cartes en fonction de leur strategie.
	 * On entre ensuite dans un boucle while et la seule condition de sortie est que le booleen d'un joueur passe a "true" : c'est a dire qu'un joueur doit gagner la partie.
	 * Un tour de boucle correspond a un tour pour un joueur. On demande a celui quelle carte il souhaite poser, puis on teste s'il a gagne,
	 * et enfin on change de tour. L'interface graphique est mise a jour au cours du programme par l'appel a nos methodes setChanged() et notifyObservers().
	 * Enfin, on affiche au joueur le nom du joueur qui a gagne, et le programme s'arrette.s
	 * @param args
	 */
	public static void main(String[] args){
		/*------------------------------------------------------------------------------------------------
		 * On recupere le nombre de Joueurs Virtuels en couvrant les exeptions 
		 *--------------------------------------------------------------------------------------------------*/
		JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();	
		String string="";
		int nombre=0;		
		while (nombre<1){
			try{
				string = jop.showInputDialog(null, "Avec combien de Joueurs Virtuels voulez-vous jouer ?(Mininum 1, Maximum 20)", "nombre de Joueurs Virtuels", JOptionPane.QUESTION_MESSAGE);
				nombre = Integer.parseInt(string);
				if( nombre < 1 ) {					
					jop2.showMessageDialog(null, "Numero incorect, le nombre doit etre superieur ou egual a 1", "nombre de Joueurs Virtuels", JOptionPane.ERROR_MESSAGE);
				}else if (nombre > 20){
					jop2.showMessageDialog(null, "Numero incorect, trop de joueurs", "nombre de Joueurs Virtuels", JOptionPane.ERROR_MESSAGE);
					nombre=0;
				}
			}catch(NumberFormatException e){
				jop2.showMessageDialog(null, "erreur : Entrer un nombre", "nombre de Joueurs Virtuels", JOptionPane.ERROR_MESSAGE);		    	
			}catch(Exception e){ 
				jop2.showMessageDialog(null, "erreur : Entrer un nombre", "nombre de Joueurs Virtuels", JOptionPane.ERROR_MESSAGE);		    	
			}			
		}
		jop2.showMessageDialog(null, "vous avez choisi de jouer avec " + string+" Joueurs Virtuels", "nombre de joueurs virtuels", JOptionPane.INFORMATION_MESSAGE);


		/* ------------------------------------------------------------------------------------------------
		 * On cree un objet de type Partie (le modele) et un objet de type VueApplication (la vue)
		 * ------------------------------------------------------------------------------------------------*/
		Partie partie= new Partie(nombre+1);//tous les elements d'une partie sont crées à partir du constructeur de la classe Partie
		VueApplication vue = new VueApplication (partie);


		/* ------------------------------------------------------------------------------------------------
		 * Déroulement du tour d'echange de cartes 
		 * ------------------------------------------------------------------------------------------------*/
		System.out.println("\n#############################");		
		System.out.println("TOUR d'ECHANGE DE CARTE ");
		System.out.println("#############################\n");

		for (int i=0 ; i< partie.getNbJoueurs(); i++){
			partie.getLesJoueurs().get(i).echangerCartes(partie);		
		}


		/* ------------------------------------------------------------------------------------------------
		 * Mise a jour de l'Interface Graphique
		 * ------------------------------------------------------------------------------------------------*/
		partie.setChanged();
		partie.notifyObservers(partie);


		/* ------------------------------------------------------------------------------------------------
		 * Deroulement d'un tour normale
		 * ------------------------------------------------------------------------------------------------*/				
		while(partie.isFinPartie()!=true){
			/* si c'est le tour du joueur 1 on affiche un message */
			if (partie.getTour()+1 == 1){
				jop2.showMessageDialog(null, "C'est a votre tour", "Tour de jeu", JOptionPane.INFORMATION_MESSAGE);
			}

			System.out.println("\n#####################");
			System.out.println("TOUR "+partie.getNbTour()+" DU JOUEUR "+partie.getLesJoueurs().get(partie.getTour()).getNumJoueur());
			System.out.println("#####################\n");

			/* Point de depart de toutes les méthodes pour modeliser le tour d'un joueur*/
			partie.getLesJoueurs().get(partie.getTour()).verifierCartesJouables(partie);

			/* mise à jour de l'Interface Graphique */
			partie.setChanged();
			partie.notifyObservers(partie);

			/* Methode qui va déterminer si un joueur a gagne ou non*/
			partie.testerFinPartie();

			/* Temps d'arret de 1 seconde entre le tour de chaque joueur */
			try {
				Thread.sleep(1000);               
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}		
		}


		/* ------------------------------------------------------------------------------------------------
		 * Fin de la partie et affichage du gagnant
		 * ------------------------------------------------------------------------------------------------*/	
		System.out.println("\n###################");
		System.out.println("LA PARTIE EST FINIE");
		System.out.println("###################\n");

		/* affichage du gagnant */	
		Iterator<Joueur> it = partie.getLesJoueurs().iterator();
		while (it.hasNext()){

			Joueur joueur=it.next();
			if (joueur.isaGagne()==true){
				if (joueur.getNumJoueur()==1){				
					jop2.showMessageDialog(null, "VOUS AVEZ GAGNE", "gagnant", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("FIN DE LA PARTIE : VOUS AVEZ GAGNE ");
				}
				else{
					jop2.showMessageDialog(null, "LE JOUEUR NUMERO "+joueur.getNumJoueur()+"A GAGNE", "gagnant", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("FIN DE LA PARTIE : LE JOUEUR NUMERO "+joueur.getNumJoueur()+" A GAGNE");
				}
			}			
		}


	}
}
