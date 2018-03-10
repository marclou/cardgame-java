package fr.utt.lo02.projet.vue;

/**
 * Cette classe va permettre a un objet qui en hérite de le rendre observable. Dans le cadre de notre projet, c'est l'objet partie
 * qui va être observable, et qui va être observé par notre inferface graphique (VueApplication). La mise a jour de l'interface va se faire 
 * grâce a la methode setChanged et update.
 * La classe observable est composée d'une tableau d'observeurs, ainsi qu'un entier qui correspond au nombre d'observeurs. De plus, 
 * le booleen hasChanged permettre de savoir quand l'objet a été modifié pour mettre a jour l'interface graphique.
 * 
 * @author Arthur Guedon et Marc Louvion
 *
 */
public class Observable {
	
	public final static int MAX_OBSERVERS = 10;
	
	
	
/* ------------------------------------------------------------------------------------------------
* Attributs
* ------------------------------------------------------------------------------------------------*/
	private Observer[] observers;
	private int numberOfObservers;
	protected boolean hasChanged;
	
	
	
/* ------------------------------------------------------------------------------------------------
* Constructeur
* ------------------------------------------------------------------------------------------------*/
	/**
	 * C'est la constructeur de la classe Observable. On lui ajoute dans son tableau des observeurs.
	 */
	public Observable(){
		observers = new Observer [Observable.MAX_OBSERVERS];
		this.numberOfObservers=0;
		this.hasChanged=false;
	}
	
	
	
/* ------------------------------------------------------------------------------------------------
* Methodes
* ------------------------------------------------------------------------------------------------*/
	
	/**
	 * 
	 * Cette methode permet d'ajouter des observeurs aux tableau d'observeurs.
	 * 
	 * @param o : c'est l'observeur que l'on souhaite ajouter
	 */
	public void addObserver (Observer o) {
		if (this.numberOfObservers < Observable.MAX_OBSERVERS){
			observers[this.numberOfObservers]=o;
			this.numberOfObservers++;
		}else{
			System.out.println("erreur nombre d'observer max atteint");
		}
	}
	
	
	/**
	 * Cette methode permet d'indiquer qu'un changement sur l'objet observable a été effectué.	
	 */
	public void setChanged(){
		this.hasChanged = true;
	}

	
	/**
	 * Cette methode permet de notifier les observeurs. Elle parcourt dont tous les observeurs et appelle chacun leur tour leur méthode update
	 * qui va ainsi les mettre a jour.
	 *  
	 * @param o : l'evenement qui interesse les observeurs.
	 */
	public void notifyObservers(Object o){
		if (this.hasChanged==true){
			for(int i=0 ; i< this.numberOfObservers; i++){
				observers[i].update(o);				
			}
			hasChanged=false;
		}
	}
	
}
