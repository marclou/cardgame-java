package fr.utt.lo02.projet.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import fr.utt.lo02.projet.coeur.carte.Carte;
import fr.utt.lo02.projet.coeur.joueur.JoueurReel;
import fr.utt.lo02.projet.coeur.partie.Partie;


/**
* Cette classe permet de modéliser l'interface graphique du jeu de la bataille norvegienne et de gerer sa mise à jour tout au long d'une partie 
* suite aux appels successifs de la methode update.
* L'interface est divisee en trois partie : haute, intermediaire et basse.
* 
* <ul>
* <li> <b>fenetre </b> : une JFrame qui va etre divisee en 4 verticalement a l'aide de JSplitPane qui seront remplis de JPannel 
* <li> <b>panelInfoJoueur</b> : un JPanel qui occupe la partie haute de la fenetre. Ce panel est divise en 2 horizontalement, dans la 
* premiere partie se place un panel idiquant des informations sur les joueurs virtuels et dans la deuxieme partie un panel dans lequel 
* est indique l'avancement des tours de jeu.
* <li> <b>panelCarteMilieu </b>: un JPanel qui occupe la partie centrale superieure. Ce panel va permettre d'afficher des infos sur le paquet du milieu
* <li> <b>panelCarteVisibleEtInfo </b>: un JPanel divise en 3 horizontalement qui occupe la partie centrale inferieur, il va contenir 
* des JPanel qui contienne respectivement le nombre de cate dans la pioche, les cartes visibles du joueur et le nombre de cartes cachees.
* <li> <b>panelCarteMain </b>: Un JPanel divise en 2 verticalement qui occupe la partie basse de la fenetre, il contients des JButton qui 
* representent les cartes que la joueur a en main.
* <li> <b>partie </b>: un objet de type partie qui permet d'acceder a toutes les donnees sur la partie en cours (ex: nombre de carte dans la main d'un joueur)
* <li> <b>listeBoutonCarteMain </b>: une ArrayList de BoutonCarte qui va représentaer toutes les cartes de la main du joueur reel et avec lesquel il va pouvoir
* interrargir quand ce sera a son tour de jouer.
* <li> <b>listeBoutonCarteVisible </b>: une ArrayList de BoutonCarte qui va représentaer toutes les cartes face visible devant le joueur reel et avec 
* lesquel il va pouvoir interrargir pendant le tour d'echange au debut de la partie.
* <li> <b>listeLabelJoueur </b>: une ArrayList de Label qui va se positionner en haut à gauche de la fenetre principale et dont le but est d'afficher toutes
* les infos utiles sur les autres joueurs durant la partie.
* </ul>
* @author Marc Louvion et Arthur Guedon
*
*/
public class VueApplication implements Observer{
	
/* ------------------------------------------------------------------------------------------------
* Attributs
* ------------------------------------------------------------------------------------------------*/
	/* Fenetre */
	private JFrame fenetre = new JFrame();
	
	/* Partie haute de la fenetre */
	private JPanel panelInfoJoueurs = new JPanel();
	private JPanel panelJoueurs = new JPanel();
	private JPanel panelTour = new JPanel();
	private Label labelTour;
	
	/* Partie intermediaire haute de la fenetre */
	private JPanel panelCarteMilieu = new JPanel();
	private Label labelCarteMilieu;
	
	/* Partie intermediaire basse de la fenetre */
	private JPanel panelCarteVisibleEtInfo = new JPanel();
	private JPanel panelNbCartesPioche = new JPanel();
	private Label labelNbCartesPioche ;	
	private JPanel panelCarteVisibleGlobal = new JPanel();
	private JPanel panelCarteVisible = new JPanel();
	private Label labelCarteCachee;
	
	/* Partie basse de la fenetre */
	private JPanel panelCarteMain = new JPanel();
	private JPanel panelMain = new JPanel();
	
	/* Tableau d'objet graphique */
	private ArrayList<BoutonCarte> listeBoutonCarteMain= new ArrayList<BoutonCarte> () ;
	private ArrayList<BoutonCarte> listeBoutonCarteVisible= new ArrayList<BoutonCarte> () ;
	private ArrayList<Label> listeLabelJoueur = new ArrayList<Label> ();
	
	Partie partie;
	
	
/* ------------------------------------------------------------------------------------------------
* Constructeur
* ------------------------------------------------------------------------------------------------*/
	/**
	 * Le constructeur de la classe VueApplication va creer tous les composants graphiques et les positionner les un par rapport aux autres
	 * @param partie
	 */
	public VueApplication (Partie partie){
		
		this.partie=partie;
		
		/* la vue observe le modele partie*/
		partie.addObserver(this);
		
		/* Fenetre */
	    fenetre.setTitle("Bataille norvegienne");
	    fenetre.setSize(400, 400);
	    fenetre.setExtendedState(fenetre.MAXIMIZED_BOTH);
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    fenetre.setLocationRelativeTo(null);
	    
	    /* JSplitPane*/
	    JSplitPane splitJoueurEtCarteMilieu = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelInfoJoueurs, panelCarteMilieu);
	    JSplitPane splitCarte = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelCarteVisibleEtInfo, panelCarteMain);
	    JSplitPane splitGlobal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitJoueurEtCarteMilieu, splitCarte);
	    splitCarte.setOneTouchExpandable(true);
	    
	    /* Panel du haut divise en deux partie */
	    panelInfoJoueurs.setLayout(new GridLayout(0, 2));
	    panelInfoJoueurs.add(panelJoueurs);    
	    	// gauche = detail sur les joueurs IA
	    panelJoueurs.setLayout(new GridLayout(partie.getNbJoueurs()-1,0));
	    for (int i=1; i<partie.getLesJoueurs().size(); i++){
	    	this.listeLabelJoueur.add(new Label("JOUEUR "+(i+1)+" :           "+ partie.getLesJoueurs().get(i).getMainJoueur().size() +
	    			" carte(s) en main       " +partie.getLesJoueurs().get(i).getCarteCachee().size() +" carte(s) cachee(s)       "
	    					+ "cartes visibles : "+partie.getLesJoueurs().get(i).getCarteVisible().get(0).getNom()+", "
	    					+partie.getLesJoueurs().get(i).getCarteVisible().get(1).getNom()+ ", "
	    					+ partie.getLesJoueurs().get(i).getCarteVisible().get(2).getNom(),0));
	    					//nombre de cartes en main + liste des cartes visibles + nombre de cartes cachees
	    	panelJoueurs.add(this.listeLabelJoueur.get(this.listeLabelJoueur.size() - 1));
	    }  
	    	// droite = tour actuel
	    labelTour = new Label("TOUR D'ECHANGE DE CARTE ", 1);
	    panelInfoJoueurs.add(labelTour);
	    
	    /* Panel du milieu - haut */
	    panelCarteMilieu.setLayout(new GridLayout(0, 1));
	    this.labelCarteMilieu = new Label ("Carte du paquet du millieu :  pas de carte",1);
	    panelCarteMilieu.add(labelCarteMilieu);
	    
	    /* Panel du milieu - bas divise en 3 parties */	
	    panelCarteVisibleEtInfo.setLayout(new GridLayout(0, 3));
	    	// gauche : nombre de cartes dans la pioche 
	    labelNbCartesPioche=new Label("Nombre de cartes dans la pioche : "+partie.getPioche().getCartesPioche().size(),1);
	    panelCarteVisibleEtInfo.add(labelNbCartesPioche);	    
	    	// milieu : carte visible
	    panelCarteVisibleEtInfo.add(panelCarteVisibleGlobal);
	    panelCarteVisibleGlobal.setLayout(new GridLayout(2, 0));
	    panelCarteVisibleGlobal.add(new Label("cartes Visibles",1));
	    panelCarteVisibleGlobal.add(panelCarteVisible);
	    Iterator<Carte> it = partie.getLesJoueurs().get(0).getCarteVisible().iterator();
	    while (it.hasNext()){
	    	Carte carte = it.next();
	    	this.listeBoutonCarteVisible.add(new BoutonCarte(carte));
	    	panelCarteVisible.add(this.listeBoutonCarteVisible.get(this.listeBoutonCarteVisible.size() - 1));
	    	this.listeBoutonCarteVisible.get(this.listeBoutonCarteVisible.size() - 1).addActionListener(new BoutonCarteVisibleListener());
	    } 	    
	    	//droite : nombre de cartes cachÈes
	    labelCarteCachee=new Label("Nombre Cartes Cachees :      "+partie.getLesJoueurs().get(0).getCarteCachee().size(),1);
	    panelCarteVisibleEtInfo.add(labelCarteCachee);
	    
	    /* Panel du bas*/
	    panelCarteMain.setLayout(new BorderLayout());
	    panelCarteMain.add(new Label("Main du JOUEUR 1 (vous)",1), BorderLayout.NORTH );
	    panelCarteMain.add(panelMain, BorderLayout.CENTER);
	    Iterator<Carte> it2 = partie.getLesJoueurs().get(0).getMainJoueur().iterator();
	    while (it2.hasNext()){
	    	Carte carte = it2.next();
	    	this.listeBoutonCarteMain.add(new BoutonCarte(carte));
	    	panelMain.add(this.listeBoutonCarteMain.get(this.listeBoutonCarteMain.size() - 1));
	    	this.listeBoutonCarteMain.get(this.listeBoutonCarteMain.size() - 1).addActionListener(new BoutonCarteMainListener());
	    }  

	    /* Mise en place et affichage de la fenetre*/
	    fenetre.getContentPane().add(splitGlobal, BorderLayout.CENTER); 
	    fenetre.setVisible(true);
	}
	
	
/* ------------------------------------------------------------------------------------------------
* Classes modelisant les actions des boutons reprÈsentant les cartes (Controleur)
* ------------------------------------------------------------------------------------------------*/
	/**
	* Cette classe représente un des trois controleur du modele MVC mis en place. Il rend les cartes visibles devant le joueur cliquables. 
	* Un clique sur une de ces cartes va permettre d'enregistrer l'indice de cette carte (sa position) et d'envoyer cette indice vers le coeur de l'application 
	* pour qu'une permutation soit effectuer.
	* @author Marc Louvion et Arthur Guedon
	*/
	class BoutonCarteVisibleListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int indiceCarte;
			indiceCarte=listeBoutonCarteVisible.indexOf(e.getSource());
			((JoueurReel)partie.getLesJoueurs().get(0)).setIndiceCarteInteface(indiceCarte);
			((JoueurReel)partie.getLesJoueurs().get(0)).setCarteInterfaceChoisi(true);
		}   
	}
	
	/**
	* Cette classe représente un deuxieme controleur du modele MVC mis en place. Il rend les cartes jouable de la main du Joueur devant le joueur cliquables. 
	* Un clique sur une de ces cartes va permettre d'enregistrer l'indice de cette carte (sa position) et d'envoyer cette indice vers le coeur de l'application 
	* pour que cette carte soit posee sur le paquet du milieu.
	* @author Marc Louvion et Arthur Guedon
	*/
	class BoutonCarteMainListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {		    	
			int indiceCarte;
			indiceCarte=listeBoutonCarteMain.indexOf(e.getSource());
			((JoueurReel)partie.getLesJoueurs().get(0)).setIndiceCarteInteface(indiceCarte);
			((JoueurReel)partie.getLesJoueurs().get(0)).setCarteInterfaceChoisi(true);
		}
	}
	
	/**
	* Cette classe représente le dernier controleur du modele MVC mis en place. Il rend les cartes non jouables de la main du joueur cliquables.
	* Chaque clic sur une de ces carte va faire apparaitre un message d'erreur indiquant au joueur que sa carte n'est pas jouable. 
	* @author Marc Louvion et Arthur Guedon
	*/
	class BoutonCarteMainNonJouableListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Carte non jouable", "carte non jouable", JOptionPane.INFORMATION_MESSAGE);		    
		}
	}
	 
	 
/* ------------------------------------------------------------------------------------------------
* Methode 
* ------------------------------------------------------------------------------------------------*/

	/**
	* Cette methode permet mettre à jour les different composant graphique en fonction des valeurs
	* de certaines variables du coueur de l'application et cela suite a l'appelle de la methode notifyObserver dans le coeur d'application
	* 
	* @param o
	*/
	public void update(Object o) {
		/* Mise a jour des informations sur les joueur virtuels */
		for (int i=1; i<partie.getLesJoueurs().size(); i++){
			if(partie.getLesJoueurs().get(i).getCarteVisible().size()==0){
				this.listeLabelJoueur.get(i-1).setText("JOUEUR "+(i+1)+" :           "+ partie.getLesJoueurs().get(i).getMainJoueur().size() +
						" carte(s) en main       " +partie.getLesJoueurs().get(i).getCarteCachee().size() +" carte(s) cachee(s)       "
						+ "cartes visibles : vide ");
			}else{
				this.listeLabelJoueur.get(i-1).setText("JOUEUR "+(i+1)+" :           "+ partie.getLesJoueurs().get(i).getMainJoueur().size() +
						" carte(s) en main       " +partie.getLesJoueurs().get(i).getCarteCachee().size() +" carte(s) cachee(s)       "
						+ "cartes visibles : "+partie.getLesJoueurs().get(i).getCarteVisible().get(0).getNom()+", "
						+partie.getLesJoueurs().get(i).getCarteVisible().get(1).getNom()+ ", "
						+ partie.getLesJoueurs().get(i).getCarteVisible().get(2).getNom());
			}
		}

		/* Mise a jour du Label informant le tour actuel */		
		labelTour.setText("TOUR "+partie.getNbTour()+" du JOUEUR "+(partie.getTour()+1));

		/* Mise a jour du Label indiquant la carte prÈsente sur la paquet du milieu */	
		if (partie.getPaquetMilieu().getCartesMilieu().size()==0){
			labelCarteMilieu.setText("nombre de carte au milieu : "+ partie.getPaquetMilieu().getCartesMilieu().size() +"      |      Carte sur le paquet du millieu :  pas de carte"
					+ "      |      Derniere carte posee : "+partie.getPaquetMilieu().getDerniereCartePosee());
		}else{
			labelCarteMilieu.setText("nombre de carte au milieu : "+ partie.getPaquetMilieu().getCartesMilieu().size() +"      |      Carte sur le paquet du millieu :  "+partie.getPaquetMilieu().getCartesMilieu().getFirst().getNom()+
					"      |      Derniere carte posee : "+partie.getPaquetMilieu().getDerniereCartePosee());
		}

		/* Mise a jour du Label indiquant le nombre de cartes dans la pioche */
		labelNbCartesPioche.setText("Nombre de cartes dans la pioche : "+partie.getPioche().getCartesPioche().size());
		
		/* Mise a jour des boutons reprÈsentant les cartes visibles */
		panelCarteVisible.removeAll();
		panelCarteVisible.repaint();
		this.listeBoutonCarteVisible.clear(); 
		Iterator<Carte> it = partie.getLesJoueurs().get(0).getCarteVisible().iterator();
		while (it.hasNext()){
			Carte carte = it.next();
			this.listeBoutonCarteVisible.add(new BoutonCarte(carte));
			panelCarteVisible.add(this.listeBoutonCarteVisible.get(this.listeBoutonCarteVisible.size() - 1));
			if(partie.getNbTour()==1){
				this.listeBoutonCarteVisible.get(this.listeBoutonCarteVisible.size() - 1).addActionListener(new BoutonCarteVisibleListener());
			}
		}
		
		/* Mise a jour du Label indiquant le nombre de cartes cachees */
		labelCarteCachee.setText("Nombre Cartes Cachees :      "+partie.getLesJoueurs().get(0).getCarteCachee().size());
		
		/* Mise a jour des boutons reprÈsentant les cartes de la main */
		panelMain.removeAll();
		panelMain.repaint();
		this.listeBoutonCarteMain.clear();
		Iterator<Carte> it2 = partie.getLesJoueurs().get(0).getMainJoueur().iterator();
		while (it2.hasNext()){
			Carte carte = it2.next();
			this.listeBoutonCarteMain.add(new BoutonCarte(carte));
			panelMain.add(this.listeBoutonCarteMain.get(this.listeBoutonCarteMain.size()- 1));
			if(partie.getNbTour()==1){
				this.listeBoutonCarteMain.get(this.listeBoutonCarteMain.size() - 1).addActionListener(new BoutonCarteMainListener());
			}else{
				if (carte.isJouable()==true){
					this.listeBoutonCarteMain.get(this.listeBoutonCarteMain.size() - 1).addActionListener(new BoutonCarteMainListener());
				}else{
					this.listeBoutonCarteMain.get(this.listeBoutonCarteMain.size() - 1).addActionListener(new BoutonCarteMainNonJouableListener());	
				}
			}
		}
		
		/* reaffiche la fenetre*/
		fenetre.setVisible(true);
	}
	
}
